package com.agh.riceitclient.util;

import com.agh.riceitclient.dto.UpdateUserDetailsDTO;

public interface DetailsListener {
    public void enqueueUpdateUserDetails(UpdateUserDetailsDTO updateUserDetailsDTO);
}
