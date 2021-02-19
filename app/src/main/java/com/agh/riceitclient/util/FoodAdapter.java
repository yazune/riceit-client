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
import com.agh.riceitclient.fragment.FoodAddFragment;
import com.agh.riceitclient.fragment.DecideFragment;
import com.agh.riceitclient.fragment.FoodUpdateFragment;
import com.agh.riceitclient.listener.MealListener;
import com.agh.riceitclient.model.Food;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class FoodAdapter extends  RecyclerView.Adapter<FoodAdapter.SubItemViewHolder> {

    private ArrayList<Food> foods;
    private Activity activity;
    private long mealId;
    private FragmentManager supportFragmentManager;

    public FoodAdapter(Activity activity, FragmentManager supportFragmentManager, long mealId) {
        this.activity = activity;
        this.supportFragmentManager = supportFragmentManager;
        this.mealId = mealId;
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {

        private TextView foodName;
        private TextView kcalAmount, protAmount, fatAmount, carboAmount;
        private Button addFoodBtn;
        private ImageView removeFoodBtn;
        private View line;

        public SubItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            kcalAmount = itemView.findViewById(R.id.food_kcal_amount);
            protAmount = itemView.findViewById(R.id.food_prot_amount);
            fatAmount = itemView.findViewById(R.id.food_fat_amount);
            carboAmount = itemView.findViewById(R.id.food_carb_amount);
            addFoodBtn = itemView.findViewById(R.id.create_food_button);
            removeFoodBtn = itemView.findViewById(R.id.food_remove_button);
            line = itemView.findViewById(R.id.food_line);
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
    public FoodAdapter.SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if(viewType == R.layout.food_item){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_create_food, parent, false);
        }
        return new FoodAdapter.SubItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.SubItemViewHolder holder, int position) {

        if (position != foods.size()){
            Food food = foods.get(position);

            NumberFormat df = DecimalFormat.getInstance();
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            df.setRoundingMode(RoundingMode.DOWN);

            holder.foodName.setText(food.getName());
            holder.kcalAmount.setText(df.format(food.getKcal()));
            holder.protAmount.setText(df.format(food.getProtein()));
            holder.fatAmount.setText(df.format(food.getFat()));
            holder.carboAmount.setText(df.format(food.getCarbohydrate()));

            if (position == (foods.size() - 1))
                holder.line.setVisibility(View.INVISIBLE);
            else
                holder.line.setVisibility(View.VISIBLE);

            holder.removeFoodBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DecideFragment decideFragment = new DecideFragment((MealListener) activity);
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
                    Fragment foodUpdateFragment = new FoodUpdateFragment((MealListener) activity);
                    FoodUpdateTransfer foodUpdateTransfer = new FoodUpdateTransfer();
                    foodUpdateTransfer.setFoodId(food.getId());
                    foodUpdateTransfer.setName(food.getName());
                    foodUpdateTransfer.setKcal(food.getKcal());
                    foodUpdateTransfer.setProtein(food.getProtein());
                    foodUpdateTransfer.setFat(food.getFat());
                    foodUpdateTransfer.setCarbohydrate(food.getCarbohydrate());
                    Bundle args = new Bundle();
                    args.putSerializable("foodUpdateTransfer", foodUpdateTransfer);
                    foodUpdateFragment.setArguments(args);
                    supportFragmentManager.beginTransaction().add(R.id.main_container, foodUpdateFragment, "foodUpdateFragment").addToBackStack(null).commit();
                    return true;
                }
            });

        } else {
            holder.addFoodBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment foodAddFragment = new FoodAddFragment((MealListener) activity);
                    Bundle args = new Bundle();
                    args.putLong("mealId", mealId);
                    foodAddFragment.setArguments(args);
                    supportFragmentManager.beginTransaction().add(R.id.main_container, foodAddFragment, "foodAddFragment").addToBackStack(null).commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return foods.size() +1;
    }
}