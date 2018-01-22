package com.santiagoapps.sleepadviser.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.santiagoapps.sleepadviser.data.DatabaseManager;
import com.santiagoapps.sleepadviser.data.model.Session;
import com.santiagoapps.sleepadviser.helpers.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.santiagoapps.sleepadviser.data.model.Session.*;

/**
 * Created by Ian on 1/23/2018.
 */

public class SessionRepo {



    private Session session;

    public SessionRepo(){
        session = new Session();
    }

    public static String createTable(){
        return String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " +   //KEY_SLEEP_DATE
                        "%s TEXT, " +   //KEY_WAKE_DATE
                        "%s TEXT, " +   //KEY_SLEEP_RATING
                        "%s TEXT, " +   //KEY_SLEEP_DURATION
                        "%s DATETIME)", //KEY_CREATED_AT
                Session.TABLE_DATA, DBHelper.KEY_ID, KEY_SLEEP_DATE, KEY_WAKE_DATE,
                Session.KEY_SLEEP_RATING, Session.KEY_SLEEP_DURATION, DBHelper.KEY_CREATED_AT);
    }

    /************** INSERT METHODS ******************/


    /**
     * Insert sleep session to the database
     *
     * @param session This is the sleepSession object containing all sleep-related dates
     * @return result : -1 if failed else; success
     */
    public long insertSession(Session session){

        SimpleDateFormat sdf = Session.SLEEP_DATE_FORMAT;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

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

    public List<Session> getAllSession(){
        SimpleDateFormat sdf = Session.SLEEP_DATE_FORMAT;
        List<Session> session_list = new ArrayList<>();

        SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + TABLE_DATA;
        Log.e(TAG, query);

        Cursor res = db.rawQuery(query,null);

        while(res.moveToNext()){
            Session session = new Session();
            session.setSession_id(Integer.parseInt(res.getString(res.getColumnIndex(DBHelper.KEY_ID))));
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
     * return session count
     */
    public int getSessionCount(){
        SQLiteDatabase x = DatabaseManager.getInstance().openDatabase();
        Cursor res = x.rawQuery("select * from " + TABLE_DATA, null);
        int count = res.getCount();
        res.close();
        return count;
    }



    /************* DELETE METHODS *****************/

    /**
     * Delete all sleep sessions
     * @return true if rowsAffected is > 0
     */
    public boolean resetSessionTable(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        int rowsAffected = db.delete(TABLE_DATA,"1",null);
        db.delete("SQLITE_SEQUENCE","NAME = ?",new String[]{TABLE_DATA});

        Log.d(TAG, "All sessions deleted! Rows affected: " + rowsAffected);
        return rowsAffected > 0;
    }






}
