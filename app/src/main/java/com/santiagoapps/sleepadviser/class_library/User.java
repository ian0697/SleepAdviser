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
    private String firebaseId;

    /**
     *
     * Default Constructor
     */
    public User(){
        setDateRegistered();
    }

    /**
     *
     * @param email_address
     * @param password
     * @param name
     */
    public User(String email, String password, String name){
        this.email= email;
        this.password=password;
        this.name=name;
        setDateRegistered();
    }

    /**
     *
     * @param firebaseId
     * @param email
     * @param password
     * @param name
     */
    public User(String firebaseId, String email, String password, String name){
        this.firebaseId = firebaseId;
        this.email= email;
        this.password=password;
        this.name=name;
        setDateRegistered();
    }


    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String id){
        firebaseId = id;
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

    public void setDateRegistered() {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateRegistered = dateFormat.format(date);

    }

    public void setDateRegistered(String dateRegistered){
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

    @Override
    public String toString() {
        return "User '" + firebaseId + "'"+
                "\nName: " + name +
                "\nEmail: " + email +
                "\nPassword: " + password +
                "\nGender: " + gender +
                "\nDate Registered: " + dateRegistered +"\n";

    }
}
