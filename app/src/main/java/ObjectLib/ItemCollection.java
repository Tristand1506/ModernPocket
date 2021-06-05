package ObjectLib;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.List;

public class ItemCollection {
    private Integer _id;
    private String collectionName;
    private String description;
    public Bitmap image;
    public List<Collectible> collectibles;



    public int getID(){
        return _id;
    }
    public void setID(int id){
        if (_id == null){

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
        percCompleation = (float) (collectibles.size() / collected) ;
        return percCompleation;
    }

    //TODO
    // image reference

}
