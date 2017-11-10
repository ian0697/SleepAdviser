package com.santiagoapps.sleepadviser.class_library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ian on 10/23/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "alarm.db";
    public static final String TABLE_NAME = "alarm_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT",
                TABLE_NAME,COL_1,COL_2,COL_3,COL_4 );
        db.execSQL(
            "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TIME TEXT, DATE TEXT, DESC TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(){}
}
