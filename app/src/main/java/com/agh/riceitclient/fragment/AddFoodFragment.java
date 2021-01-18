package com.agh.riceitclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.AddFoodDTO;
import com.agh.riceitclient.util.MealsListener;
import com.google.android.material.textfield.TextInputLayout;

public class AddFoodFragment extends Fragment {

    long mealId;
    TextInputLayout nameInput, kcalInput, protInput, fatInput, carbInput;
    Button confirmAddFood;
    MealsListener mealsListener;

    public AddFoodFragment(MealsListener mealsListener){
        this.mealsListener = mealsListener;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_add_food, container, false);

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

        double kcal = Double.valueOf(kcalStr);
        double prot = Double.valueOf(protStr);
        double fat = Double.valueOf(fatStr);
        double carb = Double.valueOf(carbStr);

        AddFoodDTO addFoodDTO = new AddFoodDTO();
        addFoodDTO.setMealId(mealId);
        addFoodDTO.setName(nameStr);
        addFoodDTO.setKcal(kcal);
        addFoodDTO.setProtein(prot);
        addFoodDTO.setFat(fat);
        addFoodDTO.setCarbohydrate(carb);

        mealsListener.enqueueAddFood(addFoodDTO);

        //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();   //todo not tested
        //getActivity().getFragmentManager().beginTransaction().remove(this).commit(); //todo not tested
        getActivity().getSupportFragmentManager().popBackStack();
    }

}
