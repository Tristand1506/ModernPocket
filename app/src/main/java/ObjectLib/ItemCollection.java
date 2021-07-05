package ObjectLib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import UtilLib.DataManager;

public class ItemCollection {
    String id;
    //Integer _accountID;
    String collectionName;
    String description;
    private Bitmap image;
    private List<Collectible> collectibles;

    public ItemCollection(){}

    public ItemCollection(String collectionName, String description, Bitmap image ) {
        setCollectionName(collectionName);
        setDescription(description);
        this.image = image;
    }

/*    public ItemCollection(String _id, String collectionName, String description, Integer _accountID, Bitmap image) {
        setID(_id);
        set_accountID(_accountID);
        setCollectionName(collectionName);
        setDescription(description);
        this.image = image;
        //this.collectibles = collectibles;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (this.id == null){
            this.id = id;
        }
        System.out.println("Failed to set new ID");
    }

    /*    public int getAccountID(){
        return _accountID;
    }
    public void set_accountID(int id){
        if (_accountID == null){
            _accountID = id;
        }
    }*/

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        if (!collectionName.trim().isEmpty()){
            this.collectionName = collectionName;
        }

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Collectible> getCollectibles() {
        if (collectibles==null) {
            collectibles = new ArrayList<Collectible>();
        }
        return collectibles;
    }

    public void setCollectibles(List<Collectible> collectibles) {
        this.collectibles = collectibles;
    }

    public int getCompletion(){
    float percCompletion;
    float collected = 0f;
    if (collectibles == null){
        return 0;
    }
        for (Collectible item : collectibles) {
            if (item.isOwned()){
                collected++;
            }
        }
        if (collectibles.size() > 0) {
            percCompletion = (float)(collected / (float)collectibles.size());
            System.out.println("Compleation: "+ collected + "/" + collectibles.size() + "\n at: " + percCompletion*100f +"%");
        }
        else{percCompletion = 0;}
        return (int)(percCompletion*100f);
    }

    public Bitmap getImageBitmap() {
        return image;
    }


    public String getImage() {
        if (image != null) {
            return DataManager.getBitmapAsBase64(image);
        }
        return null;
    }

    public void setImageBitmap(Bitmap image) {
        this.image = image;
    }
    public void setImage(String b64) {
        byte[] bArray = Base64.decode(b64,Base64.URL_SAFE);
        if (bArray != null){
            setImageBitmap( BitmapFactory.decodeByteArray(bArray, 0 ,bArray.length) );
        }
    }



    @Override
    public String toString() {
        return "ItemCollection{" +
                "id='" + id + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", description='" + description + '\'' +
                ", image=" + getImage() + '\''+
                ", collectibles=" + collectibles +
                '}';
    }
}
