package com.santiagoapps.sleepadviser.class_library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
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
    private final static String TAG = "Dormie (" + DatabaseHelper.class.getSimpleName() + ") ";

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
    private static final String KEY_ID = "ID";
    private static final String KEY_CREATED_AT = "CREATED_AT";

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
     * @param user - a User object that contains name, email, password etc.
     * @return Result - will return -1 if user fail to insert user to db
     */
    public long registerUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIREBASE_ID, user.getFirebaseId());
        values.put(KEY_FULLNAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_CREATED_AT, getDateTime());


        long result = db.insert(TABLE_USER, null, values);


        if(result!=-1){
            Log.d(TAG,"User '" + user.getFirebaseId() + "' successfully registered!");
        } else {
            Log.e(TAG,"Error inserting data");
        }

        return result;
    }


    /**
     * Insert sleep session to the database
     *
     * @param session This is the sleepSession object containing all sleep-related dates
     * @return result : -1 if failed else; success
     */
    public long insertSession(SleepSession session){

        SimpleDateFormat sdf = SleepSession.SLEEP_DATE_FORMAT;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SLEEP_DATE, sdf.format(session.getSleep_date().getTime()));
        values.put(KEY_WAKE_DATE, sdf.format(session.getWake_date().getTime()));
        values.put(KEY_SLEEP_DURATION, session.getSleep_duration());
        values.put(KEY_SLEEP_RATING, session.getSleep_rating());

        long result = db.insert(TABLE_DATA, null, values);

        if(result!=-1){

            Log.d(TAG,"Sleep session successfully registered!");
        } else {
            Log.e(TAG,"Error inserting data");
        }

        return result;
    }




    /**************** FETCH METHODS ******************/



    /**
     * Fetch user using userId
     * @param userId the Primary key from the database table of User_tbl
     *
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
     * @return userList - all users from the database
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


    public List<SleepSession> getAllSession(){
        SimpleDateFormat sdf = SleepSession.SLEEP_DATE_FORMAT;
        List<SleepSession> session_list = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DATA;
        Log.e(TAG, query);

        Cursor res = db.rawQuery(query,null);

        while(res.moveToNext()){
            SleepSession session = new SleepSession();
            session.setSession_id(Integer.parseInt(res.getString(res.getColumnIndex(KEY_ID))));
            Log.d(TAG,res.getString(res.getColumnIndex(KEY_SLEEP_DATE)));
            try {
                session.setSleep_date(sdf.parse(res.getString(res.getColumnIndex(KEY_SLEEP_DATE))));
                session.setWake_date(sdf.parse(res.getString(res.getColumnIndex(KEY_WAKE_DATE))));
            } catch(ParseException e){
                e.printStackTrace();
            }

            session_list.add(session);
        }

        return session_list;
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

        return count;
    }


    /**
     * return session count
     */
    public int getSessionCount(){
        SQLiteDatabase x = this.getReadableDatabase();
        Cursor res = x.rawQuery("select * from " + TABLE_DATA, null);
        int count = res.getCount();
        res.close();
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
     * Delete all sleep sessions
     * @return true if rowsAffected is > 0
     */
    public boolean resetSessionTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        int rowsAffected = db.delete(TABLE_DATA,"1",null);
        db.delete("SQLITE_SEQUENCE","NAME = ?",new String[]{TABLE_DATA});

        Log.d(TAG, "All sessions deleted! Rows affected: " + rowsAffected);
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
                "yyyy-MM-dd H:mm a", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
