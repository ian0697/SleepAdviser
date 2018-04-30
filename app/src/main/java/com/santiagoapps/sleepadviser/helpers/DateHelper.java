package com.santiagoapps.sleepadviser.helpers;

import android.util.Log;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * DateHelper
 *
 * All functions in this class are static
 * and are used all throughout the app
 * to parseDate and convertDate to string etc.
 *
 */

public class DateHelper {

    private static final String TAG = "Dormie("+ DateHelper.class.getSimpleName() + ")";
    private static String LOG_TEXT = "Date must not be null";

    private static SimpleDateFormat sdf;
    private Calendar calendar;

    public DateHelper(Calendar calendar) {
        this.calendar = calendar;
    }

    /*********** CONVERT CALENDAR/DATE TO STRING ***********/

    public static String dateToStandardString(Calendar date){
        sdf = new SimpleDateFormat("yyyy-MM-dd h:mm a");

        if(date!=null) return sdf.format(date.getTime());
        else{
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }

    public static String dateToSqlString(Calendar date){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(date!=null) return sdf.format(date.getTime());
        else{
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }

    public static String dateToSqlString(Date date){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(date!=null) return sdf.format(date.getTime());
        else{
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }

    public static String dateToStandardString(Date date){
        sdf = new SimpleDateFormat("yyyy-MM-dd h:mm a");

        if(date!=null) return sdf.format(date);
        else{
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }

    public static String dateToStringTime(Calendar date){
        sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(date.getTime());
    }

    public static String dateToStringTime(Date date){
        sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(date.getTime());
    }

    public static String dateToStringDayMonth(Calendar date) {
        sdf = new SimpleDateFormat("E, MMM-dd");
        return sdf.format(date.getTime());
    }

    public static int getMonth(Calendar date){
        return date.getTime().getMonth();
    }

    public static String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        return DateHelper.dateToStandardString(cal);
    }

    public static int getCurrentWeek() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    }

    public static int getCurrentMonth(){
        return Calendar.getInstance().getTime().getMonth();
    }

    public static String getTimeFormat(int hour, int min){
        String format = "";
        StringBuilder sb;

        if (hour == 0) {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12) {
            format = "PM";
        }
        else if (hour > 12) {
            hour -= 12;
            format = "PM";
        }
        else {
            format = "AM";
        }

        sb = new StringBuilder().append(hour).append(":");
        if(min < 10){
            sb.append("0");
        }

        sb.append(min).append(" ").append(format);
        return sb.toString();
    }

    /*****************************************************/


    /********* CONVERT STRING TO DATE/CALENDAR **********/

    public static Date stringStandardToDate(String date){
        sdf = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        try{
            return sdf.parse(date);
        } catch(ParseException e){
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    public static Date stringToSqlDate(String date){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            return sdf.parse(date);
        } catch(ParseException e){
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    public static String getSqlTimeFormat(int hour, int min){
        return String.format("%d:%d:00", hour,min);
    }

    public static Calendar stringTimeToCalendar(String string){
        Calendar calNow = Calendar.getInstance();

        Date date1;
        sdf = new SimpleDateFormat("h:mm a");
        try{
            date1 = sdf.parse(string);
            Calendar calendar = (Calendar) calNow.clone();

            calendar.set(Calendar.HOUR, date1.getHours());
            calendar.set(Calendar.MINUTE , date1.getMinutes());

            return calendar;

        } catch(ParseException e){
            Log.e(TAG, e.getMessage());
        }

        return null;
    }



}
