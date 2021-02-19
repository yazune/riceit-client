package com.agh.riceitclient.model;

public class UserSettings {

    private boolean usePal;
    private boolean useMan;
    private String dietType;

    public boolean isUsePal() {
        return usePal;
    }

    public void setUsePal(boolean usePal) {
        this.usePal = usePal;
    }

    public boolean isUseMan() {
        return useMan;
    }

    public void setUseMan(boolean useMan) {
        this.useMan = useMan;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }
}
