package com.agh.riceitclient.util;

import com.agh.riceitclient.dto.UpdateGoalsDTO;

public interface GoalsListener {
    public void enqueueUpdateGoals(UpdateGoalsDTO updateGoalsDTO);
}
