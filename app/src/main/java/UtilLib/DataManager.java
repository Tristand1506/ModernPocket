package UtilLib;

public  class DataManager {

    // SIngleton DataManager
    private static final DataManager holder = new DataManager();
    public static DataManager getInstance() {return holder;}
}
