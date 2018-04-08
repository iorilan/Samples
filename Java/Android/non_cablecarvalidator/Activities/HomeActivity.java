package com.example.tdw.non_cablecarvalidator.Activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdw.non_cablecarvalidator.Activities.Models.DefaultLocation;
import com.example.tdw.non_cablecarvalidator.Activities.Models.IdNamePair;
import com.example.tdw.non_cablecarvalidator.Activities.Models.TicketUsage;
import com.example.tdw.non_cablecarvalidator.Config.GlobalValues;
import com.example.tdw.non_cablecarvalidator.Config.ServerUrls;
import com.example.tdw.non_cablecarvalidator.Helpers.UtilHttp;
import com.example.tdw.non_cablecarvalidator.LocalStorage.DataObjectConfig;
import com.example.tdw.non_cablecarvalidator.LocalStorage.DataObjectOfflineUsage;
import com.example.tdw.non_cablecarvalidator.LocalStorage.OfflineUsageModel;
import com.example.tdw.non_cablecarvalidator.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private TextView textWelcome;
    private String usernameText;
    private Context m_context;
    public Spinner ddlFacility, ddlOperation;
    private Button submit, logout;
    public static ArrayList<String> blackListed = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        m_context = this;

        //display username
        String username = GlobalValues.getUserName(getApplicationContext());
        usernameText = "User: " + username;

        TextView usernameTV = (TextView)findViewById(R.id.lblUsername);
        usernameTV.setTypeface(null, Typeface.BOLD);
        usernameTV.setText(usernameText);

        textWelcome = (TextView)findViewById(R.id.lblWelcome);
        textWelcome.setText("Welcome, " + username);


        //submit
        submit = (Button)findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                updateSelectedLocation();

                Intent i = new Intent(HomeActivity.this, ResultActivity.class);
                startActivity(i);
            }
        });


        //logout
        logout = (Button)findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);

                alertDialog.setTitle("Logout"); // Sets title for your alertbox

                alertDialog.setMessage("Are you sure you want to Logout ?"); // Message to be displayed on alertbox

      /* When positive (yes/ok) is clicked */
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        GlobalValues.clear(getApplicationContext());
                        finish();
                        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        Toast.makeText(HomeActivity.this,"Successfully Logged Out", Toast.LENGTH_LONG).show();
                    }
                });

        /* When negative (No/cancel) button is clicked*/
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });




        ddlFacility = (Spinner)findViewById(R.id.ddlFacilityId);
        ddlFacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String facilityid = ((IdNamePair)parent.getItemAtPosition(position)).id;
                new getOperationIdAsyncTask().execute(facilityid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ddlOperation = (Spinner)findViewById(R.id.ddlOperationId);
        String getFacilityUrl = ServerUrls.getBaseUrl(m_context)+"GetFacilities.ashx";
        new getFacilityIdAsyncTask().execute(getFacilityUrl);


        String urlOfflineUsage = ServerUrls.getBaseUrl(m_context)+"RecordMobileOfflineUsage.ashx";
        new postOfflineUsageAsyncTask().execute(urlOfflineUsage);

        new getBlackListAsyncTask().execute();
    }


    private void updateSelectedLocation() {
        IdNamePair selectedFacility = (IdNamePair) ddlFacility.getSelectedItem();
        ResultActivity.m_facilityid = selectedFacility.id;
        ResultActivity.m_facilityName = selectedFacility.name;
        ResultActivity.m_facilityLineNum = selectedFacility.lineNum;

        IdNamePair selectedOperation = (IdNamePair) ddlOperation.getSelectedItem();
        ResultActivity.m_operationid = selectedOperation.id;
        ResultActivity.m_operationName = selectedOperation.name;
        ResultActivity.m_operationLineNum = selectedOperation.lineNum;

        // update cache
        DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
        String json = new Gson().toJson(new DefaultLocation(selectedFacility.id, selectedFacility.name, selectedOperation.id, selectedOperation.name, selectedFacility.lineNum, selectedOperation.lineNum));
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_LOCATION,json);
    }


    // ##################################
    // summary : get Facility Id Async
    // ##################################
    public class getFacilityIdAsyncTask extends AsyncTask<String, Void, Boolean> {
        List<IdNamePair> facilityIds;
        private String TAG = "getFacilityIdAsyncTask";
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(String... params) {
            facilityIds = new ArrayList<>();


            try {
                String rst =
                        UtilHttp.doHttpGet(m_context, params[0]);

                JSONArray FacilityOperations = null;

                //// run with offline mode
                if (rst == null) {

                    Log.v("", "get return null");

                    // load from local
                    DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                    rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_FACILITYINFO);
                    if(rst == null){
                        return false;
                    }

                    ////offline mode, will popup all facilities to choose
                    JSONObject jso = new JSONObject(rst);
                    FacilityOperations = jso.getJSONArray("FacilityOperations");

                } else {
                    // store copy
                    DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                    dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_FACILITYINFO, rst);
                    Log.v(TAG, "get return non null");

                    // online need to get the visible operations from login
                    // only show the facilities visible to him/her
                    if(LoginActivity.visibleFacilities != null){
                        FacilityOperations = LoginActivity.visibleFacilities;
                    }
                    // only admin can come this page , but no facility been configured
                    // has to go to admin portal link the facilities to this admin
                    else{
//                        Toast.makeText(getApplicationContext(), "there is no facility linked to this admin.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }


                if (FacilityOperations.length() != 0) {
                    for (int i = 0; i < FacilityOperations.length(); i++) {
                        facilityIds.add(new IdNamePair(
                                FacilityOperations.getJSONObject(i).getString("FacilityId"),
                                FacilityOperations.getJSONObject(i).getString("Name"),
                                FacilityOperations.getJSONObject(i).getString("LineNumber")));
                    }
                }


            }catch (JSONException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
           // m_progressDlg.dismiss();

            if (result) {

                ddlFacility = (Spinner)findViewById(R.id.ddlFacilityId);
                ArrayAdapter<IdNamePair> adapter = new ArrayAdapter(m_context, android.R.layout.simple_spinner_item, facilityIds);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ddlFacility.setAdapter(adapter);


            } else {
                Toast.makeText(getApplicationContext(), "there is no facility linked to this admin.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // ##################################
    // summary : get black list async
    // ##################################
    public  class getBlackListAsyncTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog m_progressDlg;

        @Override
        protected void onPreExecute() {
            m_progressDlg = ProgressDialog.show(m_context, null, "Loading data, please wait ...");
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                String url = ServerUrls.getBaseUrl(m_context) +"GetBlacklisted.ashx?validatorId="+Uri.encode(GlobalValues.DeviceName());
                String rst = UtilHttp.doHttpGet(m_context, url);

                // online , still not getting result, check error
                if(rst == null){
                    // offline , try get from local storage
                    DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                    rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_BLACKLIST);
                    if(rst == null){
                        //Toast.makeText(getApplicationContext(), "Load facility failed  [Offline]", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    JSONArray arr = new JSONArray(rst);
                    if (arr.length() != 0) {
                        for (int i = 0; i < arr.length(); i++) {
                            String v = arr.getString(i);
                            blackListed.add(i,v);
                        }
                    }
                    return true;
                }

                // store a copy of blacklist
                DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_BLACKLIST, rst);

            } catch (JSONException e) {
                e.printStackTrace();

            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            m_progressDlg.dismiss();

        }

    }

    public  class getOperationIdAsyncTask extends AsyncTask<String, Void, Boolean> {
        List<IdNamePair> operationIds;
        private ProgressDialog m_progressDlg;

        @Override
        protected void onPreExecute() {
            m_progressDlg = ProgressDialog.show(m_context, null, "Loading Operation, please wait ...");
        }

        @Override
        protected Boolean doInBackground(String... params) {

            String selectedFacilityId = Uri.encode(params[0]);
            try {

                String urlGetOperationByFacility = ServerUrls.getBaseUrl(m_context) +"GetFacilityOperations.ashx?FacilityId="+ selectedFacilityId;
                String rst = UtilHttp.doHttpGet(m_context, urlGetOperationByFacility);

                // online , still not getting result, check error
                if(rst ==null){
                    // offline , get from local storage
                    DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                    rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_OPERATIONINFO);
                    if(rst == null){
                        //Toast.makeText(getApplicationContext(), "Load facility failed  [Offline]", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    JSONObject jso = new JSONObject(rst);
                    JSONArray FacilityOperations = jso.getJSONArray("FacilityOperations");
                    operationIds = new ArrayList<>();
                    if (FacilityOperations.length() != 0) {
                        for (int i = 0; i < FacilityOperations.length(); i++) {
                            JSONObject jsonObj = FacilityOperations.getJSONObject(i);
                            String facilityId = jsonObj.getString("FacilityId");
                            if(facilityId.equals(params[0])){
                                operationIds.add(new IdNamePair(
                                        jsonObj.getString("OperationId"),
                                        jsonObj.getString("Name"),
                                        jsonObj.getString("LineNumber")));
                            }
                        }
                    }

                    return true;

                }

                JSONObject jso = new JSONObject(rst);
                JSONArray FacilityOperations = jso.getJSONArray("FacilityOperations");
                operationIds = new ArrayList<>();
                if (FacilityOperations.length() != 0) {
                    for (int i = 0; i < FacilityOperations.length(); i++) {

                        operationIds.add(new IdNamePair(
                                FacilityOperations.getJSONObject(i).getString("OperationId"),
                                FacilityOperations.getJSONObject(i).getString("Name"),
                                FacilityOperations.getJSONObject(i).getString("LineNumber")));
                    }
                }

                // store a copy of all [operation id, operation name , facility Id]
                String urlGetAllOperation = ServerUrls.getBaseUrl(m_context)+"GetFacilityOperations.ashx";
                DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                rst = UtilHttp.doHttpGet(m_context, urlGetAllOperation);
                dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_OPERATIONINFO, rst);


            } catch (JSONException e) {
                e.printStackTrace();

            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            m_progressDlg.dismiss();
            if (result) {
                ddlOperation = (Spinner)findViewById(R.id.ddlOperationId);
                ArrayAdapter adapter2 = new ArrayAdapter<>(m_context, android.R.layout.simple_spinner_item, operationIds);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ddlOperation.setAdapter(adapter2);

            } else {
                Toast.makeText(getApplicationContext(), "Load facility failed  [Offline]", Toast.LENGTH_SHORT).show();
            }

        }

    }

    // ##################################
    // summary : post offline usage async
    // ##################################
    public class postOfflineUsageAsyncTask extends AsyncTask<String, Void, Boolean> {
        private String TAG = "post offline usage async";
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                String url = params[0];
                // check if there is any offline usages stored in local
                DataObjectOfflineUsage dataOfflineUsage = new DataObjectOfflineUsage(m_context);
                Cursor cursor = dataOfflineUsage.getAll(dataOfflineUsage);
                if(cursor.getCount() > 0){
                    ArrayList<TicketUsage> usages = new ArrayList<>();


                    while(cursor.moveToNext()){
                        String usageDate = cursor.getString(0);
                        String ticketNo = cursor.getString(1);
                        String facilityId = cursor.getString(2);
                        String operationId = cursor.getString(3);
                        String validatorId = cursor.getString(4);
                        String operatorName = cursor.getString(5);
                        String ticketCondition = cursor.getString(6);
                        OfflineUsageModel offlineUsageModel = new
                                OfflineUsageModel(usageDate, ticketNo, facilityId, operationId,
                                validatorId, operatorName, ticketCondition);
                        usages.add(new TicketUsage(offlineUsageModel));


                        String json = new Gson().toJson(usages);
                        String rest = UtilHttp.doHttpPostWithResult(m_context, url, json);
                        usages.clear();
                    }

                    dataOfflineUsage.deleteAll(dataOfflineUsage);


                    // delete all
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }


            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

        }

    }
}
