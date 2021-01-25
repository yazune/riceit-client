package com.agh.riceitclient.dto;

import com.agh.riceitclient.model.Sport;

import java.util.ArrayList;

public class AllSportsDTO {

    private ArrayList<Sport> sports;

    public ArrayList<Sport> getSports() {
        return sports;
    }

    public void setSports(ArrayList<Sport> sports) {
        this.sports = sports;
    }
}
