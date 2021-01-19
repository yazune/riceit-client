package com.agh.riceitclient.model;

import com.agh.riceitclient.dto.GetUserDetailsDTO;

public class UserDetails {

    private String username;
    private String email;

    private double height;
    private double weight;
    private int age;
    private String gender;
    private double k;

    public void fillWithData(GetUserDetailsDTO getUserDetailsDTO){
        this.username = getUserDetailsDTO.getUsername();
        this.email = getUserDetailsDTO.getEmail();

        this.height = getUserDetailsDTO.getHeight();
        this.weight = getUserDetailsDTO.getWeight();
        this.age = getUserDetailsDTO.getAge();
        this.gender = getUserDetailsDTO.getGender();
        this.k = getUserDetailsDTO.getK();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }
}
