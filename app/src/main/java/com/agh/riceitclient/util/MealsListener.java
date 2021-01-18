package com.agh.riceitclient.util;

import com.agh.riceitclient.dto.AddFoodDTO;
import com.agh.riceitclient.dto.UpdateFoodDTO;

public interface MealsListener {

    public void enqueueUpdateFood(UpdateFoodDTO updateFoodDTO);

    public void enqueueAddFood(AddFoodDTO addFoodDTO);

    public void removeMealOrFood(boolean isMeal, long dataToRemove);

    public void enqueueCreateMeal();
}
