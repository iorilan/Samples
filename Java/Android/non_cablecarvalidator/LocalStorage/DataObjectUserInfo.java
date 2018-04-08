package com.example.tdw.non_cablecarvalidator.LocalStorage;

/**
 * Created by T.DW on 4/7/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.provider.BaseColumns;
import android.util.Log;


public class DataObjectUserInfo extends SQLiteOpenHelper
{
    public static abstract class UserTableInfo implements BaseColumns {
        public static final String USER_NAME = "user_name";
        public static final String USER_PASS = "user_pass";
        public static final String TABLE_NAME = "USER_Table";
    }


    public String CREATE_QUERY =
                                "CREATE TABLE "+ UserTableInfo.TABLE_NAME+
                                        "("    + UserTableInfo.USER_NAME+" TEXT,"
                                               + UserTableInfo.USER_PASS+" TEXT);";

    public DataObjectUserInfo(Context context) {
        super(context, DbInfo.DATABASE_NAME, null, DbInfo.DATABASE_VERSION);
        Log.d("Database Operations", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(CREATE_QUERY);
        Log.d("Database Operations", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTableInfo.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void putInformation(DataObjectUserInfo dop, String name, String pass){
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserTableInfo.USER_NAME, name);
        cv.put(UserTableInfo.USER_PASS, pass);
        long k = SQ.insert(UserTableInfo.TABLE_NAME, null, cv);
        Log.d("Database Operations", "One Row Inserted");


    }

    public Cursor getInformation(DataObjectUserInfo dop){
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {UserTableInfo.USER_NAME, UserTableInfo.USER_PASS};
        Cursor CR = SQ.query(UserTableInfo.TABLE_NAME,columns,null,null,null,null,null);


        return CR;
    }
}