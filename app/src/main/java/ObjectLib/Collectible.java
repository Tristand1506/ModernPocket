package ObjectLib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import UtilLib.DataManager;

public class Collectible {


    // generic constructor
    public Collectible(){

    }
    public Collectible(String name, String description, String date, String loc, Bitmap img, String borrowed, String returnDate) {
        this.name = name;
        this.description = description;
        image = img;
        setAcquisitionDate(date);
        setAcquisitionLoc(loc);

        setBorrowedTo(borrowed);
        setExpectedReturn(returnDate);
    }

    private String id;
    private String collectionId;
    private String name;
    private String description;
    private Bitmap image;
    private boolean isFavourite;


    private boolean isOwned;
    private Date acquisitionDate;
    // private Location acquisitionLoc;
    private String acquisitionLoc;


    ///////////////
    //Gets and Sets
    ///////////////
    public String getId(){
        return id;
    }
    public void setId(String id){
        if (this.id == null){
            this.id = id;
        }
    }

    public String getCollectionId(){
        return collectionId;
    }
    public void setCollectionId(String id){
        if (collectionId == null){
            collectionId = id;
        }
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        if (!name.trim().isEmpty()){
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
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

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    ///////////////////////
    // Owned Conditional Gets and Sets
    ///////////////////////


    public Boolean isOwned() {
        if (getAcquisitionDate() != null || getAcquisitionLoc() != null) {

            return true;
        }
        else return  false;
    }

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String getAcquisitionDate() {
        if (acquisitionDate != null) {
            return dateFormat.format(acquisitionDate);
        }
        else return null;
    }
    public void setAcquisitionDate(String acquisitionDate) {
        if (!acquisitionDate.isEmpty()) {
            this.acquisitionDate = DataManager.getDateFromString(acquisitionDate);
        }
        else this.acquisitionDate = null;
    }
    public void setAcquisitionDateWithDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getAcquisitionLoc() {
        return acquisitionLoc;
    }
    public void setAcquisitionLoc(String acquisitionLoc) {
        if (acquisitionLoc != null){
            if (!acquisitionLoc.isEmpty())
            this.acquisitionLoc = acquisitionLoc;
        }
        else this.acquisitionLoc = null;
    }

    //loaner data
   // private boolean isLent;
    private String borrowedTo;
    private Date expectedReturn;

    ///////////////////////
    // Loaner Gets and Sets
    ///////////////////////
    public Boolean isLent() {
        if (borrowedTo != null || expectedReturn != null) {
            return true;
        }
        else return  false;
    }
/*    public void setLent(boolean isLent) {
        if (!isOwned){
            this.isLent = false;
        }
        else {
            this.isLent = isLent;
        }
    }*/

    public String getBorrowedTo() {
        return borrowedTo;
    }
    public void setBorrowedTo(String borrowedTo) {
        if (!isOwned() || borrowedTo.isEmpty()){
            this.borrowedTo = null;
        }
        else {
            this.borrowedTo = borrowedTo;
        }
    }

    public String getExpectedReturn() {
        if (expectedReturn==null){
            return null;
        }
        else return dateFormat.format(expectedReturn);
    }
    public void setExpectedReturn(String expectedReturn) {
        if (!isOwned()){
            this.expectedReturn = null;
            return;
        }
        if (!expectedReturn.isEmpty()) {
            this.expectedReturn = DataManager.getDateFromString(expectedReturn);
        }
        else this.expectedReturn = null;
    }
    public void setExpectedReturnWithDate(String expectedReturn) {
        if (!expectedReturn.isEmpty()) {
            this.expectedReturn = DataManager.getDateFromString(expectedReturn);
        }
        else this.expectedReturn = null;
    }

    @Override
    public String toString() {
        return "Collectible{" +"\n" +
                "_id=" + id + "\n" +
                ", _collectionId=" + collectionId +'\n' +
                ", name='" + name + '\n' +
                ", description='" + description + '\n' +
                ", image=" + image +'\n' +
                ", isFavourite=" + isFavourite +'\n' +
                ", isOwned=" + isOwned +'\n' +
                ", acquisitionDate=" + acquisitionDate +'\n' +
                ", acquisitionLoc='" + acquisitionLoc + '\n' +
                ", isLent=" + isLent() +'\n' +
                ", borrowedTo='" + borrowedTo + '\n' +
                ", expectedReturn=" + expectedReturn +'\n' +
                '}';
    }

}
