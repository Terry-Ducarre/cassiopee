package com.example.wirelessLocationMapping;

import android.location.Location;
import android.location.LocationListener;
import android.util.Log;

public class GPSListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
        //editLocation.setText("");
        //pb.setVisibility(View.INVISIBLE);
        //Toast.makeText(getBaseContext(),"Location changed: Lat: " + loc.getLatitude() + " Lng: "+ loc.getLongitude(), Toast.LENGTH_SHORT).show();
        double longitude = loc.getLongitude();
        Log.v("GPGS","longitude : "+longitude);
        double latitude =loc.getLatitude();
        Log.v("GPS", "latitude : "+latitude);
    }
}
