package ObjectLib;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.Date;

public class Collectible {

    // generic constructor
/*
    public Collectible(int id, String name, int type, String description, Date date, Location loc, Bitmap img, boolean isFavourite, boolean isOwned, int coll) {
        this.name = name;
        this.description = description;
        this.isFavourite = isFavourite;
        this.isOwned = isOwned;
        setAcquisitionDate(acquisitionDate);
        setAcquisitionLoc(acquisitionLoc);
    }
*/
    public Collectible(int id, String name, int type, String description, Date date, String loc, Bitmap img, boolean isFavourite, boolean isOwned, int coll) {
        _id = id;
        _collectionId = coll;
        this.name = name;
        itemType = type;
        this.description = description;
        this.isFavourite = isFavourite;
        this.isOwned = isOwned;
        image = img;
        setAcquisitionDate(date);
        setAcquisitionLoc(loc);
    }

    public Collectible(String name, String description, Date date, String loc, Bitmap img, boolean isFavourite, boolean isOwned) {
        this.name = name;
        this.description = description;
        this.isFavourite = isFavourite;
        this.isOwned = isOwned;
        image = img;
        setAcquisitionDate(date);
        setAcquisitionLoc(loc);
    }
    public Collectible(String name, String description, Date date, String loc, Bitmap img) {
        this.name = name;
        this.description = description;
        image = img;
        setAcquisitionDate(date);
        setAcquisitionLoc(loc);
    }

    private Integer _id;
    private Integer _collectionId;
    int itemType;
    private String name;
    private String description;
    public Bitmap image;
    public boolean isFavourite;


    public boolean isOwned;
    private Date acquisitionDate;
    // private Location acquisitionLoc;
    private String acquisitionLoc;


    ///////////////
    //Gets and Sets
    ///////////////
    public int getID(){
        return _id;
    }
    public void setID(int id){
        if (_id == null){
            _id = id;
        }
    }
    public int getCollectionID(){
        return _collectionId;
    }
    public void setCollectionID(int id){
        if (_collectionId == null){
            _collectionId = id;
        }
    }



    public int getItemType() {
        return itemType;
    }

    public void setName(String name){
        if (!name.trim().isEmpty()){
            this.name = name;
        }
    }
    public String getName(){
        return name;
    }

    public String getDescription() {
        return description;
    }



    ///////////////////////
    // Owned Conditional Gets and Sets
    ///////////////////////
    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        if (!isOwned){
            this.acquisitionDate = null;
        }
        else {
            this.acquisitionDate = acquisitionDate;
        }
    }

    public String getAcquisitionLoc() {
        return acquisitionLoc;
    }

    /*public void setAcquisitionLoc(Location acquisitionLoc) {
        if (!isOwned){
            this.acquisitionLoc = null;
        }
        else {
            this.acquisitionLoc = acquisitionLoc;
        }
    }*/
    public void setAcquisitionLoc(String acquisitionLoc) {
        if (!isOwned){
            this.acquisitionLoc = null;
        }
        else {
            this.acquisitionLoc = acquisitionLoc;
        }
    }

    //loaner data
    private boolean isLent;
    private String borrowedTo;
    private Date expectedReturn;

    ///////////////////////
    // Loaner Gets and Sets
    ///////////////////////
    public boolean isLent() {
        return isLent;
    }

    public void setLent(boolean lent) {
        if (!isOwned){
            this.isLent = false;
        }
        else {
            this.isLent = lent;
        }
    }

    public String getBorrowedTo() {
        return borrowedTo;
    }

    public void setBorrowedTo(String borrowedTo) {
        if (!isOwned){
            this.borrowedTo = null;
        }
        else {
            this.borrowedTo = borrowedTo;
        }
    }

    public Date getExpectedReturn() {
        return expectedReturn;
    }

    public void setExpectedReturn(Date expectedReturn) {
        if (!isOwned){
            this.expectedReturn = null;
        }
        else {
            this.expectedReturn = expectedReturn;
        }
    }



}
