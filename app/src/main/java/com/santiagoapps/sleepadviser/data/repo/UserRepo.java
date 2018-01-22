package com.santiagoapps.sleepadviser.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.santiagoapps.sleepadviser.helpers.DBHelper;
import com.santiagoapps.sleepadviser.data.model.User;
import com.santiagoapps.sleepadviser.data.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ian on 1/23/2018.
 */

public class UserRepo {

    private User user;

    public UserRepo(){
        user = new User();
    }

    public static String createTable(){

        return String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s DATETIME)",
                User.TABLE, DBHelper.KEY_ID, User.KEY_FIREBASE_ID, User.KEY_FULLNAME, User.KEY_EMAIL,
                User.KEY_PASSWORD, DBHelper.KEY_CREATED_AT);

    }



    /**
     * @param user - a User object that contains name, email, password etc.
     * @return Result - will return -1 if user fail to insert user to db
     */
    public long registerUser(User user){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(User.KEY_FIREBASE_ID, user.getFirebaseId());
        values.put(User.KEY_FULLNAME, user.getName());
        values.put(User.KEY_EMAIL, user.getEmail());
        values.put(User.KEY_PASSWORD, user.getPassword());
        values.put(DBHelper.KEY_CREATED_AT, DBHelper.getDateTime());


        long result = db.insert(User.TABLE, null, values);


        if(result!=-1){
            Log.d(User.TAG,"User '" + user.getFirebaseId() + "' successfully registered!");
        } else {
            Log.e(User.TAG,"Error inserting data");
        }

        DatabaseManager.getInstance().closeDatabase();
        return result;
    }


    /************* FETCH METHODS *****************/
    /**
     * Fetch user using userId
     * @param userId the Primary key from the database table of User_tbl
     *
     */
    public User getUser(long userId){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        String query = "SELECT * FROM " + User.TABLE + " WHERE " + DBHelper.KEY_ID + " = " + userId;
        Log.e(User.TAG,query);

        Cursor c = db.rawQuery(query, null);
        if(c!=null){
            c.moveToFirst();
        } else{
            Log.e(User.TAG,"CURSOR IS NULL");
        }


        try{
            user.setFirebaseId(c.getString(c.getColumnIndex(User.KEY_FIREBASE_ID)));
            user.setName(c.getString(c.getColumnIndex(User.KEY_FULLNAME)));
            user.setEmail(c.getString(c.getColumnIndex(User.KEY_EMAIL)));
            user.setPassword(c.getString(c.getColumnIndex(User.KEY_PASSWORD)));
            user.setDateRegistered(c.getString(c.getColumnIndex(DBHelper.KEY_CREATED_AT)));

        } catch(Exception e){
            Log.e(User.TAG , "User not found");
        }

        DatabaseManager.getInstance().closeDatabase();
        return user;
    }

    /**
     * @return userList - all users from the database
     */
    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        String query = "SELECT * FROM " + User.TABLE;
        Log.e(User.TAG,query);

        Cursor res = db.rawQuery(query, null);

        while(res.moveToNext()){
            user = new User();
            user.setFirebaseId(res.getString(res.getColumnIndex(User.KEY_FIREBASE_ID)));
            user.setName(res.getString(res.getColumnIndex(User.KEY_FULLNAME)));
            user.setEmail(res.getString(res.getColumnIndex(User.KEY_EMAIL)));
            user.setPassword(res.getString(res.getColumnIndex(User.KEY_PASSWORD)));
            user.setDateRegistered(res.getString(res.getColumnIndex(DBHelper.KEY_CREATED_AT)));
            userList.add(user);
        }

        DatabaseManager.getInstance().closeDatabase();
        return userList;
    }

    //Return user record counts
    public int getUserCount(){
        SQLiteDatabase x = DatabaseManager.getInstance().openDatabase();
        Cursor res = x.rawQuery("select * from " + User.TABLE, null);
        int count = res.getCount();
        res.close();

        return count;
    }

    //Return user records as Cursor
    public Cursor getAllData(){
        SQLiteDatabase x = DatabaseManager.getInstance().openDatabase();
        Cursor res = x.rawQuery("select * from " + User.TABLE, null);
        return res;
    }


    /************* DELETE METHODS *****************/
    //Delete all users
    public boolean resetUserTable(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        int rowsAffected = db.delete(User.TABLE,"1",null);
        db.delete("SQLITE_SEQUENCE","NAME = ?",new String[]{User.TABLE});

        Log.d(User.TAG, "All user records deleted! Rows affected: " + rowsAffected);
        return rowsAffected > 0;
    }

    //Delete user by Id
    public boolean deleteUserById(int id){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Log.d(User.TAG,"DELETING " + getUser(id).toString());

        int rowsAffected = db.delete(User.TABLE, "WHERE " + DBHelper.KEY_ID + " = " + id, null);
        Log.d(User.TAG, "Rows affected: " + rowsAffected);
        return rowsAffected > 0;
    }

}
