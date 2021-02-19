package com.agh.riceitclient.dto;

import java.io.Serializable;

public class SportAddDTO implements Serializable {

    private String date;

    private String name;

    private int duration;

    private double kcalBurnt;

    private String sportType;

    public double getKcalBurnt() {
        return kcalBurnt;
    }

    public void setKcalBurnt(double kcalBurnt) {
        this.kcalBurnt = kcalBurnt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
