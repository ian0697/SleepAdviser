package com.santiagoapps.sleepadviser.class_library;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ian on 9/26/2017.
 */

public class User {

    private String email;
    private String password;
    private String name;
    private String gender;
    private String dateRegistered;



    public User(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateRegistered = dateFormat.format(date);
    }

    public User(String email, String password,String name){
        this.email= email;
        this.password=password;
        this.name=name;

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateRegistered = dateFormat.format(date);
    }

    public User(String email, String password, String name, String gender){
        this.email= email;
        this.password = password;
        this.name = name;
        this.gender = gender;

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateRegistered = dateFormat.format(date);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", dateRegistered='" + dateRegistered + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
