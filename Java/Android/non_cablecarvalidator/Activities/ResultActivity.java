package com.example.tdw.non_cablecarvalidator.Activities;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdw.non_cablecarvalidator.Activities.Models.IdNamePair;
import com.example.tdw.non_cablecarvalidator.Activities.Models.TicketLookup;
import com.example.tdw.non_cablecarvalidator.Activities.Models.TicketUsage;
import com.example.tdw.non_cablecarvalidator.Config.GlobalValues;
import com.example.tdw.non_cablecarvalidator.Config.ServerUrls;
import com.example.tdw.non_cablecarvalidator.Helpers.UtilHttp;
import com.example.tdw.non_cablecarvalidator.Helpers.ZipUtil;
import com.example.tdw.non_cablecarvalidator.LocalStorage.DataObjectConfig;
import com.example.tdw.non_cablecarvalidator.LocalStorage.DataObjectOfflineUsage;
import com.example.tdw.non_cablecarvalidator.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ResultActivity extends AppCompatActivity {

    private Context m_Context;
    private EditText txtScanning;
    private Button btnLogout, btnClear;
    private String m_ticketNo;
    private Button btnValidate;
    private EditText txtManualTicketNo;
    private Button btnManualKeyIn;
    private String m_onlineStatus = "";

    private boolean m_ignoreShowTime = false;

    private static int m_totalValid =0;
    private static HashMap m_messageList;

    //// these are the *MUST* values to set. there is no default value to be assigned
    //// before come to this screen , must set it
    public static  String m_facilityid, m_facilityName, m_operationid,
            m_operationName, m_facilityLineNum, m_operationLineNum;

    private  final String AnyOperation = "All";

    public static Boolean m_isOnline = false;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String username = GlobalValues.getUserName(getApplicationContext());

        // change

        TextView usernameTV = (TextView)findViewById(R.id.lblUsername);
        usernameTV.setTypeface(null, Typeface.BOLD);
        usernameTV.setText("User: "+username);

        m_Context = this;

        pingServer();
        updateOnlineStatus();
        updateActionbarText();

        final EditText txtScanning = (EditText) findViewById(R.id.txtScanning);
        txtScanning.setTextColor(Color.WHITE);




        final TextView tvMsg = (TextView) findViewById(R.id.tvMsg);
        TextWatcher textWatcher = new TextWatcher() {
            //ProgressDialog watcherDlg ;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //tvMsg.setText("Scanning...");
                //watcherDlg = ProgressDialog.show(m_Context, null,
                  //      "Processing, please wait ...");
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String values = txtScanning.getText().toString();
                Log.d("Testing---------",values);
                if (values.length() > 3 && (values.substring(values.length() - 3)).equals("###")){
                    //watcherDlg.dismiss();

                    values = values.substring(0,values.length()-3);
                    txtScanning.setText(values);

                    String decompressed = ZipUtil.DecompressToBase64(values);
                    if(!decompressed.equals("")){
                        values = decompressed;
                    }

                    // show ticket code

                    if(values.length() < 35){
                        tvMsg.setText(values);
                        txtScanning.setText("");
                    }
                    else{
                        String ticketCode ;
                        ticketCode  = values.substring(15,35);
                        tvMsg.setText(ticketCode);
                    }


                    validate(values);
                }
            }
        };
        txtScanning.addTextChangedListener(textWatcher);



        new getValidationMessageAsyncTask().execute();




        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ResultActivity.this);

                alertDialog.setTitle("Logout"); // Sets title for your alertbox

                alertDialog.setMessage("Are you sure you want to Logout ?"); // Message to be displayed on alertbox

      /* When positive (yes/ok) is clicked */
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        GlobalValues.clear(getApplicationContext());
                        finish();

                        Intent i = new Intent(ResultActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);



                        Toast.makeText(ResultActivity.this,"Successfully Logged Out", Toast.LENGTH_LONG).show();
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


        btnClear = (Button)findViewById(R.id.btnResetCounter);
        btnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final TextView clearUsage = (TextView)findViewById(R.id.txtTotal);
                clearUsage.setText("Total: 0");
                m_totalValid =0;

            }
        });

        //// need to show the total usage when show this screen
        showTotal();

        // ######################### manual validation start #########################
        //// it is a toggle button 'manual' or 'scan'
        //// if it is 'manual' , will show the textbox and only allow 20 digits numbers
        txtManualTicketNo = (EditText)findViewById(R.id.txtTicketNum);
        btnManualKeyIn = (Button)findViewById(R.id.btnManualValidate);
        btnManualKeyIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(btnManualKeyIn.getText().equals("Manual")){
                    txtManualTicketNo.setVisibility(View.VISIBLE);
                    btnValidate.setVisibility(View.VISIBLE);
                    btnValidate.setEnabled(false);
                    btnManualKeyIn.setText("Scan");
            }
                else{
                    txtManualTicketNo.setVisibility(View.INVISIBLE);
                    btnValidate.setVisibility(View.INVISIBLE);
                    btnManualKeyIn.setText("Manual");
                }
            }
        });

        btnValidate = (Button)findViewById(R.id.btnValidate);
        btnValidate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final TextView ticketNo = (TextView)findViewById(R.id.txtTicketNum);
                ResultActivity.this.txtScanning = (EditText) findViewById(R.id.txtScanning);
                ResultActivity.this.txtScanning.setText(ticketNo.getText());

                validate(ticketNo.getText().toString());
            }
        });

        // only if type in 20 digits enable 'validate button'
        TextWatcher manualTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String values = txtManualTicketNo.getText().toString();
                if (values.length() != 20){
                    btnValidate.setEnabled(false);
                }
                else{
                    btnValidate.setEnabled(true);
                }
                return;

            }
        };
        txtManualTicketNo.addTextChangedListener(manualTextWatcher);
        // ######################### manual validation end #########################


        ////hide dismiss button when first time load page
        Button btnDismiss = (Button) findViewById(R.id.btnDismissError);
        btnDismiss.setVisibility(View.INVISIBLE);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvstatus = (TextView) findViewById(R.id.tv_status);
                tvstatus.setBackgroundColor(ColorDefault);
                tvstatus.setText("");

                //// also clear the ticket number and product name
                final TextView txtTicketNo = (TextView)findViewById(R.id.tvMsg);
                txtTicketNo.setText("");

                final TextView txtPdtName = (TextView)findViewById(R.id.description);
                txtPdtName.setText("");
            }
        });

        //// ticket analysis
        Button btnAnalysis = (Button) findViewById(R.id.btnAnalysis);
        btnAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ResultActivity.this, TicketAnalysisActivity.class);
                i.putExtra("ticketNumber", m_ticketNo);
                startActivity(i);
            }
        });


        ////=================== start Wings of Time 'ignore show time' =====================
        final Spinner ddlIgnoreOperation = (Spinner)findViewById(R.id.ddlIgnoreOperations);
        try{
            DataObjectConfig dataObjectConfig = new DataObjectConfig(m_Context);
            String rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_OPERATIONINFO);
            if(rst == null){
                Toast.makeText(getApplicationContext(), "Load data failed !", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jso = new JSONObject(rst);
            JSONArray FacilityOperations = jso.getJSONArray("FacilityOperations");
            ArrayList operationIds = new ArrayList<>();
            if (FacilityOperations.length() != 0) {
                for (int i = 0; i < FacilityOperations.length(); i++) {
                    JSONObject jsonObj = FacilityOperations.getJSONObject(i);
                    String facilityId = jsonObj.getString("FacilityId");
                    if(facilityId.equals(m_facilityid)){
                        operationIds.add(new IdNamePair(
                                jsonObj.getString("OperationId"),
                                jsonObj.getString("Name"),
                                jsonObj.getString("LineNumber")));
                    }
                }
            }

            ArrayAdapter adapter = new ArrayAdapter<>(m_Context, android.R.layout.simple_spinner_item, operationIds);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ddlIgnoreOperation.setAdapter(adapter);


        }catch (JSONException ex){

        }
        final Button btnIgnoreShow = (Button)findViewById(R.id.btnIgnoreShowTime);
        btnIgnoreShow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String IGNORE_SHOWTIME_ON = "Ignore show time:ON";
                final String CHECK_SHOWTIME_OFF = "Ignore show time:OFF";
                String currentText = btnIgnoreShow.getText().toString();
                if(currentText.equals(IGNORE_SHOWTIME_ON)){
                    m_ignoreShowTime = false;
                    ddlIgnoreOperation.setVisibility(View.INVISIBLE);
                    btnIgnoreShow.setText(CHECK_SHOWTIME_OFF);
                }
                else{
                    m_ignoreShowTime = true;
                    // user want take current operation to ignore
                    ddlIgnoreOperation.setVisibility(View.INVISIBLE);

                    btnIgnoreShow.setText(IGNORE_SHOWTIME_ON);
                }
            }
        });

        final String wot = "wot";
        if(m_operationid.toLowerCase().contains(wot)){
            btnIgnoreShow.setVisibility(View.VISIBLE);
        }else{
            btnIgnoreShow.setVisibility(View.INVISIBLE);
        }
        ////==================== end Wings of time ignore show time ========================


        //// cancel usage also is one of the 'admin functions'
        //// need to go through the login(online)/admin pincode(offline) process
        Button btnCancelUsage = (Button)findViewById(R.id.btnCancelUsage);
        btnCancelUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Boolean isSuperAdmin = GlobalValues.getSuperAdmin(getApplicationContext());
                if(isSuperAdmin){
                    Intent i = new Intent(ResultActivity.this, CancelUsageActivity.class);
                    startActivity(i);
                    return;
                }
                if(m_isOnline){
                    Intent i = new Intent(ResultActivity.this, AdminLoginActivity.class);
                    i.putExtra("from", ResultActivity.class);
                    i.putExtra("to", CancelUsageActivity.class);
                    startActivity(i);
                }else{
                    // do nothing , should not even show this button in offline mode
                }
            }
        });

        //// Auto clear VALID message
        autoClearMessageTask();



        //// this is for 'time based'(e.g Wings Of Time - 19:40) operation only
        //// take the the 'time' part and see if current time is in ['time'-40min ,'time' +40min]
        if(LoginActivity.AdminActionCodes == null){
            systemAutoSelectOperation();
        }


        // NFC card reading
        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mPendingIntent =
                PendingIntent.getActivity(this, 0,nfcIntent , 0);

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(getApplicationContext(), "NFC feature is supported on this device.", Toast.LENGTH_SHORT).show();
            return;
        }
    }



    private void updateActionbarText() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        TextView titleTextView = new TextView(actionBar.getThemedContext());
        String actionBarText = m_facilityName + ": " + m_operationName + " " + m_onlineStatus;

        titleTextView.setText(actionBarText);
        titleTextView.setTextSize(20);
        titleTextView.setTypeface(null, Typeface.BOLD);
        actionBar.setCustomView(titleTextView);

        Button btnCancelUsage = (Button)findViewById(R.id.btnCancelUsage);
        Button btnAnalysis = (Button) findViewById(R.id.btnAnalysis);
        if(m_onlineStatus == ONLINE){
            actionBar.setBackgroundDrawable(new ColorDrawable(ONLINE_COLOR));

            if(LoginActivity.AdminActionCodes != null){
                boolean cancel = false;
                boolean analysis = false;
                try{
                    int len = LoginActivity.AdminActionCodes.length();
                    for(int i = 0;i < len; i++){
                        if(LoginActivity.AdminActionCodes.getInt(i) == 1){
                            cancel = true;
                        }
                        if(LoginActivity.AdminActionCodes.getInt(i) == 2){
                            analysis = true;
                        }
                    }
                }
                catch (JSONException ex) {

                }
                if(!cancel){
                    btnCancelUsage.setVisibility(View.INVISIBLE);
                }else{
                    btnCancelUsage.setVisibility(View.VISIBLE);
                }
                if(!analysis){
                    btnAnalysis.setVisibility(View.INVISIBLE);
                }else{
                    btnAnalysis.setVisibility(View.VISIBLE);
                }
            }
            else{
                btnCancelUsage.setVisibility(View.INVISIBLE);
                btnAnalysis.setVisibility(View.INVISIBLE);
            }
        }
        else{
            actionBar.setBackgroundDrawable(new ColorDrawable(OFFLINE_COLOR));

            btnCancelUsage.setVisibility(View.INVISIBLE);
            btnAnalysis.setVisibility(View.INVISIBLE);
        }
    }

    private void showTotal() {
        final TextView totalusage = (TextView)findViewById(R.id.txtTotal);
        String usage = Integer.toString(m_totalValid);
        totalusage.setText("Total: "+usage);
    }

    // -----------------------------------
    // auto clear valid message
    // -----------------------------------
    private Date m_msgLastShown = null;
    final Runnable updateUIRunnable = new Runnable() {
        public void run() {

            if(m_msgLastShown != null){
                long now = new Date().getTime();
                long lastUpdatedAfterFiveSeconds = m_msgLastShown.getTime() + 10000;
                if(now >= lastUpdatedAfterFiveSeconds){
                    updateResultText("",ColorError);
                }
            }
        }
     };
    public Handler autoClearMessageHandler = new Handler() ;
    private void autoClearMessageTask(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // loop if the selected facility id - operation id need to be controlled
                autoClearMessageHandler.post(updateUIRunnable);


            }
        },0,1000);
    }
    // -------------------------------------


    // -------------------------------------
    // update validation result text
    // -------------------------------------
    private int ColorValid1 = Color.parseColor("#50b848");
    private int ColorValid2 = Color.parseColor("#1e90ff");
    private int _colorValid = ColorValid2;
    private int ColorValid (){
        if(_colorValid == ColorValid1){
            _colorValid = ColorValid2;
        }
        else{
            _colorValid = ColorValid1;
        }
        return _colorValid;
    }
    private boolean IsValidColor(int color){
        return color == ColorValid1 || color == ColorValid2;
    }


    private int ColorDefault = Color.parseColor("#F07D00");
    private int ColorError = Color.parseColor("#EB1C24");

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void updateResultText(String text, int color){
        TextView tvstatus = (TextView) findViewById(R.id.tv_status);
        int currentColor = ((ColorDrawable)(tvstatus.getBackground())).getColor();
        String currentText = tvstatus.getText().toString();
        Button btnDismiss = (Button) findViewById(R.id.btnDismissError);

        Boolean updatingmsg = !text.equals("");

        // showing information
        if(updatingmsg) {
            tvstatus.setBackgroundColor(color);
            tvstatus.setText(text);
            if(color == ColorError ||
                    color == ColorDefault){
                btnDismiss.setVisibility(View.VISIBLE);
            }
            else if(IsValidColor(color)){
                btnDismiss.setVisibility(View.INVISIBLE);
            }
            m_msgLastShown = new Date();

            return;
        }

        ////meaning calling from auto clear text thread

        // showing error text
        if(currentColor == ColorError && !currentText.equals("")){
            btnDismiss.setVisibility(View.VISIBLE);
            m_msgLastShown = new Date();
            return;
        }

        // showing valid text , clear the message
        if(IsValidColor(currentColor) && !currentText.equals("")){
            btnDismiss.setVisibility(View.INVISIBLE);
            tvstatus.setBackgroundColor(ColorDefault);
            tvstatus.setText("");

            m_msgLastShown = new Date();

            //// also clear the ticket number and product name
            final TextView txtTicketNo = (TextView)findViewById(R.id.tvMsg);
            txtTicketNo.setText("");

            final TextView txtPdtName = (TextView)findViewById(R.id.description);
            txtPdtName.setText("");

            return;
        }

        // already cleared, hide dismiss button
        if(currentColor == ColorDefault){
            btnDismiss.setVisibility(View.INVISIBLE);
        }

    }

    private String m_validFrom;
    private String m_validUntil;
    private JSONArray m_TransportRanges;
    private String formatTransportRange(JSONArray dateTimeStrings)
    {
        try{
            int count = dateTimeStrings.length();

            if (count%2 != 0)
            {
                throw new IllegalArgumentException("Transport time range strings should be even number.");
            }

            String result = "";
            for (int i = 0; i < count - 1; i += 2)
            {
                String strFrom = dateTimeStrings.getString(i);
                String strTo = dateTimeStrings.getString(i+1);
                result += String.format("(%s-%s)",strFrom , strTo);
            }
            return result;
        }

        catch (JSONException ex){
            return "";
        }

    }

    private final int STATUS_VALID = 1;
    private final int STATUS_UNKNOWN = 4;
    private final int STATUS_UNKNOWN_FORMAT = 5;
    private final int STATUS_BLACKLIST = 7;
    private final int STATUS_EXPIRED = 2;
    private final int STATUS_NOACCESS = 3;
    private final int STATUS_INACTIVE = 12;
    private final int STATUS_INVALID_TRANSPORT = 40;
    private String getMessageByStatus(int status){
        final String NEWLINE = "{NewLine}";

        //// if count not find message (as the pulling message from server is async),try load from cache and see how
        //// but if it is first time login & network slow enough to return the result ,here will be an exception
        if(m_messageList == null || m_messageList.size() == 0){
            try{
                DataObjectConfig dataObjectConfig = new DataObjectConfig(m_Context);
                String json = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_VALIDATION_MESSAGE);
                JSONArray errorArray = new JSONArray(json);
                m_messageList = new HashMap<Integer, String>();
                for (int i=0; i<errorArray.length();i++){
                    int status1 = errorArray.getJSONObject(i).getInt("TicketUsageStatus");
                    String message = errorArray.getJSONObject(i).getString("Message");
                    m_messageList.put(status1,message);
                }
            }
            catch (JSONException ex){
                return "Unable to locate error message, Please logout and login again.";
            }
        }
        String msg = m_messageList.get(status).toString();
        msg = msg.replace(NEWLINE,"\r\n");
        MessageFormat fmt = new MessageFormat(msg);
        if(status == STATUS_EXPIRED){ // expired
            Object[] args =  {m_validUntil};
            return fmt.format(args);
        }
        if(status == STATUS_INACTIVE){ // inactive
            Object[] args =  {m_validFrom};
            return fmt.format(args);
        }
        if(status == STATUS_INVALID_TRANSPORT){ // invalid transport range
            String str = formatTransportRange(m_TransportRanges);
            Object[] args =  {str};
           return fmt.format(args);
        }

        return msg;

