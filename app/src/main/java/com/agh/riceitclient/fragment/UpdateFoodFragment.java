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
import com.agh.riceitclient.dto.UpdateFoodDTO;
import com.agh.riceitclient.util.MealsListener;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateFoodFragment extends Fragment {

    UpdateFoodDTO updateFoodDTO;
    TextInputLayout nameInput, kcalInput, protInput, fatInput, carbInput;
    Button confirmUpdateFood;
    MealsListener mealsListener;

    public UpdateFoodFragment(MealsListener mealsListener){
        this.mealsListener = mealsListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateFoodDTO = (UpdateFoodDTO) getArguments().getSerializable("updateFoodDTO");
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

        nameInput.getEditText().setText(updateFoodDTO.getName());
        kcalInput.getEditText().setText(String.valueOf(updateFoodDTO.getKcal()));
        protInput.getEditText().setText(String.valueOf(updateFoodDTO.getProtein()));
        fatInput.getEditText().setText(String.valueOf(updateFoodDTO.getFat()));
        carbInput.getEditText().setText(String.valueOf(updateFoodDTO.getCarbohydrate()));

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

        double kcal = Double.valueOf(kcalStr);
        double prot = Double.valueOf(protStr);
        double fat = Double.valueOf(fatStr);
        double carb = Double.valueOf(carbStr);

        updateFoodDTO.setName(nameStr);
        updateFoodDTO.setKcal(kcal);
        updateFoodDTO.setProtein(prot);
        updateFoodDTO.setFat(fat);
        updateFoodDTO.setCarbohydrate(carb);

        mealsListener.enqueueUpdateFood(updateFoodDTO);

        //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();   //todo not tested
        //getActivity().getFragmentManager().beginTransaction().remove(this).commit(); //todo not tested
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
