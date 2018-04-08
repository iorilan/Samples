package com.example.fuyan.test.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fuyan.test.Config.GlobalValues;
import com.example.fuyan.test.Config.ServerUrl;
import com.example.fuyan.test.Helpers.UtilHttp;
import com.example.fuyan.test.Helpers.ZipUtil;
import com.example.fuyan.test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CancelUsageActivity extends AppCompatActivity {

    private Context m_Context;
    private String m_facilityId;
    private String m_operationId;
    private String m_ticketNo;

    private int ColorValid = Color.parseColor("#50b848");
    private int ColorDefault = Color.parseColor("#F07D00");
    private int ColorError = Color.parseColor("#EB1C24");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_usage);

        m_Context = this;

        final EditText txtScanning = (EditText) findViewById(R.id.txtScanning);
        txtScanning.setTextColor(Color.WHITE);

        final TextView tvMsg = (TextView) findViewById(R.id.tvMsg);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // tvMsg.setText("Processing...");
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }


            @Override
            public void afterTextChanged(Editable editable) {
                String values = txtScanning.getText().toString();
                Log.d("Testing---------",values);
                if (values.length()!=0){

                    if (values.length() > 3 && (values.substring(values.length() - 3)).equals("###")) {
                        values = values.substring(0, values.length() - 3);
                        values = ZipUtil.DecompressToBase64(values);

                        if(values.length() < 35){
                            txtScanning.setText("");
                            return;
                        }
                        m_ticketNo = values.substring(15, 35);
                        m_facilityId = ResultActivity.m_facilityId;
                        m_operationId = ResultActivity.m_operationId;

                        final String urlGetUsage = ServerUrl.getApiBaseUrl(m_Context)+"GetUsageByTicketNo.ashx?ticketCode="+m_ticketNo;
                        final String urlCancel = ServerUrl.getApiBaseUrl(m_Context) + "CancelUsage.ashx?ticketCode=" + m_ticketNo
                                +"&validatorId="+ GlobalValues.DeviceName()
                                +"&facilityId="+ Uri.encode(ResultActivity.m_facilityId)
                                +"&operatorName="+Uri.encode(MyActivity.LoginUserName)
                                + "&uniqueUsageID=";

                        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(CancelUsageActivity.this);
                        alertDialog.setTitle("Cancel Usage Confirmation");
                        alertDialog.setMessage("Are you sure to cancel the usage ?");

                        alertDialog.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        new CancelUsageAsyncTask().execute(urlGetUsage,urlCancel);
                                        dialog.dismiss();
                                    }
                                });

                        alertDialog.setNeutralButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        m_ticketNo = "";
                                        txtScanning.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();


                    }
                }
                return;

            }
        };
        txtScanning.addTextChangedListener(textWatcher);


        Button btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(CancelUsageActivity.this, ResultActivity.class);
                i.putExtra("STATION", ResultActivity.m_stationId);
                i.putExtra("scanId", ResultActivity.m_direction);
                startActivity(i);
            }
        });

    }

    private class CancelUsageAsyncTask extends AsyncTask<String, Void, Boolean> {

        private String TAG = "cancel usage";
        private ProgressDialog m_processDialog;
        private String error ;
        @Override
        protected void onPreExecute() {
            // still processing
            if(m_processDialog != null && m_processDialog.isShowing()){
                return;
            }

            m_processDialog = ProgressDialog.show(m_Context, null,
                    "Processing, please wait ...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean success = true;
            String urlGetUsage = params[0];
            try{
                String jsonUsages = UtilHttp.doHttpGet(m_Context,urlGetUsage);
                JSONArray arrUsages = new JSONArray(jsonUsages);
                int len = arrUsages.length();

                ArrayList<String> cancelledUsages = new ArrayList<>();
                for (int i = 0;i < len; i++){
                    JSONObject jsonObj = arrUsages.getJSONObject(i);
                    String priorUniqueId = jsonObj.getString("PriorUniqueUsageID");
                    int usageStatus = jsonObj.getInt("UsageStatus");
                    if(usageStatus == 13 /*cancelled*/){
                        cancelledUsages.add(priorUniqueId);
                    }
                }

                ArrayList<String> filteredUsages = new ArrayList<>();


                int countFilter = 0;
                for (int i = 0;i < len; i++){
                    JSONObject jsonObj = arrUsages.getJSONObject(i);
                    String facilityId = jsonObj.getString("FacilityID");
                    String operationId = jsonObj.getString("OperationID");
                    int usageStatus = jsonObj.getInt("UsageStatus");
                    String uniqueId = jsonObj.getString("UniqueUsageID");

                    if(facilityId.equals(m_facilityId) &&
                            m_operationId.equals(operationId) &&
                            usageStatus == 1 &&
                            notBeenCancelledBefore(cancelledUsages,uniqueId))
                    {
                        filteredUsages.add(countFilter,uniqueId);
                        countFilter ++;
                    }
                }

                if(filteredUsages.size() == 0){
                    error = "Usage not found.";
                    return false;
                }

                for (String uniqueUsageId : filteredUsages)
                {
                    String cancelUrl = params[1]+Uri.encode(uniqueUsageId);

                    String result = UtilHttp.doHttpGet(m_Context, cancelUrl);
                    JSONObject jso = new JSONObject(result);
                    boolean isSuccess = jso.getBoolean("IsSuccess");
                    error = jso.getString("ErrorMsg");

                    if(!isSuccess){
                        success = false;
                    }
                }

                return success;
            }
            catch (JSONException ex){
                success = false;
            }

            return success;
        }

        private boolean notBeenCancelledBefore(ArrayList<String> cancelled, String uniqueID){
            int len = cancelled.size();
            for (int i = 0;i < len; i++){
                if(cancelled.get(i).equals(uniqueID)){
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            m_processDialog.dismiss();

            final TextView tvMsg = (TextView) findViewById(R.id.tvMsg);
            tvMsg.setText("");

            TextView txtConsole = (TextView) findViewById(R.id.tv_status);
            if(!result){
                txtConsole.setBackgroundColor(ColorError);
                txtConsole.setText(error);
            }else{
                txtConsole.setBackgroundColor(ColorValid);
                txtConsole.setText(String.format("Ticket '%s' usage of '%s-%s' has been cancelled successfully.",m_ticketNo,m_facilityId ,m_operationId));
            }
        }
    }
}
