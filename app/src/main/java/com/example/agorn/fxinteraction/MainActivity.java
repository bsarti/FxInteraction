package com.example.agorn.fxinteraction;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private Button b_request;
    private EditText et_request;
    private TextView tv_output;
    private TextView tv_possibilities;
    private String[] possibilities = {"FamocoID", "IMEI", "wifi on", "wifi off",
                                        "FMS sync", "restart NFC", "bluetooth on", "bluetooth off"};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_request = (Button) findViewById(R.id.brequest);
        et_request = (EditText) findViewById(R.id.etrequest);
        tv_output = (TextView) findViewById(R.id.tvoptput);
        tv_possibilities = (TextView) findViewById(R.id.tvpossibilities);
        String mypossibilities = "";
        for(int i=0; i<possibilities.length; i++){
            if (i==0){
                mypossibilities = mypossibilities + possibilities[i];
            }
            else{
                mypossibilities = mypossibilities + ", " + possibilities[i];
            }
        }
        tv_possibilities.setText("Possible requests: " + mypossibilities);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClickRequest(View View) {
        String myrequest = et_request.getText().toString();
        Log.d(TAG, "Entered Request");
        switch (myrequest){
            case "FamocoID":
                tv_output.setText(get_FamocoID());
                break;
            case "IMEI":
                tv_output.setText(get_IMEI());
                break;
            case "wifi on":
                Log.d(TAG, "Entered Wifi");
                set_Wifi(true);
                break;
            case "wifi off":
                set_Wifi(false);
                break;
            case "FMS sync":
                FMS_sync();
                break;
            case "restart NFC":
                restart_nfc();
                break;
            case "bluetooth on":
                set_bluetooth(true);
                break;
            case "bluetooth off":
                set_bluetooth(false);
                break;
            case "restart device":
                restart_device();
            default:
                break;
        }
    }

    public void set_bluetooth (boolean status_bluetooth){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (status_bluetooth == true){
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }
        }
        else {
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }
        }
    }

    public String get_FamocoID(){
        return Build.SERIAL;
    }

    public String get_IMEI(){
        TelephonyManager mngr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;
    }

    public void restart_device(){
        Intent i = new Intent("com.famoco.intent.action.REBOOT");
        startService(i);
    }

    public void restart_nfc(){
        Intent i = new Intent("com.famoco.intent.action.RESTART_NFC");
        startActivity(i);
        Toast toast = Toast.makeText(getApplicationContext(),"Restarting NFC",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void set_Wifi(boolean wifistatus){
        if (wifistatus == true) {
            Intent i = new Intent("com.famoco.intent.action.ENABLE_WIFI");
            startActivity(i);
        }
        else{
            Intent i = new Intent("com.famoco.intent.action.DISABLE_WIFI");
            startActivity(i);
        }
    }

    public void FMS_sync(){
        Intent i = new Intent("com.famoco.intent.action.FMS_SYNC");
        sendBroadcast(i);
        Toast toast = Toast.makeText(getApplicationContext(),"Sync Requested",Toast.LENGTH_SHORT);
        toast.show();

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
