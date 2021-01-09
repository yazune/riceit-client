package com.agh.riceitclient.dto;

public class ExistsUsernameDTO {

    private String username;

    public ExistsUsernameDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
