package com.santiagoapps.sleepadviser.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.*;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.santiagoapps.sleepadviser.R;

import static java.lang.Math.sqrt;

/**
 * Register Activity
 * -------------------------
 * this activity serves as a
 * tester of the motion sensor
 * of the app
 */

public class MotionSensorActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor sensor;
    private SensorManager sensorManager;
    private SensorEvent sensorEvent;
    private Toolbar toolbar;
    private TextView xText,yText,zText,mText;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_sensor_test);

        init();
    }

    private void init(){
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        mText = (TextView)findViewById(R.id.mText);


        toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Test motion sensor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sensorManager = (SensorManager)getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor,sensorManager.SENSOR_DELAY_NORMAL);

        mAccel = 0.0f;
        mAccelCurrent = sensorManager.GRAVITY_EARTH;
        mAccelLast = sensorManager.GRAVITY_EARTH;


    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float myGravity[];
        myGravity = sensorEvent.values.clone();

        xText.setText("X: " + myGravity[0]);
        yText.setText("Y: " + myGravity[1]);
        zText.setText("Z: " + myGravity[2]);

        //Formula for Shaking
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) sqrt(myGravity[0] * myGravity[0] + myGravity[1] * myGravity[1]+ myGravity[2] * myGravity[2]);
        float delta = mAccelCurrent - mAccelLast;

        //Formula for Acceleration
        mAccel = mAccel * 0.9f + delta;
        mText.setText("mAccel: " + mAccel);

        //Absolute values
        float xAbs = Math.abs(myGravity[0]);
        float yAbs = Math.abs(myGravity[1]);
        float mAbs = Math.abs(mAccel);

        if (mAbs > 2   &&  (yAbs > 4 || xAbs >4) ) {
            Log.d("TESTING", "You picked your phone - Accel: " + mAbs);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
