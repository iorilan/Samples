package com.example.tdw.non_cablecarvalidator.Config;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.tdw.non_cablecarvalidator.Helpers.UtilHttp;
import com.example.tdw.non_cablecarvalidator.LocalStorage.DataObjectConfig;
import com.example.tdw.non_cablecarvalidator.R;

/**
 * Created by lanliang on 9/9/16.
 */
public class ServerUrls
{
    private static String baseUrl = "";

    public static void setBaseUrl(Context context, String url){
        baseUrl = url;

        DataObjectConfig dataObjectConfig = new DataObjectConfig(context);
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_SERVER_URL, url);
    }
    public static String getBaseUrl(Context context){
        if(baseUrl.equals("")){
            final String defaultUrl = "http://10.181.9.156:8010";
            try{
                DataObjectConfig dataObjectConfig = new DataObjectConfig(context);
                baseUrl = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_SERVER_URL);
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
