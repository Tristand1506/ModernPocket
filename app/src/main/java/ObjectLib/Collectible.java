package ObjectLib;

import android.location.Location;

import java.util.Date;

public abstract class Collectible {
    String name;
    String descrioption;
    boolean isFavourite;

    boolean isOwned;
    private Date acquisitionDate;
    private Location acquisitionLoc;

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

    public Location getAcquisitionLoc() {
        return acquisitionLoc;
    }

    public void setAcquisitionLoc(Location acquisitionLoc) {
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

    public boolean isLent() {
        return isLent;
    }

    ///////////////////////
    // Loaner Gets and Sets
    ///////////////////////
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
