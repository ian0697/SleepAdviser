package com.santiagoapps.sleepadviser;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity implements SensorEventListener{

    private Sensor sensor;
    private SensorManager sensorManager;
    private SensorEvent sensorEvent;
    private TextView xText,yText,zText,mText;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        init();
    }

    private void init(){
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        mText = (TextView)findViewById(R.id.mText);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
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

        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt(myGravity[0] * myGravity[0] + myGravity[1] * myGravity[1]+ myGravity[2] * myGravity[2]);
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta;
        mText.setText("mAccel: " + mAccel);

        if (mAccel > 1.5) {
            Toast.makeText(this, "Oops you picked the phone", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
