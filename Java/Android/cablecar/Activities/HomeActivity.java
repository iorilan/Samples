package com.example.fuyan.test.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuyan.test.Config.Constant;
import com.example.fuyan.test.Config.GlobalValues;
import com.example.fuyan.test.R;

// select line page
public class HomeActivity extends AppCompatActivity {
    Button btnLogout, btnMtFaberLine, btnSentosaLine;
    TextView txtWelcome;
    Spinner dropdownDirection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dropdownDirection = (Spinner)findViewById(R.id.spinnerWelcome);
        String[] items = new String[]{Constant.Direction.Entry, Constant.Direction.Exit};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdownDirection.setAdapter(adapter);

        btnMtFaberLine = (Button)findViewById(R.id.btnMtFaberLine);
        btnSentosaLine = (Button)findViewById(R.id.btnSentosaLine);
        btnLogout = (Button)findViewById(R.id.btnLogout);


        String username = GlobalValues.getUserName(getApplicationContext());
        TextView textViewUserName = (TextView)findViewById(R.id.usernameTV);
        textViewUserName.setTypeface(null, Typeface.BOLD);
        textViewUserName.setText("User: " + username);


        txtWelcome = (TextView)findViewById(R.id.txtWelcome);
        txtWelcome.setText("WELCOME, " + username);

        btnMtFaberLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(HomeActivity.this, MtFaberLineActivity.class);
                activity.putExtra("scanId", dropdownDirection.getSelectedItem().toString());
                startActivity(activity);

            }
        });

        btnSentosaLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(HomeActivity.this, STSLineActivity.class);
                activity.putExtra("scanId", dropdownDirection.getSelectedItem().toString());
                startActivity(activity);

            }
        });




        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                alertDialog.setTitle("Logout");
                alertDialog.setMessage("Are you sure you want to Logout ?");


        /* When positive (yes/ok) is clicked */
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent i = new Intent(HomeActivity.this, MyActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
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

    }

    }
