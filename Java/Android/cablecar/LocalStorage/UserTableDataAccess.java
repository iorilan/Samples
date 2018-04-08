package com.example.fuyan.test.LocalStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;


public class UserTableDataAccess extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public String CREATE_QUERY = "CREATE TABLE "+ DataTables.UserTable.TABLE_NAME+"("+ DataTables.UserTable.COLUMN_USER_NAME +" TEXT, "+ DataTables.UserTable.COLUMN_PASSWORD +" TEXT);";

    public UserTableDataAccess(Context context) {
        super(context, DbInfo.DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "User table Created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(CREATE_QUERY);
        Log.d("Database Operations", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(UserTableDataAccess dop, String userName, String password){
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataTables.UserTable.COLUMN_USER_NAME, userName);
        cv.put(DataTables.UserTable.COLUMN_PASSWORD, password);
        long k = SQ.insert(DataTables.UserTable.TABLE_NAME, null, cv);
        //Log.d("Database Operations", "One Row Inserted");

    }

    public Cursor getIterator(UserTableDataAccess dop){
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {DataTables.UserTable.COLUMN_USER_NAME, DataTables.UserTable.COLUMN_PASSWORD};
        Cursor iterator = SQ.query(DataTables.UserTable.TABLE_NAME,columns,null,null,null,null,null);
        return iterator;
    }
}
