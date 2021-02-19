package com.agh.riceitclient.model;

import com.agh.riceitclient.dto.UserDetailsGetDTO;

public class UserDetails {

    private String username;
    private String email;

    private double height;
    private double weight;
    private int age;
    private String gender;
    private double pal;

    public void fillWithData(UserDetailsGetDTO userDetailsGetDTO){
        this.username = userDetailsGetDTO.getUsername();
        this.email = userDetailsGetDTO.getEmail();

        this.height = userDetailsGetDTO.getHeight();
        this.weight = userDetailsGetDTO.getWeight();
        this.age = userDetailsGetDTO.getAge();
        this.gender = userDetailsGetDTO.getGender();
        this.pal = userDetailsGetDTO.getPal();
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

    public double getPal() {
        return pal;
    }

    public void setPal(double pal) {
        this.pal = pal;
    }
}
