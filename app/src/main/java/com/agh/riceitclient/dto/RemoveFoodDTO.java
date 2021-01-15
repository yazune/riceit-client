package com.agh.riceitclient.dto;

public class RemoveFoodDTO {

    private long foodId;

    public RemoveFoodDTO(long foodId) {
        this.foodId = foodId;
    }

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }
}

