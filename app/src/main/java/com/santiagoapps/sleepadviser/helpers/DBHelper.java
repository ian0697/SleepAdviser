package com.santiagoapps.sleepadviser.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.santiagoapps.sleepadviser.data.model.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ian on 10/23/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    //logCat TAG
    private final static String TAG = "Dormie (" + DBHelper.class.getSimpleName() + ") ";

    //Db version
        private static final int DATABASE_VERSION = 3;

    //Db name:
    public static final String DATABASE_NAME = "SleepAdviserDB.db";

    //TBL names:
    private static final String TABLE_USER = "tbl_user";

    private static final String TABLE_DATA ="tbl_data";
    private static final String TABLE_SLEEP_USER = "tbl_data_user";
    /* COLUMN NAMES */
    //Common column names;
    public static final String KEY_ID = "ID";
    public static final String KEY_CREATED_AT = "CREATED_AT";

    //USER TABLE - column names
    private static final String KEY_FIREBASE_ID = "FIREBASE_ID";
    private static final String KEY_FULLNAME = "FULL_NAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PASSWORD = "PASSWORD";

    //SLEEP SESSION TABLE - column names
    private static final String KEY_SLEEP_DATE = "SLEEP_TIME";
    private static final String KEY_WAKE_DATE = "WAKE_TIME";
    private static final String KEY_SLEEP_RATING = "SLEEP_RATING";
    private static final String KEY_SLEEP_DURATION = "SLEEP_DURATION";

    //DATA_USER TABLE - column names
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_SLEEP_ID = "sleep_id";

    /* TABLE CREATE STATEMENTS */
    //USER TABLE create statement
    private static final String CREATE_TABLE_USERS = String.format("CREATE TABLE %s " +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s DATETIME)",
                    TABLE_USER, KEY_ID, KEY_FIREBASE_ID, KEY_FULLNAME,KEY_EMAIL, KEY_PASSWORD, KEY_CREATED_AT);

    //DATA TABLE create statement
    private static final String CREATE_TABLE_DATA = String.format("CREATE TABLE %s " +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +   //KEY_SLEEP_DATE
                    "%s TEXT, " +   //KEY_WAKE_DATE
                    "%s TEXT, " +   //KEY_SLEEP_RATING
                    "%s TEXT, " +   //KEY_SLEEP_DURATION
                    "%s DATETIME)", //KEY_CREATED_AT
            TABLE_DATA, KEY_ID, KEY_SLEEP_DATE, KEY_WAKE_DATE,
            KEY_SLEEP_RATING, KEY_SLEEP_DURATION, KEY_CREATED_AT);

    //DATA_USER TABLE create statement
    private static final String CREATE_TABLE_USERDATA = String.format("CREATE TABLE %s " +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s DATETIME)",
            TABLE_SLEEP_USER, KEY_ID, KEY_USER_ID, KEY_SLEEP_ID, KEY_CREATED_AT);


    //CONSTRUCTOR
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_DATA);
        db.execSQL(CREATE_TABLE_USERDATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLEEP_USER);
        onCreate(db);
    }


    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd H:mm a", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
