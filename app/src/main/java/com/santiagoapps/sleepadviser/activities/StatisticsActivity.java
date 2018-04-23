package com.santiagoapps.sleepadviser.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.adapter.SessionAdapter;
import com.santiagoapps.sleepadviser.data.model.Session;
import com.santiagoapps.sleepadviser.data.repo.SessionRepo;
import com.santiagoapps.sleepadviser.helpers.DBHelper;
import com.santiagoapps.sleepadviser.helpers.DateHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class StatisticsActivity extends AppCompatActivity {

    private Toolbar actionBar;
    private String actions[] = {
            "January",
            "February",
            "March",
            "April" ,
            "May",
            "June",
            "July" ,
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    private ArrayAdapter<String> adapter;


    private LinearLayout emptyData;
    private ArrayList<Session> sessionArrayList;
    private ListView listSession;
    private SessionRepo db;
    private SessionAdapter sessionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_statistics);
        setUpActionBar();

        db = new SessionRepo();
        listSession = findViewById(R.id.listSession);
        emptyData = findViewById(R.id.emptyData);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setUpList(DateHelper.getCurrentMonth());
    }

    private void setUpList(int month){
        sessionArrayList = new ArrayList<>();
        sessionArrayList = (ArrayList<Session>) db.getSessionByMonth(month);

//        StringBuilder sb = new StringBuilder();
//        sb.append("Before sorting: \n");
//        for (Session session: sessionArrayList) {
//            sorted.add(session.getSleepDate());
//            sb.append(session.getSleepDate().toString() + "\n");
//        }
//
//        Collections.sort(sorted);
//
//        sb.append("AFTER SORTING \n");
//        for (Calendar s : sorted) {
//            sb.append(s.toString() + "\n");
//        }
//
//        Log.d("Sorting", sb.toString());



        sessionAdapter = new SessionAdapter(this, R.layout.layout_data, sessionArrayList);

        if(sessionArrayList.size()!=0){
            emptyData.setVisibility(LinearLayout.GONE);
            listSession.setVisibility(LinearLayout.VISIBLE);
            listSession.setAdapter(sessionAdapter);
        } else {
            emptyData.setVisibility(LinearLayout.VISIBLE);
            listSession.setVisibility(LinearLayout.GONE);
        }

    }

    private void setUpActionBar(){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, actions);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        actionBar = findViewById(R.id.toolbar2);
        setSupportActionBar(actionBar);

        ActionBar actionBar1 = getSupportActionBar();
        actionBar1.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar1.setTitle(" ");
        actionBar1.setDisplayShowTitleEnabled(false);
        actionBar1.setHomeButtonEnabled(false);

        ActionBar.OnNavigationListener listener = new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                setUpList(itemPosition);
                return false;
            }
        };

        actionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        actionBar1.setListNavigationCallbacks(adapter, listener);
        actionBar1.setSelectedNavigationItem(DateHelper.getCurrentMonth());

    }


}
