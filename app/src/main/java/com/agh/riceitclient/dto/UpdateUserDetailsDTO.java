package com.agh.riceitclient.dto;

import com.agh.riceitclient.model.UserDetails;

import java.io.Serializable;

public class UpdateUserDetailsDTO implements Serializable {

    private double height;

    private double weight;

    private String gender;

    private int age;

    private double k;

    public void fillWithData(UserDetails userDetails){
        this.height = userDetails.getHeight();
        this.weight = userDetails.getWeight();
        this.gender = userDetails.getGender();
        this.age = userDetails.getAge();
        this.k = userDetails.getK();
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }
}
