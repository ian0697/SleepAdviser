package com.santiagoapps.sleepadviser.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Ian on 1/23/2018.
 */

public class DateHelper {

    private static final String TAG = "Dormie("+ DateHelper.class.getSimpleName() + ")";
    private static String LOG_TEXT = "Date must not be null";

    private static SimpleDateFormat sdf;
    private Date date;
    private Calendar calendar;

    public DateHelper(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getDay() {
        sdf = new SimpleDateFormat("dd");
        if (date != null) {
            return sdf.format(date);
        } else{
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }


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


    public String getHourFormat(Date date){
        sdf = new SimpleDateFormat("h:mm a");
        if(date!=null) return sdf.format(date);
        else {
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }

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

    public static String dateToString(Date date){
        sdf = new SimpleDateFormat("yyyy-MM-dd h:mm a");

        if(date!=null) return sdf.format(date);
        else{
            Log.d(TAG, LOG_TEXT);
            return null;
        }
    }

    public String dateToString(){
        Date date = calendar.getTime();
        sdf = new SimpleDateFormat("yyyy-MM-dd h:mm a");

        if(date!=null) return sdf.format(date);
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
}
