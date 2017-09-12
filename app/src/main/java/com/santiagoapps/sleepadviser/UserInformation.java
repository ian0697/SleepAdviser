package com.santiagoapps.sleepadviser;

/**
 * Created by Ian on 9/12/2017.
 */

public class UserInformation {

    public String name;
    public String email;
    public String password;
    public String photoURL;

    public UserInformation (String name,String email,String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
