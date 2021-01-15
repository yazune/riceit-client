package com.agh.riceitclient.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.fragment.DecideDialogFragment;
import com.agh.riceitclient.model.Meal;

import java.util.ArrayList;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ItemViewHolder>{

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<Meal> meals;
    private Context context;
    private Activity activity;

    public MealsAdapter(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView mealName;
        private TextView kcalAmount, protAmount, fatAmount, carboAmount;
        private RecyclerView foodsRv;
        private Button addMealBtn;
        private ImageView removeMealBtn;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.meal_name);
            kcalAmount = itemView.findViewById(R.id.meal_kcal_amount);
            protAmount = itemView.findViewById(R.id.meal_prot_amount);
            fatAmount = itemView.findViewById(R.id.meal_fat_amount);
            carboAmount = itemView.findViewById(R.id.meal_carb_amount);
            foodsRv = itemView.findViewById(R.id.foods_rv);
            addMealBtn = itemView.findViewById(R.id.create_meal_button);
            removeMealBtn = itemView.findViewById(R.id.meal_remove_button);
        }
    }

    @NonNull
    @Override
    public MealsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if(viewType == R.layout.meal_item){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_create_meal, parent, false);
        }
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == meals.size()) ? R.layout.btn_create_meal : R.layout.meal_item;
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.ItemViewHolder holder, int position) {

        if (position != meals.size()){

            Meal meal = meals.get(position);
            holder.mealName.setText("Meal " + (position+1));
            holder.kcalAmount.setText(Double.toString(meal.getKcal()));
            holder.protAmount.setText(Double.toString(meal.getProtein()));
            holder.fatAmount.setText(Double.toString(meal.getFat()));
            holder.carboAmount.setText(Double.toString(meal.getCarbohydrate()));

            holder.foodsRv.setVisibility(meal.isExpanded()? View.VISIBLE : View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean expanded = meal.isExpanded();
                    meal.setExpanded(!expanded);
                    notifyItemChanged(position);
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                    holder.foodsRv.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );

            holder.removeMealBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DecideDialogFragment dialogFragment = new DecideDialogFragment((RemoveObjectListener) activity);
                    Bundle args = new Bundle();
                    args.putLong("dataToRemove", meal.getId());
                    args.putString("dataType", "meal");
                    dialogFragment.setArguments(args);
                    dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "decideDialogFragment");
                }
            });

            linearLayoutManager.setInitialPrefetchItemCount(meal.getFoods().size());

            FoodsAdapter foodsAdapter = new FoodsAdapter(activity, context);
            foodsAdapter.setFoods(meal.getFoods());
            holder.foodsRv.setLayoutManager(linearLayoutManager);
            holder.foodsRv.setAdapter(foodsAdapter);
            holder.foodsRv.setRecycledViewPool(viewPool);
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
