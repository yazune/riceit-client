package com.agh.riceitclient.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.fragment.SportUpdateFragment;
import com.agh.riceitclient.listener.SportListener;
import com.agh.riceitclient.model.Sport;

import java.util.ArrayList;
import java.util.HashMap;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.ItemViewHolder>{

    private ArrayList<Sport> sports;
    HashMap<String, String> sportTypeMap = SportConstants.generateMapWithCodesAsKeys();

    SportListener sportListener;
    FragmentManager supportFragmentManager;

    public SportAdapter(SportListener sportListener, FragmentManager supportFragmentManager) {
        this.sportListener = sportListener;
        this.supportFragmentManager = supportFragmentManager;
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
    public SportAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item, parent, false);

        return new SportAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Sport sport = sports.get(position);
        holder.name.setText(sport.getName());

        String type = sportTypeMap.get(sport.getSportType());
        holder.type.setText(type);
        holder.duration.setText(Integer.toString(sport.getDuration()));
        holder.kcalBurnt.setText(Double.toString(sport.getKcalBurnt()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Fragment sportUpdateFragment = new SportUpdateFragment(sportListener);
                SportUpdateTransfer sportUpdateTransfer = new SportUpdateTransfer();
                sportUpdateTransfer.setName(sport.getName());
                sportUpdateTransfer.setDuration(sport.getDuration());
                sportUpdateTransfer.setKcalBurnt(sport.getKcalBurnt());
                sportUpdateTransfer.setSportId(sport.getId());
                sportUpdateTransfer.setSportType(sportTypeMap.get(sport.getSportType()));

                Bundle args = new Bundle();

                args.putSerializable("sportUpdateTransfer", sportUpdateTransfer);
                sportUpdateFragment.setArguments(args);
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.main_container, sportUpdateFragment, "sportUpdateFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            }
        });

        holder.btnRemoveSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sportListener.enqueueRemoveSport(new IdTransfer(sport.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

}
