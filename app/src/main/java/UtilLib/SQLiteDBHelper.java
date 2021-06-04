package UtilLib;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import androidx.annotation.Nullable;
import ObjectLib.UserAcount;

public class SQLiteDBHelper  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pocket_DB";
    public static final String ACCOUNT_TABLE_NAME = "User_account";
    public static final String ACCOUNT_COLUMN_ID = "_id";
    public static final String ACCOUNT_COLUMN_USERNAME = "username";
    public static final String ACCOUNT_COLUMN_EMAIL = "email";
    public static final String ACCOUNT_COLUMN_PASSWORD = "password";

    public SQLiteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ACCOUNT_TABLE_NAME + " (" +
                ACCOUNT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACCOUNT_COLUMN_USERNAME + " TEXT, " +
                ACCOUNT_COLUMN_EMAIL + " TEXT, " +
                ACCOUNT_COLUMN_PASSWORD + " TEXT" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

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
        String query = "INSERT INTO "+ACCOUNT_TABLE_NAME+" " +
                "("+ACCOUNT_COLUMN_USERNAME+", "+ACCOUNT_COLUMN_EMAIL+", "+ ACCOUNT_COLUMN_PASSWORD+") " +
                "VALUES (" + account.getUsername() + ", " + account.getEmail() + ", " +account.getPassword() + ")";
        //ContentValues values = new ContentValues();
        //values.put(ACCOUNT_COLUMN_ID, null);
        //values.put(ACCOUNT_COLUMN_USERNAME, account.getUsername());
        //values.put(ACCOUNT_COLUMN_EMAIL, account.getEmail());
        //values.put(ACCOUNT_COLUMN_PASSWORD, account.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        //db.insert(ACCOUNT_TABLE_NAME, null, values);
        db.close();
    }

    public UserAcount findAccount(String username) {
        String query = "Select * FROM " + ACCOUNT_TABLE_NAME + "WHERE" + ACCOUNT_COLUMN_USERNAME + " = " + "'" + username + "'";
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
                String.valueOf(account.getAccountID())
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

}
