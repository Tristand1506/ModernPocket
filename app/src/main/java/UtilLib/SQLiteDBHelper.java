package UtilLib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
        // Account DB
        db.execSQL("CREATE TABLE " + ACCOUNT_TABLE_NAME + " (" +
                ACCOUNT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACCOUNT_COLUMN_USERNAME + " TEXT, " +
                ACCOUNT_COLUMN_EMAIL + " TEXT, " +
                ACCOUNT_COLUMN_PASSWORD + " TEXT" + ")");
        System.out.println("Acount DB Created");

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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE_NAME);
        onCreate(sqLiteDatabase);
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
            values.put(ACCOUNT_COLUMN_PASSWORD, account.getPassword());

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
            account.setID(Integer.parseInt(cursor.getString(0)));
            account.setUsername(cursor.getString(1));
            account.setEmail(cursor.getString(2));
            account.setPassword(cursor.getString(3));
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
            account.setID(Integer.parseInt(cursor.getString(0)));
            account.setUsername(cursor.getString(1));
            account.setEmail(cursor.getString(2));
            account.setPassword(cursor.getString(3));
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
            account.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(ACCOUNT_TABLE_NAME, ACCOUNT_COLUMN_ID + "=?",
                    new String[] {
                String.valueOf(account.getID())
            });
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
    public List<ItemCollection> loadCollections() {

        String query = " Select*FROM " + COLLECTION_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        List<ItemCollection> outList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int result_0 = Integer.parseInt(cursor.getString(0));
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            int result_3 = Integer.parseInt(cursor.getString(3));
            Bitmap result_4 = getBitmapFromByteArray(cursor.getBlob(4));
            outList.add(new ItemCollection(result_0,result_1,result_2,result_3,result_4));
        }
        cursor.close();
        db.close();
        return outList;
    }
    public void addCollection(ItemCollection collection, UserAcount activeUser) {
        //String query = "INSERT INTO "+ACCOUNT_TABLE_NAME+" " +
        //        "("+ACCOUNT_COLUMN_USERNAME+", "+ACCOUNT_COLUMN_EMAIL+", "+ ACCOUNT_COLUMN_PASSWORD+") " +
        //        "VALUES (" + account.getUsername() + ", " + account.getEmail() + ", " +account.getPassword() + ")";
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
            Log.d("Error", "Error while trying to add Collection to database");
        }
        finally {
            db.endTransaction();
        }
    }
    public ItemCollection findCollectionByName(String collectionName) {
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
    }
    // find by item
    /*public UserAcount findCollectionByItem(String email) {
        String query = "Select * FROM " + ACCOUNT_TABLE_NAME + " WHERE " + ACCOUNT_COLUMN_EMAIL + " = " + "'" + email + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserAcount account = new UserAcount();
        if (cursor.moveToFirst()) {
            //cursor.moveToFirst();
            account.setID(Integer.parseInt(cursor.getString(0)));
            account.setUsername(cursor.getString(1));
            account.setEmail(cursor.getString(2));
            account.setPassword(cursor.getString(3));
            cursor.close();
        }
        else account = null;
        db.close();
        return account;
    }*/
    public boolean deleteCollection(int ID) {
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
    }
    public boolean updateCollection(int ID, ItemCollection collection) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        //args.put(COLLECTION_COLUMN_ID, ID);
        args.put(COLLECTION_COLUMN_NAME, collection.getCollectionName());
        args.put(COLLECTION_COLUMN_DESCRIPTION, collection.getDescription());
        args.put(COLLECTION_COLUMN_IMAGE, getBitmapAsByteArray(collection.image));
        return db.update(COLLECTION_TABLE_NAME, args, COLLECTION_COLUMN_ID + "=" + ID, null) > 0;
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


}
