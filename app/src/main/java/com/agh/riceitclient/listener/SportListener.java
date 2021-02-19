package com.agh.riceitclient.listener;

import com.agh.riceitclient.dto.SportAddDTO;
import com.agh.riceitclient.util.IdTransfer;
import com.agh.riceitclient.util.SportUpdateTransfer;

public interface SportListener {

    public void enqueueAddSport(SportAddDTO sportAddDTO);

    public void enqueueRemoveSport(IdTransfer idTransfer);

    public void enqueueUpdateSport(SportUpdateTransfer sportUpdateTransfer);
}
