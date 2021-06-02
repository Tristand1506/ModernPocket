package ObjectLib;

import java.util.List;

public class ItemCollection {
    String collectionName;
    String description;
    List<Collectible> collectibles;

    public float Compleation(){
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
