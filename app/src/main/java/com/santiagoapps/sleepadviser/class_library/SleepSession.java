package com.santiagoapps.sleepadviser.class_library;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * SleepSession class
 *
 * This class serves as an object
 * for the sleep sessions of the
 * app's user. This class will handle all the
 * sleep_date related objects in the application
 *
 * @author Ian Santiago(iansantiago0697@gmail.com)
 * @since 11/17/2017
 */

public class SleepSession {

    private Calendar sleep_date;
    private Calendar wake_date;
    private String sleep_time;
    private String wake_time;
    private String sleep_quality;

    public static final SimpleDateFormat SLEEP_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd h:mm a");
    public static final SimpleDateFormat SLEEP_TIME_FORMAT = new SimpleDateFormat("HH:mm a");


    /**
     * Required empty Constructor
     * initializes sleep_date & wake_date
     */
    public SleepSession(){
        sleep_time = "";
        wake_time = "";
        sleep_quality = "";
        sleep_date = Calendar.getInstance();
        wake_date = Calendar.getInstance();
    }


    /**
     * accepts two parameters:
     * @param sleep_time sleeping_time in String format
     * @param wake_time wake_time in String format
     */
    public SleepSession(String sleep_time, String wake_time){
        this.sleep_time = sleep_time;
        this.wake_time = wake_time;
        sleep_quality = "";
        sleep_date = Calendar.getInstance();
        wake_date = Calendar.getInstance();
    }

    /**
     * accepts two parameters:
     * @param sleep_date as Calendar object
     * @param wake_date as Calendar object
     */
    public SleepSession(Calendar sleep_date, Calendar wake_date){
        sleep_time = "";
        wake_time = "";
        sleep_quality = "";
        this.sleep_date = sleep_date;
        this.wake_date = wake_date;
    }


    /**
     * Return the sleep_date
     * as a Calendar object
     */
    public Calendar getSleep_date(){
        return sleep_date;
    }


    /**
     * Set the sleep date in by accepting
     * parsed date through the formats defined
     * in this class
     */
    public void setSleep_date(Date parseDate){
        sleep_date.setTime(parseDate);
    }

    /**
     * Return the wake_date
     * as a Calendar object
     */
    public Calendar getWake_date(){
        return wake_date;
    }


    /**
     * Set the wake date in by accepting
     * parsed date through the formats defined
     * in this class
     */
    public void setWake_date(Date parseDate){
        wake_date.setTime(parseDate);
    }


    /**
     * return sleep_time in
     * 'HH:mm a' format
     */
    public String getSleep_time() {
        return sleep_time;
    }


    /**
     * Set sleep_time that accepts a 'HH:mm a' format
     * @param sleep_time - accepts String value with valid format
     */
    public void setSleep_time(String sleep_time) {
        this.sleep_time = sleep_time;
    }


    /**
     * return the sleep duration between
     * the sleeping time and the waking time
     * by converting it in milliseconds
     */
    public String sleepDuration(){
        long mills = wake_date.getTimeInMillis() - sleep_date.getTimeInMillis();
        int hours = (int) (mills/(1000 * 60 * 60));
        int mins = (int) (mills/(1000 * 60)) % 60;

        String diff = hours + " hours " + mins + " minutes";
        return diff;
    }

    /**
     * return wake_time in
     * 'HH:mm a' format
     */
    public String getWake_time() {
        return wake_time;
    }

    /**
     * Set wake_time that accepts a 'HH:mm a' format
     * @param wake_time - accepts String value with valid format
     */
    public void setWake_time(String wake_time) {
        this.wake_time = wake_time;
    }


    public String getSleep_quality() {
        return sleep_quality;
    }

    public void setSleep_quality(String sleep_quality) {
        this.sleep_quality = sleep_quality;
    }



}
