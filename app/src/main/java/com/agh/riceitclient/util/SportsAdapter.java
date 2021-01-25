package com.agh.riceitclient.util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.model.Sport;

import java.util.ArrayList;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ItemViewHolder>{

    private ArrayList<Sport> sports;

    public SportsAdapter() {
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView type, duration, kcalBurnt;
        private ImageView btnRemoveSport;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sport_name);
            type = itemView.findViewById(R.id.sport_type);
            duration = itemView.findViewById(R.id.sport_duration_amount);
            kcalBurnt = itemView.findViewById(R.id.sport_burnt_amount);
            btnRemoveSport = itemView.findViewById(R.id.btn_sport_remove);
        }
    }

    public void setSports(ArrayList<Sport> sports){
        this.sports = sports;
    }

    @NonNull
    @Override
    public SportsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item, parent, false);

        return new SportsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Sport sport = sports.get(position);
        holder.name.setText(sport.getName());
        holder.type.setText(sport.getSportType());
        holder.duration.setText(Integer.toString(sport.getDuration()));
        holder.kcalBurnt.setText(Double.toString(sport.getKcalBurnt()));

        holder.btnRemoveSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

}
