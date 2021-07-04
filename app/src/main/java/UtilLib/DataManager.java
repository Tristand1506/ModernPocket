package UtilLib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.ByteArrayOutputStream;
import android.util.Base64;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import ObjectLib.Collectible;
import ObjectLib.ItemCollection;
import ObjectLib.Task;

import static android.util.Base64.URL_SAFE;

public class DataManager {

    // Singleton DataManager
    private static final DataManager Data = new DataManager();
    public static DataManager getInstance() {return Data;}

    private String TAG = "DataManger";

    private DatabaseReference collectionDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Collections");
    private DatabaseReference taskDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Tasks");
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference("Collection Images");

    List<Task> tasks = new ArrayList<Task>();
    List<ItemCollection> collections = new ArrayList<ItemCollection>();

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
                    System.out.println( coll.toString() );
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
    private ItemCollection activeCollection;
    private Collectible activeItem;

    public ItemCollection getActiveCollection() {
        return activeCollection;
    }
    public Collectible getActiveItem() {
        return activeItem;
    }
    public void setActiveCollection(ItemCollection ac){
        activeCollection = ac;
    }
    public void saveActiveCollection(ItemCollection collectionIn){
        collections.set(collections.indexOf(activeCollection), collectionIn);
    }
    public void setActiveItem(Collectible item){
        activeItem = item;
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
            collectionDatabase.child(collection.getId()).child("collectionName").setValue(collection.getCollectionName());
            collectionDatabase.child(collection.getId()).child("description").setValue(collection.getDescription());
            collectionDatabase.child(collection.getId()).child("image").setValue(collection.getImage());
            collectionDatabase.child(collection.getId()).child("collectibles").setValue(collection.getCollectibles());
        }
        else{
            collectionDatabase.child(activeCollection.getId()).setValue(collection);
        }

    }
    public void AddOrUpdateItem(Collectible item, Context context){
        if (activeItem == null) {
            collectionDatabase.child(activeCollection.getId()).child("collectables").setValue(item);
            //SQLiteDBHelper.getDataBase(context).addItem(item, activeCollection);
        }
        else{
            item.setCollectionID(activeItem.getCollectionID());
            //SQLiteDBHelper.getDataBase(context).updateItem(activeItem.getID(),item);
        }

    }
    public void RemoveCollection(ItemCollection collection){
        collections.remove(collection);
    }

    public void RefreshCollection(Context context){
        /*collections =  SQLiteDBHelper.getDataBase(context).loadCollections(LoginManager.getInstance().getActiveUser());
        if (activeCollection != null){
            activeCollection = SQLiteDBHelper.getDataBase(context).findCollectionByID(activeCollection.getID());
        }*/
    }

    public void initData(){
        collectionDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Collections");
        taskDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Tasks");
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



}
