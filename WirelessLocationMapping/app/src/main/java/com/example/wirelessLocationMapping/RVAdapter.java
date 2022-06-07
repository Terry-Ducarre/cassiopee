package com.example.wirelessLocationMapping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

public class RVAdapter extends RecyclerView.Adapter<ViewHolder> {
    //Vector<String> elements;
    ArrayList<ScanDevice> scanDevices;

    public RVAdapter(ArrayList<ScanDevice> list) {
        this.scanDevices=list;
        /*
        elements = new Vector<String>();
        for (int i=0; i<list.size(); i++)
            elements.add("" + i + list.get(i).getSsid());

         */
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.type.setImageResource(scanDevices.get(position).getImageId());
        holder.name.setText(scanDevices.get(position).getSsid());

        Date date= new Date(scanDevices.get(position).getTime()*1000L);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(date);
        holder.time.setText(""+formatted);

        holder.rssi.setText(""+scanDevices.get(position).getRssi());
        holder.address.setText(scanDevices.get(position).getBssid());
    }

    public int getItemCount() {
        return scanDevices.size();
    }}