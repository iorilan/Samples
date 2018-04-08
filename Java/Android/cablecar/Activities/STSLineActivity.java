package com.example.fuyan.test.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuyan.test.LocalStorage.DataObjectConfig;
import com.example.fuyan.test.R;

public class STSLineActivity extends AppCompatActivity {
    String actionBarText;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stsline);

        btnLogout = (Button)findViewById(R.id.btnLogout);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        TextView titleTextView = new TextView(actionBar.getThemedContext());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String username = pref.getString("user_name", null);

        TextView usernameTV = (TextView)findViewById(R.id.usernameTV);
        usernameTV.setTypeface(null, Typeface.BOLD);
        usernameTV.setText("User: " + username);
        actionBarText = "Sentosa Line";

        Intent intentin = getIntent();
        String scanIn = intentin.getStringExtra("scanId");

        if(scanIn.equals("Entry")){

            actionBarText+=" (Entry)";
            titleTextView.setText(actionBarText);
            titleTextView.setTextSize(20);
            titleTextView.setTypeface(null, Typeface.BOLD);
            actionBar.setCustomView(titleTextView);


        }
        else{
            actionBarText+=" (Exit)";
            titleTextView.setText(actionBarText);
            titleTextView.setTextSize(20);
            titleTextView.setTypeface(null, Typeface.BOLD);
            actionBar.setCustomView(titleTextView);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(STSLineActivity.this);

                alertDialog.setTitle("Logout"); // Sets title for your alertbox

                alertDialog.setMessage("Are you sure you want to Logout ?"); // Message to be displayed on alertbox


        /* When positive (yes/ok) is clicked */
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent i = new Intent(STSLineActivity.this, MyActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        Toast.makeText(STSLineActivity.this,"Successfully Logged Out", Toast.LENGTH_LONG).show();
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
    }

    public void doSiloso(View v) {
        Intent intentin = getIntent();
        String scanId = intentin.getStringExtra("scanId");

        DataObjectConfig dataObjectConfig = new DataObjectConfig(this);
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_STATION_ID,"5");
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_SCAN_DIRECTION,scanId);

        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("scanId", scanId);
        intent.putExtra("STATION", 5);

        ResultActivity.m_stationId = 5;
        ResultActivity.m_direction = scanId;
        startActivity(intent);

    }

    public void doIm1(View v) {
        Intent intentin = getIntent();
        String scanId = intentin.getStringExtra("scanId");

        DataObjectConfig dataObjectConfig = new DataObjectConfig(this);
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_STATION_ID,"6");
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_SCAN_DIRECTION,scanId);

        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("scanId", scanId);
        intent.putExtra("STATION", 6);

        ResultActivity.m_stationId = 6;
        ResultActivity.m_direction = scanId;
        startActivity(intent);

    }

    public void doIm2(View v) {
        Intent intentin = getIntent();
        String scanId = intentin.getStringExtra("scanId");


        DataObjectConfig dataObjectConfig = new DataObjectConfig(this);
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_STATION_ID,"7");
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_SCAN_DIRECTION,scanId);

        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("scanId", scanId);
        intent.putExtra("STATION", 7);

        ResultActivity.m_stationId = 7;
        ResultActivity.m_direction = scanId;
        startActivity(intent);
    }

    public void doMerlion(View v) {
        Intent intentin = getIntent();
        String scanId = intentin.getStringExtra("scanId");

        DataObjectConfig dataObjectConfig = new DataObjectConfig(this);
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_STATION_ID,"8");
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_SCAN_DIRECTION,scanId);

        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("scanId", scanId);
        intent.putExtra("STATION", 8);

        ResultActivity.m_stationId = 8;
        ResultActivity.m_direction = scanId;
        startActivity(intent);

    }
}
