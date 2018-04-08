package com.example.fuyan.test.Activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuyan.test.Config.GlobalValues;
import com.example.fuyan.test.LocalStorage.DataObjectConfig;
import com.example.fuyan.test.R;

public class MtFaberLineActivity extends AppCompatActivity {

    String actionBarText;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt_faber_line);

        btnLogout = (Button)findViewById(R.id.btnLogout);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        TextView titleTextView = new TextView(actionBar.getThemedContext());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String username = pref.getString("user_name", null);
        TextView usernameTV = (TextView)findViewById(R.id.usernameTV);
        usernameTV.setTypeface(null, Typeface.BOLD);
        usernameTV.setText("User: " + username);
        actionBarText = "Mt.Faber Line";

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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MtFaberLineActivity.this);
                alertDialog.setTitle("Logout");
                alertDialog.setMessage("Are you sure you want to Logout ?");


        /* When positive (yes/ok) is clicked */
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent i = new Intent(MtFaberLineActivity.this, MyActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        GlobalValues.clear(getApplicationContext());
                        finish();
                        Toast.makeText(MtFaberLineActivity.this,"Successfully Logged Out", Toast.LENGTH_LONG).show();
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

    public void goToMountFaber(View v) {

        Intent intentin = getIntent();
        String scanId = intentin.getStringExtra("scanId");

        DataObjectConfig dataObjectConfig = new DataObjectConfig(this);
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_STATION_ID,"1");
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_SCAN_DIRECTION,scanId);

        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("scanId", scanId);
        intent.putExtra("STATION", 1);

        ResultActivity.m_stationId = 1;
        ResultActivity.m_direction = scanId;
        startActivity(intent);
    }

    public void goToHf1(View v) {
        Intent intentin = getIntent();
        String scanId = intentin.getStringExtra("scanId");

        DataObjectConfig dataObjectConfig = new DataObjectConfig(this);
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_STATION_ID,"2");
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_SCAN_DIRECTION,scanId);

        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("scanId", scanId);
        intent.putExtra("STATION", 2);

        ResultActivity.m_stationId = 2;
        ResultActivity.m_direction = scanId;
        startActivity(intent);

    }

    public void goToHf2(View v) {
        Intent intentin = getIntent();
        String scanId = intentin.getStringExtra("scanId");

        DataObjectConfig dataObjectConfig = new DataObjectConfig(this);
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_STATION_ID,"3");
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_SCAN_DIRECTION,scanId);

        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("scanId", scanId);
        intent.putExtra("STATION", 3);

        ResultActivity.m_stationId = 3;
        ResultActivity.m_direction = scanId;
        startActivity(intent);

    }

    public void goToSentosa(View v) {
        Intent intentin = getIntent();
        String scanId = intentin.getStringExtra("scanId");

        DataObjectConfig dataObjectConfig = new DataObjectConfig(this);
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_STATION_ID,"4");
        dataObjectConfig.set(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_SCAN_DIRECTION,scanId);

        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("scanId", scanId);
        intent.putExtra("STATION", 4);

        ResultActivity.m_stationId = 4;
        ResultActivity.m_direction = scanId;
        startActivity(intent);

    }
}
