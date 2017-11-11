package com.santiagoapps.sleepadviser.main_activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.santiagoapps.sleepadviser.SleepingActivity;
import com.santiagoapps.sleepadviser.class_library.DatabaseHelper;
import com.santiagoapps.sleepadviser.nav_section.DashboardSection;
import com.santiagoapps.sleepadviser.nav_section.MusicSection;
import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.nav_section.StatisticSection;

public class NavigationMain extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawer;
    DatabaseHelper myDb;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);

        setNavigation();


        //Initialization
        setFragment(new DashboardSection());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavigationMain.this, SleepingActivity.class));
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        myDb = new DatabaseHelper(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(this,SettingsActivity.class);
            startActivity(i);

            return true;
        }
        if (id == R.id.log_out){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setNavigation(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.first_nav_item){
                    setFragment(new DashboardSection());
                    toolbar.setTitle("Dashboard");
                } else if (id == R.id.second_nav_item){
                    setFragment(new StatisticSection());
                    toolbar.setTitle("Statistics");
                } else if (id == R.id.third_nav_item){
                    setFragment(new MusicSection());
                    toolbar.setTitle("Sleep Music Library");
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    public void setFragment(Fragment fragment){
        if(fragment!=null){
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }
        drawer.closeDrawer(GravityCompat.START);
    }
}
