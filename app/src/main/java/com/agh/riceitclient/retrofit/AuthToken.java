package com.agh.riceitclient.retrofit;

public class AuthToken {

    private static String type;
    private static String token;

    public static String getType(){
        return type;
    }

    public static String getToken(){
        return token;
    }

    public static void addToken(String type, String token){
        AuthToken.type = type;
        AuthToken.token = token;
    }



}
