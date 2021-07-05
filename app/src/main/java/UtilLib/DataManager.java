package UtilLib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.SleeplessStudios.modernpocket.Objectives;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.ByteArrayOutputStream;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ObjectLib.Collectible;
import ObjectLib.ItemCollection;
import ObjectLib.Objective;
import ObjectLib.Task;

import static android.util.Base64.URL_SAFE;

public class DataManager {

    // Singleton DataManager
    private static final DataManager Data = new DataManager();
    public static DataManager getInstance() {return Data;}

    private String TAG = "DataManger";

    private DatabaseReference collectionDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Collections");
    private DatabaseReference itemDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Items");
    private DatabaseReference taskDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Tasks");
    private DatabaseReference objectiveDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Objectives");
    //private StorageReference storageRef = FirebaseStorage.getInstance().getReference("Collection Images");

    List<Task> tasks = new ArrayList<Task>();
    List<ItemCollection> collections = new ArrayList<ItemCollection>();
    List<Collectible> items = new ArrayList<Collectible>();
    List<Objective> objectives = new ArrayList<Objective>();

    public DatabaseReference getCollectionDatabase(){
        return collectionDatabase;
    }
    public DatabaseReference getTaskDatabase(){
        return taskDatabase;
    }
    public DataManager(){
        collectionDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (collections!=null) {
                    collections.clear();
                }
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    ItemCollection coll = snap.getValue(ItemCollection.class);
                    coll.setId(snap.getKey());
                    //System.out.println( coll.toString() );
                    if (coll == null){
                        System.out.println( "error reading object collection");
                    }
                    else collections.add(coll);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        itemDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (items!=null) {
                    items.clear();
                }
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    Collectible item = snap.getValue(Collectible.class);
                    item.setId(snap.getKey());
                    try{
                        item.setFavourite(snap.child("isFavorite").getValue(boolean.class));
                    }
                    catch(Exception e){
                        item.setFavourite(false);
                    }
                    if (item == null){
                        System.out.println( "error reading object collection");
                    }
                    else items.add(item);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        taskDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (tasks!=null) {
                    tasks.clear();
                }
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    Task task = snap.getValue(Task.class);
                    tasks.add(task);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



    //Active Objects//
    private String activeCollection;
    private String activeItem;
    private String activeTask;
    private String activeObjective;

    public ItemCollection getActiveCollection() {
        for (ItemCollection coll: collections) {
            if (coll.getId().equals(activeCollection)){
                if (coll.getCollectibles()!=null) {
                    coll.getCollectibles().clear();
                }
                for (Collectible item:items) {
                    if (item.getCollectionId().equals(coll.getId())){
                        //System.out.println(item.toString());
                        coll.getCollectibles().add(item);
                    }
                }
                return coll;
            }
        }
        return null;
    }
    public Collectible getActiveItem() {
        for (Collectible item: items) {
            if (item.getId().equals(activeItem)){
                return item;
            }
        }
        return null;
    }
    public Task getActiveTask(){
        for (Task task: tasks) {
            if (task.getId().equals(activeTask)){
                if (task.getObjectives()!=null) {
                    task.getObjectives().clear();
                }
                for (Objective objective:objectives) {
                    if (objective.getTaskId().equals(task.getId())){
                        //System.out.println(item.toString());
                        task.getObjectives().add(objective);
                    }
                }
                return task;
            }
        }
        return null;
    }

    public void setActiveCollection(ItemCollection ac){

        if (ac != null){
            List<Collectible> in = new ArrayList<Collectible>();
            //System.out.println("Adding active Collections Items for item: " +ac.getId() );
            for (Collectible item:items) {
                if (item.getCollectionId().equals(ac.getId())){
                    //System.out.println(item.toString());
                    in.add(item);
                }
            }
            ac.setCollectibles(in);
            for (ItemCollection coll: collections) {
                if (coll.getId().equals(ac.getId())){
                    //System.out.println(item.toString());
                    activeCollection = coll.getId();
                }
            }
            getActiveCollection().setCollectibles(in);
        }
        else activeCollection = null;
        /*if (activeCollection!=null) {
            System.out.println("Checking active collection for items");
            for (Collectible item : activeCollection.getCollectibles()) {
                //System.out.println(item.toString());
            }
        }*/
    }
    public void setActiveItem(Collectible inItem){
        if (inItem != null){
            for (Collectible item:items) {
                if (item.getId().equals(inItem.getId())){
                    //System.out.println(item.toString());
                    activeItem = item.getId();
                }
            }
        }
        else activeItem = null;
    }
    public void setActiveTask(Task at){

        if (at != null){
            List<Objective> in = new ArrayList<Objective>();
            //System.out.println("Adding active Collections Items for item: " +ac.getId() );
            for (Objective obj: objectives) {
                if (obj.getId().equals(at.getId())){
                    //System.out.println(item.toString());
                    in.add(obj);
                }
            }
            at.setObjectives(in);
            for (Task task: tasks) {
                if (task.getId().equals(at.getId())){
                    //System.out.println(item.toString());
                    activeTask = task.getId();
                }
            }
            getActiveTask().setObjectives(in);
        }
        else activeTask = null;
        /*if (activeCollection!=null) {
            System.out.println("Checking active collection for items");
            for (Collectible item : activeCollection.getCollectibles()) {
                //System.out.println(item.toString());
            }
        }*/
    }

    public void SaveData(){
        //TODO Save Data
    }
    public void LoadData(){
        // TODO Load Data
    }

    public void AddTask(Task task){
        tasks.add(task);
    }
    public void RemoveTask(Task task){
        tasks.remove(task);
    }

    public void AddOrUpdateCollection(ItemCollection collection){
        if (activeCollection == null){
            collection.setId(collectionDatabase.push().getKey());
            collectionDatabase.child(collection.getId()).child("id").setValue(collection.getId());
            collectionDatabase.child(collection.getId()).child("collectionName").setValue(collection.getCollectionName());
            collectionDatabase.child(collection.getId()).child("description").setValue(collection.getDescription());
            collectionDatabase.child(collection.getId()).child("image").setValue(collection.getImage());
            collectionDatabase.child(collection.getId()).child("collectibles").setValue(collection.getCollectibles());
        }
        else{
            collectionDatabase.child(getActiveCollection().getId()).child("id").setValue(collection.getId());
            collectionDatabase.child(getActiveCollection().getId()).child("collectionName").setValue(collection.getCollectionName());
            collectionDatabase.child(getActiveCollection().getId()).child("description").setValue(collection.getDescription());
            collectionDatabase.child(getActiveCollection().getId()).child("image").setValue(collection.getImage());
            //collectionDatabase.child(getActiveCollection().getId()).child("collectibles").setValue(collection.getCollectibles());

        }

    }
    public void AddOrUpdateItem(Collectible item){
        if (activeItem == null) {
            item.setId(itemDatabase.push().getKey());
            itemDatabase.child(item.getId()).child("id").setValue(item.getId());
            itemDatabase.child(item.getId()).child("collectionId").setValue(getActiveCollection().getId());
            itemDatabase.child(item.getId()).child("name").setValue(item.getName());
            itemDatabase.child(item.getId()).child("description").setValue(item.getDescription());
            itemDatabase.child(item.getId()).child("image").setValue(item.getImage());
            itemDatabase.child(item.getId()).child("isFavorite").setValue(item.isFavourite());

            itemDatabase.child(item.getId()).child("acquisitionDate").setValue(item.getAcquisitionDate());
            itemDatabase.child(item.getId()).child("acquisitionLoc").setValue(item.getAcquisitionLoc());

            itemDatabase.child(item.getId()).child("isLent").setValue(item.isLent());
            itemDatabase.child(item.getId()).child("borrowedTo").setValue(item.getBorrowedTo());
            itemDatabase.child(item.getId()).child("expectedReturn").setValue(item.getExpectedReturn());
        }
        else{
            itemDatabase.child(getActiveItem().getId()).child("id").setValue(getActiveItem().getId());
            itemDatabase.child(getActiveItem().getId()).child("collectionId").setValue(getActiveCollection().getId());
            itemDatabase.child(getActiveItem().getId()).child("name").setValue(item.getName());
            itemDatabase.child(getActiveItem().getId()).child("description").setValue(item.getDescription());
            itemDatabase.child(getActiveItem().getId()).child("image").setValue(item.getImage());
            itemDatabase.child(getActiveItem().getId()).child("isFavorite").setValue(getActiveItem().isFavourite());

            itemDatabase.child(getActiveItem().getId()).child("acquisitionDate").setValue(item.getAcquisitionDate());
            itemDatabase.child(getActiveItem().getId()).child("acquisitionLoc").setValue(item.getAcquisitionLoc());

            itemDatabase.child(getActiveItem().getId()).child("isLent").setValue(item.isLent());
            itemDatabase.child(getActiveItem().getId()).child("borrowedTo").setValue(item.getBorrowedTo());
            itemDatabase.child(getActiveItem().getId()).child("expectedReturn").setValue(item.getExpectedReturn());
        }

    }

    public void AddOrUpdateTask(Task task) {
        if (activeTask == null) {
            task.setId(taskDatabase.push().getKey());
            taskDatabase.child(task.getId()).child("id").setValue(task.getId());
            taskDatabase.child(task.getId()).child("taskName").setValue(task.getTaskName());
            taskDatabase.child(task.getId()).child("date").setValue(task.getDate());
        } else {
            taskDatabase.child(getActiveTask().getId()).child("id").setValue(task.getId());
            taskDatabase.child(getActiveTask().getId()).child("taskName").setValue(task.getTaskName());
            taskDatabase.child(getActiveTask().getId()).child("date").setValue(task.getDate());
            //collectionDatabase.child(getActiveCollection().getId()).child("collectibles").setValue(collection.getCollectibles());
        }
    }

    public void RemoveCollection(ItemCollection task){
        collections.remove(task);
    }

    public void RefreshActiveCollection(){
        getActiveCollection();
    }

    public void initData(){
        collectionDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Collections");
        itemDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Items");
        taskDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Tasks");
        objectiveDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Objectives");
    }

    public static String getBitmapAsBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), URL_SAFE) ;
    }
    public static Bitmap getBitmapFromByteArray(byte[] bArray){
        if (bArray != null){
            return BitmapFactory.decodeByteArray(bArray, 0 ,bArray.length);
        }
        return null;
    }
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static Date getDateFromString(String dateIn){

        Date date = new Date();
        try{
            date = dateFormat.parse(dateIn);
        } catch (ParseException e){
            Log.e("DataBase", "getDateFromString: Failed, invalid format ", e );
            return null;
        }
        return date;
    }
    public static boolean validateDateFromString(String dateIn){

        Date date = new Date();
        try{
            date = dateFormat.parse(dateIn);
            return true;
        } catch (ParseException e){
            Log.e("DataBase", "getDateFromString: Failed, invalid format ", e );
            return false;
        }

    }
    public List<Collectible> getFavourites()
        {
            List<Collectible> out = new ArrayList<Collectible>();
            for (Collectible item : items)
            {
                if (item.isFavourite())
                {
                    out.add(item);
                }
            }
            return out;
        }
}
