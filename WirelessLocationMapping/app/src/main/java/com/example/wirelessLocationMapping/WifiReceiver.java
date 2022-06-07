package com.example.wirelessLocationMapping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

public class WifiReceiver extends BroadcastReceiver {
    private final MainActivity mainActivity;

    public WifiReceiver( final MainActivity mainActivity, final Context context ) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context c, Intent intent) {
        MainActivity.info("WifiReceiver onReceive method called");
        //lastScanResponseTime = now;
        // final long start = now;
        final WifiManager wifiManager = (WifiManager) mainActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> results = null;
        try {
            results = wifiManager.getScanResults(); // return can be null! informations about the list returned at https://developer.android.com/reference/android/net/wifi/ScanResult
            MainActivity.info("scan results : "+results.toString());
            LocationManager locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            long now = System.currentTimeMillis()/1000;
            for(ScanResult device: results){
                double longitude=location.getLongitude();
                double latitude=location.getLatitude();
                String ssid= device.SSID;
                String bssid=device.BSSID;
                double rssi=device.level;
                mainActivity.myDatabaseHelper.insertWifiData(now,longitude,latitude,ssid,bssid,rssi);
            }
        } catch (final SecurityException ex) {
            MainActivity.info("security exception getting scan results: " + ex);
        } catch (final Exception ex) {
            // ignore, happens on some vm's
            MainActivity.info("exception getting scan results: " + ex);
        }
    }
    public void doWifiScan(){
        mainActivity.info("doWifiScan method called");
        final WifiManager wifiManager = (WifiManager) mainActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        boolean success=wifiManager.startScan();//returns false if the location is not enabled
        mainActivity.info("doWifiScan returns : "+String.valueOf(success));
    }
}
