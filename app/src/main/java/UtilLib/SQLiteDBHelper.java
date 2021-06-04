package UtilLib;

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

    public String loadHandler() {
        String result = "";
        String query = " Select*FROM " + ACCOUNT_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }
    public void addAccount(UserAcount account) {
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_COLUMN_ID, account.getAccountID());
        values.put(ACCOUNT_COLUMN_USERNAME, account.getUsername());
        values.put(ACCOUNT_COLUMN_EMAIL, account.getEmail());
        values.put(ACCOUNT_COLUMN_PASSWORD, account.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNT_TABLE_NAME, null, values);
        db.close();
    }
    public UserAcount findHandler(String username) {}
    public boolean deleteHandler(int ID) {}
    public boolean updateHandler(int ID, String name) {}

}
