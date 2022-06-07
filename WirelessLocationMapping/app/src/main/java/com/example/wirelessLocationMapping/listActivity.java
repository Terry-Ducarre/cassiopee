package com.example.wirelessLocationMapping;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class listActivity extends AppCompatActivity {
    MyDatabaseHelper myDatabaseHelper;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        rv = (RecyclerView)findViewById(R.id.rvDbData);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        myDatabaseHelper=new MyDatabaseHelper(listActivity.this);
        ArrayList<ScanDevice> ScanDevices;
        ScanDevices=myDatabaseHelper.getScanDevices();

        RVAdapter rvAdapter=new RVAdapter(ScanDevices);
        rv.setAdapter(rvAdapter);
    }
}
