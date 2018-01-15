package com.santiagoapps.sleepadviser.class_library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ian on 10/23/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //logCat TAG
    private static final String TAG = "SleepAdviserDB";

    //Db version
    private static final int DATABASE_VERSION = 2;

    //Db name:
    public static final String DATABASE_NAME = "SleepAdviserDB.db";

    //TBL names:
    private static final String TABLE_USER = "tbl_user";
    private static final String TABLE_DATA ="tbl_data";
    private static final String TABLE_SLEEP_USER = "tbl_data_user";

    /* COLUMN NAMES */
    //Common column names;
    private static final String KEY_ID = "ID";
    private static final String KEY_CREATED_AT = "CREATED_AT";

    //USER TABLE - column names
    private static final String KEY_FIREBASE_ID = "FIREBASE_ID";
    private static final String KEY_FULLNAME = "FULL_NAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PASSWORD = "PASSWORD";

    //DATA TABLE - column names
    private static final String KEY_SLEEP_TIME = "SLEEP_TIME";
    private static final String KEY_WAKE_TIME = "WAKE_TIME";
    private static final String KEY_SLEEP_QUALITY = "SLEEP_QUALITY";
    private static final String KEY_TIME_ASLEEP = "TIME_ASLEEP";
    private static final String KEY_TIME_IN_BED = "TIME_IN_BED";
    private static final String KEY_TIME_TO_SLEEP = "TIME_TO_SLEEP";
    private static final String KEY_MOOD = "SLEEP_MOOD";

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
                    "%s TEXT, " +   //KEY_SLEEP_TIME
                    "%s TEXT, " +   //KEY_WAKE_TIME
                    "%s TEXT, " +   //KEY_SLEEP_QUALITY
                    "%s TEXT, " +   //KEY_TIME_ASLEEP
                    "%s TEXT, " +   //KEY_TIME_IN_BED
                    "%s TEXT, " +   //KEY_TIME_TO_SLEEP
                    "%s TEXT, " +   //KEY_MOOD
                    "%s DATETIME)", //KEY_CREATED_AT
            TABLE_DATA, KEY_ID, KEY_SLEEP_TIME, KEY_WAKE_TIME,
            KEY_SLEEP_QUALITY, KEY_TIME_ASLEEP, KEY_TIME_IN_BED,
            KEY_TIME_TO_SLEEP, KEY_MOOD, KEY_CREATED_AT);

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

    /************** INSERT METHODS ******************/

    /**
     * REGISTERING USER
     */
    public long registerUser(User user){
        Log.d(TAG,user.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIREBASE_ID, user.getFirebaseId());
        values.put(KEY_FULLNAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_CREATED_AT, getDateTime());

        long result = db.insert(TABLE_USER, null, values);
        Log.d(TAG, "Value of database result is : " + result);
        return result;
    }


    /**************** FETCH METHODS ******************/

    /**
     * FETCH SINGLE USER by id
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
            user.setFirebaseId(c.getString(c.getColumnIndex(KEY_FIREBASE_ID)));
            user.setName(c.getString(c.getColumnIndex(KEY_FULLNAME)));
            user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
            user.setDateRegistered(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
        } catch(Exception e){
            Log.e(TAG , "User not found");
        }


        return user;
    }

    /**
     * RETURN ALL USER DATA AS LISTS
     */
    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_USER;
        Log.e(TAG,query);

        Cursor res = db.rawQuery(query, null);

        while(res.moveToNext()){
            User user = new User();
            user.setFirebaseId(res.getString(res.getColumnIndex(KEY_FIREBASE_ID)));
            user.setName(res.getString(res.getColumnIndex(KEY_FULLNAME)));
            user.setEmail(res.getString(res.getColumnIndex(KEY_EMAIL)));
            user.setPassword(res.getString(res.getColumnIndex(KEY_PASSWORD)));
            user.setDateRegistered(res.getString(res.getColumnIndex(KEY_CREATED_AT)));
            userList.add(user);
        }

        return userList;
    }


    /**
     * RETURN ALL TABLE USER DATA
     */
    public Cursor getAllData(){
        SQLiteDatabase x = this.getWritableDatabase();
        Cursor res = x.rawQuery("select * from " + TABLE_USER, null);
        return res;
    }

    /**
     * RETURN USER RECORD COUNT
     */
    public int getUserCount(){
        SQLiteDatabase x = this.getReadableDatabase();
        Cursor res = x.rawQuery("select * from " + TABLE_USER, null);
        int count = res.getCount();
        res.close();
        Log.d(TAG, "User record count: " + count);
        return count;
    }

    /************* DELETE METHODS *****************/

    /**
     * DELETE ALL USER
     */
    public boolean resetUserTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        int rowsAffected = db.delete(TABLE_USER,"1",null);
        db.delete("SQLITE_SEQUENCE","NAME = ?",new String[]{TABLE_USER});

        Log.d(TAG, "All user records deleted! Rows affected: " + rowsAffected);
        return rowsAffected > 0;
    }

    /**
     * DELETE USER BY ID
     */
    public boolean deleteUserById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG,"DELETING " + getUser(id).toString());

        int rowsAffected = db.delete(TABLE_USER, "WHERE " + KEY_ID + " = " + id, null);
        Log.d(TAG, "Rows affected: " + rowsAffected);
        return rowsAffected > 0;
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
