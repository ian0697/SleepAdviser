package com.santiagoapps.sleepadviser.main_activity;

import android.app.Dialog;
import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.TimePicker;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.class_library.SleepService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SleepingActivity extends AppCompatActivity {
    private Dialog myDialog;
    private TextView txtTime;
    private TextView txtAm;
    private TextView tvDescription;
    private Button btnSleep;

    Intent sleep_service;

    private String wake_time = "";
    private Toolbar toolbar;
    public static Camera cam;
    private static final String TAG = "SleepAdviser";
    Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeping);

        initComponents();
        setUpAlarm();
        setUpToolbar();
        showAlarmDialog();
    }

    public void initComponents(){
        myDialog = new Dialog(SleepingActivity.this);
        txtTime = (TextView)findViewById(R.id.txtTime);
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

    public void setUpAlarm(){
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlarmDialog();
            }
        });
    }

    public void startSleeping(){
        sleep_service = new Intent(SleepingActivity.this, SleepService.class);
        startService(sleep_service);
    }

    public void showAlarmDialog(){
        TextView txtClose;
        CardView btnEnter;
        final TimePicker timePicker;
        myDialog.setContentView(R.layout.dialog_alarm);
        timePicker = myDialog.findViewById(R.id.timePicker);

        btnEnter = myDialog.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get time
                String AM_PM;
                int hour = timePicker.getHour();
                int min = timePicker.getMinute();

                String minute;
                if(timePicker.getMinute() < 10){
                    minute = "0" + timePicker.getMinute();
                } else {
                    minute = "" + timePicker.getMinute();
                }

                if(hour < 12){
                    AM_PM = "AM";
                } else{
                    hour = timePicker.getHour() - 12;
                    AM_PM = "PM";
                }

                txtTime.setText(hour + ":" + minute);
                txtAm.setText(AM_PM);

                wake_time = hour + ":" + minute + " " + AM_PM;
                tvDescription.setText("Wake me up at " + wake_time);
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

    public void wakeUp(View v){
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
