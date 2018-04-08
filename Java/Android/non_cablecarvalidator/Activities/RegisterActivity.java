package com.example.tdw.non_cablecarvalidator.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tdw.non_cablecarvalidator.Config.ServerUrls;
import com.example.tdw.non_cablecarvalidator.LocalStorage.DataObjectUserInfo;
import com.example.tdw.non_cablecarvalidator.R;

public class RegisterActivity extends AppCompatActivity {
    Button b1, b2;
    EditText t1, t2, t3;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        b1 = (Button) findViewById(R.id.button4);
        b2 = (Button) findViewById(R.id.button5);
        t1 = (EditText) findViewById(R.id.editText3);
        t2 = (EditText) findViewById(R.id.editText4);
        t3 = (EditText) findViewById(R.id.editText5);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_name = t1.getText().toString();
                String user_pass = t2.getText().toString();
                String confirm_pass = t3.getText().toString();
                DataObjectUserInfo dataContext = new DataObjectUserInfo(ctx);

                if (user_name.length() > 0 && user_pass.length() >0 && confirm_pass.length()> 0) {
                    if (user_pass.equals(confirm_pass)) {

                        Cursor CR = dataContext.getInformation(dataContext);
                        CR.moveToFirst();

                        if (CR.getCount() > 0) {
                            boolean registerStatus = true;
                            do {
                                if (user_name.equals(CR.getString(0))) {
                                    registerStatus = false;
                                }

                            } while (CR.moveToNext());
                            if (!registerStatus) {
                                Toast.makeText(getBaseContext(), "Existing User Name!", Toast.LENGTH_LONG).show();
                            } else {
                                dataContext.putInformation(dataContext, user_name, user_pass);
                                Toast.makeText(getBaseContext(), "Registration Success!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                            }
                        }else{
                            dataContext.putInformation(dataContext, user_name, user_pass);
                            Toast.makeText(getBaseContext(), "Registration Success!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Password Mismatch!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getBaseContext(), "Please Fill Up The Fields!", Toast.LENGTH_LONG).show();
                }

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });
    }
}
