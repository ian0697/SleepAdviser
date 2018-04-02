package com.santiagoapps.sleepadviser.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.santiagoapps.sleepadviser.data.model.Session;
import com.santiagoapps.sleepadviser.data.model.User;
import com.santiagoapps.sleepadviser.data.repo.SessionRepo;
import com.santiagoapps.sleepadviser.data.repo.UserRepo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * DBHelper
 *
 * This class handles all the
 * database configurations
 */

public class DBHelper extends SQLiteOpenHelper {

    //logCat TAG
    private final static String TAG = "Dormie (" + DBHelper.class.getSimpleName() + ") ";

    //Db version
    private static final int DATABASE_VERSION = 3;

    //Db name:
    private static final String DATABASE_NAME = "SleepAdviserDB.db";

    //TBL names:
    private static final String TABLE_SLEEP_USER = "tbl_data_user";

    /* COLUMN NAMES */
    //Common column names;
    public static final String KEY_ID = "ID";
    public static final String KEY_CREATED_AT = "CREATED_AT";

    //DATA_USER TABLE - column names
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_SLEEP_ID = "sleep_id";

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
        db.execSQL(UserRepo.createTable());
        db.execSQL(SessionRepo.createTable());
        db.execSQL(CREATE_TABLE_USERDATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Session.TABLE);
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
