package com.agh.riceitclient.listener;

import com.agh.riceitclient.dto.ManualParametersDTO;

public interface GoalListener {
    public void enqueueUpdateManualParameters(ManualParametersDTO manualParametersDTO);
}
