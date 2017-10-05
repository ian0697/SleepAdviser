package com.santiagoapps.sleepadviser.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.santiagoapps.sleepadviser.R;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.Math.sqrt;

/**
 * Created by Ian on 10/2/2017.
 */

public class Page2Fragment extends Fragment implements SensorEventListener{

    View rootView;

    private Sensor sensor;
    private SensorManager sensorManager;
    private SensorEvent sensorEvent;
    private TextView xText,yText,zText,mText;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_page2, container, false);
        init();

        return rootView;
    }

    private void init(){
        xText = (TextView)rootView.findViewById(R.id.xText);
        yText = (TextView)rootView.findViewById(R.id.yText);
        zText = (TextView)rootView.findViewById(R.id.zText);
        mText = (TextView)rootView.findViewById(R.id.mText);

        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
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
            Toast.makeText(this.getContext(), "Oops you picked the phone", LENGTH_SHORT).show();
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
