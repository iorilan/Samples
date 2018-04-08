package com.example.tdw.non_cablecarvalidator.LocalStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by lanliang on 25/8/16.
 */

public class DataObjectConfig extends SQLiteOpenHelper
{
    public static abstract class ConfigTableInfo implements BaseColumns {
        public static final String COLUMN_KEY = "JsonKey";
        public static final String COLUMN_JSON = "JsonString";
        public static final String TABLE_NAME = "JSON_Table";


        public static final String KEY_FACILITYINFO = "FacilityInfo";
        public static final String KEY_OPERATIONINFO = "OperationInfo";
        public static final String KEY_VALIDATION_MESSAGE = "ValidationMessage";
        public static final String KEY_DEFAULT_LOCATION = "DefaultLocation";
        public static final String KEY_ADMIN_PINCODE = "AdminPinCodes";
        public static final String KEY_BLACKLIST = "BlackList";

        public static final String KEY_SERVER_URL = "ServerUrl";
    }


    public String CREATE_QUERY =
            "CREATE TABLE  IF NOT EXISTS "+ ConfigTableInfo.TABLE_NAME+
                    "("    + ConfigTableInfo.COLUMN_KEY +" TEXT,"
                    + ConfigTableInfo.COLUMN_JSON +" TEXT);";

    public DataObjectConfig(Context context) {
        super(context, DbInfo.DATABASE_NAME, null, DbInfo.DATABASE_VERSION);
        Log.d("DataObjectConfig ", "Database Created");
        onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(CREATE_QUERY);
        Log.d("DataObjectConfig", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ConfigTableInfo.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void set(DataObjectConfig dop, String key, String json){

        SQLiteDatabase db;
        try{
            String jsonResult = getJSON(dop, key);
            if(jsonResult == null){
                db = dop.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(ConfigTableInfo.COLUMN_KEY, key);
                cv.put(ConfigTableInfo.COLUMN_JSON, json);
                long k = db.insert(ConfigTableInfo.TABLE_NAME, null, cv);
                Log.d("ConfigTableInfo", "One Row Inserted");

                db.close();
            }
            else{
                db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(ConfigTableInfo.COLUMN_KEY, key);
                values.put(ConfigTableInfo.COLUMN_JSON, json);

                // updating row
                db.update(ConfigTableInfo.TABLE_NAME, values, ConfigTableInfo.COLUMN_KEY + " = ?",
                        new String[] { key });

                db.close();
            }
        }
        catch (Exception ex){
            String msg = ex.getMessage();
        }

    }

    public String getJSON(DataObjectConfig dop, String key){
        try{
            SQLiteDatabase SQ = dop.getReadableDatabase();
            String[] columns = {ConfigTableInfo.COLUMN_KEY, ConfigTableInfo.COLUMN_JSON};
            Cursor CR = SQ.rawQuery("SELECT * FROM "+ConfigTableInfo.TABLE_NAME + " WHERE "+ConfigTableInfo.COLUMN_KEY + " = '"+key+"'",null);

            if(CR.getCount() > 0){
                CR.moveToNext();
                return CR.getString(1);
            }

        }
        catch (Exception ex){

        }




        return null;
    }
}
