package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.AddFoodDTO;
import com.agh.riceitclient.dto.UpdateFoodDTO;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateFoodActivity extends AppCompatActivity {

    UpdateFoodDTO updateFoodDTO;
    TextInputLayout nameInput, kcalInput, protInput, fatInput, carbInput;
    Button confirmUpdateFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        nameInput = findViewById(R.id.update_food_name);
        kcalInput = findViewById(R.id.update_food_kcal);
        protInput = findViewById(R.id.update_food_prot);
        fatInput = findViewById(R.id.update_food_fat);
        carbInput = findViewById(R.id.update_food_carb);
        confirmUpdateFood = findViewById(R.id.btn_update_food_confirm);

        updateFoodDTO = (UpdateFoodDTO) getIntent().getSerializableExtra("updateFoodDTO");

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

        Intent returnIntent = new Intent();
        returnIntent.putExtra("updateFoodDTO", updateFoodDTO);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}