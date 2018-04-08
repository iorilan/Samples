package com.example.fuyan.test.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuyan.test.Config.GlobalValues;
import com.example.fuyan.test.Config.ServerUrl;
import com.example.fuyan.test.LocalStorage.DataObjectConfig;
import com.example.fuyan.test.Models.IdNamePair;
import com.example.fuyan.test.Models.LineNameStationName;
import com.example.fuyan.test.R;
import com.example.fuyan.test.Helpers.UtilHttp;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


// login page
public class MyActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtUsername, txtPassword;
    Context m_context = this;
    public static boolean loginOnline;
    public static String LoginUserName;

    public static JSONArray AdminActionCodes = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if (username.length() > 0 && password.length() > 0) {
                    String url = ServerUrl.getApiBaseUrl(m_context) + "GetAdminPincode.ashx?username="
                            + Uri.encode(username) +
                            "&password="+Uri.encode(password);

                    new getPinCodeAsyncTask().execute(url);
                } else {
                    Toast.makeText(getApplicationContext(), "Username/Password can not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // #### set api url

        TextView lblLogin = (TextView) findViewById(R.id.lblRegister);

        lblLogin.setOnClickListener(new View.OnClickListener(){
            int loginclickCount = 1;
            @Override
            public void onClick(View view) {
                if(loginclickCount == 5){
                    Intent i = new Intent(MyActivity.this, ReadCardTestActivity.class);
                    startActivity(i);

//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyActivity.this);
//                    alertDialog.setTitle("API location");
//                    alertDialog.setMessage("Select Environment");
//
//                    alertDialog.setPositiveButton("DEV",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ServerUrl.setUrl(getString(R.string.devUrl));
//                                    dialog.dismiss();
//                                }
//                            });
//
//                    alertDialog.setNegativeButton("UAT",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ServerUrl.setUrl(getString(R.string.uatUrl));
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
                    loginclickCount = 0;
                }
                else{
                    loginclickCount ++;
                }

            }
        });

        // ####



        String urlGetFacility = ServerUrl.getApiBaseUrl(m_context) + "GetFacilities.ashx";
        new getFacilityIdAsyncTask().execute(urlGetFacility);

        String urlGetOperation = ServerUrl.getApiBaseUrl(m_context) + "GetFacilityOperations.ashx";
        new getOperationIdAsyncTask().execute(urlGetOperation);


        ImageView lblHidden = (ImageView) findViewById(R.id.login_bg);
        lblHidden.setOnClickListener(new View.OnClickListener(){
            int clickCount = 1;
            @Override
            public void onClick(View view) {
                if(clickCount == 5){


//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyActivity.this);
//                    alertDialog.setTitle("API location");
//                    alertDialog.setMessage("Select Environment");
//
//                    alertDialog.setPositiveButton("UAT/Training",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ServerUrl.setUrl(m_context,"http://10.181.9.197:8010/");
//                                    dialog.dismiss();
//                                }
//                            });
//
//                    alertDialog.setNegativeButton("WirelessSG",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ServerUrl.setUrl(m_context,"http://202.172.171.109/");
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.setNeutralButton("Production",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ServerUrl.setUrl(m_context,"http://10.181.9.156:8010/");
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.setNeutralButton("SIT",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ServerUrl.setUrl(m_context,"http://10.181.9.220:8010/");
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
                    String[] arr = new String[4];
                    arr[0] = "UAT";
                    arr[1] = "WirelessSG";
                    arr[2] = "SIT";
                    arr[3] = "Production";

                    android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(MyActivity.this);
                    alertBuilder.setItems(arr, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int index) {
                            try{
                                if(index == 0){
                                    ServerUrl.setUrl(m_context,"http://10.181.9.197:8010/");
                                    return;
                                }
                                if(index == 1){
                                    ServerUrl.setUrl(m_context,"http://202.172.171.109/");
                                    return;
                                }
                                if(index == 2){
                                    ServerUrl.setUrl(m_context,"http://10.181.9.220:8010/");
                                    return;
                                }
                                if(index == 3){
                                    ServerUrl.setUrl(m_context,"http://10.181.9.156:8010/");
                                    return;
                                }
                            }
                            catch (Exception ex){

                            }
                            finally {
                                dialog.dismiss();
                            }
                        }
                    });
                    alertBuilder.create().show();

                    clickCount = 0;
                }
                else{
                    clickCount ++;
                }

            }
        });
    }


    public class getPinCodeAsyncTask extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog m_progressDlg;
        private boolean isSuperAdmin = false;
        private JSONArray visibleFacilities;
        @Override
        protected void onPreExecute() {
            m_progressDlg = ProgressDialog.show(m_context, null,
                    "Processing...");
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                String rst =
                        UtilHttp.doHttpGet(m_context, params[0]);
                //{
                // server object structure:
                // public bool IsSuperAdmin { get; set; }
                // public bool IsSuccess { get; set; }
                // public string ErrorMsg { get; set; }
                // public IEnumerable<string> Pincodes { get; set; }
                //}

                if (rst == null) {
                    loginOnline = false;

                    // in offline mode , allow operator to choose the location
                    isSuperAdmin = true;
                    // continue with offline mode
                    return true;
                } else {
                    loginOnline = true;
                    // verify the login result
                    JSONObject jso = new JSONObject(rst);
                    boolean isSuccess = jso.getBoolean("IsSuccess");
                    isSuperAdmin = jso.getBoolean("IsSuperAdmin");
                    AdminActionCodes = jso.getJSONArray("AdminActionCodes");
                    visibleFacilities = jso.getJSONArray("Facilities");
                    JSONArray pinCodes = jso.getJSONArray("Pincodes");
                    if(isSuccess){
                        if (pinCodes.length() != 0) {
                            ArrayList<String> pins = new ArrayList<>();
                            int len = pinCodes.length();
                            for (int i = 0; i < len; i++) {
                                pins.add(pinCodes.getString(i));
                            }
                            String pinJson = new Gson().toJson(pins);
                            DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                            dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_ADMIN_PIN, pinJson);
                        }

                        return true;
                    }else{
                        return false;
                    }
                }

            }catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            m_progressDlg.dismiss();

            if (result) {
                txtUsername = (EditText)findViewById(R.id.txtUsername);

                GlobalValues.setUserName(getApplicationContext(), txtUsername.getText().toString());
                GlobalValues.setSuperAdmin(getApplicationContext(), isSuperAdmin);
                LoginUserName = txtUsername.getText().toString();

                if(isSuperAdmin){
                    Intent i = new Intent(MyActivity.this, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
                else{
                    DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                    String stationId = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_STATION_ID);
                    String scanIn = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_SCAN_DIRECTION);

                    if(stationId == null || scanIn == null){
                        Toast.makeText(getApplicationContext(), "Failed to load default setting, login with admin first.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        boolean hasAccess = false;
                       LineNameStationName lineNameStationName = ResultActivity.getLineNameStationName(Integer.parseInt(stationId));

                        try{
                            if (visibleFacilities.length() != 0) {
                                for (int i = 0; i < visibleFacilities.length(); i++) {
                                    String fid = visibleFacilities.getJSONObject(i).getString("FacilityId");
                                    if(fid.equals(lineNameStationName.facilityId)){
                                        hasAccess = true;
                                        break;
                                    }
                                }
                            }
                            if(!hasAccess){
                                Toast.makeText(getApplicationContext(), "User has no access to this attraction, please contact administrator.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        catch (JSONException ex) {

                        }

                        Intent intent = new Intent(MyActivity.this, ResultActivity.class);

                        intent.putExtra("scanId", scanIn);
                        intent.putExtra("STATION", Integer.valueOf(stationId));
                        startActivity(intent);
                    }
                }

            } else {
                Toast.makeText(getApplicationContext(), "Login Failed! Wrong Credentials", Toast.LENGTH_SHORT).show();
            }
        }

    }


    // ##################################
    // summary : get Facility Id Async
    // ##################################
    public class getFacilityIdAsyncTask extends AsyncTask<String, Void, Boolean> {
        List<IdNamePair> facilityIds;
        private ProgressDialog m_progressDlg;

        private String TAG = "getFacilityIdAsyncTask";
        @Override
        protected void onPreExecute() {
            m_progressDlg = ProgressDialog.show(m_context, null,
                    "Processing...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            facilityIds = new ArrayList<>();

            try {
                String rst =

                        //"{\"FacilityOperations\":[\n" +
                        //"{\"AntiPassFlag\":null,\"AntiPassMinute\":null,\"FacilityId\":\"4D\",\"Name\":\"4D\",\"OperationId\":null,\"RecId\":0,\"ModifiedDatetime\":null,\"CreatedDatetime\":null,\"DataAreAId\":null,\"RecVersion\":0,\"Partition\":0,\"RecordStatus\":0},\n" +
                        //"{\"AntiPassFlag\":null,\"AntiPassMinute\":null,\"FacilityId\":\"4D Combo\",\"Name\":\"4D All (Combo)\",\"OperationId\":null,\"RecId\":0,\"ModifiedDatetime\":null,\"CreatedDatetime\":null,\"DataAreAId\":null,\"RecVersion\":0,\"Partition\":0,\"RecordStatus\":0}\n" +
                        //"]\n" +
                        //"}";

                        UtilHttp.doHttpGet(m_context, params[0]);

                if (rst == null) {
                    return false;

                } else {
                    // store copy
                    DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                    dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_FACILITYINFO, rst);
                    Log.v(TAG, "get return non null");
                }

            }catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            m_progressDlg.dismiss();
        }

    }

    // ##################################
    // summary : get operation id async
    // ##################################
    public  class getOperationIdAsyncTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog m_progressDlg;
        @Override
        protected void onPreExecute() {
            m_progressDlg = ProgressDialog.show(m_context, null,
                    "Processing...");
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                    String urlGetOperationByFacility = params[0];
                    String rst = UtilHttp.doHttpGet(m_context, urlGetOperationByFacility);
                    if(rst ==null){
                        return false;
                    }

                    // store a copy of all [operation id, operation name , facility Id]
                    DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                    dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_OPERATIONINFO, rst);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            m_progressDlg.dismiss();
        }
    }
}