//            ticket status :
//            Default = 0,// a default value , in case server did not return any ticket status after validation
//            Valid = 1,//— Valid
//            Expired = 2,//— Expired
//            NoAccess = 3,//— Not Valid
//            UnknownTicketNumber = 4,//— Unknown Ticket Number
//            UnknownFormat = 5,// — Unknown Ticket Format
//            ReEntry = 6,// — Re-entry
//            Blacklisted = 7,// — Ticket Blacklisted
//            Returned = 8,// — Ticket Returned
//            Voided = 9,//— Ticket Voided
//            //Replaced = 10,// the 'replaced' splitted to 3 enums (Upgrade, Addon, Reprint)
//            ReplacedUpgrade = 10,
//            NoUsageLeft = 11,//— No Usage Left
//            Inactive = 12,//— Ticket Inactive
//            Cancelled = 13,//— Usage cancelled
//            BlockoutPeriod = 14,//- blockout period
//            Redeemed = 15,
//            ReplacedAddOn = 16,
//            ReplacedReprint = 17,
//            Revalidated = 18,
//            ServiceRecovery = 19,
//            InvalidCablecarExit = 30,
//            InvalidTransportRange = 40,
//            ExitSuccess = 41,
//            ExitError=42,
//            WaitingScan = 50,
//            InsufficientFund = 80,
//            CEPASEntrySuccess=81,
//            UnknownError = 99
//        }

    }

    // -----------------------------------------


    // ------ online status timer task ---------
    private final int ONLINE_COLOR = Color.parseColor("#00AEEF");
    private final int OFFLINE_COLOR = Color.parseColor("#EB1C24");
    private final String ONLINE = "Online";
    private final String OFFLINE = "Offline";

    final Runnable updateTitleRunnable = new Runnable() {
        public void run() {
            updateActionbarText();
        }
    };
    public Handler updateTitleHandler = new Handler() ;
    private void pingServer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String url = ServerUrls.getBaseUrl(m_Context)+"Ping.ashx";
                String rst = UtilHttp.doHttpGet(m_Context, url);
                if(rst == null){
                    m_isOnline = false;
                }else {
                    m_isOnline = true;
                }
            }
        },0,2000);
    }
    private void updateOnlineStatus() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(m_isOnline){
                    m_onlineStatus = ONLINE;
                }else {
                    m_onlineStatus = OFFLINE;
                }
                updateTitleHandler.post(updateTitleRunnable);
            }
        },0,2000);
    }
    // -----------------------------------------


    // ##########################################
    // summary : get validation message async :
    // ##########################################
    private class getValidationMessageAsyncTask extends AsyncTask<Void, Void, Boolean>{
        private ProgressDialog m_dialog;
        private Integer status;
        private String message;
        private String TAG = "get validation message";
        @Override
        protected void onPreExecute() {
            m_dialog = ProgressDialog.show(m_Context, null,
                    "Loading, please wait ...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean success = false;
            String rst;
            if(m_isOnline){
                String url = ServerUrls.getBaseUrl(m_Context)+"MessagesManagement/GetValidatorMessagesByType.ashx?validatorType=Mobile";
                rst = UtilHttp.doHttpGet(m_Context, url);
                DataObjectConfig dataObjectConfig = new DataObjectConfig(m_Context);
                dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_VALIDATION_MESSAGE, rst);
                Log.v(TAG, "get return non null");
            }
            else{
                DataObjectConfig dataObjectConfig = new DataObjectConfig(m_Context);
                rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_VALIDATION_MESSAGE);
                if(rst == null){
                    return false;
                }
            }

            try {
                JSONArray errorArray = new JSONArray(rst);
                if(m_messageList == null){
                    m_messageList = new HashMap<Integer, String>();
                }

                for (int i=0; i<errorArray.length();i++){
                    status = errorArray.getJSONObject(i).getInt("TicketUsageStatus");
                    message = errorArray.getJSONObject(i).getString("Message");
                    m_messageList.put(status,message);
                }

                success = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            m_dialog.dismiss();
        }
    }



    //validate ticket
    private void validate(String ticketInfo) {

        final EditText txtScanning = (EditText) findViewById(R.id.txtScanning);
        txtScanning.setText("");

        if(ticketInfo.length() == 0){
            return;
        }


        String ticketNumber = ticketInfo;
        if(ticketInfo.length()> 35) {
            ticketNumber = ticketInfo.substring(15, 35);
        }

        // validate based on online status
        //final Spinner ddlIgnoreOperation = (Spinner)findViewById(R.id.ddlIgnoreOperations);
        //IdNamePair selectedIgnoreOperation = (IdNamePair) ddlIgnoreOperation.getSelectedItem();
        String ignoreOperationId = m_operationid;
        if(m_isOnline){
            // if select all , pass in the operation id encoded in ticket
            // there should NOT be many operations, take the only one

            new OnlineValidationAsyncTask().execute(ticketNumber, ignoreOperationId, ticketInfo);
        }
        else{
            new OfflineValidationAsyncTask().execute(ticketInfo, ignoreOperationId,"0");
        }

    }



    // ##########################################
    // summary : online validation async :
    // ##########################################
    private ProgressDialog m_processDialogValidation;
    public static boolean m_isPrompSelection = false;

    private TicketLookup validatingLookup;
    private class OnlineValidationAsyncTask extends AsyncTask<String, Void, Boolean> {

        private Integer status = 0 ;// default
        private String productName = "";
        private String TAG = "online validation";
        private boolean needChooseAttraction = false;

        private ArrayList<String> prompSelectionOperation = new ArrayList<>();
        AlertDialog.Builder alertBuilder ;
        private String ticketInfo = "";
        private String moreInfo ="";
        @Override
        protected void onPreExecute() {
            alertBuilder = new AlertDialog.Builder(ResultActivity.this);

            // still processing
            if(m_processDialogValidation != null && m_processDialogValidation.isShowing()){
                return;
            }

            m_processDialogValidation = ProgressDialog.show(m_Context, null,
                    "Validating, please wait ...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean success = false;
            m_ticketNo = params[0];
            String ignoreOperationId = params[1];
            ticketInfo = params[2];
            try {
                String url = ServerUrls.getBaseUrl(m_Context)+"ValidateTicketEntry.ashx" + "?ticketNumber=" + m_ticketNo + "&facilityID=" + Uri.encode(m_facilityid)
                        + "&facilityOperationId=" + Uri.encode(m_operationid) + "&ignoreShowTime=" + m_ignoreShowTime
                        +"&validatorId="+ Uri.encode(GlobalValues.DeviceName())
                        + "&ignoreOperationId="+Uri.encode(ignoreOperationId)
                        +"&operatorName="+Uri.encode(LoginActivity.LoginUserName);
                String rst = UtilHttp.doHttpGet(m_Context, url);
                if (rst == null) {
                    Log.v(TAG, "get return null");
                    success= false;
                    return success;
                } else {
                    Log.v(TAG, "get return non null");

                    JSONObject jso = new JSONObject(rst);
                    boolean allow = jso.getBoolean("AllowEntry");
                    if (allow) {
                        success = true;
                        m_totalValid += 1;

                        JSONObject objLookup = jso.getJSONObject("TicketLookupObject");
                        Boolean needSelectOperation = objLookup.getInt("FacilityAction") == 1;
                        m_isPrompSelection = needSelectOperation;
                        productName = jso.getString("ProductName");

                        if(needSelectOperation // this is for madame tussaut & Image of Singapore
                                || m_operationName.equals(AnyOperation)  // this is for 4D combol
                            )
                        {

                            needChooseAttraction = true;
                            validatingLookup = new TicketLookup(
                                    objLookup.getInt("EntryWeight"),
                                    objLookup.getString("ItemID"),
                                    objLookup.getString("ItemDescription"),
                                    objLookup.getInt("QtyGroup"),
                                    objLookup.getString("FacilityID"),
                                    objLookup.getString("OperationID"),
                                    objLookup.getString("TicketCode"),
                                    objLookup.getString("LineNum"),
                                    objLookup.getString("TicketTableID"),
                                    objLookup.getString("ID"),
                                    objLookup.getString("AccessID"),
                                    objLookup.getString("PackageLineGroup"),
                                    objLookup.getString("PackageID"),
                                    objLookup.getString("PackageLineItemId"),
                                    objLookup.getString("PackageLineItemDescription")
                            );

                            JSONArray prompArr = jso.getJSONArray("FacilityOperationSelectionList");
                            if(prompArr != null ){
                                int size = prompArr.length();
                                for(int i = 0; i < size;i ++){
                                    JSONObject obj = prompArr.getJSONObject(i);
                                    String opId = obj.getString("OperationId");
                                    prompSelectionOperation.add(i, opId);
                                }
                            }

                        }

                        int isPackage = objLookup.getInt("IsPackage");
                        if(isPackage == 1){
                            int limit = objLookup.getInt("GroupEntryLimit");
                            int remaining = objLookup.getInt("GroupEntryLimitRemain");
                            remaining = remaining > 0 ? remaining - 1 : 0;
                            moreInfo = "Remaining Usage : " + remaining + "/" + limit;
                        }

                    } else {
                        productName = jso.getString("ProductName");
                        status = jso.getInt("TicketErrorStatus");
                        m_validFrom = jso.getString("ValidFrom");
                        m_validUntil = jso.getString("ValidUntil");
                        m_TransportRanges = jso.getJSONArray("TransportTimeRanges");
                    }
                }
            }
             catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            m_processDialogValidation.dismiss();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                    Locale.ENGLISH);

            //TextView lblCurrentTime = (TextView) findViewById(R.id.tv_time);
            //lblCurrentTime.setText(sdf.format(new Date()));

            if (result) {
                if(needChooseAttraction)
                {
                    try{
                        //fetch the operations from cache with current facility
                        DataObjectConfig dataObjectConfig = new DataObjectConfig(m_Context);
                        String rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_OPERATIONINFO);
                        if(rst == null){
                            Toast.makeText(getApplicationContext(), "Load facility failed ", Toast.LENGTH_SHORT).show();
                            return ;
                        }

                        JSONObject jsoOp = new JSONObject(rst);
                        JSONArray facilityOperations = jsoOp.getJSONArray("FacilityOperations");
                        final ArrayList<IdNamePair> operationValues = new ArrayList<>();

                        List<String> operationNames = new ArrayList<>();
                        int count = 0;
                        if (facilityOperations.length() != 0) {

                            for (int i = 0; i < facilityOperations.length(); i++) {
                                JSONObject jsonObj = facilityOperations.getJSONObject(i);
                                String facilityId = jsonObj.getString("FacilityId");
                                if(facilityId.equals(m_facilityid)){
                                    operationValues.add(new IdNamePair(
                                            jsonObj.getString("OperationId"),
                                            jsonObj.getString("Name"),
                                            jsonObj.getString("LineNumber")));

                                    operationNames.add(count,jsonObj.getString("Name"));
                                    count ++;
                                }
                            }
                        }

                        if(operationNames.size() > 0){

                            alertBuilder.setTitle("Choose Attraction");

                            String[] arr = new String[operationNames.size()];
                            //// the API name is a bit confusing , suppose to call something like 'FillArray(..)'
                            operationNames.toArray(arr);


                            alertBuilder.setItems(arr, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int index) {
                                    try{
                                        IdNamePair selected = operationValues.get(index);

                                        if(validatingLookup==null){
                                            return;
                                        }

                                        boolean selectedCorrect = false;
                                        for (String op : prompSelectionOperation){
                                            if(op.equals(selected.id)){
                                                selectedCorrect = true;
                                                break;
                                            }
                                            Log.v(TAG, "selected : "+selected.id + ", acture : " + op);
                                        }

                                        if(!selectedCorrect){
                                            String errormsg = getMessageByStatus(STATUS_NOACCESS);
                                            updateResultText(errormsg, ColorError);
                                            Log.v(TAG, "should go back");
                                            return;
                                        }

                                        new validateSelectionAsync().execute(selected.id);
                                    }
                                    catch (Exception ex){

                                    }
                                    finally {
                                        dialog.dismiss();
                                    }
                                }
                            });
                            alertBuilder.create().show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Load facility Operation failed ", Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (JSONException ex){

                    }

                }
                else{
                    String msg = getMessageByStatus(STATUS_VALID);

                    //// for package message
                    if(!moreInfo.equals("")){
                        msg += "\r\n" + moreInfo;
                    }
                    updateResultText(msg,ColorValid());
                }

            } else {
                if(status == STATUS_UNKNOWN
                        && GlobalValues.onlineUnknownAutoOfflineCheck(m_Context)
                        && !ticketInfo.equals("")){
                    new OfflineValidationAsyncTask().execute(ticketInfo,"1");
                    return;
                }


                String msg = getMessageByStatus(status);
                updateResultText(msg,ColorError);
            }
            final TextView txtTicketNo = (TextView)findViewById(R.id.tvMsg);
            txtTicketNo.setText(m_ticketNo);

            final TextView txtPdtName = (TextView)findViewById(R.id.description);
            txtPdtName.setText(productName);

            showTotal();
        }
    }

    public class validateSelectionAsync extends AsyncTask<String, Void, Boolean> {
        private String TAG = "validate ticket against the selected operation async";
        private String productName;
        private int status;
        private String selectedOperation;
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                selectedOperation = params[0];
                String url = ServerUrls.getBaseUrl(m_Context)+"ValidateTicketEntry.ashx"
                        + "?ticketNumber=" + m_ticketNo + "&facilityID=" + Uri.encode(m_facilityid)
                        + "&facilityOperationId=" + Uri.encode(selectedOperation) + "&ignoreShowTime=" + m_ignoreShowTime
                        +"&validatorId="+ Uri.encode(GlobalValues.DeviceName())
                        + "&ignoreOperationId="
                        +"&operatorName="+Uri.encode(LoginActivity.LoginUserName)
                        +"&selected=1";
                String rst = UtilHttp.doHttpGet(m_Context, url);
                if (rst == null) {
                    updateResultText("No Response From Server", ColorError);
                } else {
                    JSONObject jso = new JSONObject(rst);
                    boolean allow = jso.getBoolean("AllowEntry");
                    if (!allow) {
                        productName = jso.getString("ProductName");
                        status = jso.getInt("TicketErrorStatus");
                        m_validFrom = jso.getString("ValidFrom");
                        m_validUntil = jso.getString("ValidUntil");
                        m_TransportRanges = jso.getJSONArray("TransportTimeRanges");
                        return false;
                    }
                }
            } catch (Exception ex) {
                //
            }


            return true;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            final TextView txtTicketNo = (TextView)findViewById(R.id.tvMsg);
            txtTicketNo.setText(m_ticketNo);

            final TextView txtPdtName = (TextView)findViewById(R.id.description);
            txtPdtName.setText(productName);

            if(result){
                TicketUsage usage = new TicketUsage(validatingLookup, m_facilityid, selectedOperation);
                usage.ValidatorID = GlobalValues.DeviceName();

                // post usage and forget
                // if there is any error ,will catch and log it on server
                String json = new Gson().toJson(usage);
                String usageUrl = ServerUrls.getBaseUrl(m_Context)+"RecordTicketUsage.ashx";

                //// java is not allowed to block the UI thread doing network post
                //// have to wrap this inside one async task
                new recordUsageAsync().execute(usageUrl, json);

                String msg = getMessageByStatus(STATUS_VALID);
                updateResultText(msg,ColorValid());
            }
            else{
                updateResultText(getMessageByStatus(status), ColorError);
            }
        }
    }
    public class recordUsageAsync extends AsyncTask<String, Void, Boolean> {
        private String TAG = "record usage async";
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                String url = params[0];
                String json = params[1];
                String result = UtilHttp.doHttpPostWithResult(m_Context, url ,json);
            }
            catch (Exception ex){
                //
            }


            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

        }

    }



    // ##########################################
    // summary : offline validation async :
    // check format
    // check attraction
    // check start date ,end date
    // save the *Valid* usage into local DB
    // ##########################################
    private class OfflineValidationAsyncTask extends AsyncTask<String, Void, Boolean> {
        int status = 0;
        private String error ;
        private String ticketCondition;
        @Override
        protected void onPreExecute() {
            if(m_processDialogValidation != null && m_processDialogValidation.isShowing()){
                return;
            }

            m_processDialogValidation = ProgressDialog.show(m_Context, null,
                    "Validating, please wait ...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String json = params[0];
            ticketCondition = params[1];

            try{

                // to test offline
//                json = "{    \"ticketCode\":\"72016080845350951401\",\n" +
//                        "               \"quantity\":1,\n" +
//                        "               \"startDate\": \"2016-08-08\",\n" +
//                        "               \"endDate\":\"2016-09-09\",\n" +
//                        "               \"facilities\":[{\"facilityId\":\"123\",\"operationIds\":[\"123\",\"234\"]" +
//                        "        }]}";
                //{     “ticketCode”:”72016100420917775400”,
                //      ”quantity”:1,”startDate”:”20161004 17:33:48”,
                // ”endDate”:”20170104 17:33:48”,
                // ”facilities”:[{“facilityId”:”037”,”operationIds”:[“1”],”facilityAction”:0,”daysOfWeekUsage”:[1,1,1,1,1,1,1]}
                // ]}



                // De-Serialize to JSON
                JSONObject ticketJson = new JSONObject(json);

                // check format
                m_ticketNo = ticketJson.getString("ticketCode");
                if(!isValidFormat(m_ticketNo)){
                    status = STATUS_UNKNOWN_FORMAT;
                    return false;
                }

                if(HomeActivity.blackListed != null && HomeActivity.blackListed.contains(m_ticketNo)){
                    status = STATUS_BLACKLIST;
                    return false;
                }

                // check access
                Boolean accessAllow = false;
                try{
                    JSONArray facilityInfo = ticketJson.getJSONArray("facilities");
                    int facilityInfoCount = facilityInfo.length();
                    for (int i = 0;i < facilityInfoCount;i ++){
                        JSONObject infoObj = facilityInfo.getJSONObject(i);
                        String facilityLineNumber = infoObj.getString("facilityId");
                        JSONArray operations = infoObj.getJSONArray("operationIds");
                        int operationCount = operations.length();
                        for (int j = 0;j < operationCount; j++){
                            String operationId = operations.getString(j);
                            if(facilityLineNumber.equals(m_facilityLineNum) &&
                                    (operationId.equals(m_operationLineNum) || operationId.equals("0") || m_ignoreShowTime)){
                                accessAllow = true;
                                break;
                            }
                        }
                    }
                }catch (JSONException jsonEx){
                    error = "Ticket Encoding error :" +jsonEx.getMessage();
                    return false;
                }



                if(!accessAllow){
                    status = STATUS_NOACCESS;
                    return false;
                }

                if(m_ignoreShowTime){
                    return true;
                }


                try{
                    // check start, end date
                    String startString = ticketJson.getString("startDate");
                    String endString = ticketJson.getString("endDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startDate = sdf.parse(startString);
                    Date endDate = sdf.parse(endString);

                    Date now = new Date();
                    if(startDate.after(now)){
                        m_validFrom = startDate.toString();
                        status = STATUS_INACTIVE;
                        return false;
                    }
                    if(endDate.before(now)){
                        m_validUntil = endDate.toString();
                        status = STATUS_EXPIRED;
                        return false;
                    }
                }
                catch (ParseException ex){
                    error = "Ticket Encoding error :" +ex.getMessage();
                    return false;
                }

                return true;
            }
            catch (JSONException ex){
                error = "Ticket Encoding error :" +ex.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            m_processDialogValidation.dismiss();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                    Locale.ENGLISH);
            //TextView tvtime = (TextView) findViewById(R.id.tv_time);
            //tvtime.setText(sdf.format(new Date()));

            if (result) {
                // save offline valid usages
                DataObjectOfflineUsage dataObject = new DataObjectOfflineUsage(m_Context);
                dataObject.add(dataObject, m_ticketNo, m_facilityid, m_operationid, ticketCondition);

                String msg = getMessageByStatus(STATUS_VALID);
                updateResultText(msg,ColorValid());

            } else {
                if(status != 0){
                    String msg = getMessageByStatus(status);
                    updateResultText(msg,ColorError);
                }else{
                    updateResultText(error, ColorError);
                }

            }
            final TextView lblTicketCode = (TextView)findViewById(R.id.tvMsg);
            lblTicketCode.setText(m_ticketNo);

            showTotal();
        }



        private Boolean isValidFormat(String number)
        {
            int interim = 0;
            for (int index = 0; index < number.length(); index++) {
                char currCh = number.charAt(index);
                if (! Character.isDigit(currCh)) {
                    throw new RuntimeException(number + " is not a valid number");
                }

                int currentIndex = currCh - 48;
                interim = matrix[interim][currentIndex];
            }

            return interim == 0;
        }
        private int[][] matrix = new int[][]
        {
                {0, 3, 1, 7, 5, 9, 8, 6, 4, 2},
                {7, 0, 9, 2, 1, 5, 4, 8, 6, 3},
                {4, 2, 0, 6, 8, 7, 1, 3, 5, 9},
                {1, 7, 5, 0, 9, 8, 3, 4, 2, 6},
                {6, 1, 2, 3, 0, 4, 5, 9, 7, 8},
                {3, 6, 7, 4, 2, 0, 9, 5, 8, 1},
                {5, 8, 6, 9, 7, 2, 0, 1, 3, 4},
                {8, 9, 4, 5, 3, 6, 2, 0, 1, 7},
                {9, 4, 3, 8, 6, 1, 7, 2, 0, 5},
                {2, 5, 8, 1, 4, 3, 6, 7, 9, 0}
        };

    }





    // ----------------------------------------------------------------
    // summary : this is for WOT ticket operation id auto selection
    // because the operation id is time based , ppl dunt want go back select the operation id manually each time
    // say now the time is within [17:10,17:50] , and there is operationId name 'WOT-17:30', select it. (before and after 20 mins)
    // --------------- auto select time based operation ---------------

    private final char TIMEBASED_SAPERATOR = '-';
    final Runnable updateOperationByTime = new Runnable() {
        private String toTime(String name){
            try{
                String timeStr = name.split("-")[1];
                //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                //Date time = sdf.parse(timeStr);
                return timeStr;
            }
            catch(Exception ex){
                return null;
            }
        }
        public void run() {
            try
            {
                if(!timeBasedFacility()){
                    return;
                }

                // 1. get WOT operations from cache
                DataObjectConfig dataObjectConfig = new DataObjectConfig(m_Context);
                String rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_OPERATIONINFO);
                if(rst == null){
                    //// something wrong , there is no cache found of facility operations
                    return ;
                }

                // 2. get operations form cache for current facility id
                JSONObject jso = new JSONObject(rst);
                JSONArray FacilityOperations = jso.getJSONArray("FacilityOperations");
                ArrayList<IdNamePair> cachedOperations = new ArrayList<>();
                if (FacilityOperations.length() != 0) {
                    for (int i = 0; i < FacilityOperations.length(); i++) {
                        JSONObject jsonObj = FacilityOperations.getJSONObject(i);
                        String facilityId = jsonObj.getString("FacilityId");
                        if(facilityId.equals(m_facilityid)){
                            // found time based facility
                            cachedOperations.add(new IdNamePair(
                                    jsonObj.getString("OperationId"),
                                    jsonObj.getString("Name"),
                                    jsonObj.getString("LineNumber")));
                        }
                    }
                }

                int count = cachedOperations.size();
                if(count == 0){
                    return;
                }

                //// 3. try find the matched operation
                IdNamePair foundOperation = null;
                for (int i = 0;i < count; i++){
                    String operationId = cachedOperations.get(i).id;
                    if(isTimeBasedOperation(operationId)){
                        String timeStr = toTime(operationId);

                        //// configuration wrong.
                        //// the format should be something like WOT:7:40
                        if(timeStr == null){
                            break;
                        }

                        Calendar now = Calendar.getInstance();
                        int hour = now.get(Calendar.HOUR);
                        int amPm = now.get(Calendar.AM_PM);
                        if(amPm == 1){
                            hour += 12;
                        }
                        int minute = now.get(Calendar.MINUTE);

                        String nowStr = hour + ":"+minute;
                        Boolean canSelect = canSelect(timeStr, nowStr);

                        if(canSelect){
                            foundOperation = cachedOperations.get(i);
                            break;
                        }
                    }
                }

                if(foundOperation != null)
                {
                    m_operationid = foundOperation.id;
                    m_operationName = foundOperation.name;
                    m_operationLineNum = foundOperation.lineNum;
                }
                else{
                   // do nothing
                }
            }
            catch(Exception exp){
               // ddlOperation.setEnabled(true);
            }

        }
    };

    private Boolean canSelect(String timeStr, String nowStr){
        int timeMin = getMiniutes(timeStr);
        int nowMin = getMiniutes(nowStr);

        // only if current time before and after the show 20min , then lock the operation
        return nowMin <= (timeMin + 40) && nowMin >= (timeMin - 40);
    }
    private int getMiniutes(String hhMM){
        String[] hourMinute = hhMM.split(":");
        int hr = Integer.parseInt(hourMinute[0]);
        int min = Integer.parseInt(hourMinute[1]);

        return hr * 60 + min;
    }
    private boolean timeBasedFacility (){
        return m_facilityid.toLowerCase().contains("wot");
    }
    public Handler autoSelectTimeBasedOperationHandler = new Handler() ;
    private void systemAutoSelectOperation() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // loop if the selected facility id - operation id need to be controlled
                autoSelectTimeBasedOperationHandler.post(updateOperationByTime);
            }
        },0,2000);
    }
    private Boolean isTimeBasedOperation(String name){
        return name.indexOf(TIMEBASED_SAPERATOR) > 0;
    }
