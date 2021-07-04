package UtilLib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import ObjectLib.Collectible;
import ObjectLib.ItemCollection;
import ObjectLib.UserAcount;

public class SQLiteDBHelper  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pocket_DB";

    //account DB
    public static final String ACCOUNT_TABLE_NAME = "User_account";
    public static final String ACCOUNT_COLUMN_ID = "_id";
    public static final String ACCOUNT_COLUMN_USERNAME = "username";
    public static final String ACCOUNT_COLUMN_EMAIL = "email";
    public static final String ACCOUNT_COLUMN_PASSWORD = "password";

    //collectible DB
    public static final String COLLECTION_TABLE_NAME = "collections";
    public static final String COLLECTION_COLUMN_ID = "_id";
    public static final String COLLECTION_COLUMN_NAME = "name";
    public static final String COLLECTION_COLUMN_DESCRIPTION = "description";
    public static final String COLLECTION_COLUMN_IMAGE = "image";
    public static final String COLLECTION_COLUMN_ACCOUNT_ID = "account_id";

    //Item DB
    public static final String ITEM_TABLE_NAME = "items";
    public static final String ITEM_COLUMN_ID = "_id";
    public static final String ITEM_COLUMN_NAME = "name";
    public static final String ITEM_COLUMN_TYPE = "type";
    public static final String ITEM_COLUMN_DESCRIPTION = "description";
    public static final String ITEM_COLUMN_ACQUISITION_DATE = "date";
    public static final String ITEM_COLUMN_LOCATION = "location";
    public static final String ITEM_COLUMN_IMAGE = "image";
    public static final String ITEM_COLUMN_IS_FAVORITE = "favorite";
    public static final String ITEM_COLUMN_IS_OWNED = "owned";
    public static final String ITEM_COLUMN_COLLECTION_ID = "collection_id";

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //initialise singleton of DB
    private static SQLiteDBHelper sInstance;

    public static synchronized SQLiteDBHelper getDataBase(Context context) {
        if (sInstance == null) {
            sInstance = new SQLiteDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateAccountDB(db);
        CreateCollectionDB(db);
        CreateItemDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COLLECTION_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private void CreateCollectionDB(SQLiteDatabase db){
        //Collection DB
        db.execSQL("CREATE TABLE " + COLLECTION_TABLE_NAME + " (" +
                COLLECTION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLLECTION_COLUMN_NAME + " TEXT, " +
                COLLECTION_COLUMN_DESCRIPTION + " TEXT, " +
                COLLECTION_COLUMN_ACCOUNT_ID + " INTEGER NOT NULL, " +
                COLLECTION_COLUMN_IMAGE + " BLOB," +
                " FOREIGN KEY ("+COLLECTION_COLUMN_ACCOUNT_ID+") REFERENCES "+ACCOUNT_TABLE_NAME+" ("+ACCOUNT_COLUMN_ID+"));");
        System.out.println("Collection DB Created");
    }
    private void CreateItemDB(SQLiteDatabase db){
        //ITEMs DB
        db.execSQL("CREATE TABLE " + ITEM_TABLE_NAME + " (" +
                ITEM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEM_COLUMN_NAME + " TEXT, "+
                ITEM_COLUMN_TYPE + " TEXT NOT NULL, " +
                ITEM_COLUMN_DESCRIPTION + " TEXT, " +
                ITEM_COLUMN_ACQUISITION_DATE+ " TEXT, " +
                ITEM_COLUMN_LOCATION+ " TEXT, " +
                ITEM_COLUMN_IMAGE + " BLOB," +
                ITEM_COLUMN_IS_FAVORITE + " INTEGER," +
                ITEM_COLUMN_IS_OWNED + " INTEGER," +
                ITEM_COLUMN_COLLECTION_ID + " INTEGER NOT NULL, " +
                " FOREIGN KEY ("+ITEM_COLUMN_COLLECTION_ID+") REFERENCES "+COLLECTION_TABLE_NAME+" ("+COLLECTION_COLUMN_ID+"));");
        System.out.println("Collection DB Created");
    }
    private void CreateAccountDB(SQLiteDatabase db){
        // Account DB
        db.execSQL("CREATE TABLE " + ACCOUNT_TABLE_NAME + " (" +
                ACCOUNT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACCOUNT_COLUMN_USERNAME + " TEXT, " +
                ACCOUNT_COLUMN_EMAIL + " TEXT, " +
                ACCOUNT_COLUMN_PASSWORD + " TEXT" + ")");
        System.out.println("Account DB Created");
    }

    //////////////////
    // Account Methods
    //////////////////
    public String loadAccounts() {
        String result = "";
        String query = " Select*FROM " + ACCOUNT_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            result += String.valueOf(result_0) + " " + result_1 + " " + result_2 + " " + result_3 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }
    public void addAccount(UserAcount account) {
        //String query = "INSERT INTO "+ACCOUNT_TABLE_NAME+" " +
        //        "("+ACCOUNT_COLUMN_USERNAME+", "+ACCOUNT_COLUMN_EMAIL+", "+ ACCOUNT_COLUMN_PASSWORD+") " +
        //        "VALUES (" + account.getUsername() + ", " + account.getEmail() + ", " +account.getPassword() + ")";
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(ACCOUNT_COLUMN_ID, null);
            values.put(ACCOUNT_COLUMN_USERNAME, account.getUsername());
            values.put(ACCOUNT_COLUMN_EMAIL, account.getEmail());
            //values.put(ACCOUNT_COLUMN_PASSWORD, account.getPassword());

            db.insertOrThrow(ACCOUNT_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.d("Error", "Error while trying to add acoount to database");
        }
        finally {
            db.endTransaction();
        }
    }
    public UserAcount findAccountUserName(String username) {
        String query = "Select * FROM " + ACCOUNT_TABLE_NAME + " WHERE " + ACCOUNT_COLUMN_USERNAME + " = " + "'" + username + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserAcount account = new UserAcount();
        if (cursor.moveToFirst()) {
            //cursor.moveToFirst();
            //account.setID(Integer.parseInt(cursor.getString(0)));
            account.setUsername(cursor.getString(1));
            account.setEmail(cursor.getString(2));
            //account.setPassword(cursor.getString(3));
            cursor.close();
        }
        else account = null;
        db.close();
        return account;
    }
    public UserAcount findAccountEmail(String email) {
        String query = "Select * FROM " + ACCOUNT_TABLE_NAME + " WHERE " + ACCOUNT_COLUMN_EMAIL + " = " + "'" + email + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserAcount account = new UserAcount();
        if (cursor.moveToFirst()) {
            //cursor.moveToFirst();
            //account.setID(Integer.parseInt(cursor.getString(0)));
            account.setUsername(cursor.getString(1));
            account.setEmail(cursor.getString(2));
            //account.setPassword(cursor.getString(3));
            cursor.close();
        }
        else account = null;
        db.close();
        return account;
    }
    // non
    public boolean deleteAccount(int ID) {
        boolean result = false;
        String query = "Select*FROM" + ACCOUNT_TABLE_NAME + "WHERE" + ACCOUNT_COLUMN_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserAcount account = new UserAcount();
        if (cursor.moveToFirst()) {
            //account.setID(Integer.parseInt(cursor.getString(0)));
            /*db.delete(ACCOUNT_TABLE_NAME, ACCOUNT_COLUMN_ID + "=?",
                    new String[] {
                String.valueOf(account.getID())
            });*/
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean updateAccount(int ID, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(ACCOUNT_COLUMN_ID, ID);
        args.put(ACCOUNT_COLUMN_USERNAME, name);
        return db.update(ACCOUNT_TABLE_NAME, args, ACCOUNT_COLUMN_ID + "=" + ID, null) > 0;
    }

    //////////////////////
    // Collection Methods
    //////////////////////
    /*public List<ItemCollection> loadCollections( UserAcount account) {

        String query = " Select * FROM " + COLLECTION_TABLE_NAME + " WHERE "+ COLLECTION_COLUMN_ACCOUNT_ID + " = " + "'" + account.getID() + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        List<ItemCollection> outList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int result_0 = Integer.parseInt(cursor.getString(0));
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            int result_3 = Integer.parseInt(cursor.getString(3));
            Bitmap result_4 = getBitmapFromByteArray(cursor.getBlob(4));
            ItemCollection out = new ItemCollection(result_0,result_1,result_2,result_3,result_4);
            out.collectibles = getItemsFromCollections(out);
            outList.add(out);
        }
        cursor.close();
        db.close();
        return outList;
    }*/
    /*public void addCollection(ItemCollection collection, UserAcount activeUser) {

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(ACCOUNT_COLUMN_ID, null);
            values.put(COLLECTION_COLUMN_NAME, collection.getCollectionName());
            values.put(COLLECTION_COLUMN_DESCRIPTION, collection.getDescription());
            values.put(COLLECTION_COLUMN_ACCOUNT_ID, activeUser.getID() );
            values.put(COLLECTION_COLUMN_IMAGE, getBitmapAsByteArray(collection.image));

            db.insertOrThrow(COLLECTION_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.d("Error", "Error while trying to add Collection to database with exception\n"+e);
        }
        finally {
            db.endTransaction();
        }
    }*/
    /*public ItemCollection findCollectionByName(String collectionName) {
        String query = "Select * FROM " + COLLECTION_TABLE_NAME + " WHERE " + COLLECTION_COLUMN_NAME + " = " + "'" + collectionName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ItemCollection collection = new ItemCollection();
        if (cursor.moveToFirst()) {
            //cursor.moveToFirst();
            collection.setID(Integer.parseInt(cursor.getString(0)));
            collection.setCollectionName(cursor.getString(1));
            collection.setDescription(cursor.getString(2));
            collection.set_accountID(Integer.parseInt(cursor.getString(3)));
            collection.image = getBitmapFromByteArray(cursor.getBlob(4));
            cursor.close();
        }
        else collection = null;
        db.close();
        return collection;
    }*/
    /*public ItemCollection findCollectionByID(int id) {
        String query = "Select * FROM " + COLLECTION_TABLE_NAME + " WHERE " + COLLECTION_COLUMN_ID + " = " + "'" + id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ItemCollection collection = new ItemCollection();
        if (cursor.moveToFirst()) {
            //cursor.moveToFirst();
            collection.setID(cursor.getString(0));
            collection.setCollectionName(cursor.getString(1));
            collection.setDescription(cursor.getString(2));
            collection.set_accountID(Integer.parseInt(cursor.getString(3)));
            collection.image = getBitmapFromByteArray(cursor.getBlob(4));
            cursor.close();
        }
        else collection = null;
        db.close();
        return collection;
    }*/
    /*public UserAcount findCollectionByItem(Collectible item) {
        String query = "Select * FROM " + COLLECTION_TABLE_NAME + " WHERE " + COLLECTION_COLUMN_ID + " = " + "'" + item.getID() + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserAcount account = new UserAcount();
        if (cursor.moveToFirst()) {
            //cursor.moveToFirst();
            account.setID(cursor.getString(0));
            account.setUsername(cursor.getString(1));
            account.setEmail(cursor.getString(2));
            account.setPassword(cursor.getString(3));
            cursor.close();
        }
        else account = null;
        db.close();
        return account;
    }*/
    /*public boolean deleteCollection(int ID) {
        boolean result = false;
        String query = "Select*FROM" + COLLECTION_TABLE_NAME + "WHERE" + COLLECTION_COLUMN_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ItemCollection collection = new ItemCollection();
        if (cursor.moveToFirst()) {
            collection.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(COLLECTION_TABLE_NAME, COLLECTION_COLUMN_ID + "=?",
                    new String[] {
                            String.valueOf(collection.getID())
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }*/
    public boolean updateCollection(int ID, ItemCollection collection) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        //args.put(COLLECTION_COLUMN_ID, ID);
        args.put(COLLECTION_COLUMN_NAME, collection.getCollectionName());
        args.put(COLLECTION_COLUMN_DESCRIPTION, collection.getDescription());
        //args.put(COLLECTION_COLUMN_IMAGE, getBitmapAsByteArray(collection.image));
        return db.update(COLLECTION_TABLE_NAME, args, COLLECTION_COLUMN_ID + "=" + ID, null) > 0;
    }

    // item methods
    /*public List<Collectible> getItemsFromCollections(ItemCollection collection) {
        String query = " Select * FROM " + ITEM_TABLE_NAME + " WHERE "+ ITEM_COLUMN_COLLECTION_ID+ " = " + "'" + collection.getID() + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        List<Collectible> outList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            int type  = Integer.parseInt(cursor.getString(2));
            String desc = cursor.getString(3);
            boolean owned = convertTOBool(Integer.parseInt( cursor.getString(8)));
            Date date;
            String loc;
            if (owned) {
                date = getDateFromString(cursor.getString(4));
                //Location loc = LocationFromLatLong(cursor.getString(5));
                loc = cursor.getString(5);
            }else{
                date = null;
                loc = "";
            }
            Bitmap image = getBitmapFromByteArray(cursor.getBlob(6));
            boolean favorite = convertTOBool(Integer.parseInt( cursor.getString(7)));
            int containingCollection =Integer.parseInt(cursor.getString(9));
            outList.add(new Collectible(id, name, type, desc, date, loc, image, favorite, owned, containingCollection) {
            });
        }
        cursor.close();
        db.close();
        return outList;
    }*/
    public void addItem(Collectible item, ItemCollection collection) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(ACCOUNT_COLUMN_ID, null);
            values.put(ITEM_COLUMN_NAME, item.getName());
            values.put(ITEM_COLUMN_TYPE, item.getItemType());
            values.put(ITEM_COLUMN_DESCRIPTION, item.getDescription());
            if(item.isOwned){
                values.put(ITEM_COLUMN_ACQUISITION_DATE, iso8601Format.format(item.getAcquisitionDate()));
                //values.put(ITEM_COLUMN_LOCATION, item.getAcquisitionLoc().getLatitude() + "-" +item.getAcquisitionLoc().getLatitude());
                values.put(ITEM_COLUMN_LOCATION, item.getAcquisitionLoc());
            }
            values.put(ITEM_COLUMN_IMAGE, getBitmapAsByteArray(item.image));
            values.put(ITEM_COLUMN_IS_FAVORITE, item.isFavourite);
            values.put(ITEM_COLUMN_IS_OWNED, item.isOwned);
            values.put(ITEM_COLUMN_COLLECTION_ID, collection.getId());

            db.insertOrThrow(ITEM_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.d("Error", "Error while trying to add Item to database with exception\n"+e);
        }
        finally {
            db.endTransaction();
        }
    }
    public boolean updateItem(int ID, Collectible item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        //args.put(COLLECTION_COLUMN_ID, ID);
        args.put(ITEM_COLUMN_NAME, item.getName());
        args.put(ITEM_COLUMN_TYPE, item.getItemType());
        args.put(ITEM_COLUMN_DESCRIPTION, item.getDescription());
        if(item.isOwned){
            args.put(ITEM_COLUMN_ACQUISITION_DATE, iso8601Format.format(item.getAcquisitionDate()));
            //args.put(ITEM_COLUMN_LOCATION, item.getAcquisitionLoc().getLatitude() + "-" +item.getAcquisitionLoc().getLatitude());
            args.put(ITEM_COLUMN_LOCATION, item.getAcquisitionLoc());
        }
        args.put(ITEM_COLUMN_IMAGE, getBitmapAsByteArray(item.image));
        args.put(ITEM_COLUMN_IS_FAVORITE, item.isFavourite);
        args.put(ITEM_COLUMN_IS_OWNED, item.isOwned);
        args.put(ITEM_COLUMN_COLLECTION_ID, item.getCollectionID());
        return db.update(ITEM_TABLE_NAME, args, ITEM_COLUMN_ID + "=" + ID, null) > 0;
    }




    // decode Util
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    public static Bitmap getBitmapFromByteArray(byte[] bArray){
        if (bArray != null){
            return BitmapFactory.decodeByteArray(bArray, 0 ,bArray.length);
        }
        return null;
    }

    static DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getDateFromString(String dateIn){
        Date date = new Date();
        try{
            date = iso8601Format.parse(dateIn);
        } catch (ParseException e){
            Log.e("DataBase", "getDateFromString: Failed, invalid format ", e );
        }
        return date;
    }




    public static Location LocationFromLatLong(String latLong){
        Location loc = new Location(LocationManager.GPS_PROVIDER);
        String[] latLongArr= latLong.split("-");
        loc.setLatitude(Double.parseDouble(latLongArr[0]));
        loc.setLongitude(Double.parseDouble(latLongArr[1]));
        return loc;
    }

    private static boolean convertTOBool(int in){
        if (in == 1){
            return true;
        }
        else{
            return false;
        }
    }

}
