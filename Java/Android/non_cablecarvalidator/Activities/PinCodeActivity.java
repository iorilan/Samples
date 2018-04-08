package com.example.tdw.non_cablecarvalidator.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tdw.non_cablecarvalidator.Config.GlobalValues;
import com.example.tdw.non_cablecarvalidator.LocalStorage.DataObjectConfig;
import com.example.tdw.non_cablecarvalidator.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PinCodeActivity extends AppCompatActivity {

    private Context m_context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        m_context = this;

        Bundle extras = getIntent().getExtras();
        final Class<? extends Activity> from = (Class<? extends Activity>)extras.getSerializable("from");
        final Class<? extends Activity> to = (Class<? extends Activity>)extras.getSerializable("to");




        Button btnSubmit = (Button)findViewById(R.id.btnSubmitAdminCode);
        btnSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                EditText txtPin = (EditText)findViewById(R.id.txtPincode);
                String pin = txtPin.getText().toString();

                DataObjectConfig dataObjectConfig = new DataObjectConfig(m_context);
                String json = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_ADMIN_PINCODE);
                ArrayList<String> pinCodes = new ArrayList<>();
                pinCodes = new Gson().fromJson(json, pinCodes.getClass());
                int count = pinCodes.size();
                boolean foundMatch = false;
                for (int i = 0;i < count; i++){
                    if(pin.equals(pinCodes.get(i))){
                        foundMatch = true;
                        break;
                    }
                }

                if(foundMatch){
                    GlobalValues.setSuperAdmin(getApplicationContext(), true);

                    Intent i = new Intent(PinCodeActivity.this, to);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Pin Code is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Button btnBack = (Button)findViewById(R.id.btnAdminPinBack);
        btnBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(PinCodeActivity.this, from);
                startActivity(i);
            }
        });
    }


}
