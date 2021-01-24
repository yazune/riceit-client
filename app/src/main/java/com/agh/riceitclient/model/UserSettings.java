package com.agh.riceitclient.model;

public class UserSettings {

    private boolean useK;
    private boolean useManParameters;
    private String dietType;

    public boolean isUseK() {
        return useK;
    }

    public void setUseK(boolean useK) {
        this.useK = useK;
    }

    public boolean isUseManParameters() {
        return useManParameters;
    }

    public void setUseManParameters(boolean useManParameters) {
        this.useManParameters = useManParameters;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }
}
