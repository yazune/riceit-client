package com.agh.riceitclient.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.UpdateFoodDTO;
import com.agh.riceitclient.fragment.AddFoodFragment;
import com.agh.riceitclient.fragment.DecideFragment;
import com.agh.riceitclient.fragment.UpdateFoodFragment;
import com.agh.riceitclient.model.Food;

import java.util.ArrayList;

public class FoodsAdapter extends  RecyclerView.Adapter<FoodsAdapter.SubItemViewHolder> {

    private ArrayList<Food> foods;
    private Activity activity;
    private long mealId;
    private FragmentManager supportFragmentManager;

    public FoodsAdapter(Activity activity, FragmentManager supportFragmentManager, long mealId) {
        this.activity = activity;
        this.supportFragmentManager = supportFragmentManager;
        this.mealId = mealId;
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {

        private TextView foodName;
        private TextView kcalAmount, protAmount, fatAmount, carboAmount;
        private Button addFoodBtn;
        private ImageView removeFoodBtn;

        public SubItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            kcalAmount = itemView.findViewById(R.id.food_kcal_amount);
            protAmount = itemView.findViewById(R.id.food_prot_amount);
            fatAmount = itemView.findViewById(R.id.food_fat_amount);
            carboAmount = itemView.findViewById(R.id.food_carb_amount);
            addFoodBtn = itemView.findViewById(R.id.create_food_button);
            removeFoodBtn = itemView.findViewById(R.id.food_remove_button);
        }
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == foods.size()) ? R.layout.btn_create_food : R.layout.food_item;
    }

    @NonNull
    @Override
    public FoodsAdapter.SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if(viewType == R.layout.food_item){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_create_food, parent, false);
        }
        return new FoodsAdapter.SubItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FoodsAdapter.SubItemViewHolder holder, int position) {

        if (position != foods.size()){
            Food food = foods.get(position);
            holder.foodName.setText(food.getName());
            holder.kcalAmount.setText(Double.toString(food.getKcal()));
            holder.protAmount.setText(Double.toString(food.getProtein()));
            holder.fatAmount.setText(Double.toString(food.getFat()));
            holder.carboAmount.setText(Double.toString(food.getCarbohydrate()));

            holder.removeFoodBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DecideFragment decideFragment = new DecideFragment((MealsListener) activity);
                    Bundle args = new Bundle();
                    args.putLong("dataToRemove", food.getId());
                    args.putString("dataType", "food");
                    decideFragment.setArguments(args);
                    decideFragment.show(((AppCompatActivity) activity).getSupportFragmentManager(), "decideFragment");
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Fragment updateFoodFragment = new UpdateFoodFragment((MealsListener) activity);
                    UpdateFoodDTO updateFoodDTO = new UpdateFoodDTO();
                    updateFoodDTO.fillWithFood(food, food.getId());
                    Bundle args = new Bundle();
                    args.putSerializable("updateFoodDTO", updateFoodDTO);
                    updateFoodFragment.setArguments(args);
                    supportFragmentManager.beginTransaction().add(R.id.main_container, updateFoodFragment, "updateFoodFragment").addToBackStack(null).commit();
                    return true;
                }
            });

        } else {
            holder.addFoodBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment addFoodFragment = new AddFoodFragment((MealsListener) activity);
                    Bundle args = new Bundle();
                    args.putLong("mealId", mealId);
                    addFoodFragment.setArguments(args);
                    supportFragmentManager.beginTransaction().add(R.id.main_container, addFoodFragment, "addFoodFragment").addToBackStack(null).commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return foods.size() +1;
    }
}