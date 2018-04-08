package com.example.tdw.non_cablecarvalidator.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.example.tdw.non_cablecarvalidator.R;

/**
 * Created by lanliang on 20/9/16.
 */
public class GlobalValues
{

    public static String appVersion(){
        return "2.0";
    }
    public static void setSuperAdmin(Context context, boolean value){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isSuperAdmin",value);
        editor.commit();
    }

    public static boolean getSuperAdmin(Context context){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Boolean isAdmin = pref.getBoolean("isSuperAdmin", false);
        return isAdmin;
    }

    public static void setUserName(Context context, String value){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_name", value);
        editor.commit();
    }
    public static String getUserName(Context context){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String username = pref.getString("user_name", null);
        return username;
    }



    public static void clear(Context context){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().commit();
    }

    public static String DeviceName(){
        return android.os.Build.MODEL + "-"+ Build.ID;
    }

    public static Boolean onlineUnknownAutoOfflineCheck(Context context){
        return Boolean.parseBoolean(context.getString(R.string.onlineUnknownAutoOfflineCheck));
    }
}
