package com.santiagoapps.sleepadviser.data.model;

import android.util.Log;

import com.santiagoapps.sleepadviser.helpers.DateHelper;

import java.util.Calendar;
import java.util.Date;

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
    public static final String KEY_USER_ID = "USER_ID";
    public static final String KEY_SLEEP_DATE = "SLEEP_TIME";
    public static final String KEY_WAKE_DATE = "WAKE_TIME";
    public static final String KEY_SLEEP_RATING = "SLEEP_RATING";
    public static final String KEY_SLEEP_DURATION = "SLEEP_DURATION";

    public static final String TABLE ="tbl_data";

    private Calendar sleep_date;
    private Calendar wake_date;
    private int sleepQuality;
    private int id;
    private long durationInMills;
    private String userId;
    private String sleepQualityDesc;
    private String strSleepDate;
    private String strWakeDate;
    private String sleep_duration;

//    Required empty Constructor
    public Session(){
        sleepQuality = 0;
        sleep_date = Calendar.getInstance();
        wake_date = Calendar.getInstance();
    }

    /**
     * accepts two parameters:
     * @param sleep_date accepts Sleeping date as Calendar object
     * @param wake_date accepts wake date as Calendar object
     */
    public Session(Calendar sleep_date, Calendar wake_date){
        sleepQuality = 0;
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
        this.sleepQuality = sleep_rating;
        this.sleep_date = sleep_date;
        this.wake_date = wake_date;
    }


    /**
     * Return the sleep_date
     * as a Calendar object
     */
    public Calendar getSleepDate(){
        return sleep_date;
    }



    /**
     * Set the sleep date in by accepting
     * parsed date through the formats defined
     * in this class
     */
    public void setSleepDate(Date parseDate){
        sleep_date.setTime(parseDate);
        strSleepDate = DateHelper.dateToString(parseDate);
    }

    /**
     * Return the wake_date
     * as a Calendar object
     */
    public Calendar getWakeDate(){
        return wake_date;
    }


    /**
     * Set the wake date in by accepting
     * parsed date through the formats defined
     * in this class
     */
    public void setWakeDate(Date parseDate){
        wake_date.setTime(parseDate);
        strWakeDate = DateHelper.dateToString(parseDate);
    }


    public String getStrSleepDate() {
        return strSleepDate;
    }

    public void setStrSleepDate(String strSleepDate) {
        this.strSleepDate = strSleepDate;
    }

    public String getStrWakeDate() {
        return strWakeDate;
    }

    public void setStrWakeDate(String strWakeDate) {
        this.strWakeDate = strWakeDate;
    }

    public int getid(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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

        sleep_duration = hours + " h " + mins + " min";
        return sleep_duration;
    }

    public void setDurationInMills(long mills){
        this.durationInMills = mills;
    }

    public long getDurationInMills(){
        return wake_date.getTimeInMillis() - sleep_date.getTimeInMillis();
    }

    public Date getCalendarDuration(){
        Date date = new Date();
        date.setTime(getDurationInMills());
        return date;
    }

    public double getDurationInDecimal(){
        double decimal;
        double hr = getCalendarDuration().getHours();
        if(hr > 12){
            hr = hr - 12;
        }

        double min = getCalendarDuration().getMinutes() / 60;

        decimal = hr + min;
        return decimal;
    }

    public void setSleepDuration(String sleep_duration){
        this.sleep_duration = sleep_duration;
    }

    public int getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(int sleep_rating) {
        this.sleepQuality = sleep_rating;

        switch(sleepQuality) {
            case 1: sleepQualityDesc = "Poor"; break;
            case 2: sleepQualityDesc = "Good"; break;
            case 3: sleepQualityDesc = "Excellent"; break;
            default: sleepQualityDesc = "not defined"; break;
        }

    }

    public String getSleepQualityDesc() {
        return sleepQualityDesc;
    }

    public void setSleepQualityDesc(String sleepQualityDesc) {
        this.sleepQualityDesc = sleepQualityDesc;
    }

    public void setUserId(String user_id) {
        this.userId = user_id;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nId: " + getid());
        sb.append("\nSleep date: " + DateHelper.dateToString(sleep_date.getTime()));
        sb.append("\nWake date: " + DateHelper.dateToString(wake_date.getTime()));
        sb.append("\nSleep duration: " + getSleep_duration());
        sb.append("\nSleep Quality: " + sleepQualityDesc);
        sb.append("\nUser id: " + getUserId());

        Log.d(TAG, sb.toString());
        return sb.toString();
    }
}
