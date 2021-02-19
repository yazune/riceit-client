package com.agh.riceitclient.listener;

import com.agh.riceitclient.dto.FoodAddDTO;
import com.agh.riceitclient.util.FoodAddTransfer;
import com.agh.riceitclient.util.FoodUpdateTransfer;

public interface MealListener {

    public void enqueueUpdateFood(FoodUpdateTransfer foodUpdateTransfer);

    public void enqueueAddFood(FoodAddDTO foodAddDTO);

    public void removeMealOrFood(boolean isMeal, long dataToRemove);

    public void enqueueCreateMeal();
}
