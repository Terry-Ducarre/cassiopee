package com.example.wirelessLocationMapping;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    CardView cv;
    ImageView type;
    TextView name;
    TextView time;
    TextView rssi;
    TextView address;

    public ViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        type = (ImageView) itemView.findViewById(R.id.typeIv);
        name = (TextView) itemView.findViewById(R.id.nameTv);
        time = (TextView) itemView.findViewById(R.id.timeTv);
        rssi = (TextView) itemView.findViewById(R.id.rssiTv);
        address = (TextView) itemView.findViewById(R.id.addressTv);
    }
}