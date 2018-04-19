package com.santiagoapps.sleepadviser.app;

public interface IOnStartup {
    void setFirstTimeStatus(boolean flag);
    boolean isFirstStart();
}
