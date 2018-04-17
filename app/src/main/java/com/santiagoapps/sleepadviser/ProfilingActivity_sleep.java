package com.santiagoapps.sleepadviser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.santiagoapps.sleepadviser.activities.NavigationActivity;
import com.santiagoapps.sleepadviser.data.model.Session;

public class ProfilingActivity_sleep extends AppCompatActivity {
    private TimePicker timePicker;
    private Button btnEnter;
    private String format;
    private StringBuilder sb;
    String occupation , name, gender;
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiling_sleep);
        timePicker = findViewById(R.id.timePicker);
        timePicker.setHour(10);
        timePicker.setMinute(0);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        gender = (String) bd.get("SESSION_GENDER");
        name = (String) bd.get("SESSION_NAME");
        occupation = (String) bd.get("SESSION_OCCUPATION");
        age = (int) bd.get("SESSION_AGE");

        btnEnter = (Button) findViewById(R.id.BtnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int hr = timePicker.getHour();
                int min = timePicker.getMinute();

                Intent intent = new Intent(getBaseContext(), NavigationActivity.class);
                intent.putExtra("SESSION_SLEEP_GOAL", getTimeFormat(hr,min));
                intent.putExtra("SESSION_AGE", age);
                intent.putExtra("SESSION_GENDER", gender);
                intent.putExtra("SESSION_NAME",name);
                intent.putExtra("SESSION_OCCUPATION", occupation);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
    }

    private String getTimeFormat(int hour, int min){
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        sb = new StringBuilder().append(hour).append(":").append(min).append(" ").append(format);
        return sb.toString();
    }
}
