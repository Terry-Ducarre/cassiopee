package com.example.wirelessLocationMapping;

import static com.example.wirelessLocationMapping.MainActivity.DEBUG_BLUETOOTH_DATA;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

public class BluetoothReceiver extends BroadcastReceiver {
    private MainActivity mainActivity;
    long lastDiscoveryAt;

    public BluetoothReceiver( final MainActivity mainActivity, final Context context ) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context c, Intent intent) {
        MainActivity.info("BluetoothReceiver onReceive method called");
        String action = intent.getAction();

        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Un récupère le périphérique bluetooth détecté durant le scan
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            // Et on affiche ses informations dans un toast et dans la fenêtre LogCat
            String message = device.getName() + " | " + device.getAddress() + " | " + intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
            MainActivity.info(message);

            LocationManager locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            double rssi=intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
            long now = System.currentTimeMillis()/1000;
            double longitude=location.getLongitude();
            double latitude=location.getLatitude();
            String name=device.getName();
            String address=device.getAddress();
            mainActivity.myDatabaseHelper.insertBluetoothData(now,longitude,latitude,name,address,rssi);


        }
    }
    public void doBluetoothScan(){
        mainActivity.info("doBluetoothScan method called");
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.startDiscovery();
            lastDiscoveryAt = System.currentTimeMillis();
        } else {
            if (DEBUG_BLUETOOTH_DATA) {
                MainActivity.info("skipping bluetooth scan; discover already in progress (last scan started "+(System.currentTimeMillis()-lastDiscoveryAt)+"ms ago)");
            }
        }
    }
}
