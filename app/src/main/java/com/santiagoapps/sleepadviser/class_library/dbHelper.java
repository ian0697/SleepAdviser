package com.santiagoapps.sleepadviser.class_library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ariel on 11/11/2017.
 */

public class dbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "user_table";
    private static final String ID = "USER_ID";
    private static final String NAME = "USER_NAME";
    private static final String PASSWORD = "USER_PASS";
    private static final String GENDER = "USER_GENDER";
    private static final String AGE = "USER_AGE";
    private static final String OCCUPATION = "USER_OCCUPATION";
    private static final String SLEEPID = "SLEEP_ID";
    private static final String SLEEPTIME = "SLEEP_TIME";
    private static final String WAKEUPTIME = "WAKEUP_TIME";
    private static final String SLEEPHOUR = "SLEEP_HOUR";

    public dbHelper(Context x){
        super(x, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase x) {
        x.execSQL("create table" + TABLE_NAME + " (USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT, USER_PASSWORD TEXT, USER_GENDER TEXT, USER_AGE TEXT, USER_OCCUPATION TEXT, SLEEP_ID INTEGER AUTOINCREMENT, SLEEP_TIME TEXT, WAKEUP_TIME TEXT, SLEEP_HOUR TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase x, int i, int i1) {
        x.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(x);
    }

    public boolean insertData(String name, String pass, String gender, String age, String occupation, String sleep_time, String wakeup_time, String sleep_hour){
        SQLiteDatabase x = this.getWritableDatabase();
        ContentValues cV = new ContentValues();
        cV.put(NAME, name);
        cV.put(PASSWORD, pass);
        cV.put(GENDER, gender);
        cV.put(AGE, age);
        cV.put(OCCUPATION, occupation);
        cV.put(SLEEPTIME, sleep_time);
        cV.put(WAKEUPTIME, wakeup_time);
        cV.put(SLEEPHOUR, sleep_hour);

        long result = x.insert(TABLE_NAME, null, cV);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase x = this.getWritableDatabase();
        Cursor z = x.rawQuery("Select * from " + TABLE_NAME, null);
        return z;
    }


}
