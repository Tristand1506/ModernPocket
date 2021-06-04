package UtilLib;

import java.util.Collection;
import java.util.List;

import ObjectLib.Collectible;
import ObjectLib.ItemCollection;
import ObjectLib.Task;

public  class DataManager {

    // Singleton DataManager
    private static final DataManager Data = new DataManager();
    public static DataManager getInstance() {return Data;}

    List<Task> tasks;
    List<ItemCollection> collections;

    private ItemCollection activeCollection;
    public void setActiveCollection(ItemCollection ac){
        activeCollection = ac;
    }
    public void saveActiveCollection(ItemCollection collectionIn){
        collections.set(collections.indexOf(activeCollection), collectionIn);
    }


    private Collectible activeItem;
    public void setActiveItem(Collectible item){
        activeItem = item;
    }
    public void saveActiveItem(Collectible itemIn){
        ItemCollection in = activeCollection;
        in.collectibles.set(in.collectibles.indexOf(activeItem), itemIn);
        saveActiveCollection(in);
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

    public void AddCollection(ItemCollection collection){
        collections.add(collection);
    }
    public void RemoveCollection(ItemCollection collection){
        collections.remove(collection);
    }



}
