package com.example.fuyan.test.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fuyan.test.R;
import com.example.fuyan.test.LocalStorage.UserTableDataAccess;

public class RegisterActivity extends AppCompatActivity {

    Button btnBack, btnRegister;
    EditText txtUserName, txtPassword, txtConfirmPassword;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtUserName = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_name = txtUserName.getText().toString();
                String user_pass = txtPassword.getText().toString();
                String confirm_pass = txtConfirmPassword.getText().toString();
                UserTableDataAccess dbOperation = new UserTableDataAccess(context);

                if (user_name.length() > 0 && user_pass.length() >0 && confirm_pass.length()> 0) {
                    if (user_pass.equals(confirm_pass)) {

                        Cursor iterator = dbOperation.getIterator(dbOperation);
                        iterator.moveToFirst();

                        if (iterator.getCount() > 0) {
                            boolean registerStatus = true;
                            do {
                                if (user_name.equals(iterator.getString(0))) {
                                    registerStatus = false;
                                }

                            } while (iterator.moveToNext());
                            if (!registerStatus) {
                                Toast.makeText(getBaseContext(), "Existing User Name!", Toast.LENGTH_LONG).show();
                            } else {
                                dbOperation.insert(dbOperation, user_name, user_pass);
                                Toast.makeText(getBaseContext(), "Registration Success!", Toast.LENGTH_LONG).show();
                                Intent activity = new Intent(RegisterActivity.this, MyActivity.class);
                                startActivity(activity);
                            }
                        }else{
                            dbOperation.insert(dbOperation, user_name, user_pass);
                            Toast.makeText(getBaseContext(), "Registration Success!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegisterActivity.this, MyActivity.class);
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MyActivity.class);
                startActivity(i);

            }
        });
    }
}
