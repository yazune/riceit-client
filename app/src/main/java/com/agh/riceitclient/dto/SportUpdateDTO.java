package com.agh.riceitclient.dto;

import java.io.Serializable;

public class SportUpdateDTO implements Serializable {

    private String name;

    private int duration;

    private String sportType;

    private double kcalBurnt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKcalBurnt() {
        return kcalBurnt;
    }

    public void setKcalBurnt(double kcalBurnt) {
        this.kcalBurnt = kcalBurnt;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }
}
