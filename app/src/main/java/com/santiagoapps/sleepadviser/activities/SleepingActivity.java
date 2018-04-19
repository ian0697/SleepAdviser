package com.santiagoapps.sleepadviser.activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.hardware.Camera;
import java.util.Calendar;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.TimePicker;

import com.santiagoapps.sleepadviser.receivers.AlarmNotificationReceiver;
import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.SleepService;
import com.santiagoapps.sleepadviser.adapter.MessageAdapter;
import com.santiagoapps.sleepadviser.data.model.Message;
import com.santiagoapps.sleepadviser.helpers.DateHelper;

/**
 * SleepingActivity
 *
 * This activity handles the ui
 * when the sleep now fab(button) is pressed
 *
 */

public class SleepingActivity extends AppCompatActivity {

    private static final String TAG = "SleepAdviser";

    /* components */
    private Dialog myDialog;
    private TextView txtTime;
    private TextView txtAm;
    private TextView tvDescription;
    private Button btnSleep;
    private Toolbar toolbar;
    private TimePicker timePicker;


    /* for bottomsheet */
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottomsheet;
    private ListView chat_list;
    private MessageAdapter mAdapter;
    private Context context = this;

    /* sleep service related */
    Intent sleep_service;
    private String wake_time = "";
    private Calendar calendar;

    /* for flash */
    public static Camera cam;
    Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeping);

        initComponents();
        setUpToolbar();

        setUpBottomsheet();
    }

    public void setUpBottomsheet(){

        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        chat_list = bottomsheet.findViewById(R.id.list_chat);
        mAdapter = new MessageAdapter(this,R.layout.single_message);
        chat_list.setAdapter(mAdapter);
        chat_list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        mAdapter.add(new Message(true, "Hi! I recommend that you wake up between 6am - 8am."));
        mAdapter.add(new Message(true, "Set the alarm to 6am?"));

        TextView choice_1 = bottomsheet.findViewById(R.id.choice_1);
        choice_1.setText("Yes please!");
        choice_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO

            }
        });

        TextView choice_2 = bottomsheet.findViewById(R.id.choice_2);
        choice_2.setText("No, I'll set it myself");
        choice_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                mAdapter.add(new Message(true, "No, I'll set it myself"));
                showAlarmDialog();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });


        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                chat_list.setSelection(mAdapter.getCount()-1);
            }
        });
    }

    public void initComponents(){
        bottomsheet = (LinearLayout)findViewById(R.id.bottomsheet);
        calendar = Calendar.getInstance();
        myDialog = new Dialog(SleepingActivity.this);

        txtTime = (TextView)findViewById(R.id.txtTime);
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlarmDialog();
            }
        });

        txtAm = (TextView)findViewById(R.id.txtAm);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        btnSleep = (Button) findViewById(R.id.btnStartSleep);
    }

    public void setUpToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Sleep now");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void startSleeping(){
        sleep_service = new Intent(SleepingActivity.this, SleepService.class);
        startAlarm();
        startService(sleep_service);
    }

    public void startAlarm(){
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Intent myIntent = new Intent(SleepingActivity.this, AlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(context, DateHelper.dateToString(calendar), Toast.LENGTH_SHORT).show();

    }

    public void showAlarmDialog(){
        TextView txtClose;
        CardView btnEnter;
        myDialog.setContentView(R.layout.dialog_alarm);
        timePicker = myDialog.findViewById(R.id.timePicker);

        btnEnter = myDialog.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm(timePicker.getHour(), timePicker.getMinute());
                myDialog.dismiss();
            }
        });

        txtClose = myDialog.findViewById(R.id.txtClose);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        myDialog.show();
    }

    public void setAlarm(int hr, int minute){
        //Get time
        String AM_PM;
        int hour = hr;
        int min = minute;

        String minuteStr;
        if(timePicker.getMinute() < 10){
            minuteStr = "0" + timePicker.getMinute();
        } else {
            minuteStr = "" + timePicker.getMinute();
        }

        if(hour < 12){
            AM_PM = "AM";
        } else{
            hour = timePicker.getHour() - 12;
            AM_PM = "PM";
        }

        txtTime.setText(hour + ":" + minuteStr);
        txtAm.setText(AM_PM);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.MILLISECOND, 0);


        wake_time = hour + ":" + minuteStr + " " + AM_PM;
        tvDescription.setText("Wake me up at " + wake_time);
    }

    public void btnSleep_onClick(View v){
        //TODO: for time record (Sleep time)
        startSleeping();

        //DialogBox set-up
        TextView txtClose;
        final TextView flashDesc;
        CardView btnWake;
        CardView btnFlash;
        myDialog.setContentView(R.layout.dialog_unlocked);

        txtClose = (TextView) myDialog.findViewById(R.id.txtClose);
        flashDesc = (TextView) myDialog.findViewById(R.id.flashDesc);
        btnFlash = (CardView) myDialog.findViewById(R.id.btnFlash);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if flash is open
                if(isOpen){
                    cam.stopPreview();
                    cam.release();
                    cam = null;

                    flashDesc.setText("OPEN FLASHLIGHT");
                    isOpen = false;

                    Log.d(TAG, "FLASHLIGHT IS OFF!");
                }
                myDialog.dismiss();
            }
        });


        btnWake = (CardView) myDialog.findViewById(R.id.btnWake);
        btnWake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(sleep_service);
            }
        });

        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isOpen){
                    cam = Camera.open();
                    Camera.Parameters p = cam.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    cam.setParameters(p);
                    cam.startPreview();

                    flashDesc.setText("CLOSE FLASHLIGHT");
                    isOpen = true;

                    Log.d(TAG, "FLASHLIGHT IS ON!");
                }else  {
                    cam.stopPreview();
                    cam.release();
                    cam = null;

                    flashDesc.setText("OPEN FLASHLIGHT");
                    isOpen = false;

                    Log.d(TAG, "FLASHLIGHT IS OFF!");
                }

            }
        });

        myDialog.show();


    }
}
