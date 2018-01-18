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
 * sleeping-date related objects in the application
 *
 * @author Ian Santiago(iansantiago0697@gmail.com)
 * @since 11/17/2017
 */

public class SleepSession {

    private Calendar sleep_date;
    private Calendar wake_date;
    private String sleep_quality;

    public static final SimpleDateFormat SLEEP_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd h:mm a");
    public static final SimpleDateFormat SLEEP_TIME_FORMAT = new SimpleDateFormat("HH:mm a");



//    Required empty Constructor
    public SleepSession(){
        sleep_quality = "";
        sleep_date = Calendar.getInstance();
        wake_date = Calendar.getInstance();
    }

    /**
     * accepts two parameters:
     * @param sleep_date accepts Sleeping date as Calendar object
     * @param wake_date accepts wake date as Calendar object
     */
    public SleepSession(Calendar sleep_date, Calendar wake_date){
        sleep_quality = "";
        this.sleep_date = sleep_date;
        this.wake_date = wake_date;
    }

    /**
     * accepts THREE parameters:
     * @param sleep_date accepts Sleeping date as Calendar object
     * @param wake_date accepts wake date as Calendar object
     * @param sleep_quality Describes how the user feels as they woke up
     */
    public SleepSession(Calendar sleep_date, Calendar wake_date, String sleep_quality){
        this.sleep_quality = sleep_quality;
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
     * return the sleep duration between
     * the sleeping time and the waking time
     * by converting it to milliseconds
     */
    public String sleepDuration(){
        long mills = wake_date.getTimeInMillis() - sleep_date.getTimeInMillis();
        int hours = (int) (mills/(1000 * 60 * 60));
        int mins = (int) (mills/(1000 * 60)) % 60;

        String diff = hours + " hours " + mins + " minutes";
        return diff;
    }



    public String getSleep_quality() {
        return sleep_quality;
    }

    public void setSleep_quality(String sleep_quality) {
        this.sleep_quality = sleep_quality;
    }



}
