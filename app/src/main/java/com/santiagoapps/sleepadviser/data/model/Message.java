package com.santiagoapps.sleepadviser.data.model;

/**
 * Created by IAN on 2/7/2018.
 */

public class Message {

    private boolean isDormie;
    private String message;

    public Message(boolean isDormie, String message){
        super();
        this.isDormie = isDormie;
        this.message = message;
    }

    public boolean isDormie() {
        return isDormie;
    }

    public void setDormie(boolean dormie) {
        isDormie = dormie;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
