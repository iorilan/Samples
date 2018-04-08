package com.example.fuyan.test.Config;

import android.content.Context;

import com.example.fuyan.test.LocalStorage.DataObjectConfig;
import com.example.fuyan.test.R;

/**
 * Created by lanliang on 24/9/16.
 */
public class ServerUrl {
    private static String baseUrl = "";

    public static void setUrl(Context context, String url){
        baseUrl = url;

        DataObjectConfig dataObjectConfig = new DataObjectConfig(context);
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_SERVERURL, url);
    }
    public static String getApiBaseUrl(Context context){
        if(baseUrl.equals("")){
            final String defaultUrl = "http://10.181.9.197:8010";
            try{
                DataObjectConfig dataObjectConfig = new DataObjectConfig(context);
                baseUrl = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_SERVERURL);
            }
            catch (Exception ex){
                baseUrl = defaultUrl;
            }
            if(baseUrl == null || baseUrl == ""){
                baseUrl = defaultUrl;
            }
        }
        return baseUrl;
    }

}
