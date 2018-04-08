package com.example.fuyan.test.Activities;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fuyan.test.Config.GlobalValues;
import com.example.fuyan.test.Config.ServerUrl;
import com.example.fuyan.test.Helpers.ZipUtil;
import com.example.fuyan.test.LocalStorage.DataObjectConfig;
import com.example.fuyan.test.LocalStorage.DataObjectOfflineUsage;
import com.example.fuyan.test.Models.IdNamePair;
import com.example.fuyan.test.Models.LineNameStationName;
import com.example.fuyan.test.Models.OfflineUsageModel;
import com.example.fuyan.test.R;
import com.example.fuyan.test.Models.TicketUsage;
import com.example.fuyan.test.Helpers.UtilHttp;
import com.google.gson.Gson;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "SentosaScan";
    private Context m_context;
    public static String m_operationId;
    public static String m_facilityId;
    private String m_facilityLineNum;
    private String m_operationLineNum;

    private String m_ticketNo;
    public static  int m_isScanIn;
    public static   String m_direction; // Entry, Exit
    private ActionBar m_actionBar;
    private TextView m_titleTextView;
    public static int m_stationId = -1;

    private String m_productName = "";
    private static int m_totalusage =0;

    public static final String ONLINE = "Online";
    public static final String OFFLINE = "Offline";
    private final int ONLINE_COLOR = Color.parseColor("#00aeef");
    private final int OFFLINE_COLOR = Color.parseColor("#eb1c24");
    public static String m_onlineStatus = OFFLINE;

    private HashMap<Integer, String> m_messageList = new HashMap<Integer, String>();

    private static ArrayList<String> blacklist = new ArrayList<>();

    //####### STATIONS
    private static final String STATION_MTFAB_ID = "CC MtFab";
    private static final String STATION_HFB_ID = "CC HbrFB";
    private static final String STATION_HFA_ID = "CC HbrFA";
    private static final String STATION_SENTOSA_ID = "CC Sentosa";

    private static final String STATION_MTFAB_NAME = "Mount Faber";
    private static final String STATION_HFB_NAME = "Hbr Front B";
    private static final String STATION_HFA_NAME = "Hbr Front A";
    private static final String STATION_SENTOSA_NAME = "Sentosa";

    private static final String STATION_MERLION_ID = "CC Merlion";
    private static final String STATION_IMBIAH_A_ID = "CC ImbA";
    private static final String STATION_IMBIAH_B_ID = "CC ImbB";
    private static final String STATION_SILOSO_ID = "CC Siloso";

    private static final String STATION_MERLION_NAME = "Merlion";
    private static final String STATION_IMBIAH_A_NAME = "Imbiah A";
    private static final String STATION_IMBIAH_B_NAME = "Imbiah B";
    private static final String STATION_SILOSO_NAME = "Siloso";
    //####### STATIONS

    //#### LINE NAMES
    private static final String MTFBR_LINE_NAME = "Mt.Faber Line";
    private static final String MTFBR_LINE_ID = "MtFaberLine";
    private static final String SENTOSA_LINE_NAME = "Sentosa Line";
    private static final String SENTOSA_LINE_ID = "STSLine";
    //####


    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        m_context = this;

        ////hide scanning textbox
        final EditText txtTicketScanningInfo = (EditText) findViewById(R.id.txtTicketScanningInfo);
        txtTicketScanningInfo.setTextColor(Color.WHITE);

        ////
        final TextView textviewTicketNo = (TextView) findViewById(R.id.textviewTicketNo);
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textviewTicketNo.setText("Validating Ticket...");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String values = txtTicketScanningInfo.getText().toString();

                if (values.length() > 3 && values.substring(values.length() - 3).equals("###")) {
                    //watcherDlg.dismiss();
                    txtTicketScanningInfo.setText("");

                    values = values.substring(0,values.length()-3);

                    String decompressed = ZipUtil.DecompressToBase64(values);
                    if(!decompressed.equals("")){
                        values = decompressed;
                    }


                    //// a non-sentosa QR code
                    if(values.length() < 35){
                        textviewTicketNo.setText(values);
                    }
                    else{
                        m_ticketNo = values.substring(15,35);
                        textviewTicketNo.setText(m_ticketNo);
                    }


                    validate(values);
                }
                return;

            }
        };
        txtTicketScanningInfo.addTextChangedListener(textWatcher);

        ////Log out
        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ResultActivity.this);
                alertDialog.setTitle("Logout");
                alertDialog.setMessage("Are you sure you want to Logout ?");

        /* When positive (yes/ok) is clicked */
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(ResultActivity.this, MyActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        Toast.makeText(ResultActivity.this, "Successfully Logged Out", Toast.LENGTH_LONG).show();
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

        ////reset counter
        Button btnClear = (Button) findViewById(R.id.clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_totalusage = 0;
                showTotal();
            }
        });


        //// init facilty info, update title text
        m_actionBar = getSupportActionBar();
        m_actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        m_titleTextView = new TextView(m_actionBar.getThemedContext());
        m_titleTextView.setTextSize(20);
        m_titleTextView.setTypeface(null, Typeface.BOLD);



        updateFacilityInfoAndTitlebar();


        Button btnDismiss = (Button) findViewById(R.id.btnDismissError);
        btnDismiss.setVisibility(View.INVISIBLE);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvstatus = (TextView) findViewById(R.id.tv_status);
                tvstatus.setBackgroundColor(ColorDefault);
                tvstatus.setText("");

                // also clear the stations marking
                updateStationImageAndText(true,"","","","",0,0,0,0);

                //// also clear the ticket number and product name
                final TextView lblTicketNo = (TextView)findViewById(R.id.textviewTicketNo);
                lblTicketNo.setText("");
                final TextView description = (TextView) findViewById(R.id.description);
                description.setText("");
            }
        });

        // ticket analysis
        Button btnAnalysis = (Button) findViewById(R.id.btnAnalysis);
        btnAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this, TicketAnalysisActivity.class);
                i.putExtra("ticketNumber", m_ticketNo);
                startActivity(i);
            }
        });

        //// cancel usage also is one of the 'admin functions'
        //// need to go through the login(online)/admin pincode(offline) process
        Button btnCancelUsage = (Button) findViewById(R.id.btnCancelUsage);
        btnCancelUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean isSuperAdmin = GlobalValues.getSuperAdmin(getApplicationContext());
                if (isSuperAdmin) {
                    Intent i = new Intent(ResultActivity.this, CancelUsageActivity.class);
                    startActivity(i);
                    return;
                }
                if (m_onlineStatus.equals(ONLINE)) {
                    Intent i = new Intent(ResultActivity.this, AdminLoginActivity.class);
                    i.putExtra("from", ResultActivity.class);
                    i.putExtra("to", CancelUsageActivity.class);
                    startActivity(i);
                } else {
                    // offline can not do cancel
                }


            }
        });


        // ##### manual validate start ############
        Button btnManual = (Button) findViewById(R.id.btnManual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ResultActivity.this);
                alertDialog.setTitle("Ticket number");
                alertDialog.setMessage("Enter ticket number");

                final EditText txtManualTicketNo = new EditText(ResultActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                txtManualTicketNo.setLayoutParams(lp);
                alertDialog.setView(txtManualTicketNo);
                //alertDialog.setIcon(R.drawable.);

                alertDialog.setPositiveButton("Validate",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                EditText scanningText = (EditText) findViewById(R.id.txtTicketScanningInfo);
                                scanningText.setText(txtManualTicketNo.getText());
                                validate(scanningText.getText().toString());
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
        // // ##### manual validate end ############



        updateOnlineStatusEverySeconds();

        autoClearMsgTimerTask();

        updateStationImageAndText(true,
                "","","","",
                0,0,0,0);

        showTotal();

        TextView tvstatus = (TextView) findViewById(R.id.tv_status);
        tvstatus.setBackgroundColor(ColorDefault);
        tvstatus.setText("");

        // get validation messages from server
        new getValidationMessageAsyncTask().execute(ServerUrl.getApiBaseUrl(m_context));

        // if there is any offline usage saved on local:
        // upload to server
        String urlRecordUsage = ServerUrl.getApiBaseUrl(m_context) + "RecordMobileOfflineUsage.ashx";
        new postOfflineUsageAsyncTask().execute(urlRecordUsage);


        new getBlackListAsyncTask().execute();
        // ####################
        // screen shots
        //
        // 1. Valid Ticket
        // 2. Ticket has expired on <Date> dd-MMM-yyyy HH:mm:ss
        // 3. Ticket has No Access
        // 4. Unknown format
        // 5. Ticket Blacklisted. Please refer guest to ticketing counter.
        // 6. returnd : Exchanged ticket. Please produce new ticket instead.
        // 7. void : Ticket status is voided. Please refer guest to ticketing counter.
        // 8. replaced upgrade : Ticket has been replaced by upgrade at the counter. Please use new ticket instead.
        // 9. No Usage Left.
        // 10. Ticket not yet valid.
        // 11. Attraction is in blockout period.
        // 12. Replaced add on :Ticket has been replaced with add on at the counter. Please use new ticket instead.
        // 13. replaced reprint : Ticket has been replaced due to reprint. Please use new ticket instead.
        // 14. Invalid Transport time range.
        // 15. unknown error : Unknown Error. Please contact Helpdesk @ XXXX XXXX
 //m_totalusage = 12;
       // showTotal();
        //updateResultText("Valid Ticket", ColorValid());
//        updateStationImageAndText(false,STATION_MTFAB_NAME + " (1)"
//                ,STATION_HFB_NAME+ " (1)"
//                ,STATION_SENTOSA_NAME+ " (1)"
//                ,STATION_HFA_NAME+ " (1)"
//                ,2,1,1,1);
       // updateResultText("Ticket has expired on 09-Nov-2016 20:00:00", ColorError);
        //updateResultText("Ticket has No Access", ColorError);
 //updateResultText("Unknown format", ColorError);
// updateResultText("Ticket Blacklisted. Please refer guest to ticketing counter.", ColorError);
// updateResultText("Exchanged ticket. Please produce new ticket instead.", ColorError);
  //      updateResultText("Ticket status is voided. Please refer guest to ticketing counter.", ColorError);
// updateResultText("Ticket has been replaced by upgrade at the counter. Please use new ticket instead.", ColorError);
       // updateResultText("No Usage Left.", ColorError);
// updateResultText("Ticket not yet valid.", ColorError);
        //updateResultText("Attraction is in blockout period.", ColorError);
        //updateResultText("Ticket has been replaced with add on at the counter. Please use new ticket instead.", ColorError);
        //updateResultText("Ticket has been replaced due to reprint. Please use new ticket instead.", ColorError);
       // updateResultText("Invalid Transport time range.", ColorError);
       // updateResultText("Unknown Error. Please contact Helpdesk @ +65 6736 8672", ColorError);
        // updateResultText("Invalid cable car exit.", ColorError);
        // ###################


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

    // ------- UI helpers ---------------------------
    private void updateFacilityInfoAndTitlebar() {
        String titleText = "";

        final String STATION = "Station: ";
        switch (m_stationId) {
            case 1:
                m_operationId = STATION_MTFAB_ID;
                titleText += STATION+STATION_MTFAB_NAME+"\n";
                break;
            case 2:
                m_operationId = STATION_HFB_ID;
                titleText += STATION+STATION_HFB_NAME+"\n";
                break;
            case 3:
                m_operationId = STATION_HFA_ID;
                titleText += STATION+STATION_HFA_NAME+"\n";
                break;
            case 4:
                m_operationId = STATION_SENTOSA_ID;
                titleText += STATION+STATION_SENTOSA_NAME+"\n";
                break;
            case 5:
                m_operationId = STATION_SILOSO_ID;
                titleText += STATION+STATION_SILOSO_NAME+"\n";
                break;
            case 6:
                m_operationId = STATION_IMBIAH_A_ID;
                titleText += STATION+STATION_IMBIAH_A_NAME+"\n";
                break;
            case 7:
                m_operationId = STATION_IMBIAH_B_ID;
                titleText += STATION+STATION_IMBIAH_B_NAME+"\n";
                break;
            case 8:
                m_operationId = STATION_MERLION_ID;
                titleText += STATION+STATION_MERLION_NAME+"\n";
                break;
        }



        if (isMtFabLine()) {
            m_facilityId = MTFBR_LINE_ID;
            titleText += MTFBR_LINE_NAME;

            if (m_direction.equals("Entry")) {
                titleText +=" (Entry)";
                m_isScanIn = 1;
            } else {
                titleText +=" (Exit)";
                m_isScanIn = 0;
            }
        }
        else {
            m_facilityId = SENTOSA_LINE_ID;
            titleText += SENTOSA_LINE_NAME;

            if (m_direction.equals("Entry")) {
                titleText +=" (Entry)";
                m_isScanIn = 1;
            } else {
                titleText +=" (Exit)";
                m_isScanIn = 0;
            }
        }

        Button btnCancelUsage = (Button)findViewById(R.id.btnCancelUsage);
        Button btnAnalysis = (Button) findViewById(R.id.btnAnalysis);

        if(m_onlineStatus == ONLINE){
            m_actionBar.setBackgroundDrawable(new ColorDrawable(ONLINE_COLOR));

            if(MyActivity.AdminActionCodes != null){
                boolean cancel = false;
                boolean analysis = false;
                try{
                    int len = MyActivity.AdminActionCodes.length();
                    for(int i = 0;i < len; i++){
                        if(MyActivity.AdminActionCodes.getInt(i) == 1){
                            cancel = true;
                        }
                        if(MyActivity.AdminActionCodes.getInt(i) == 2){
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
            m_actionBar.setBackgroundDrawable(new ColorDrawable(OFFLINE_COLOR));

            btnCancelUsage.setVisibility(View.INVISIBLE);
            btnAnalysis.setVisibility(View.INVISIBLE);
        }



        String username = GlobalValues.getUserName(getApplicationContext());

        m_titleTextView.setText(titleText + " " + m_onlineStatus + " "+username);
        m_actionBar.setCustomView(m_titleTextView);


        // set facility line number and operation line number
        try{
            DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
            String rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_FACILITYINFO);
            if(rst != null && rst != ""){
                JSONObject jso = new JSONObject(rst);
                JSONArray Facilities = jso.getJSONArray("FacilityOperations");
                if (Facilities.length() != 0) {
                    for (int i = 0; i < Facilities.length(); i++) {
                        IdNamePair info = new IdNamePair(
                                Facilities.getJSONObject(i).getString("FacilityId"),
                                Facilities.getJSONObject(i).getString("Name"),
                                Facilities.getJSONObject(i).getString("LineNumber"));
                        if(info.id.equals(m_facilityId)){
                            m_facilityLineNum = info.lineNum;
                            break;
                        }
                    }
                }
            }


            rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_OPERATIONINFO);
            if(rst != null && rst != ""){
                JSONObject jso = new JSONObject(rst);
                JSONArray Operations = jso.getJSONArray("FacilityOperations");
                if (Operations.length() != 0) {
                    for (int i = 0; i < Operations.length(); i++) {
                        JSONObject obj = Operations.getJSONObject(i);
                        String facilityId = obj.getString("FacilityId");
                        IdNamePair info = new IdNamePair(
                                obj.getString("OperationId"),
                                obj.getString("Name"),
                                obj.getString("LineNumber"));
                        if(info.id.equals(m_operationId) && facilityId.equals(m_facilityId)){
                            m_operationLineNum = info.lineNum;
                            break;
                        }
                    }
                }
            }

        }
        catch (JSONException ex){

        }


    }

    private boolean isMtFabLine() {
        return m_operationId.equals(STATION_MTFAB_ID)||
            m_operationId.equals(STATION_HFB_ID) ||
            m_operationId.equals(STATION_HFA_ID) ||
            m_operationId.equals(STATION_SENTOSA_ID);
    }

    public static LineNameStationName getLineNameStationName(int stationId){
        switch (stationId) {
            case 1:
                return new LineNameStationName(MTFBR_LINE_ID, MTFBR_LINE_NAME, STATION_MTFAB_ID, STATION_MTFAB_NAME);
            case 2:
                return new LineNameStationName(MTFBR_LINE_ID, MTFBR_LINE_NAME, STATION_HFB_ID, STATION_HFB_NAME);
            case 3:
                return new LineNameStationName(MTFBR_LINE_ID, MTFBR_LINE_NAME, STATION_HFA_ID, STATION_HFA_NAME);
            case 4:
                return new LineNameStationName(MTFBR_LINE_ID, MTFBR_LINE_NAME, STATION_SENTOSA_ID, STATION_SENTOSA_NAME);
            case 5:
                return new LineNameStationName(SENTOSA_LINE_ID, SENTOSA_LINE_NAME, STATION_SILOSO_ID, STATION_SILOSO_NAME);
            case 6:
                return new LineNameStationName(SENTOSA_LINE_ID, SENTOSA_LINE_NAME, STATION_IMBIAH_A_ID, STATION_IMBIAH_A_NAME);
            case 7:
                return new LineNameStationName(SENTOSA_LINE_ID, SENTOSA_LINE_NAME, STATION_IMBIAH_B_ID, STATION_IMBIAH_B_NAME);
            case 8:
                return new LineNameStationName(SENTOSA_LINE_ID, SENTOSA_LINE_NAME, STATION_MERLION_ID, STATION_MERLION_NAME);
            default:
                return new LineNameStationName("unknown","unknown","unknown","unknown");
        }
    }


    private void showTotal() {
        final TextView total = (TextView)findViewById(R.id.total);
        String usage = Integer.toString(m_totalusage);
        total.setText("Total : "+usage);
    }

    // ------------------------------------------------

    // -------- update online status timer , every 2 seconds ------

    final Runnable updateTitleRunnable = new Runnable() {
        public void run() {
            updateFacilityInfoAndTitlebar();
        }
    };
     public Handler updateTitleHandler = new Handler() ;
    private void updateOnlineStatusEverySeconds() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String url = ServerUrl.getApiBaseUrl(m_context)+"Ping.ashx";
                String rst = UtilHttp.doHttpGet(m_context, url);
                if(rst == null){
                    m_onlineStatus = OFFLINE;

                }else {
                    m_onlineStatus = ONLINE;
                }

                updateTitleHandler.post(updateTitleRunnable);
            }
        },0,2000);
    }
    // ------------------------------------------------------------



    // ---------------------- auto clear VALID message ---------------------
    // first time VALID scan show 'valid color 1' then 2nd time valid show 'valid color 2'
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

    private int ColorDefault = Color.parseColor("#f07d00");
    private int ColorError = Color.parseColor("#eb1c24");
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

            updateFacilityInfoAndTitlebar();
        }
    };
    public Handler autoClearMessageHandler = new Handler() ;
    private void autoClearMsgTimerTask(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // loop if the selected facility id - operation id need to be controlled
                autoClearMessageHandler.post(updateUIRunnable);
            }
        },0,1000);
    }


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

            // also clear the stations marking
            updateStationImageAndText(true,"","","","",0,0,0,0);

            //// also clear the ticket number and product name
            final TextView lblTicketNo = (TextView)findViewById(R.id.textviewTicketNo);
            lblTicketNo.setText("");
            final TextView description = (TextView) findViewById(R.id.description);
            description.setText("");


            m_msgLastShown = new Date();

            return;
        }

        // already cleared, hide dismiss button
        if(currentColor == ColorDefault){
            btnDismiss.setVisibility(View.INVISIBLE);
        }

    }
    //------------------------------------------------------------

    // ---------------- get validation message[status: message] async ----------------
    private class getValidationMessageAsyncTask extends AsyncTask<String, Void, Boolean>{
        private ProgressDialog m_dialog;
        private String err;
        private Integer status;
        private String message;

        @Override
        protected void onPreExecute() {
            m_dialog = ProgressDialog.show(m_context, null,
                    "Loading, please wait ...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean success = false;
            String rst;
            if(m_onlineStatus == ONLINE){
                String url =params[0] + "MessagesManagement/GetValidatorMessagesByType.ashx?validatorType=Mobile";
                rst = UtilHttp.doHttpGet(m_context, url);
                DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_VALIDATION_MESSAGE, rst);
                Log.v(TAG, "get return non null");
            }
            else{
                DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                rst = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_VALIDATION_MESSAGE);
                if(rst == null){
                    return false;
                }
            }

            try {
                JSONArray errorArray = new JSONArray(rst);
                Log.d("@@@@@@TESTING1------",Integer.valueOf(errorArray.length()).toString());
                for (int i=0; i<errorArray.length();i++){
                    status = errorArray.getJSONObject(i).getInt("TicketUsageStatus");
                    message = errorArray.getJSONObject(i).getString("Message");
                    Log.d("@@@@@@TESTING2------",Integer.valueOf(status).toString());
                    Log.d("@@@@@@TESTING3------",message);
                    m_messageList.put(status,message);
                    Log.d("@@@@@@TESTING4------",Integer.valueOf(m_messageList.size()).toString());
                }
                success = true;
            } catch (JSONException e) {
                e.printStackTrace();
                err = e.getMessage();
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            m_dialog.dismiss();
        }
    }
    // -------------------------------------------------------------------------------


    // ------------------- get message by status-------------------------------------------------
    // summary :
    // get the message[status:message] from server when loading page
    // cache the messages in local
    // get the message by status(and extra value: validUtil, validFrom, transformRanges if needed)

    private final int STATUS_VALID = 1;
    private final int STATUS_UNKNOWN = 4;
    private final int STATUS_UNKNOWN_FORMAT = 5;
    private final int STATUS_BLACKLIST = 7;
    private final int STATUS_EXPIRED = 2;
    private final int STATUS_NOACCESS = 3;
    private final int STATUS_INACTIVE = 12;
    private final int STATUS_INVALID_TRANSPORT = 40;
    private String getMessageByStatus(int status){
        try{
            if(m_messageList == null || m_messageList.size() == 0){
                try{
                    DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
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


            final String NEWLINE = "{NewLine}";
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
        }
        catch (Exception ex){
            return "Unable to locate error message.Please try logout and login again .";
        }
//        public enum TicketUsageStatus
//        {
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
                result += String.format("({0}-{1})",strFrom , strTo);
            }
            return result;
        }

        catch (JSONException ex){
            return "";
        }
    }

    // -------------------------------------------------------------------------------------------


    private void validate(String ticketInfo) {
        ImageView img1 = (ImageView) findViewById(R.id.imageView1);
        ImageView img2 = (ImageView) findViewById(R.id.imageView2);
        ImageView img3 = (ImageView) findViewById(R.id.imageView3);
        ImageView img4 = (ImageView) findViewById(R.id.imageView4);

        TextView t1 = (TextView) findViewById(R.id.station1);
        TextView t2 = (TextView) findViewById(R.id.station2);
        TextView t3 = (TextView) findViewById(R.id.station3);
        TextView t4 = (TextView) findViewById(R.id.station4);

        img1.setImageDrawable(null);
        img2.setImageDrawable(null);
        img3.setImageDrawable(null);
        img4.setImageDrawable(null);

        t1.setBackgroundColor(Color.TRANSPARENT);
        t2.setBackgroundColor(Color.TRANSPARENT);
        t3.setBackgroundColor(Color.TRANSPARENT);
        t4.setBackgroundColor(Color.TRANSPARENT);


        String json = ticketInfo;

        // validate based on online status
        if(m_onlineStatus == ONLINE){
            new OnlineValidationAsyncTask().execute(m_ticketNo, json);
        }
        else{
            new OfflineValidationAsyncTask().execute(json, "0");
        }

    }

    // ##################################
    // summary : online validation async
    // ##################################
    private class OnlineValidationAsyncTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pd;
        private Integer status = 0;
        private String ticketInfo = "";

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(m_context, null,
                    "Validating, please wait ...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            ticketInfo = params[1];
            boolean success = false;
            String url = ServerUrl.getApiBaseUrl(m_context) + "Validation/CheckTicketEntry?";
            url = url + "ticketnumber=" + params[0]
                    + "&facilityid=" + Uri.encode(m_facilityId)
                    + "&validatorId="+ Uri.encode(GlobalValues.DeviceName())
                    + "&facilityOperationId=" + Uri.encode(m_operationId)
                    +"&operatorName="+Uri.encode(MyActivity.LoginUserName)
                    + "&scanIn=" + m_isScanIn;
            String rst = UtilHttp.doHttpGet(m_context, url);
            if (rst == null) {
                Log.v(TAG, "get return null");
            } else {
                Log.v(TAG, "get return non null");
                try {
                    JSONObject jso = new JSONObject(rst);
                    boolean allow = jso.getBoolean("AllowEntry");

                    m_productName = jso.getString("ProductName");

                    if (allow) {
                        success = true;
                    } else {

                        status = jso.getInt("TicketErrorStatus");
                        m_validFrom = jso.getString("ValidFrom");
                        m_validUntil = jso.getString("ValidUntil");
                        m_TransportRanges = jso.getJSONArray("TransportTimeRanges");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean isOk) {
            pd.dismiss();
            TextView tvstatus = (TextView) findViewById(R.id.tv_status);
            tvstatus.setText(m_ticketNo);

            final TextView description = (TextView) findViewById(R.id.description);
            description.setText(m_productName);


            if (isOk) {
                if(m_isScanIn == 1){
                    String msg = getMessageByStatus(STATUS_VALID);
                    updateResultText(msg, ColorValid());
                }else{
                    updateResultText("Valid Exit", ColorValid());
                }


                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
                mp.start();

                m_totalusage +=1;
            } else {
                if(status == STATUS_UNKNOWN &&
                        GlobalValues.onlineUnknownAutoOfflineCheck(m_context) &&
                        !ticketInfo.equals("")){
                    new OfflineValidationAsyncTask().execute(ticketInfo,"1");
                    return;
                }



                String err = getMessageByStatus(status);
                updateResultText(err, ColorError);

                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
                mp.start();
            }
            new CablecarTicketUsageUpdating().execute(m_ticketNo);
        }
    }

    // ##########################################
    // summary : update station text and image:
    // FLAG 1 : means first time , show 'cycle' icon
    // FLAG 2 : means not first time, show 'check' icon
    // for 0 times used :StationName; for 0< times used: stationName (x)
    // ##########################################
    private class CablecarTicketUsageUpdating extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pd;

        private String textField1 = "";
        private String textField2 = "";
        private String textField3 = "";
        private String textField4 = "";
        private int FLAG1 = 0;
        private int FLAG2 = 0;
        private int FLAG3 = 0;
        private int FLAG4 = 0;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(m_context, null,
                    "Checking usage, please wait ...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean success = false;
            String url = ServerUrl.getApiBaseUrl(m_context) + "validation/getCablecarUsages?";
            url = url + "ticketCode=" + params[0] + "&facilityID=" + Uri.encode(m_facilityId);
            String rst = UtilHttp.doHttpGet(m_context, url);
            if (rst == null) {
                return false;
            } else {

                Log.v(TAG, "get return non null");
                try {
                    JSONArray jso = new JSONArray(rst);
                    Log.v("TESTING", "" + jso.length());


                    if (jso.length() == 0) {
                        if (m_facilityId.equals(MTFBR_LINE_ID)) {
                            textField1 = STATION_MTFAB_NAME;
                            textField2 = STATION_HFB_NAME;
                            textField3 = STATION_HFA_NAME;
                            textField4 = STATION_SENTOSA_NAME;
                        } else {
                            textField1 = STATION_SILOSO_NAME;
                            textField2 = STATION_IMBIAH_A_NAME;
                            textField3 = STATION_IMBIAH_B_NAME;
                            textField4 = STATION_MERLION_NAME;
                        }

                    } else {
                        if (m_facilityId.equals(MTFBR_LINE_ID)) {
                            textField1 = STATION_MTFAB_NAME;
                            textField2 = STATION_HFB_NAME;
                            textField3 = STATION_HFA_NAME;
                            textField4 = STATION_SENTOSA_NAME;
                            for (int i = 0; i < jso.length(); i++) {
                                JSONObject jsobj = jso.getJSONObject(i);
                                String stationID = jsobj.getString("OperationID");
                                int numVisited = jsobj.getInt("NumTimesUsed");
                                String timesVisited = String.valueOf(numVisited);

                                //// First station mark differently
                                if(i==0){
                                    if (stationID.equals(STATION_MTFAB_ID)) {
                                        textField1 = STATION_MTFAB_NAME+" (" + timesVisited+")";
                                        FLAG1 = 2;
                                    } else if (stationID.equals(STATION_HFB_ID)) {
                                        textField2 = STATION_HFB_NAME+" (" + timesVisited+")";
                                        FLAG2 = 2;
                                    } else if (stationID.equals(STATION_HFA_ID)) {
                                        textField3 = STATION_HFA_NAME+" (" + timesVisited+")";
                                        FLAG3 = 2;
                                    } else if (stationID.equals(STATION_SENTOSA_ID)) {
                                        textField4 = STATION_SENTOSA_NAME+" (" + timesVisited+")";
                                        FLAG4 = 2;
                                    }
                                }
                                else{
                                    if (stationID.equals(STATION_MTFAB_ID)) {
                                        textField1 = STATION_MTFAB_NAME+" (" + timesVisited+")";
                                        FLAG1 = 1;
                                    } else if (stationID.equals(STATION_HFB_ID)) {
                                        textField2 = STATION_HFB_NAME+" (" + timesVisited+")";
                                        FLAG2 = 1;
                                    } else if (stationID.equals(STATION_HFA_ID)) {
                                        textField3 = STATION_HFA_NAME+" (" + timesVisited+")";
                                        FLAG3 = 1;
                                    } else if (stationID.equals(STATION_SENTOSA_ID)) {
                                        textField4 = STATION_SENTOSA_NAME+" (" + timesVisited+")";
                                        FLAG4 = 1;
                                    }

                                }

                            }
                        } else {
                            textField1 = STATION_SILOSO_NAME;
                            textField2 = STATION_IMBIAH_A_NAME;
                            textField3 = STATION_IMBIAH_B_NAME;
                            textField4 = STATION_MERLION_NAME;


                            for (int i = 0; i < jso.length(); i++) {
                                JSONObject jsobj = jso.getJSONObject(i);
                                String stationID = jsobj.getString("OperationID");
                                int numVisited = jsobj.getInt("NumTimesUsed");
                                String timesVisited = String.valueOf(numVisited);
                                if (i==0){

                                    if (stationID.equals(STATION_SILOSO_ID)) {
                                        textField1 = STATION_SILOSO_NAME+" (" + timesVisited+")";
                                        FLAG1 = 2;
                                    } else if (stationID.equals(STATION_IMBIAH_A_ID)) {
                                        textField2 = STATION_IMBIAH_A_NAME+" (" + timesVisited+")";
                                        FLAG2 = 2;
                                    } else if (stationID.equals(STATION_IMBIAH_B_ID)) {
                                        textField3 = STATION_IMBIAH_B_NAME+" (" + timesVisited+")";
                                        FLAG3 = 2;
                                    } else if (stationID.equals(STATION_MERLION_ID)) {
                                        textField4 = STATION_MERLION_NAME+" (" + timesVisited+")";
                                        FLAG4 = 2;
                                    }
                                }
                                else {
                                    if (stationID.equals(STATION_SILOSO_ID)) {
                                        textField1 = STATION_SILOSO_NAME+" (" + timesVisited+")";
                                        FLAG1 = 1;
                                    } else if (stationID.equals(STATION_IMBIAH_A_ID)) {
                                        textField2 = STATION_IMBIAH_A_NAME+" (" + timesVisited+")";
                                        FLAG2 = 1;
                                    } else if (stationID.equals(STATION_IMBIAH_B_ID)) {
                                        textField3 = STATION_IMBIAH_B_NAME+" (" + timesVisited+")";
                                        FLAG3 = 1;
                                    } else if (stationID.equals(STATION_MERLION_ID)) {
                                        textField4 = STATION_MERLION_NAME+" (" + timesVisited+")";
                                        FLAG4 = 1;
                                    }
                                }
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pd.dismiss();
            updateStationImageAndText(false,
                    textField1, textField2, textField3, textField4,
                    FLAG1, FLAG2, FLAG3, FLAG4);
        }
    }
    private void updateStationImageAndText(Boolean clear,
                                           String textField1,String textField2,String textField3,String textField4,
                                           int Flag1,int Flag2,int Flag3,int Flag4
                                           ) {
        TextView t1 = (TextView) findViewById(R.id.station1);
        TextView t2 = (TextView) findViewById(R.id.station2);
        TextView t3 = (TextView) findViewById(R.id.station3);
        TextView t4 = (TextView) findViewById(R.id.station4);

        ImageView img1 = (ImageView) findViewById(R.id.imageView1);
        ImageView img2 = (ImageView) findViewById(R.id.imageView2);
        ImageView img3 = (ImageView) findViewById(R.id.imageView3);
        ImageView img4 = (ImageView) findViewById(R.id.imageView4);


        if(clear){
            if (m_facilityId.equals(MTFBR_LINE_ID)) {
                textField1 = STATION_MTFAB_NAME;
                textField2 = STATION_HFB_NAME;
                textField3 = STATION_HFA_NAME;
                textField4 = STATION_SENTOSA_NAME;
            } else {
                textField1 = STATION_SILOSO_NAME;
                textField2 = STATION_IMBIAH_A_NAME;
                textField3 = STATION_IMBIAH_B_NAME;
                textField4 = STATION_MERLION_NAME;
            }


            t1.setText(textField1);
            t2.setText(textField2);
            t3.setText(textField3);
            t4.setText(textField4);
            t1.setBackgroundColor(Color.WHITE);
            t2.setBackgroundColor(Color.WHITE);
            t3.setBackgroundColor(Color.WHITE);
            t4.setBackgroundColor(Color.WHITE);
            img1.setVisibility(View.INVISIBLE);
            img2.setVisibility(View.INVISIBLE);
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
            return;
        }


        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.VISIBLE);
        img3.setVisibility(View.VISIBLE);
        img4.setVisibility(View.VISIBLE);

        t1.setText(textField1);
        t2.setText(textField2);
        t3.setText(textField3);
        t4.setText(textField4);


        if (Flag1 == 1){
            t1.setBackgroundColor(Color.LTGRAY);
            img1.setImageResource(R.drawable.check);
        }
        if (Flag1 == 2){
            t1.setBackgroundColor(Color.LTGRAY);
            img1.setImageResource(R.drawable.first);
        }
        if (Flag2 == 1){
            t2.setBackgroundColor(Color.LTGRAY);
            img2.setImageResource(R.drawable.check);
        }
        if (Flag2 == 2){
            t2.setBackgroundColor(Color.LTGRAY);
            img2.setImageResource(R.drawable.first);
        }

        if (Flag3 == 1){
            t3.setBackgroundColor(Color.LTGRAY);
            img3.setImageResource(R.drawable.check);
        }
        if (Flag3 == 2){
            t3.setBackgroundColor(Color.LTGRAY);
            img3.setImageResource(R.drawable.first);
        }

        if (Flag4 == 1){
            t4.setBackgroundColor(Color.LTGRAY);
            img4.setImageResource(R.drawable.check);
        }
        if (Flag4 == 2){
            t4.setBackgroundColor(Color.LTGRAY);
            img4.setImageResource(R.drawable.first);
        }

        showTotal();

        final TextView lblTicketNo = (TextView)findViewById(R.id.textviewTicketNo);
        lblTicketNo.setText(m_ticketNo);
    }

    // ##########################################
    // summary : offline validation async
    // ##########################################
    private class OfflineValidationAsyncTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pd;
        private String err;
        private String TicketCondition;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(m_context, null,
                    "Validating, please wait ...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String json = params[0];
            TicketCondition = params[1];

            try{

                //// sample JSON
                //{“ticketCode”:”72016100420917775400”,
                // ”quantity”:1,
                // ”startDate”:”20161004 17:33:48”,
                // ”endDate”:”20170104 17:33:48”,
                // ”facilities”:[{“facilityId”:”037”,”operationIds”:[“1”],”facilityAction”:0,”daysOfWeekUsage”:[1,1,1,1,1,1,1]}]}

                // De-Serialize to JSON
                JSONObject ticketJson = new JSONObject(json);

                // check format
                m_ticketNo = ticketJson.getString("ticketCode");
                if(!isValidFormat(m_ticketNo)){
                    err = getMessageByStatus(STATUS_UNKNOWN_FORMAT);
                    return false;
                }

                if(blacklist != null && blacklist.contains(m_ticketNo)){
                    err = getMessageByStatus(STATUS_BLACKLIST);
                    return false;
                }

                // check access
                Boolean accessAllow = false;
                JSONArray facilityInfo = ticketJson.getJSONArray("facilities");
                int facilityInfoCount = facilityInfo.length();
                for (int i = 0;i < facilityInfoCount;i ++){
                    JSONObject infoObj = facilityInfo.getJSONObject(i);
                    String facilityId = infoObj.getString("facilityId");
                    JSONArray operations = infoObj.getJSONArray("operationIds");
                    int operationCount = operations.length();
                    for (int j = 0;j < operationCount; j++){
                        String operationId = operations.getString(j);
                        if(facilityId.equals(m_facilityLineNum) && operationId.equals(m_operationLineNum)){
                            accessAllow = true;
                            break;
                        }
                    }
                }
                if(!accessAllow){
                    err = getMessageByStatus(STATUS_NOACCESS);
                    return false;
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
                        err = getMessageByStatus(STATUS_INACTIVE);
                        return false;
                    }
                    if(endDate.before(now)){
                        m_validUntil = endDate.toString();
                        err = getMessageByStatus(STATUS_EXPIRED);
                        return false;
                    }
                }
                catch (ParseException ex){
                    err = "Ticket encoding error :[startDate], [endDate]";
                    return false;
                }

                return true;

            }
            catch (JSONException ex){
                err = "Ticket encoding error :"+ex.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pd.dismiss();
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
            //        Locale.ENGLISH);

            if (result) {
                // save offline valid usages
                DataObjectOfflineUsage dataObject = new DataObjectOfflineUsage(m_context);
                dataObject.add(dataObject, m_ticketNo, m_facilityId, m_operationId, TicketCondition);

                String msg = getMessageByStatus(STATUS_VALID);
                updateResultText(msg,ColorValid());
            } else {
                updateResultText(err,ColorError);
            }
            final TextView lblTicketCode = (TextView)findViewById(R.id.textviewTicketNo);
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
                String url = ServerUrl.getApiBaseUrl(m_context) +"GetBlacklisted.ashx?validatorId="+Uri.encode(GlobalValues.DeviceName());
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
                            blacklist.add(i,v);
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
                        OfflineUsageModel offlineUsageModel = new OfflineUsageModel(usageDate, ticketNo, facilityId,
                                operationId, validatorId, operatorName,ticketCondition);
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
                //
            }


            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

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

        if(m_onlineStatus == ONLINE){
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
        private String TAG = "card online validation";
        private String productName = "";
        private String expiredAt = "";
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean success = false;
            m_ticketNo = params[0];
            try {
                String url = ServerUrl.getApiBaseUrl(m_context)+"ValidateNfcCard.ashx" +
                        "?ticketNumber=" + m_ticketNo
                        + "&facilityId=" + Uri.encode(m_facilityId)
                        + "&facilityOperationId=" + Uri.encode(m_operationId)
                        +"&operatorName="+Uri.encode(MyActivity.LoginUserName)
                        +"&validatorId="+ Uri.encode(GlobalValues.DeviceName());
                String rst = UtilHttp.doHttpGet(m_context, url);
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
                        m_totalusage += 1;
                        JSONObject lookup = jso.getJSONObject("TicketLookup");
                        productName = lookup.getString("ItemDescription");
                        expiredAt = lookup.getString("EndDate");
                        try{
                            expiredAt = expiredAt.substring(0, 10);
                        }catch (Exception exx){

                        }

                    } else {
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

            if (result) {
                if(m_isScanIn == 1){
                    String msg = filterMessage(getMessageByStatus(STATUS_VALID));
                    updateResultText(msg, ColorValid());
                }else{
                    updateResultText("Valid Exit", ColorValid());
                }

                final TextView description = (TextView) findViewById(R.id.description);
                description.setText(productName + "\r\n" + "Expired at : "+ expiredAt);

                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
                mp.start();

                m_totalusage +=1;


            } else {
                String err = filterMessage(getMessageByStatus(status));
                updateResultText(err, ColorError);

                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
                mp.start();
            }

            TextView tvstatus = (TextView) findViewById(R.id.textviewTicketNo);
            tvstatus.setText(m_ticketNo);

            showTotal();
            new CablecarTicketUsageUpdating().execute(m_ticketNo);
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
    //  ### —————————————————————————————
}
