package com.example.wirelessLocationMapping;

public class BtMarker {
    String name;
    String address;
    float longitude;
    float latitude;

    public BtMarker(String name,String address,float longitude,float latitude){
        this.name=name;
        this.address=address;
        this.longitude=longitude;
        this.latitude=latitude;
    }
    public String toString(){
        return(this.address+" | "+this.longitude+" | "+this.latitude);
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
