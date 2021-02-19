package com.agh.riceitclient.dto;

import com.agh.riceitclient.model.Food;

import java.io.Serializable;

public class FoodUpdateDTO implements Serializable {

    private String name;
    private double kcal;
    private double protein;
    private double fat;
    private double carbohydrate;

    public void fillWithFood(Food food, long foodId){
        this.name = food.getName();
        this.kcal = food.getKcal();
        this.protein = food.getProtein();
        this.fat = food.getFat();
        this.carbohydrate = food.getCarbohydrate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
