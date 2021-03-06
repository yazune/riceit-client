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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.fragment.DecideFragment;
import com.agh.riceitclient.listener.MealListener;
import com.agh.riceitclient.model.Meal;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<Meal> meals;
    private Activity activity;
    private FragmentManager supportFragmentManager;

    public MealAdapter(Activity activity, FragmentManager supportFragmentManager){
        this.activity = activity;
        this.supportFragmentManager = supportFragmentManager;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView mealName;
        private TextView kcalAmount, protAmount, fatAmount, carboAmount;
        private RecyclerView foodRV;
        private Button addMealBtn;
        private ImageView removeMealBtn;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.meal_name);
            kcalAmount = itemView.findViewById(R.id.meal_kcal_amount);
            protAmount = itemView.findViewById(R.id.meal_prot_amount);
            fatAmount = itemView.findViewById(R.id.meal_fat_amount);
            carboAmount = itemView.findViewById(R.id.meal_carb_amount);
            foodRV = itemView.findViewById(R.id.foods_rv);
            addMealBtn = itemView.findViewById(R.id.create_meal_button);
            removeMealBtn = itemView.findViewById(R.id.meal_remove_button);
        }
    }

    @NonNull
    @Override
    public MealAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if(viewType == R.layout.meal_item){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_create_meal, parent, false);
        }
        return new MealAdapter.ItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == meals.size()) ? R.layout.btn_create_meal : R.layout.meal_item;
    }

    @Override
    public void onBindViewHolder(@NonNull MealAdapter.ItemViewHolder holder, int position) {

        if (position != meals.size()){

            Meal meal = meals.get(position);

            NumberFormat df = DecimalFormat.getInstance();
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            df.setRoundingMode(RoundingMode.DOWN);

            holder.mealName.setText("Meal " + (position+1));
            holder.kcalAmount.setText(df.format(meal.getKcal()));
            holder.protAmount.setText(df.format(meal.getProtein()));
            holder.fatAmount.setText(df.format(meal.getFat()));
            holder.carboAmount.setText(df.format(meal.getCarbohydrate()));

            holder.foodRV.setVisibility(meal.isExpanded()? View.VISIBLE : View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean expanded = meal.isExpanded();
                    meal.setExpanded(!expanded);
                    notifyItemChanged(position);
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                    holder.foodRV.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );

            holder.removeMealBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DecideFragment decideFragment = new DecideFragment((MealListener)activity);
                    Bundle args = new Bundle();
                    args.putLong("dataToRemove", meal.getId());
                    args.putString("dataType", "meal");
                    decideFragment.setArguments(args);
                    decideFragment.show(((AppCompatActivity) activity).getSupportFragmentManager(), "decideFragment");
                }
            });

            linearLayoutManager.setInitialPrefetchItemCount(meal.getFoods().size());

            FoodAdapter foodAdapter = new FoodAdapter(activity, supportFragmentManager, meal.getId());
            foodAdapter.setFoods(meal.getFoods());
            holder.foodRV.setLayoutManager(linearLayoutManager);
            holder.foodRV.setAdapter(foodAdapter);
            holder.foodRV.setRecycledViewPool(viewPool);
        } else {
            holder.addMealBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MealListener) activity).enqueueCreateMeal();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return meals.size() + 1;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

}
