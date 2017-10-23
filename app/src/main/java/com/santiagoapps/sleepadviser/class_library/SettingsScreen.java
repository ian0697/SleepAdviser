package com.santiagoapps.sleepadviser.class_library;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.santiagoapps.sleepadviser.R;

/**
 * Created by Ian on 10/14/2017.
 */

public class SettingsScreen extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_screen);
        SwitchPreference switchPreference = (SwitchPreference)findPreference("switch_active");
        switchPreference.setEnabled(false);
    }
}
