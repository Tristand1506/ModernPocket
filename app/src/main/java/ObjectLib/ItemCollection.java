package ObjectLib;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.List;

public class ItemCollection {
    private Integer _id;
    private Integer _accountID;
    private String collectionName;
    private String description;
    public Bitmap image;
    public List<Collectible> collectibles;

    public ItemCollection(){}

    public ItemCollection(String collectionName, String description, Bitmap image ) {
        setCollectionName(collectionName);
        setDescription(description);
        this.image = image;
    }

    public ItemCollection(Integer _id, String collectionName, String description, Integer _accountID, Bitmap image) {
        setID(_id);
        set_accountID(_accountID);
        setCollectionName(collectionName);
        setDescription(description);
        this.image = image;
        //this.collectibles = collectibles;
    }

    public int getID(){
        return _id;
    }
    public void setID(int id){
        if (_id == null){
            _id = id;
        }
    }

    public int getAccountID(){
        return _accountID;
    }
    public void set_accountID(int id){
        if (_accountID == null){
            _accountID = id;
        }
    }

    public String getCollectionName(){
        return collectionName;
    }
    public void setCollectionName(String name){
        if (!name.trim().isEmpty()){
            collectionName = name;
        }
    }

    public String getDescription(){
        return  description;
    }
    public void setDescription(String desc){
        description = desc;
    }

    public float getCompleation(){
    float percCompleation;
    int collected = 0;
        for (Collectible item : collectibles) {
            if (item.isOwned){
                collected++;
            }
        }
        if (collectibles.size() > 0) {
            percCompleation = (float) (collected / collectibles.size());
        }
        else{percCompleation = 0;}
        return percCompleation;
    }

    public int getCompleted() {
        return 5;
    }

    //TODO
    // image reference

}
