package com.agh.riceitclient.dto;

import com.agh.riceitclient.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealsDTO {

    private ArrayList<Meal> meals;

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }
}
