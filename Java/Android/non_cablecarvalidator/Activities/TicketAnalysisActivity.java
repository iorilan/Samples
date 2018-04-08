package com.example.tdw.non_cablecarvalidator.Activities;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdw.non_cablecarvalidator.Config.ServerUrls;
import com.example.tdw.non_cablecarvalidator.Helpers.ZipUtil;
import com.example.tdw.non_cablecarvalidator.R;
import com.example.tdw.non_cablecarvalidator.Helpers.UtilHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TicketAnalysisActivity extends AppCompatActivity {

    private Context m_Context;
    private String m_ticketNo;
    public Handler updateUsageMsgHandler = new Handler();

    private String m_productName ;
    private String m_StatusStr ;
    private String m_UsageCountStatus ;
    private ArrayList<String> m_usageRecord = new ArrayList<String>();

    private EditText txtScanning;
    private static final String TAG = "TAG_TicketAnalysis";

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_analysis);

        m_Context = this;

        Intent intentin = getIntent();
        m_ticketNo = intentin.getStringExtra("ticketNumber");
        if(m_ticketNo != null && !m_ticketNo.equals("")){
            new ticketAnalysisAsyncTask().execute();
        }


        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String values = txtScanning.getText().toString();
                Log.d("Testing---------",values);
                if (values.length() > 3 && (values.substring(values.length() - 3)).equals("###")) {

                    values = values.substring(0, values.length() -3);
                    // try unzip it
                    String decompressed = ZipUtil.DecompressToBase64(values);
                    if(!decompressed.equals("")){
                        values = decompressed;
                    }

                    if(values.length()< 35){
                        txtScanning.setText("");
                        return;
                    }
                    m_ticketNo = values.substring(15, 35);
                    txtScanning.setText("");
                    new ticketAnalysisAsyncTask().execute();
                }
            }
        };
        txtScanning = (EditText) findViewById(R.id.txtScanning);
        txtScanning.setTextColor(Color.WHITE);
        txtScanning.addTextChangedListener(textWatcher);


        Button btnManual = (Button) findViewById(R.id.btnManual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(TicketAnalysisActivity.this);
                alertDialog.setTitle("Ticket number");
                alertDialog.setMessage("Enter ticket number");

                final EditText txtManualTicketNo = new EditText(TicketAnalysisActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                txtManualTicketNo.setLayoutParams(lp);
                alertDialog.setView(txtManualTicketNo);
                //alertDialog.setIcon(R.drawable.);

                alertDialog.setPositiveButton("Analysis",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String value = txtManualTicketNo.getText().toString();
                                m_ticketNo = value;
                                new ticketAnalysisAsyncTask().execute();
                            }
                        });

                alertDialog.setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });



        //// NFC card reading
        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mPendingIntent =
                PendingIntent.getActivity(this, 0,nfcIntent , 0);

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(getApplicationContext(), "NFC feature is supported on this device.", Toast.LENGTH_SHORT).show();
            return;
        }


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    final Runnable updateUsageInfoRunnable = new Runnable() {
        public void run() {

            // update label
            TextView lblTicketCode = (TextView)findViewById(R.id.lblTicketCode);
            lblTicketCode.setText(m_ticketNo);

            TextView lblProductName = (TextView) findViewById(R.id.lblProductName);
            lblProductName.setText(m_productName);

            TextView lblStatus = (TextView) findViewById(R.id.lblStatus);
            lblStatus.setText(m_StatusStr);

            TextView lblUsageCount = (TextView) findViewById(R.id.lblUsage);
            lblUsageCount.setText(m_UsageCountStatus);

            TextView lblUsageDetails = (TextView) findViewById(R.id.lblUsageDetails);
            lblUsageDetails.setText("");
            lblUsageDetails.setMovementMethod(new ScrollingMovementMethod());
            if(m_usageRecord.size() > 0){
                int count = 1;
                for(String recordMsg : m_usageRecord){
                    String currentStr = lblUsageDetails.getText().toString();
                    lblUsageDetails.setText(
                            currentStr +
                                    "\r\n"+count + "." + recordMsg);
                    count++;
                }
            }
            else{
                lblUsageDetails.setText("");
            }

            m_productName = "";
            m_StatusStr = "";
            m_UsageCountStatus = "";
            m_usageRecord.clear();

        }
    };

    private class ticketAnalysisAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog m_dialog;

        @Override
        protected void onPreExecute() {
            m_dialog = ProgressDialog.show(m_Context, null,
                    "Loading, please wait ...");
        }

        // sample JSON :
        //{
        //      "IsSuccess":true,
        //      "ErrorMsg":"",
        //      "Title": {"ProductName":"Fun pass play 3 - edd","Status":"Valid","UsageCountStatus":"2/3"},
        //      "Records":
        //          [
        //              {"UsedAt":"26/8/2016 11:58:50 AM","FacilityOperation":"FSCombat,FSCombat"},
        //              {"UsedAt":"26/8/2016 11:59:38 AM","FacilityOperation":"ButterflyInsect,ButterflyInsect"}
        //          ]
        // }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url = ServerUrls.getBaseUrl(m_Context)+"TicketAnalysis.ashx"
                    + "?ticketNumber="+m_ticketNo
            + "&facilityId="+ Uri.encode(ResultActivity.m_facilityid)
            + "&operationId="+ Uri.encode(ResultActivity.m_operationid)
            + "&isScanin=true";
            String rst = UtilHttp.doHttpGet(m_Context, url);
            if(rst == null){
                Log.v(TAG, "get return null");
                return false;
            }else{
                Log.v(TAG, "get return non null");
                try {
                    JSONObject responseObj = new JSONObject(rst);
                    Boolean isSuccess = Boolean.parseBoolean(responseObj.getString("IsSuccess"));
                    if(!isSuccess){
                        return false;
                    }

                    JSONObject titleObj = responseObj.getJSONObject("Title");
                        m_productName = "Product : "+titleObj.getString("ProductName");
                        m_StatusStr = "Status : "+titleObj.getString("Status");

                        String countStatus = titleObj.getString("UsageCountStatus");
                        if(!countStatus.equals("")){
                            m_UsageCountStatus = "Usage : "+ countStatus;
                        }else{
                            m_UsageCountStatus = "";
                        }

                        JSONArray records = responseObj.getJSONArray("Records");
                        int recordCount = records.length();
                        for (int i = 0; i< recordCount; i++){
                            JSONObject recordObj = records.getJSONObject(i);

                            String usedAt = recordObj.getString("UsedAt");
                            boolean cancelled = recordObj.getBoolean("Cancelled");
                            String facilityOperation = recordObj.getString("FacilityOperation");

                            String formattedRecord = "Used At : " + usedAt
                                                                 + "\r\n"
                                                    +"Attraction : "+ facilityOperation;
                            if(cancelled){
                                formattedRecord += " (USAGE CANCELLED) ";
                            }
                            m_usageRecord.add(i, formattedRecord);
                        }


                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            updateUsageMsgHandler.post(updateUsageInfoRunnable);
            m_dialog.dismiss();
        }
    }



    // ###----------------- NFC card reading --------------------------

    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    protected void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onNewIntent(Intent intent){
        getTagInfo(intent);
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void getTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] tagId = tag.getId();
        String str = ByteArrayToHexString(tagId);
        str = flipHexStr(str);
        Long cardNo = Long.parseLong(str, 16);

//        AlertDialog.Builder ab = new AlertDialog.Builder(this);
//        ab.setMessage("TAGID=" + cardNo);
//        ab.setPositiveButton("OK", null);
//        ab.show();

            // if select all , pass in the operation id encoded in ticket
            // there should NOT be many operations, take the only one

        m_ticketNo = cardNo.toString();
        if(m_ticketNo != null && !m_ticketNo.equals("")){
            new ticketAnalysisAsyncTask().execute();
        }

    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

    private String flipHexStr(String s){
        StringBuilder  result = new StringBuilder();
        for (int i = 0; i <=s.length()-2; i=i+2) {
            result.append(new StringBuilder(s.substring(i,i+2)).reverse());
        }
        return result.reverse().toString();
    }

    private String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F" };
        String out = "";

        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }
    //  ### -----------------------------------------------------------

}
