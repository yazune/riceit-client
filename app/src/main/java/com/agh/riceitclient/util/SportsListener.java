package com.agh.riceitclient.util;

import com.agh.riceitclient.dto.AddSportManDTO;
import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.dto.RemoveSportDTO;
import com.agh.riceitclient.dto.UpdateSportDTO;

public interface SportsListener {

    public void enqueueAddSportManually(AddSportManDTO addSportManDTO);

    public void enqueueRemoveSport(RemoveSportDTO removeSportDTO);

    public void enqueueUpdateSport(UpdateSportDTO updateSportDTO);
}
