package com.example.tdw.non_cablecarvalidator.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdw.non_cablecarvalidator.Config.GlobalValues;
import com.example.tdw.non_cablecarvalidator.Config.ServerUrls;
import com.example.tdw.non_cablecarvalidator.Helpers.UtilHttp;
import com.example.tdw.non_cablecarvalidator.LocalStorage.DataObjectConfig;
import com.example.tdw.non_cablecarvalidator.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminLoginActivity extends AppCompatActivity {

    private Context m_context;
    private Class<? extends Activity> m_from;
    private Class<? extends Activity> m_to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        m_context = this;

        Bundle extras = getIntent().getExtras();
        m_from = (Class<? extends Activity>)extras.getSerializable("from");
        m_to = (Class<? extends Activity>)extras.getSerializable("to");


        final EditText txtUsername = (EditText)findViewById(R.id.txtAdminLoginUserName);
        final EditText txtPassword = (EditText)findViewById(R.id.txtAdminLoginPwd);


        Button btnSubmit = (Button)findViewById(R.id.btnAdminLogin);
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // TODO
                String loginUrl = ServerUrls.getBaseUrl(m_context) +"GetAdminPincode.ashx?username="
                        + Uri.encode(txtUsername.getText().toString()) +
                        "&password="+Uri.encode(txtPassword.getText().toString());

                new adminLoginGetPinCodeAsyncTask().execute(loginUrl);
            }
        });

        Button btnBack = (Button)findViewById(R.id.btnAdminLoginBack);
        btnBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminLoginActivity.this, m_from);
                startActivity(i);
            }
        });
    }

    public class adminLoginGetPinCodeAsyncTask extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog m_progressDlg;
        private boolean isSuperAdmin = false;
        @Override
        protected void onPreExecute() {
            m_progressDlg = ProgressDialog.show(m_context, null,
                    "Processing...");
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                String rst =
                        UtilHttp.doHttpGet(m_context, params[0]);
                //{
                // public bool IsSuperAdmin { get; set; }
                // public bool IsSuccess { get; set; }
                // public string ErrorMsg { get; set; }
                // public IEnumerable<string> Pincodes { get; set; }
                //}

                if (rst == null) {
                    return false;
                } else {
                    // verify the login result
                    JSONObject jso = new JSONObject(rst);
                    boolean isSuccess = jso.getBoolean("IsSuccess");
                    isSuperAdmin = jso.getBoolean("IsSuperAdmin");
                    if(isSuccess){
                        return true;
                    }else{
                        return false;
                    }
                }

            }catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            m_progressDlg.dismiss();

            if (result) {
                if(isSuperAdmin){
                    Intent i = new Intent(AdminLoginActivity.this, m_to);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "This user is not super admin", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Login Failed! Wrong Credentials", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
