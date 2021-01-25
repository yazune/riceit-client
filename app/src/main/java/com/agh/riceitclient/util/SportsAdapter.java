package com.agh.riceitclient.util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
import com.agh.riceitclient.activity.MainActivity;
import com.agh.riceitclient.dto.RemoveSportDTO;
import com.agh.riceitclient.dto.UpdateSportDTO;
import com.agh.riceitclient.dto.UpdateUserDetailsDTO;
import com.agh.riceitclient.fragment.SportsUpdateFragment;
import com.agh.riceitclient.fragment.UserDetailsUpdateFragment;
import com.agh.riceitclient.model.Sport;

import java.util.ArrayList;
import java.util.HashMap;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ItemViewHolder>{

    private ArrayList<Sport> sports;
    HashMap<String, String> sportsTypeMap = SportConstants.generateMapWithCodesAsKeys();

    SportsListener sportsListener;
    FragmentManager supportFragmentManager;

    public SportsAdapter(SportsListener sportsListener, FragmentManager supportFragmentManager) {
        this.sportsListener = sportsListener;
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
    public SportsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item, parent, false);

        return new SportsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Sport sport = sports.get(position);
        holder.name.setText(sport.getName());

        String type = sportsTypeMap.get(sport.getSportType());
        holder.type.setText(type);
        holder.duration.setText(Integer.toString(sport.getDuration()));
        holder.kcalBurnt.setText(Double.toString(sport.getKcalBurnt()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Fragment sportsUpdateFragment = new SportsUpdateFragment(sportsListener);
                UpdateSportDTO updateSportDTO = new UpdateSportDTO();
                updateSportDTO.setName(sport.getName());
                updateSportDTO.setDuration(sport.getDuration());
                updateSportDTO.setKcalBurnt(sport.getKcalBurnt());
                updateSportDTO.setSportId(sport.getId());
                updateSportDTO.setSportType(sportsTypeMap.get(sport.getSportType()));

                Bundle args = new Bundle();

                args.putSerializable("updateSportDTO", updateSportDTO);
                sportsUpdateFragment.setArguments(args);
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.main_container, sportsUpdateFragment, "sportsUpdateFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            }
        });

        holder.btnRemoveSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sportsListener.enqueueRemoveSport(new RemoveSportDTO(sport.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

}
