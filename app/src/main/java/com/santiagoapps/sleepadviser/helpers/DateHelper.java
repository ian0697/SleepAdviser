package com.santiagoapps.sleepadviser.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    /*********** CONVERT CALENDAR TO STRING ***********/

    public static String getMonthDay(Date date){
        sdf = new SimpleDateFormat("E M/dd");

        if(date!=null) return sdf.format(date);
        else{
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }

    public static String getMonthDay(Calendar date){
        sdf = new SimpleDateFormat("E M/dd");

        if(date!=null) return sdf.format(date.getTime());
        else{
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }

    public static String dateToString(Calendar date){
        sdf = new SimpleDateFormat("yyyy-MM-dd h:mm a");

        if(date!=null) return sdf.format(date.getTime());
        else{
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }

    public static String dateToString(Date date){
        sdf = new SimpleDateFormat("yyyy-MM-dd h:mm a");

        if(date!=null) return sdf.format(date);
        else{
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }

    public static String dateToTime(Calendar date){
        sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(date.getTime());
    }

    public static String dateToDayMonth(Calendar date) {
        sdf = new SimpleDateFormat("E, MM-dd");
        return sdf.format(date.getTime());
    }

    public static int getMonth(Calendar date){
        return date.getTime().getMonth();
    }

    public static String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        return DateHelper.dateToString(cal);
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
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
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

    public static Date stringToDate(String date){
        sdf = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        try{
            return sdf.parse(date);
        } catch(ParseException e){
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    public static Calendar stringToCalendar(String string){
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
