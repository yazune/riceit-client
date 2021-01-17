package com.agh.riceitclient.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.activity.AddFoodActivity;
import com.agh.riceitclient.fragment.DecideDialogFragment;
import com.agh.riceitclient.model.Food;

import java.util.ArrayList;

public class FoodsAdapter extends  RecyclerView.Adapter<FoodsAdapter.SubItemViewHolder> {

    private ArrayList<Food> foods;
    private Activity activity;
    private Context context;
    private long mealId;

    public FoodsAdapter(Activity activity, Context context, long mealId) {
        this.activity = activity;
        this.context = context;
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
        return new SubItemViewHolder(view);

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
                    DecideDialogFragment dialogFragment = new DecideDialogFragment((RemoveObjectListener) activity);
                    Bundle args = new Bundle();
                    args.putLong("dataToRemove", food.getId());
                    args.putString("dataType", "food");
                    dialogFragment.setArguments(args);
                    dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "decideDialogFragment");
                }
            });
        } else {
            holder.addFoodBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(activity, AddFoodActivity.class);
                    i.putExtra("mealId", mealId);
                    (activity).startActivityForResult(i, ActivityType.ADD_FOOD.code);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return foods.size() +1;
    }
}
