package com.agh.riceitclient.dto;

public class RemoveMealDTO {

    private long mealId;

    public RemoveMealDTO(long mealId) {
        this.mealId = mealId;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }
}
