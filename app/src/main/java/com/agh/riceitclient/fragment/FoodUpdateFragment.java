package com.agh.riceitclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.util.FoodUpdateTransfer;
import com.agh.riceitclient.listener.MealListener;
import com.google.android.material.textfield.TextInputLayout;

public class FoodUpdateFragment extends Fragment {

    FoodUpdateTransfer foodUpdateTransfer;
    TextInputLayout nameInput, kcalInput, protInput, fatInput, carbInput;
    Button confirmUpdateFood;
    MealListener mealListener;

    public FoodUpdateFragment(MealListener mealListener){
        this.mealListener = mealListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodUpdateTransfer = (FoodUpdateTransfer) getArguments().getSerializable("foodUpdateTransfer");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_meals_update_food, container, false);

        nameInput = v.findViewById(R.id.update_food_name);
        kcalInput = v.findViewById(R.id.update_food_kcal);
        protInput = v.findViewById(R.id.update_food_prot);
        fatInput = v.findViewById(R.id.update_food_fat);
        carbInput = v.findViewById(R.id.update_food_carb);
        confirmUpdateFood = v.findViewById(R.id.btn_update_food_confirm);

        nameInput.getEditText().setText(foodUpdateTransfer.getName());
        kcalInput.getEditText().setText(String.valueOf(foodUpdateTransfer.getKcal()));
        protInput.getEditText().setText(String.valueOf(foodUpdateTransfer.getProtein()));
        fatInput.getEditText().setText(String.valueOf(foodUpdateTransfer.getFat()));
        carbInput.getEditText().setText(String.valueOf(foodUpdateTransfer.getCarbohydrate()));

        confirmUpdateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFood(view);
            }
        });

        return v;
    }

    public void updateFood(View view){
        String nameStr = nameInput.getEditText().getText().toString().trim();
        String kcalStr = kcalInput.getEditText().getText().toString().trim();
        String protStr = protInput.getEditText().getText().toString().trim();
        String fatStr = fatInput.getEditText().getText().toString().trim();
        String carbStr = carbInput.getEditText().getText().toString().trim();

        double kcal = Double.parseDouble(kcalStr);
        double prot = Double.parseDouble(protStr);
        double fat = Double.parseDouble(fatStr);
        double carb = Double.parseDouble(carbStr);

        foodUpdateTransfer.setName(nameStr);
        foodUpdateTransfer.setKcal(kcal);
        foodUpdateTransfer.setProtein(prot);
        foodUpdateTransfer.setFat(fat);
        foodUpdateTransfer.setCarbohydrate(carb);

        mealListener.enqueueUpdateFood(foodUpdateTransfer);

        hideKeyboard();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
