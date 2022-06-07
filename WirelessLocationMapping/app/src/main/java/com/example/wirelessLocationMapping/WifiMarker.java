package com.example.wirelessLocationMapping;

public class WifiMarker {
    String ssid;
    String bssid;
    float longitude;
    float latitude;

    public WifiMarker(String ssid,String bssid,float longitude,float latitude){
        this.ssid=ssid;
        this.bssid=bssid;
        this.longitude=longitude;
        this.latitude=latitude;
    }
    public String toString(){
        return(this.bssid+" | "+this.longitude+" | "+this.latitude);
    }

    public String getSsid() {
        return ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
