package com.agh.riceitclient.util;

import java.util.HashMap;
import java.util.Map;

public enum ActivityType {
    ADD_FOOD(1),
    EDIT_FOOD(2);

    public final int code;

    private ActivityType(int code){
        this.code = code;
    }

}
