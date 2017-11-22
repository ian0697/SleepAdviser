package com.santiagoapps.sleepadviser.class_library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ian on 10/23/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //logCat TAG
    private static final String TAG = "SleepAdviserDB";

    //Db version
    private static final int DATABASE_VERSION = 1;

    //Db name:
    public static final String DATABASE_NAME = "SleepAdviserDB.db";

    //TBL names:
    private static final String TABLE_USER = "tbl_user";
    private static final String TABLE_DATA ="tbl_data";
    private static final String TABLE_SLEEP_USER = "tbl_data_user";

    //Common column names;
    private static final String KEY_ID = "ID";
    private static final String KEY_CREATED_AT = "CREATED_AT";

    //USER TABLE - column names
    private static final String KEY_FULLNAME = "FULL_NAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PASSWORD = "PASSWORD";

    //DATA TABLE - column names
    private static final String KEY_SLEEP_TIME = "SLEEP_TIME";
    private static final String KEY_WAKE_TIME = "WAKE_TIME";
    private static final String KEY_SLEEP_QUALITY = "SLEEP_QUALITY";

    //DATA_USER TABLE - column names
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_SLEEP_ID = "sleep_id";

    //TABLE CREATE STATEMENTS
    //USER TABLE create statement
    private static final String CREATE_TABLE_USERS = String.format("CREATE TABLE %s " +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s DATETIME)",
                    TABLE_USER,KEY_ID, KEY_FULLNAME,KEY_EMAIL, KEY_PASSWORD, KEY_CREATED_AT);

    //DATA TABLE create statement
    private static final String CREATE_TABLE_DATA = String.format("CREATE TABLE %s " +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s DATETIME)",
            TABLE_DATA, KEY_ID, KEY_SLEEP_TIME, KEY_WAKE_TIME, KEY_SLEEP_QUALITY, KEY_CREATED_AT);

    //DATA_USER TABLE create statement
    private static final String CREATE_TABLE_USERDATA = String.format("CREATE TABLE %s " +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s DATETIME)",
            TABLE_SLEEP_USER, KEY_ID, KEY_USER_ID, KEY_SLEEP_ID, KEY_CREATED_AT);

    //CONSTRUCTOR
    public DatabaseHelper(Context context) {
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

    /*
     * REGISTERING USER
     */
    public long registerUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FULLNAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_CREATED_AT, getDateTime());
        long result = db.insert(TABLE_USER, null, values);

        return result;
    }

    /*
     * FETCH SINGLE USER
     */
    public User getUser(long userId){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_ID + " = " + userId;
        Log.e(TAG,query);

        Cursor c = db.rawQuery(query, null);
        if(c!=null){
            c.moveToFirst();
        } else{
            Log.e(TAG,"CURSOR IS NULL");
        }

        User user = new User();
        try{
            user.setName(c.getString(1));
            user.setEmail(c.getString(2));
            user.setPassword(c.getString(3));
            user.setDateRegistered(c.getString(4));
        } catch(Exception e){
            Log.e(TAG , "User not found");
        }


        return user;
    }

    /*
     * RETURN ALL TABLE USER DATA
     */
    public Cursor getAllData(){
        SQLiteDatabase x = this.getWritableDatabase();
        Cursor res = x.rawQuery("select * from " + TABLE_USER, null);
        return res;
    }

    /*
     * RETURN USER RECORD COUNTS
     */
    public int getUserCount(){
        SQLiteDatabase x = this.getReadableDatabase();
        Cursor res = x.rawQuery("select * from " + TABLE_USER, null);
        int count = res.getCount();
        res.close();
        Log.d(TAG, "User record count: " + count);
        return count;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
