package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.AddFoodDTO;
import com.agh.riceitclient.model.Food;
import com.google.android.material.textfield.TextInputLayout;

public class AddFoodActivity extends AppCompatActivity {

    long mealId;
    TextInputLayout nameInput, kcalInput, protInput, fatInput, carbInput;
    Button confirmAddFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        mealId = (long) getIntent().getSerializableExtra("mealId");

        nameInput = findViewById(R.id.add_food_name);
        kcalInput = findViewById(R.id.add_food_kcal);
        protInput = findViewById(R.id.add_food_prot);
        fatInput = findViewById(R.id.add_food_fat);
        carbInput = findViewById(R.id.add_food_carb);

        confirmAddFood = findViewById(R.id.btn_add_food_confirm);

        confirmAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFood(view);
            }
        });

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

        Intent returnIntent = new Intent();
        returnIntent.putExtra("addFoodDTO", addFoodDTO);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}