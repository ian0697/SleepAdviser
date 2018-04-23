package com.santiagoapps.sleepadviser;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.santiagoapps.sleepadviser.helpers.DateHelper;

import static java.lang.Math.sqrt;

/**
 * SleepService
 *
 * This class handles the motion sensor
 * service applied to the Dormie app.
 *
 * @author Ian Santiago(iansantiago0697@gmail.com)
 * @since 1/19/2018
 */

public class SleepService extends Service implements SensorEventListener {
    private final static String TAG = "Dormie (" + SleepService.class.getSimpleName() + ") ";
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private Sensor sensor;
    private SensorManager sensorManager;
    private SensorEvent sensorEvent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Sleep service destroyed - " + DateHelper.getCurrentTime());

        //Unregister listener when service destroyed
        sensorManager.unregisterListener(this,sensor);
        Log.d(TAG, "SensorManager listener unregistered");
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {

        Log.d(TAG, "Sleep service started" + DateHelper.getCurrentTime());

        //Register listener
        sensorManager = (SensorManager)getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        mAccel = 0.0f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        return START_STICKY;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float myGravity[];
        myGravity = sensorEvent.values.clone();

        //Formula for Shaking
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) sqrt(myGravity[0] * myGravity[0] + myGravity[1] * myGravity[1]+ myGravity[2] * myGravity[2]);
        float delta = mAccelCurrent - mAccelLast;

        //Formula for Acceleration
        mAccel = mAccel * 0.9f + delta;

        //Absolute values
        float xAbs = Math.abs(myGravity[0]);
        float yAbs = Math.abs(myGravity[1]);
        float zAbs = Math.abs(myGravity[2]);
        float mAbs = Math.abs(mAccel);


        if (mAbs > 2   &&  (yAbs > 4 || xAbs >4) ) {
            Log.d(TAG, "You picked your phone - Accel: " + mAbs);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
