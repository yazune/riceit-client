package com.agh.riceitclient.model;

public class Day {

    private long id;
    private String date;

    private double kcalToEat;
    private double proteinToEat;
    private double fatToEat;
    private double carbohydrateToEat;

    private double kcalConsumed;
    private double proteinConsumed;
    private double fatConsumed;
    private double carbohydrateConsumed;

    private double kcalBurnt;
    private double proteinBurnt;
    private double fatBurnt;
    private double carbohydrateBurnt;

    private boolean useK;

    public boolean isUseK() {
        return useK;
    }

    public void setUseK(boolean useK) {
        this.useK = useK;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getKcalToEat() {
        return kcalToEat;
    }

    public void setKcalToEat(double kcalToEat) {
        this.kcalToEat = kcalToEat;
    }

    public double getProteinToEat() {
        return proteinToEat;
    }

    public void setProteinToEat(double proteinToEat) {
        this.proteinToEat = proteinToEat;
    }

    public double getFatToEat() {
        return fatToEat;
    }

    public void setFatToEat(double fatToEat) {
        this.fatToEat = fatToEat;
    }

    public double getCarbohydrateToEat() {
        return carbohydrateToEat;
    }

    public void setCarbohydrateToEat(double carbohydrateToEat) {
        this.carbohydrateToEat = carbohydrateToEat;
    }

    public double getKcalConsumed() {
        return kcalConsumed;
    }

    public void setKcalConsumed(double kcalConsumed) {
        this.kcalConsumed = kcalConsumed;
    }

    public double getProteinConsumed() {
        return proteinConsumed;
    }

    public void setProteinConsumed(double proteinConsumed) {
        this.proteinConsumed = proteinConsumed;
    }

    public double getFatConsumed() {
        return fatConsumed;
    }

    public void setFatConsumed(double fatConsumed) {
        this.fatConsumed = fatConsumed;
    }

    public double getCarbohydrateConsumed() {
        return carbohydrateConsumed;
    }

    public void setCarbohydrateConsumed(double carbohydrateConsumed) {
        this.carbohydrateConsumed = carbohydrateConsumed;
    }

    public double getKcalBurnt() {
        return kcalBurnt;
    }

    public void setKcalBurnt(double kcalBurnt) {
        this.kcalBurnt = kcalBurnt;
    }

    public double getProteinBurnt() {
        return proteinBurnt;
    }

    public void setProteinBurnt(double proteinBurnt) {
        this.proteinBurnt = proteinBurnt;
    }

    public double getFatBurnt() {
        return fatBurnt;
    }

    public void setFatBurnt(double fatBurnt) {
        this.fatBurnt = fatBurnt;
    }

    public double getCarbohydrateBurnt() {
        return carbohydrateBurnt;
    }

    public void setCarbohydrateBurnt(double carbohydrateBurnt) {
        this.carbohydrateBurnt = carbohydrateBurnt;
    }
}
