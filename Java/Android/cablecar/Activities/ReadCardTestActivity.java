package com.example.fuyan.test.Activities;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fuyan.test.Config.GlobalValues;
import com.example.fuyan.test.LocalStorage.DataObjectConfig;
import com.example.fuyan.test.R;

import static com.example.fuyan.test.Helpers.ZipUtil.DecompressToBase64;

public class ReadCardTestActivity extends AppCompatActivity {

    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;


    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_nfc_card_test);

        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        mPendingIntent =
                PendingIntent.getActivity(this, 0, nfcIntent, 0);

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(getApplicationContext(), "NFC feature is supported on this device.", Toast.LENGTH_SHORT).show();
            return;
        }

        final Context context = this;


        final EditText txtScanning = (EditText) findViewById(R.id.txtScanning);
        txtScanning.setTextColor(Color.WHITE);

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
                Log.d("Testing---------", values);
                if (values.length() > 3 && (values.substring(values.length() - 3)).equals("###")) {
                    //watcherDlg.dismiss();

                    values = values.substring(0, values.length() - 3);
                    txtScanning.setText("");

                    String decompressed = DecompressToBase64(values);
                    if (!decompressed.equals("")) {
                        values = decompressed;
                    }

                    // show ticket code
                    AlertDialog.Builder ab = new AlertDialog.Builder(context);
                    ab.setMessage(values);
                    ab.setPositiveButton("OK", null);
                    ab.show();

                }
            }
        };
        txtScanning.addTextChangedListener(textWatcher);

        DataObjectConfig dataObjectConfig = new DataObjectConfig(context);
        String url = dataObjectConfig.getJSON(dataObjectConfig, DataObjectConfig.ConfigTableInfo.KEY_SERVERURL);

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("App Version :" + GlobalValues.AppVersion() + "\r\n"+
                      "Server Url :"  + url
        );
        ab.setPositiveButton("OK", null);
        ab.show();
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    protected void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        getTagInfo(intent);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void getTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] tagId = tag.getId();
        String str = ByteArrayToHexString(tagId);
        str = flipHexStr(str);
        Long cardNo = Long.parseLong(str, 16);

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("TAGID=" + cardNo);
        ab.setPositiveButton("OK", null);
        ab.show();
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

    private String flipHexStr(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= s.length() - 2; i = i + 2) {
            result.append(new StringBuilder(s.substring(i, i + 2)).reverse());
        }
        return result.reverse().toString();
    }

    private String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F"};
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
}

