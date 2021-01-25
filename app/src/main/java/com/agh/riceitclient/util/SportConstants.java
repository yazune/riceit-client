package com.agh.riceitclient.util;

import java.util.HashMap;

public class SportConstants {

    private final static String[][] constants = {
            {"Running with 8.05 km/h", "RUNNING_5"},
            {"Running with 8.37 km/h","RUNNING_5_2"},
            {"Running with 9.67 km/h","RUNNING_6"},
            {"Running with 10.78 km/h","RUNNING_6_7"},
            {"Running with 12.07 km/h","RUNNING_7_5"},
            {"Running with 13.84 km/h","RUNNING_8_6"},
            {"Running with 16.09 km/h","RUNNING_10"},
            {"Bicycling with 19.31 km/h", "BICYCLING_12"},
            {"Bicycling with 22.53 km/h", "BICYCLING_14"},
            {"Bicycling with 25.75 km/h", "BICYCLING_16"},
            {"Bicycling with 32.19 km/h", "BICYCLING_20"},
            {"Swimming (General)","SWIMMING_GENERAL"},
            {"Swimming (Backstroke)","SWIMMING_BACKSTROKE"},
            {"Swimming (Breaststroke)","SWIMMING_BREASTSTROKE"},
            {"Swimming (Butterfly)","SWIMMING_BUTTERFLY"},
            {"Swimming (Crawl)","SWIMMING_CRAWL"},
    };

    public static HashMap<String, String> generateMapWithNamesAsKeys(){

        HashMap<String, String> sportTypesMap = new HashMap<>();

        for(int i = 0; i < constants.length; i++){
            sportTypesMap.put(constants[i][0], constants[i][1]);
        }
        return sportTypesMap;
    }

    public static HashMap<String, String> generateMapWithCodesAsKeys(){

        HashMap<String, String> sportTypesMap = new HashMap<>();

        for(int i = 0; i < constants.length; i++){
            sportTypesMap.put(constants[i][1], constants[i][0]);
        }
        return sportTypesMap;
    }

    public static String[] generateArrayWithNames(){

        String[] labels = new String[constants.length];

        for(int i = 0; i < constants.length; i++){
            labels[i] = constants[i][0];
        }

        return labels;
    }

}
