package com.example.tdw.non_cablecarvalidator.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdw.non_cablecarvalidator.Activities.Models.IdNamePair;
import com.example.tdw.non_cablecarvalidator.Activities.Models.TicketUsage;
import com.example.tdw.non_cablecarvalidator.Config.GlobalValues;
import com.example.tdw.non_cablecarvalidator.Config.ServerUrls;
import com.example.tdw.non_cablecarvalidator.Helpers.UtilHttp;
import com.example.tdw.non_cablecarvalidator.LocalStorage.DataObjectConfig;
import com.example.tdw.non_cablecarvalidator.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtUsername, txtPassword;
    Context m_context = this;
    public static String LoginUserName;

    public static JSONArray visibleFacilities = null;
    public static JSONArray AdminActionCodes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtUsername = (EditText)findViewById(R.id.txtAdminLoginUserName);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginUrl = ServerUrls.getBaseUrl(m_context) +"GetAdminPincode.ashx?username="
                        + Uri.encode(txtUsername.getText().toString()) +
                        "&password="+Uri.encode(txtPassword.getText().toString());
                if(txtUsername.getText().equals("") ||
                        txtPassword.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Username/Password can not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                new getPinCodeAsyncTask().execute(loginUrl);
            }
        });



        TextView lblLogin = (TextView)findViewById(R.id.lblLogin);
        lblLogin.setOnClickListener(new View.OnClickListener(){

            int loginclickCount = 1;
            @Override
            public void onClick(View view) {
                if(loginclickCount >= 5){
                    Intent i = new Intent(LoginActivity.this, ReadCardTestActivity.class);
                    startActivity(i);
                    loginclickCount = 0;
                }
                else{
                    loginclickCount ++;
                }

            }
        });



        ImageView lblHidden = (ImageView)findViewById(R.id.login_bg);
        lblHidden.setOnClickListener(new View.OnClickListener(){

            int clickCount = 1;
            @Override
            public void onClick(View view) {
                if(clickCount >= 5){


                    String[] arr = new String[5];
                    arr[0] = "UAT";
                    arr[1] = "WirelessSG";
                    arr[2] = "SIT";
                    arr[3] = "Production";
                    arr[4] = "Dev";
                   android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(LoginActivity.this);
                    alertBuilder.setItems(arr, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int index) {
                            try{
                                if(index == 0){
                                    ServerUrls.setBaseUrl(m_context,"http://10.181.9.197:8010/");
                                    return;
                                }
                                if(index == 1){
                                    ServerUrls.setBaseUrl(m_context,"http://202.172.171.109/");
                                    return;
                                }
                                if(index == 2){
                                    ServerUrls.setBaseUrl(m_context,"http://10.181.9.220:8010/");
                                    return;
                                }
                                if(index == 3){
                                    ServerUrls.setBaseUrl(m_context,"http://10.181.9.156:8010/");
                                    return;
                                }
                                if(index == 4){
                                    ServerUrls.setBaseUrl(m_context,"http://52.187.48.211:8010/");
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

                if (rst == null) {
                    // continue with offline mode, consider as super admin
                    isSuperAdmin = true;
                    return true;
                } else {
                    // verify the login result
                    JSONObject jso = new JSONObject(rst);
                    boolean isSuccess = jso.getBoolean("IsSuccess");
                    isSuperAdmin = jso.getBoolean("IsSuperAdmin");
                    JSONArray pinCodes = jso.getJSONArray("Pincodes");

                    //// if super admin
                    ////try get the visible facilities when login
                    visibleFacilities = jso.getJSONArray("Facilities");
                    if(isSuperAdmin){
                        AdminActionCodes = jso.getJSONArray("AdminActionCodes");
                    }

                    if(isSuccess){

                        if (pinCodes.length() != 0) {
                            ArrayList<String> pins = new ArrayList<>();
                            for (int i = 0; i < pinCodes.length(); i++) {
                                pins.add(pinCodes.getString(i));
                            }
                            String pinJson = new Gson().toJson(pins);
                            DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                            dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_ADMIN_PINCODE, pinJson);
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
                txtUsername = (EditText)findViewById(R.id.txtAdminLoginUserName);
                LoginUserName = txtUsername.getText().toString();
                GlobalValues.setUserName(getApplicationContext(), txtUsername.getText().toString());
                GlobalValues.setSuperAdmin(getApplicationContext(), isSuperAdmin);


                if(isSuperAdmin){
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
                else{
                    try{
                        // load default location from cache
                        // set default location
                         DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                        String rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_LOCATION);
                        // cache is empty , select the first one
                        if(rst == null){
                            Toast.makeText(getApplicationContext(), "Default Facility not set, login as super admin first.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JSONObject jso = new JSONObject(rst);
                        String facilityId = jso.getString("facilityId");
                        String operationId = jso.getString("operationId");
                        String facilityName = jso.getString("facilityName");
                        String operationName = jso.getString("operationName");
                        String facilityLineNum = jso.getString("facilityLineNum");
                        String operationLineNum = jso.getString("operationLineNum");

                        boolean hasAccess = false;
                        if (visibleFacilities.length() != 0) {
                            for (int i = 0; i < visibleFacilities.length(); i++) {
                                String fid = visibleFacilities.getJSONObject(i).getString("FacilityId");
                                if(fid.equals(facilityId)){
                                    hasAccess = true;
                                    break;
                                }
                            }
                        }
                        if(!hasAccess){
                            Toast.makeText(getApplicationContext(), "User has no access to this attraction, please contact administrator.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ResultActivity.m_facilityid = facilityId;
                        ResultActivity.m_facilityName = facilityName;
                        ResultActivity.m_facilityLineNum = facilityLineNum;

                        ResultActivity.m_operationid = operationId;
                        ResultActivity.m_operationName = operationName;
                        ResultActivity.m_operationLineNum = operationLineNum;

                        Intent i = new Intent(LoginActivity.this, ResultActivity.class);
                        startActivity(i);
                    }
                    catch (JSONException ex){

                    }

                }


            } else {
                Toast.makeText(getApplicationContext(), "Login Failed! Wrong Credentials", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
