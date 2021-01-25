package com.agh.riceitclient.dto;

public class RemoveSportDTO {

    private long sportId;

    public RemoveSportDTO(long sportId) {
        this.sportId = sportId;
    }

    public long getSportId() {
        return sportId;
    }

    public void setSportId(long sportId) {
        this.sportId = sportId;
    }
}
