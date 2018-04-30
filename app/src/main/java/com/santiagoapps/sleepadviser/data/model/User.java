package com.santiagoapps.sleepadviser.data.model;

import com.santiagoapps.sleepadviser.helpers.DateHelper;

import java.util.Date;

/**
 * User Model class
 * Created by Ian on 9/26/2017.
 */

public class User {

    public static final String TAG = "Dormie(" + User.class.getSimpleName() + ")";

    //DATABASE
    public static final String TABLE = "tbl_user";
    public static final String KEY_FIREBASE_ID = "FIREBASE_ID";
    public static final String KEY_FULLNAME = "FULL_NAME";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_PASSWORD = "PASSWORD";

    //Variables
    private String email;
    private String password;
    private String name;
    private String gender;
    private String dateRegistered;
    private String firebaseId;

    private String sleepGoal;
    private int age;

    /**
     * Default Constructor
     */
    public User(){
        setDateRegistered();
    }

    /**
     * @param email
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

    private void setDateRegistered() {
        //now date
        Date date = new Date();
        dateRegistered = DateHelper.dateToStandardString(date);
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

    public String getSleepGoal() {
        return sleepGoal;
    }

    public void setSleepGoal(String sleepGoal) {
        this.sleepGoal = sleepGoal;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