// ----------------------------------------------------------------------------




     // ###----------------- NFC card reading --------------------------

    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    protected void onResume() {
        try{
            super.onResume();
            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
        }
        catch (Exception ex){

        }
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


        if(m_isOnline){
            // if select all , pass in the operation id encoded in ticket
            // there should NOT be many operations, take the only one


            new CardValidationAsyncTask().execute(cardNo.toString());
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

    private class CardValidationAsyncTask extends AsyncTask<String, Void, Boolean> {

        private Integer status = 0 ;// default
        private String productName = "";
        private String TAG = "card online validation";
        private String expiredAt = "";
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean success = false;
            m_ticketNo = params[0];
            try {
                String url = ServerUrls.getBaseUrl(m_Context)+"ValidateNfcCard.ashx" +
                        "?ticketNumber=" + m_ticketNo
                        + "&facilityId=" + Uri.encode(m_facilityid)
                        + "&facilityOperationId=" + Uri.encode(m_operationid)
                        +"&validatorId="+ Uri.encode(GlobalValues.DeviceName())
                        +"&operatorName="+Uri.encode(LoginActivity.LoginUserName);

                String rst = UtilHttp.doHttpGet(m_Context, url);
                if (rst == null) {
                    Log.v(TAG, "get return null");
                    success= false;
                    return success;
                } else {
                    Log.v(TAG, "get return non null");

                    JSONObject jso = new JSONObject(rst);
                    boolean allow = jso.getInt("ValidationStatus") == 1;


                    if (allow) {
                        success = true;
                        m_totalValid += 1;

                        JSONObject lookup = jso.getJSONObject("TicketLookup");
                        productName = lookup.getString("ItemDescription");
                        try{
                            expiredAt = lookup.getString("EndDate");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date startDate = sdf.parse(expiredAt);
                            expiredAt = startDate.toString().substring(0,11);
                        }catch (ParseException exx){

                        }
                    } else {
                        productName = "";
                        status = jso.getInt("ValidationStatus");
                        m_validFrom = jso.getString("ValidFrom");
                        m_validUntil = jso.getString("ValidUntil");
                        m_TransportRanges = jso.getJSONArray("TransportTimeRanges");
                    }
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);



            //TextView lblCurrentTime = (TextView) findViewById(R.id.tv_time);
            //lblCurrentTime.setText(sdf.format(new Date()));

            if (result) {

                final TextView description = (TextView) findViewById(R.id.description);
                description.setText(productName + "\r\n" + "Expired at : "+ expiredAt);

                String msg = filterMessage(getMessageByStatus(STATUS_VALID));
                updateResultText(msg,ColorValid());
            } else {
                String msg = filterMessage(getMessageByStatus(status));
                updateResultText(msg,ColorError);
            }
            final TextView txtTicketNo = (TextView)findViewById(R.id.tvMsg);
            txtTicketNo.setText(m_ticketNo);

            final TextView txtPdtName = (TextView)findViewById(R.id.description);
            txtPdtName.setText(productName);

            showTotal();
        }
    }

    private String filterMessage(String msg){
        if(msg.contains("ticket")){
            return msg.replace("ticket","card");
        }
        if(msg.contains("Ticket")){
            return msg.replace("Ticket","Card");
        }

        return msg;
    }
    //  ### -----------------------------------------------------------
}
