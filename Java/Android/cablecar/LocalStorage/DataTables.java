package com.example.fuyan.test.LocalStorage;

import android.provider.BaseColumns;

public class DataTables {
    public DataTables(){

    }

    public static abstract class UserTable implements BaseColumns{

        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_PASSWORD = "user_pass";
        public static final String TABLE_NAME = "USER_Table";

    }
}
