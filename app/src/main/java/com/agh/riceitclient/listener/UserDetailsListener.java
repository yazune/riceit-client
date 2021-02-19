package com.agh.riceitclient.listener;

import com.agh.riceitclient.dto.UserDetailsUpdateDTO;

public interface UserDetailsListener {
    public void enqueueUpdateUserDetails(UserDetailsUpdateDTO userDetailsUpdateDTO);
}
