package ObjectLib.Collectibles;

import android.location.Location;

import java.util.Date;

import ObjectLib.Collectible;

public class Book extends Collectible {
    public Book(String name, String description, boolean isFavourite, boolean isOwned, Date acquisitionDate, Location acquisitionLoc, String author, String series, int pages)
    { super(name, description, isFavourite, isOwned, acquisitionDate, acquisitionLoc);

    this.author = author;
    this.series = series;
    this.pages = pages;
    }

    String author;
    String series;
    int pages;

}
