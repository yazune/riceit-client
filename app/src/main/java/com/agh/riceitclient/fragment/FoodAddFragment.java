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
import com.agh.riceitclient.dto.FoodAddDTO;
import com.agh.riceitclient.util.FoodAddTransfer;
import com.agh.riceitclient.listener.MealListener;
import com.google.android.material.textfield.TextInputLayout;

public class FoodAddFragment extends Fragment {

    long mealId;
    TextInputLayout nameInput, kcalInput, protInput, fatInput, carbInput;
    Button confirmAddFood;
    MealListener mealListener;

    public FoodAddFragment(MealListener mealListener){
        this.mealListener = mealListener;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_meals_add_food, container, false);

        nameInput = v.findViewById(R.id.add_food_name);
        kcalInput = v.findViewById(R.id.add_food_kcal);
        protInput = v.findViewById(R.id.add_food_prot);
        fatInput = v.findViewById(R.id.add_food_fat);
        carbInput = v.findViewById(R.id.add_food_carb);

        confirmAddFood = v.findViewById(R.id.btn_add_food_confirm);

        confirmAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFood(view);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealId = getArguments().getLong("mealId");
    }

    public void createFood(View view){
        String nameStr = nameInput.getEditText().getText().toString().trim();
        String kcalStr = kcalInput.getEditText().getText().toString().trim();
        String protStr = protInput.getEditText().getText().toString().trim();
        String fatStr = fatInput.getEditText().getText().toString().trim();
        String carbStr = carbInput.getEditText().getText().toString().trim();

        double kcal = Double.parseDouble(kcalStr);
        double prot = Double.parseDouble(protStr);
        double fat = Double.parseDouble(fatStr);
        double carb = Double.parseDouble(carbStr);

        FoodAddDTO foodAddDTO = new FoodAddDTO();
        foodAddDTO.setMealId(mealId);
        foodAddDTO.setName(nameStr);
        foodAddDTO.setKcal(kcal);
        foodAddDTO.setProtein(prot);
        foodAddDTO.setFat(fat);
        foodAddDTO.setCarbohydrate(carb);

        mealListener.enqueueAddFood(foodAddDTO);

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
