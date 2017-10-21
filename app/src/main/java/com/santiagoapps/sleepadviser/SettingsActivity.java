package com.santiagoapps.sleepadviser;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Fragment fragment = new SettingsScreen();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if(savedInstanceState == null){
            //first run
            fragmentTransaction.add(R.id.settings_layout, fragment, "settings_layout");
            fragmentTransaction.commit();
        }
        else {
            fragment = getFragmentManager().findFragmentByTag("settings_layout");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public static class SettingsScreen extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_screen);
            SwitchPreference switchPreference = (SwitchPreference)findPreference("switch_active");
            switchPreference.setEnabled(false);
        }
    }
}
