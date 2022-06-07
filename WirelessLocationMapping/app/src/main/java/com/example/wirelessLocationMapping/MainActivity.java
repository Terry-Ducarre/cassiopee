package com.example.wirelessLocationMapping;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final boolean DEBUG_BLUETOOTH_DATA = false;
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    TextView mStatusBlueTvValue;
    ImageView mBlueIv;
    Button mOnBtnBt, mOffBtnBt;

    TextView mStatusWifiTvValue;
    ImageView mWifiIv;
    Button mOnBtnWifi, mOffBtnWifi;

    BluetoothAdapter mBlueAdapter;
    WifiReceiver wifiReceiver;
    BluetoothReceiver bluetoothReceiver;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBtAndWifiDisplay();

        info("setupPermissions");
        setupPermissions();
        //info("setupActivationDialog");
        //setupActivationDialog();
        info("onCreate setup complete");
        this.wifiReceiver = new WifiReceiver(this, getApplicationContext());
        setupWifiReceiverIntent();
        this.bluetoothReceiver = new BluetoothReceiver(this, getApplicationContext());
        setupBluetoothReceiverIntent();

        myDatabaseHelper=new MyDatabaseHelper(MainActivity.this);

        Button doScanButton = (Button) findViewById(R.id.main_button);
        doScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiReceiver.doWifiScan();
                bluetoothReceiver.doBluetoothScan();
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                //info(location.toString());
                //info("time now : "+System.currentTimeMillis());
                //Intent intent = new Intent(MainActivity.this, listActivity.class);
                //startActivity(intent);
            }
        });
        Button listActivityButton = (Button) findViewById(R.id.listActivityButton);
        listActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), listActivity.class);
                startActivity(intent);
            }
        });

        /*
        Button readDbButton = (Button) findViewById(R.id.readDbButton);
        readDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabaseHelper.readWifiTable();
                myDatabaseHelper.readBluetoothTable();
            }
        });
        */

        Button goToListButton = (Button) findViewById(R.id.goToMapButton);
        goToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });
    }



    /*
    Wifi and bluetooth setup methods
     */
    private boolean canBtBeActivated() {
        try {
            final BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
            if (bt == null) {
                info("No bluetooth adapter");
                return false;
            }
            if (!bt.isEnabled()) {
                return true;
            }
        } catch (java.lang.SecurityException sex) {
            info("bt activation security exception");
        }
        return false;
    }

    private boolean canWifiBeActivated() {
        final WifiManager wifiManager = (WifiManager) this.getApplicationContext().
                getSystemService(Context.WIFI_SERVICE);
        if (null == wifiManager) {
            return false;
        }
        return !wifiManager.isWifiEnabled(); //&& !state.inEmulator;
    }

    //Not working very well but its okay for now
    private void setupActivationDialog() {
        final boolean canActivateBt = canBtBeActivated();
        final boolean canActivateWifi = canWifiBeActivated();
        info("can bt be activated : "+canActivateBt);
        if(canActivateBt){
            Toast toast= Toast.makeText(getApplicationContext(),"You need to activate the bluetooth",Toast.LENGTH_LONG);
            toast.show();
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBT);
        }
        info("can wifi be activated : " + canActivateWifi);
        if(canActivateWifi){
            Toast toast= Toast.makeText(getApplicationContext(),"You need to activate the wifi",Toast.LENGTH_LONG);
            toast.show();
            Intent enableWifi = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(enableWifi);
        }
    }
    /*
    Setting up the bluetooth receiver methods
     */
    private void setupBluetoothReceiverIntent() {
        // Sets the bluetooth receiver intents to ACTION_FOUND, so that the bluetoothReceiver (a BroadcastReceiver) "receives" the intent
        MainActivity.info("register Bluetooth BroadcastReceiver");
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bluetoothReceiver, filter);
    }
    /*
    Setting up the wifi receiver methods
     */
    private void setupWifiReceiverIntent() {
        // Sets the wifi receiver intents to SCAN_RESULTS_AVAILABLE_ACTION, so that the wifiReceiver (a BroadcastReceiver) "receives" the intent
        MainActivity.info("register Wifi BroadcastReceiver");
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiReceiver, intentFilter);
    }
    /*
    Logging methods
     */
    public static void info(final String value) {
        Log.i("I_TAG", Thread.currentThread().getName() + "] " + value);
    }
    /*
    permissions
     */
    private void setupPermissions() {
        //Needs to ask permissions for the broadcastReceiver to work
        final List<String> permissionsList = new ArrayList<>();
        permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsList.add(Manifest.permission.BLUETOOTH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 1);
        }
    }
    private void setupBtAndWifiDisplay(){
        //Code coming from https://devofandroid.blogspot.com/2018/07/bluetooth-example.html

        mStatusBlueTvValue = findViewById(R.id.statusBluetoothTvValue);
        mBlueIv       = findViewById(R.id.bluetoothIv);
        mOnBtnBt = findViewById(R.id.onBtnBt);
        mOffBtnBt = findViewById(R.id.offBtnBt);

        mStatusWifiTvValue = findViewById(R.id.statusWifiTvValue);
        mWifiIv       = findViewById(R.id.wifiIv);
        mOnBtnWifi = findViewById(R.id.onBtnWifi);
        mOffBtnWifi = findViewById(R.id.offBtnWifi);

        //adapter
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        //check if bluetooth is available or not
        if (mBlueAdapter == null){
            mStatusBlueTvValue.setText("Unavailable");
            mStatusBlueTvValue.setTextColor(Color.parseColor("#FF0000"));//red
        }
        else {
            mStatusBlueTvValue.setText("Available");
            mStatusBlueTvValue.setTextColor(Color.parseColor("#59c639"));//green
        }

        //set image according to bluetooth status(on/off)
        if (mBlueAdapter.isEnabled()){
            mBlueIv.setImageResource(R.drawable.ic_action_on);
        }
        else {
            mBlueIv.setImageResource(R.drawable.ic_action_off);
        }

        //check if wifi is available or not
        if (wifiManager == null){
            mStatusWifiTvValue.setText("Unavailable");
            mStatusWifiTvValue.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
            mStatusWifiTvValue.setText("Available");
            mStatusWifiTvValue.setTextColor(Color.parseColor("#59c639"));
        }

        //set image according to wifi status(on/off)
        if (wifiManager.isWifiEnabled()){
            mWifiIv.setImageResource(R.drawable.wf_action_on);
        }
        else {
            mWifiIv.setImageResource(R.drawable.wf_action_off);
        }

        //Bluetooth on btn click
        mOnBtnBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isEnabled()){
                    Toast.makeText(getApplicationContext(),"Turning On Bluetooth...",Toast.LENGTH_SHORT).show();
                    //intent to on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                    //startActivity(intent, REQUEST_ENABLE_BT);
                    mBlueIv.setImageResource(R.drawable.ic_action_on);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Bluetooth is already on",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Wifi on btn click
        mOnBtnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (!wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(getApplicationContext(),"Turning Wifi On",Toast.LENGTH_SHORT).show();
                    mWifiIv.setImageResource(R.drawable.wf_action_on);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wifi is already on",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Bluetooth off btn click
        mOffBtnBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()){
                    mBlueAdapter.disable();
                    Toast.makeText(getApplicationContext(),"Turning Bluetooth Off",Toast.LENGTH_SHORT).show();
                    mBlueIv.setImageResource(R.drawable.ic_action_off);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Bluetooth is already off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Wifi off btn click
        mOffBtnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(false);
                    Toast.makeText(getApplicationContext(),"Turning Wifi Off",Toast.LENGTH_SHORT).show();
                    mWifiIv.setImageResource(R.drawable.wf_action_off);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wifi is already off",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}