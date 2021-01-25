package com.agh.riceitclient.util;

import com.agh.riceitclient.dto.AddSportManDTO;
import com.agh.riceitclient.dto.DateDTO;

public interface SportsListener {

    public void enqueueAddSportManually(AddSportManDTO addSportManDTO);
}
