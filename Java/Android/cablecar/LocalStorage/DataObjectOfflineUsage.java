package com.example.fuyan.test.LocalStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.fuyan.test.Activities.MyActivity;
import com.example.fuyan.test.Config.GlobalValues;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lanliang on 25/8/16.
 */
public class DataObjectOfflineUsage extends SQLiteOpenHelper {
    public static abstract class OfflineUsageTableInfo implements BaseColumns {
        public static final String USAGE_DATE = "UsageDate" ;

        public static final String TICKETNUMBER  = "TicketNo";

        public static final String FACILITY_ID = "FacilityID";

        public static final String OPERATION_ID = "OperationID"; // attraction name

        public static final String VALIDATOR_ID = "ValidatorID"; // device name
        public static final String OPERATOR_NAME = "OperatorName";
        public static final String TICKET_CONDITION = "TicketCondition";

        public static final String TABLE_NAME = "OFFLINE_USAGE_Table";
    }


    public String CREATE_QUERY =
            "CREATE TABLE   IF NOT EXISTS "+ OfflineUsageTableInfo.TABLE_NAME+ "("
                    + OfflineUsageTableInfo.USAGE_DATE+" TEXT,"
                    + OfflineUsageTableInfo.TICKETNUMBER+" TEXT,"
                    + OfflineUsageTableInfo.FACILITY_ID+" TEXT,"
                    + OfflineUsageTableInfo.OPERATION_ID+" TEXT,"
                    + OfflineUsageTableInfo.VALIDATOR_ID+" TEXT,"
                    + OfflineUsageTableInfo.OPERATOR_NAME+" TEXT,"
                    + OfflineUsageTableInfo.TICKET_CONDITION+" TEXT"
                    +        ");";

    public DataObjectOfflineUsage(Context context) {
        super(context, DbInfo.DATABASE_NAME, null, DbInfo.DATABASE_VERSION);
        onCreate(this.getWritableDatabase());
        Log.d("DataObjectOfflineUsage", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(CREATE_QUERY);
        Log.d("Database offline usage ", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(DataObjectOfflineUsage dataObject, String ticketNo, String facilityId, String operationId, String ticketCondition){
        SQLiteDatabase SQ = dataObject.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cv.put(OfflineUsageTableInfo.USAGE_DATE, sdf.format(d));
        cv.put(OfflineUsageTableInfo.TICKETNUMBER, ticketNo);
        cv.put(OfflineUsageTableInfo.FACILITY_ID, facilityId);
        cv.put(OfflineUsageTableInfo.OPERATION_ID, operationId);
        cv.put(OfflineUsageTableInfo.VALIDATOR_ID, GlobalValues.DeviceName());
        cv.put(OfflineUsageTableInfo.OPERATOR_NAME, MyActivity.LoginUserName);
        cv.put(OfflineUsageTableInfo.TICKET_CONDITION, ticketCondition);
        long k = SQ.insert(OfflineUsageTableInfo.TABLE_NAME, null, cv);
        Log.d("Database Operations", "One Row Inserted");
    }

    public Cursor getAll(DataObjectOfflineUsage dop){
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {OfflineUsageTableInfo.USAGE_DATE, OfflineUsageTableInfo.TICKETNUMBER, OfflineUsageTableInfo.FACILITY_ID,
                OfflineUsageTableInfo.OPERATION_ID, OfflineUsageTableInfo.VALIDATOR_ID, OfflineUsageTableInfo.OPERATOR_NAME,
                OfflineUsageTableInfo.TICKET_CONDITION
        };
        Cursor CR = SQ.query(OfflineUsageTableInfo.TABLE_NAME,columns,null,null,null,null,null);
        return CR;
    }

    public void deleteAll(DataObjectOfflineUsage dop){
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String sql = "delete from "+OfflineUsageTableInfo.TABLE_NAME ;
        SQ.execSQL(sql);
    }
}