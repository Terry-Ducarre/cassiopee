package com.example.wirelessLocationMapping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_FILE_NAME = "mydatabase";

    //Table names
    private static final String SCAN_TABLE_NAME = "scan_table";


    // Scan Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "time";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_SSID = "SSID";
    private static final String KEY_BSSID = "BSSID";
    private static final String KEY_RSSI = "RSSI";
    private static final String KEY_TYPE = "type";
/*
    //Table names
    private static final String WIFI_TABLE_NAME = "wifi_table";
    private static final String BLUETOOTH_TABLE_NAME = "bluetooth_table";

    // Wifi Table Columns
    private static final String KEY_WIFI_ID = "id";
    private static final String KEY_WIFI_TIME = "time";
    private static final String KEY_WIFI_LONGITUDE = "longitude";
    private static final String KEY_WIFI_LATITUDE = "latitude";
    private static final String KEY_WIFI_SSID = "SSID";
    private static final String KEY_WIFI_BSSID = "BSSID";
    private static final String KEY_WIFI_RSSI = "RSSI";

    // Bluetooth Table Columns
    private static final String KEY_BLUETOOTH_ID = "id";
    private static final String KEY_BLUETOOTH_TIME = "time";
    private static final String KEY_BLUETOOTH_LONGITUDE = "longitude";
    private static final String KEY_BLUETOOTH_LATITUDE = "latitude";
    private static final String KEY_BLUETOOTH_NAME = "name";
    private static final String KEY_BLUETOOTH_RSSI = "RSSI";
    private static final String KEY_BLUETOOTH_ADDRESS = "address";
*/
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        String CREATE_WIFI_TABLE = "CREATE TABLE " + WIFI_TABLE_NAME +
                "(" +
                KEY_WIFI_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_WIFI_TIME + " REAL," +
                KEY_WIFI_LONGITUDE + " REAL," +
                KEY_WIFI_LATITUDE + " REAL," +
                KEY_WIFI_SSID + " TEXT," +
                KEY_WIFI_BSSID + " TEXT," +
                KEY_WIFI_RSSI + " REAL" +
                ")";

        String CREATE_BLUETOOTH_TABLE = "CREATE TABLE " + BLUETOOTH_TABLE_NAME +
                "(" +
                KEY_BLUETOOTH_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_BLUETOOTH_TIME + " REAL," +
                KEY_BLUETOOTH_LONGITUDE + " REAL," +
                KEY_BLUETOOTH_LATITUDE + " REAL," +
                KEY_BLUETOOTH_NAME + " TEXT," +
                KEY_BLUETOOTH_RSSI + " REAL," +
                KEY_BLUETOOTH_ADDRESS + " TEXT" +
                ")";

        db.execSQL(CREATE_WIFI_TABLE);
        db.execSQL(CREATE_BLUETOOTH_TABLE);
        */
        String CREATE_SCAN_TABLE = "CREATE TABLE " + SCAN_TABLE_NAME +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_TIME + " REAL," +
                KEY_LONGITUDE + " REAL," +
                KEY_LATITUDE + " REAL," +
                KEY_SSID + " TEXT," +
                KEY_BSSID + " TEXT," +
                KEY_RSSI + " REAL," +
                KEY_TYPE + " INTEGER" +
                ")";

        db.execSQL(CREATE_SCAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insertWifiData(long time, double longitude, double latitude, String ssid, String bssid, double rssi)
    {
        MainActivity.info(" Insert in database");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, time);
        values.put(KEY_LONGITUDE, longitude);
        values.put(KEY_LATITUDE, latitude);
        values.put(KEY_SSID, ssid);
        values.put(KEY_BSSID, bssid);
        values.put(KEY_RSSI, rssi);
        values.put(KEY_TYPE,1);
        db.insertOrThrow(SCAN_TABLE_NAME,null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void insertBluetoothData(long time, double longitude, double latitude, String name,String  address, double rssi)
    {
        MainActivity.info(" Insert in database");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, time);
        values.put(KEY_LONGITUDE, longitude);
        values.put(KEY_LATITUDE, latitude);
        values.put(KEY_SSID, name);
        values.put(KEY_BSSID, address);
        values.put(KEY_RSSI, rssi);
        values.put(KEY_TYPE,0);
        db.insertOrThrow(SCAN_TABLE_NAME,null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    /*
    public void readWifiTable()
    {
        MainActivity.info("Reading database...");
        String select = new String("SELECT * from " + WIFI_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        MainActivity.info("Number of entries: " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                MainActivity.info("Reading: " +
                        cursor.getInt(0) + " | "+
                        cursor.getInt(1) + " | "+
                        cursor.getFloat(2) + " | "+
                        cursor.getFloat(3) + " | "+
                        cursor.getString(4) + " | "+
                        cursor.getString(5) + " | "+
                        cursor.getFloat(6) + " | "
                );
            } while (cursor.moveToNext());
        }
    }

    public void readBluetoothTable()
    {
        MainActivity.info("Reading database...");
        String select = new String("SELECT * from " + BLUETOOTH_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        MainActivity.info("Number of entries: " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                MainActivity.info("Reading: " +
                        cursor.getInt(0) + " | "+
                        cursor.getInt(1) + " | "+
                        cursor.getFloat(2) + " | "+
                        cursor.getFloat(3) + " | "+
                        cursor.getString(4) + " | "+
                        cursor.getFloat(5) + " | "+
                        cursor.getString(6) + " | "
                );
            } while (cursor.moveToNext());
        }
    }
    */

    /*
    public ArrayList<WifiDevice> getWifiDevices()
    {
        String select = new String("SELECT * from " + WIFI_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        ArrayList<WifiDevice> result=new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                long time=cursor.getInt(1);
                double longitude=cursor.getFloat(2);
                double latitude=cursor.getFloat(3);
                String ssid = cursor.getString(4);
                String bssid=cursor.getString(5);
                double rssi=cursor.getFloat(6);
                result.add(new WifiDevice(time,longitude,latitude,ssid,bssid,rssi));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public ArrayList<BluetoothDevice> getBluetoothDevices()
    {
        String select = new String("SELECT * from " + WIFI_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        ArrayList<BluetoothDevice> result=new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                long time=cursor.getInt(1);
                double longitude=cursor.getFloat(2);
                double latitude=cursor.getFloat(3);
                String name = cursor.getString(4);
                double rssi=cursor.getFloat(5);
                String address=cursor.getString(6);
                result.add(new BluetoothDevice(time,longitude,latitude,name,rssi,address));
            } while (cursor.moveToNext());
        }
        return result;
    }
    */
    public ArrayList<ScanDevice> getScanDevices()
    {
        String select = new String("SELECT * from " + SCAN_TABLE_NAME+" ORDER BY "+ KEY_TIME+" DESC");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        ArrayList<ScanDevice> result=new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                long time=cursor.getInt(1);
                double longitude=cursor.getFloat(2);
                double latitude=cursor.getFloat(3);
                String ssid = cursor.getString(4);
                String bssid=cursor.getString(5);
                double rssi=cursor.getFloat(6);
                int type=cursor.getInt(7);
                result.add(new ScanDevice(time,longitude,latitude,ssid,bssid,rssi,type));
            } while (cursor.moveToNext());
        }
        return result;
    }
    /*
    public ArrayList<BtMarker> getBtMarkers(){//can't get max(time) in the query
        MainActivity.info("Getting bluetooth markers");
        String query=new String("SELECT DISTINCT("+ KEY_BLUETOOTH_ADDRESS+") as _id,"+KEY_BLUETOOTH_ADDRESS+","+KEY_BLUETOOTH_LONGITUDE+","+KEY_BLUETOOTH_LATITUDE+", "+KEY_BLUETOOTH_NAME+" from "+BLUETOOTH_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<BtMarker> result=new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String address=cursor.getString(1);
                float longitude=cursor.getFloat(2);
                float latitude=cursor.getFloat(3);
                String name=cursor.getString(4);
                result.add(new BtMarker(name,address,longitude,latitude));
                //MainActivity.info("new marker : "+address+" | "+longitude+" | "+latitude);
            } while (cursor.moveToNext());
        }
        return result;
    }
    public ArrayList<WifiMarker> getWifiMarkers(){//can't get max(time) in the query
        MainActivity.info("Getting wifi markers");
        String query=new String("SELECT DISTINCT("+ KEY_WIFI_BSSID+") as _id,"+KEY_WIFI_BSSID+","+KEY_WIFI_LONGITUDE+","+KEY_WIFI_LATITUDE+", "+KEY_WIFI_SSID+" from "+WIFI_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<WifiMarker> result=new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String bssid=cursor.getString(1);
                float longitude=cursor.getFloat(2);
                float latitude=cursor.getFloat(3);
                String ssid=cursor.getString(4);
                result.add(new WifiMarker(ssid,bssid,longitude,latitude));
                //MainActivity.info("new marker : "+address+" | "+longitude+" | "+latitude);
            } while (cursor.moveToNext());
        }
        return result;
    }
    */


    public ArrayList<BtMarker> getBtMarkers(){//can't get max(time) in the query
        MainActivity.info("Getting bluetooth markers");
        String query=new String("SELECT DISTINCT("+ KEY_BSSID+") as _id,"+KEY_BSSID+","+KEY_LONGITUDE+","+KEY_LATITUDE+", "+KEY_SSID+" from "+SCAN_TABLE_NAME+" where "+KEY_TYPE+"=0");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<BtMarker> result=new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String address=cursor.getString(1);
                float longitude=cursor.getFloat(2);
                float latitude=cursor.getFloat(3);
                String name=cursor.getString(4);
                result.add(new BtMarker(name,address,longitude,latitude));
                //MainActivity.info("new marker : "+address+" | "+longitude+" | "+latitude);
            } while (cursor.moveToNext());
        }
        return result;
    }
    public ArrayList<WifiMarker> getWifiMarkers(){//can't get max(time) in the query
        MainActivity.info("Getting wifi markers");
        String query=new String("SELECT DISTINCT("+ KEY_BSSID+") as _id,"+KEY_BSSID+","+KEY_LONGITUDE+","+KEY_LATITUDE+", "+KEY_SSID+" from "+SCAN_TABLE_NAME+" where "+KEY_TYPE+"=1");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<WifiMarker> result=new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String bssid=cursor.getString(1);
                float longitude=cursor.getFloat(2);
                float latitude=cursor.getFloat(3);
                String ssid=cursor.getString(4);
                result.add(new WifiMarker(ssid,bssid,longitude,latitude));
                //MainActivity.info("new marker : "+address+" | "+longitude+" | "+latitude);
            } while (cursor.moveToNext());
        }
        return result;
    }

}
