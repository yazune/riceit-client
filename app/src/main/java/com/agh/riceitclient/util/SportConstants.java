package com.agh.riceitclient.util;

import java.util.HashMap;

public class SportConstants {

    private final static String[][] constants = {
            {"Running with 6.43 km/h", "RUNNING_4"},
            {"Running with 8.05 km/h", "RUNNING_5"},
            {"Running with 8.37 km/h","RUNNING_5_2"},
            {"Running with 9.66 km/h","RUNNING_6"},
            {"Running with 10.78 km/h","RUNNING_6_7"},
            {"Running with 11.27 km/h","RUNNING_7"},
            {"Running with 12.07 km/h","RUNNING_7_5"},
            {"Running with 12.87 km/h","RUNNING_8"},
            {"Running with 13.84 km/h","RUNNING_8_6"},
            {"Running with 14.48 km/h","RUNNING_9"},
            {"Running with 16.09 km/h","RUNNING_10"},
            {"Running with 17.70 km/h","RUNNING_11"},
            {"Running with 19.31 km/h","RUNNING_12"},
            {"Running with 20.92 km/h","RUNNING_13"},
            {"Running with 22.53 km/h","RUNNING_14"},
            {"Bicycling with speed between 16.09 km/h and 19.31 km/h", "BICYCLING_10_TO_12"},
            {"Bicycling with speed between 19.31 km/h and 22.53 km/h", "BICYCLING_12_TO_14"},
            {"Bicycling with speed between 22.53 km/h and 25.75 km/h", "BICYCLING_14_TO_16"},
            {"Bicycling with speed between 25.75 km/h and 30.58 km/h", "BICYCLING_16_TO_19"},
            {"Bicycling with speed higher than 30.58 km/h", "BICYCLING_MORE_THAN_20"},
            {"Swimming (General)","SWIMMING_GENERAL"},
            {"Swimming (Backstroke)","SWIMMING_BACKSTROKE"},
            {"Swimming (Crawl)","SWIMMING_CRAWL"},
            {"Swimming (Breaststroke)","SWIMMING_BREASTSTROKE"},
            {"Swimming (Butterfly)","SWIMMING_BUTTERFLY"}
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
