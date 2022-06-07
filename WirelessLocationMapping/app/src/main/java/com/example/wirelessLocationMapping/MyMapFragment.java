package com.example.wirelessLocationMapping;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MyMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap googleMap;

    public MyMapFragment()  {getMapAsync(this);}

    @Override
    public void onMapReady(final GoogleMap gmap) {
        this.googleMap = gmap;

        MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(getActivity());
        ArrayList<BtMarker> btMarkersList=myDatabaseHelper.getBtMarkers();
        ArrayList<WifiMarker> WifiMarkersList=myDatabaseHelper.getWifiMarkers();
        for(WifiMarker marker:WifiMarkersList){
            this.googleMap.addMarker(new MarkerOptions().position(new LatLng(marker.getLatitude(),marker.getLongitude())).title(marker.getSsid()+" - "+marker.getBssid()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
        for(BtMarker marker:btMarkersList){
            this.googleMap.addMarker(new MarkerOptions().position(new LatLng(marker.getLatitude(),marker.getLongitude())).title(marker.getName()+" - "+marker.getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }

        //set default position to the first wifi marker
        if(!WifiMarkersList.isEmpty()) {
            LatLng latLng=new LatLng(WifiMarkersList.get(0).getLatitude(), WifiMarkersList.get(0).getLongitude());
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        }else{
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(43.17,5.22)));//Paris coordinates
        }
/*
        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : "+ latLng.longitude);
                // Clear previously click position.
                googleMap.clear();
                // Zoom the Marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                // Add Marker on Map
                googleMap.addMarker(markerOptions);
            }
        });

 */
    }
}
