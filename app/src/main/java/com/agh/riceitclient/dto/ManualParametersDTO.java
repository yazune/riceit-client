package com.agh.riceitclient.dto;

import java.io.Serializable;

public class ManualParametersDTO implements Serializable {

    private double kcalMan;
    private double proteinMan;
    private double fatMan;
    private double carbohydrateMan;

    public ManualParametersDTO(double kcalMan, double proteinMan, double fatMan, double carbohydrateMan) {
        this.kcalMan = kcalMan;
        this.proteinMan = proteinMan;
        this.fatMan = fatMan;
        this.carbohydrateMan = carbohydrateMan;
    }

    public double getKcalMan() {
        return kcalMan;
    }

    public void setKcalMan(double kcalMan) {
        this.kcalMan = kcalMan;
    }

    public double getProteinMan() {
        return proteinMan;
    }

    public void setProteinMan(double proteinMan) {
        this.proteinMan = proteinMan;
    }

    public double getFatMan() {
        return fatMan;
    }

    public void setFatMan(double fatMan) {
        this.fatMan = fatMan;
    }

    public double getCarbohydrateMan() {
        return carbohydrateMan;
    }

    public void setCarbohydrateMan(double carbohydrateMan) {
        this.carbohydrateMan = carbohydrateMan;
    }
}
