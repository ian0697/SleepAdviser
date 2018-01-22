package com.santiagoapps.sleepadviser.data.model;

import android.util.Log;

import com.santiagoapps.sleepadviser.helpers.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Session class
 *
 * This class serves as an object
 * for the sleep sessions of the
 * app's user. This class will handle all the
 * sleeping-date related objects in the application
 *
 * @author Ian Santiago(iansantiago0697@gmail.com)
 * @since 11/17/2017
 */

public class Session {

    public static final String TAG = "Dormie (" + Session.class.getSimpleName()+ ")";

    //Database
    public static final String KEY_SLEEP_DATE = "SLEEP_TIME";
    public static final String KEY_WAKE_DATE = "WAKE_TIME";
    public static final String KEY_SLEEP_RATING = "SLEEP_RATING";
    public static final String KEY_SLEEP_DURATION = "SLEEP_DURATION";

    public static final String TABLE_DATA ="tbl_data";

    private Calendar sleep_date;
    private Calendar wake_date;
    private int sleep_rating;
    private int session_id;
    private String sleep_duration;

    public static final SimpleDateFormat SLEEP_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd h:mm a");
    public static final SimpleDateFormat SLEEP_TIME_FORMAT = new SimpleDateFormat("HH:mm a");



//    Required empty Constructor
    public Session(){
        sleep_rating = 0;
        sleep_date = Calendar.getInstance();
        wake_date = Calendar.getInstance();
    }

    /**
     * accepts two parameters:
     * @param sleep_date accepts Sleeping date as Calendar object
     * @param wake_date accepts wake date as Calendar object
     */
    public Session(Calendar sleep_date, Calendar wake_date){
        sleep_rating = 0;
        this.sleep_date = sleep_date;
        this.wake_date = wake_date;
    }


    /**
     * accepts THREE parameters:
     * @param sleep_date accepts Sleeping date as Calendar object
     * @param wake_date accepts wake date as Calendar object
     * @param sleep_rating Describes how the user feels as they woke up
     */
    public Session(Calendar sleep_date, Calendar wake_date, int sleep_rating){
        this.sleep_rating = sleep_rating;
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
    public String getSleep_duration(){
        long mills = wake_date.getTimeInMillis() - sleep_date.getTimeInMillis();
        int hours = (int) (mills/(1000 * 60 * 60));
        int mins = (int) (mills/(1000 * 60)) % 60;

        sleep_duration = hours + " hours " + mins + " minutes";
        return sleep_duration;
    }

    public void setSleep_duration(String sleep_duration){
        this.sleep_duration = sleep_duration;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getSleep_rating() {
        return sleep_rating;
    }

    public void setSleep_quality(int sleep_rating) {
        this.sleep_rating = sleep_rating;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sleep id: " + session_id);
        sb.append("\nSleep date: " + DateHelper.dateToString(sleep_date.getTime()));
        sb.append("\nWake date: " + DateHelper.dateToString(wake_date.getTime()));
        sb.append("\nSleep duration: " + getSleep_duration());

        Log.d(TAG, sb.toString());
        return sb.toString();
    }
}
