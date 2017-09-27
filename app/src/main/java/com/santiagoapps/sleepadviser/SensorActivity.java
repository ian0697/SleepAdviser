package com.santiagoapps.sleepadviser;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener{

    private Sensor sensor;
    private SensorManager sensorManager;
    private SensorEvent sensorEvent;
    private TextView xText,yText,zText;

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

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor,sensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float myGravity[];
        myGravity = sensorEvent.values.clone();

        xText.setText("X: " + myGravity[0]);
        yText.setText("Y: " + myGravity[1]);
        zText.setText("Z: " + myGravity[2]);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
