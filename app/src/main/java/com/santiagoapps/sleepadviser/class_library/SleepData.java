package com.santiagoapps.sleepadviser.class_library;

/**
 * Created by Ian on 11/20/2017.
 */

public class SleepData {



    public SleepData(String sleep_time, String wake_time, String sleep_quality){
        this.sleep_time = sleep_time;
        this.wake_time = wake_time;
        this.sleep_quality = sleep_quality;

    }

    public SleepData(String sleep_time){
        this.sleep_time = sleep_time;
        wake_time = "";
        sleep_quality = "";

    }

    public String getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(String sleep_time) {
        this.sleep_time = sleep_time;
    }

    public String getWake_time() {
        return wake_time;
    }

    public void setWake_time(String wake_time) {
        this.wake_time = wake_time;
    }

    public String getSleep_quality() {
        return sleep_quality;
    }

    public void setSleep_quality(String sleep_quality) {
        this.sleep_quality = sleep_quality;
    }

    private String sleep_time;
    private String wake_time;
    private String sleep_quality;

}
