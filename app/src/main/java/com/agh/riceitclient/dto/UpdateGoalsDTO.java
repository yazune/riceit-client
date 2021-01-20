package com.agh.riceitclient.dto;

import com.agh.riceitclient.model.Goal;

import java.io.Serializable;

public class UpdateGoalsDTO implements Serializable {

    private double manKcal;
    private double manProtein;
    private double manFat;
    private double manCarbohydrate;

    public void fillWithData(Goal goal){
        this.manKcal = goal.getManKcal();
        this.manProtein = goal.getManProtein();
        this.manFat = goal.getManFat();
        this.manCarbohydrate = goal.getManCarbohydrate();
    }


    public double getManKcal() {
        return manKcal;
    }

    public void setManKcal(double manKcal) {
        this.manKcal = manKcal;
    }

    public double getManProtein() {
        return manProtein;
    }

    public void setManProtein(double manProtein) {
        this.manProtein = manProtein;
    }

    public double getManFat() {
        return manFat;
    }

    public void setManFat(double manFat) {
        this.manFat = manFat;
    }

    public double getManCarbohydrate() {
        return manCarbohydrate;
    }

    public void setManCarbohydrate(double manCarbohydrate) {
        this.manCarbohydrate = manCarbohydrate;
    }
}
