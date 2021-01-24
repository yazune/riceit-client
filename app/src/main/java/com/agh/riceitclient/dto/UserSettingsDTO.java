package com.agh.riceitclient.dto;

public class UserSettingsDTO {

    private boolean useK;

    private boolean useManParameters;

    private String dietType;

    public UserSettingsDTO(boolean useK, boolean useManParameters, String dietType) {
        this.useK = useK;
        this.useManParameters = useManParameters;
        this.dietType = dietType;
    }

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
