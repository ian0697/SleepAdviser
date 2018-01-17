package com.santiagoapps.sleepadviser.main_activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.*;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.class_library.SleepSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


public class CreateRecord extends AppCompatActivity {

    private final String TAG = "TimeRecord";

    private Toolbar toolbar;

    //components
    private TextView tvSleepTime,tvWakeTime, tvWakeDate ,tvDuration;
    private Button btnCancel, btnAdd;
    private TimePickerDialog mTimePicker;
    private DatePickerDialog mDatePicker;
    private CardView card1, card2, card3;

    private SleepSession session;
    SimpleDateFormat inputFormat = new SimpleDateFormat("E M/dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        setToolbar();

        session = new SleepSession();

        //textView for sleeping time
        tvSleepTime = (TextView)findViewById(R.id.tvSleepTime);
        tvSleepTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSleepingTime();
            }
        });


        //textView for waking time
        tvWakeTime = (TextView)findViewById(R.id.tvWakeupTime);
        tvWakeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWakeUpTime();
            }
        });


        //textView for sleep session date
        tvWakeDate = (TextView)findViewById(R.id.tvWakeDate);
        Date now = new Date();
        tvWakeDate.setText(inputFormat.format(now));
        tvWakeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });


        tvDuration = (TextView) findViewById(R.id.tvDuration);

        //Buttons
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOnClick();
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToDashboard();
            }
        });

        setSleepingTime();
        setSleepQuality();

    }

    private void setSleepQuality(){
        card1 = (CardView)findViewById(R.id.card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card2.setCardBackgroundColor(getResources().getColor(R.color.app_color));
                card3.setCardBackgroundColor(getResources().getColor(R.color.app_color));
                card1.setCardBackgroundColor(getResources().getColor(R.color.blue_dark1));

            }
        });

        card2 = (CardView)findViewById(R.id.card2);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card2.setCardBackgroundColor(getResources().getColor(R.color.blue_dark1));
                card1.setCardBackgroundColor(getResources().getColor(R.color.app_color));
                card3.setCardBackgroundColor(getResources().getColor(R.color.app_color));

            }
        });

        card3 = (CardView)findViewById(R.id.card3);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card3.setCardBackgroundColor(getResources().getColor(R.color.blue_dark1));
                card2.setCardBackgroundColor(getResources().getColor(R.color.app_color));
                card1.setCardBackgroundColor(getResources().getColor(R.color.app_color));

            }
        });
    }

    private void setDate(){
        Calendar mCurrent = Calendar.getInstance();
        int year = mCurrent.get(Calendar.YEAR);
        int month = mCurrent.get(Calendar.MONTH);
        int day = mCurrent.get(Calendar.DAY_OF_MONTH);

        mDatePicker = new DatePickerDialog(CreateRecord.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                try {
                    SimpleDateFormat sdf = SleepSession.SLEEP_DATE_FORMAT;

                    String date1 = year + "-" + (month+1) + "-" + day + " " + session.getSleep_time();
                    String date2 = year + "-" + (month+1) + "-" + day + " " + session.getWake_time();

                    Log.d(TAG, "Sleep time: " + date1 + "\nWake up time: " + date2);

                    Calendar temp = Calendar.getInstance();
                    temp.setTime(sdf.parse(date1));

                    if((temp.get(Calendar.AM_PM)) == Calendar.PM){
                        temp.add(Calendar.DAY_OF_YEAR, -1);
                        date1 = year + "-" + (month+1) + "-" + (day-1) + " " + session.getSleep_time();
                    }

                    session.setSleep_date(sdf.parse(date1));
                    session.setWake_date(sdf.parse(date2));
                    tvDuration.setText(session.sleepDuration());
                    tvWakeDate.setText(inputFormat.format(session.getWake_date().getTime()));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, year, month, day);

        mDatePicker.setTitle("Select sleep date");
        mDatePicker.show();
    }

    private void setSleepingTime(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = 0;
        mTimePicker = new TimePickerDialog(CreateRecord.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String AM_PM;
                String minute;
                int hour = selectedHour;

                if(selectedMinute < 10){
                    minute = "0" + selectedMinute;
                } else {
                    minute = "" + selectedMinute;
                }

                if(selectedHour < 12){
                    AM_PM = "AM";
                } else{
                    hour = selectedHour - 12;
                    AM_PM = "PM";
                }

                tvSleepTime.setText( hour + ":" + minute + " " + AM_PM );
                session.setSleep_time( hour + ":" + minute + " " + AM_PM );

                setWakeUpTime();
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Sleeping Time");
        mTimePicker.show();



}

    private void setWakeUpTime(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(CreateRecord.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String AM_PM;
                String minute;
                int hour = selectedHour;

                if(selectedMinute < 10){
                    minute = "0" + selectedMinute;
                } else {
                    minute = "" + selectedMinute;
                }

                if(selectedHour < 12){
                    AM_PM = "AM";
                } else{
                    hour = selectedHour - 12;
                    AM_PM = "PM";
                }

                tvWakeTime.setText( hour + ":" + minute + " " + AM_PM );
                session.setWake_time( hour + ":" + minute + " " + AM_PM );
                setDate();
            }
        }, hour, minute, false);




        mTimePicker.setTitle("Select Wake Time");
        mTimePicker.show();
    }


    private void setToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Create sleep record");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToDashboard();
            }
        });
    }

    private void backToDashboard(){
        startActivity(new Intent(CreateRecord.this, NavigationMain.class));
        finish();
    }

    private void addOnClick(){
        //TODO : Add methods here
        Snackbar.make(findViewById(R.id.content), "Added sleep session", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }

}
