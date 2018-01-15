package com.santiagoapps.sleepadviser;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateRecord extends AppCompatActivity {

    private Toolbar toolbar;

    //components
    private TextView tvSleepTime;
    private TextView tvWakeTime;
    private TextView tvDuration;
    private TextView tvWakeDate;
    private TimePickerDialog mTimePicker;
    private DatePickerDialog mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        setToolbar();

        tvSleepTime = (TextView)findViewById(R.id.tvSleepTime);
        tvSleepTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSleepingTime();
            }
        });

        tvWakeTime = (TextView)findViewById(R.id.tvWakeupTime);
        tvWakeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWakeUpTime();
            }
        });

        tvWakeDate = (TextView)findViewById(R.id.tvWakeDate);
        tvWakeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

        tvDuration = (TextView) findViewById(R.id.tvDuration);


        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd h:mm a");

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(format.parse("2018-1-15 11:00 pm"));
            c2.setTime(format.parse("2018-1-16 6:30 am"));



            long mills = c2.getTimeInMillis() - c1.getTimeInMillis();
            int hours = (int) (mills/(1000 * 60 * 60));
            int mins = (int) (mills/(1000*60)) % 60;

            String diff = hours + " hours " + mins + " minutes"; // updated value every1 second
            tvDuration.setText(diff);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setDate(){
        Calendar mCurrent = Calendar.getInstance();
        int year = mCurrent.get(Calendar.YEAR);
        int month = mCurrent.get(Calendar.MONTH);
        int day = mCurrent.get(Calendar.DAY_OF_MONTH);

        mDatePicker = new DatePickerDialog(CreateRecord.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            }
        }, year, month, day);

        mDatePicker.setTitle("Select sleep date");
        mDatePicker.show();
    }

    private void setSleepingTime(){
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


                tvSleepTime.setText( hour + ":" + minute + " " + AM_PM);
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

                tvWakeTime.setText( hour + ":" + minute + " " + AM_PM);
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
                finish();
                Toast.makeText(CreateRecord.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
