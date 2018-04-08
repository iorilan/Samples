package com.example.fuyan.test.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fuyan.test.Config.Constant;
import com.example.fuyan.test.LocalStorage.DataObjectConfig;
import com.example.fuyan.test.Models.LineNameStationName;
import com.example.fuyan.test.R;

public class DefaultStationActivity extends AppCompatActivity {

    private int m_stationId = 1;
    private String m_scanIn = Constant.Direction.Entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_station);

        // load default settings from cache

        DataObjectConfig dataObjectConfig = new DataObjectConfig(this);
        String stationId = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_STATION_ID);
        if(stationId != null){
            m_stationId =Integer.parseInt(stationId);
        }

        String scanIn = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_DEFAULT_SCAN_DIRECTION);
        if(scanIn != null){
            m_scanIn = scanIn;
        }

        // show facility info
        LineNameStationName facilityInfo = ResultActivity.getLineNameStationName(m_stationId);
        TextView tvLineName = (TextView)findViewById(R.id.lblDefaultLineName);
        TextView tvStationName = (TextView)findViewById(R.id.lblDefaultStationName);
        TextView tvDirection = (TextView)findViewById(R.id.lblDefaultDirection);
        tvLineName.setText(facilityInfo.facilityName);
        tvStationName.setText(facilityInfo.stationName);
        String directionText = m_scanIn ;
        tvDirection.setText(directionText);

        // continue button
        Button btnContinue = (Button)findViewById(R.id.btnDefaultStationContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DefaultStationActivity.this, ResultActivity.class);
                intent.putExtra("scanId", m_scanIn);
                intent.putExtra("STATION", m_stationId);
                startActivity(intent);
            }
        });

        // update button
        Button btnUpdate = (Button)findViewById(R.id.btnDefaultLocationUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyActivity.loginOnline){
                    Intent i = new Intent(DefaultStationActivity.this, AdminLoginActivity.class);
                    i.putExtra("from", DefaultStationActivity.class);
                    i.putExtra("to", HomeActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(DefaultStationActivity.this, PinCodeActivity.class);
                    i.putExtra("from", DefaultStationActivity.class);
                    i.putExtra("to", HomeActivity.class);
                    startActivity(i);
                }


            }
        });
    }
}
