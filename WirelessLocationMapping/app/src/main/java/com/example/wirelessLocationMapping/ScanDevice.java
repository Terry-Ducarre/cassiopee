package com.example.wirelessLocationMapping;

public class ScanDevice {
    long time;
    double longitude;
    double latitude;
    String ssid;
    String bssid;
    double rssi;
    int type;
    public int imageId;

    public int getImageId() {
        return imageId;
    }

    public ScanDevice(long time, double longitude, double latitude, String ssid, String bssid, double rssi, int type) {
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ssid = ssid;
        this.bssid = bssid;
        this.rssi = rssi;
        this.type = type;
        if(type==0){
            this.imageId=R.drawable.ic_action_on;
        }else if(type==1){
            this.imageId=R.drawable.wf_action_on;
        }
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "WifiDevice{" +
                "time=" + time +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", ssid='" + ssid + '\'' +
                ", bssid='" + bssid + '\'' +
                ", rssi=" + rssi +
                ", type=" + type +
                '}';
    }

    public long getTime() {
        return time;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getSsid() {
        return ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public double getRssi() {
        return rssi;
    }
}
