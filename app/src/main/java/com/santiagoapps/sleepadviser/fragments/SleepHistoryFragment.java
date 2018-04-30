package com.santiagoapps.sleepadviser.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.data.model.Session;
import com.santiagoapps.sleepadviser.data.repo.SessionRepo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SleepHistoryFragment extends Fragment {

    private static final String TAG = "Dormie (" + SleepHistoryFragment.class.getSimpleName() + ")";
    private final static int WEEK = 1;
    private final static int MONTH = 2;
    private int toggle = 1;

    private View rootView;
    private Context context;
    private BarChart barChart;

    private Button btnMonth, btnWeek;

    public int COLOR_GREEN;
    public int COLOR_RED;

    int[] colors;

    public SleepHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sleep_history, container, false);
        context = getActivity().getBaseContext();

        COLOR_GREEN = getActivity().getResources().getColor(R.color.colorGreen);
        COLOR_RED = getActivity().getResources().getColor(R.color.colorRed);
        colors = new int[]{
                Color.CYAN,
                Color.CYAN,
                Color.CYAN,
                Color.CYAN,
                Color.CYAN,
                Color.CYAN,
                Color.CYAN
        };

        barChart = rootView.findViewById(R.id.barChart);
        btnMonth = rootView.findViewById(R.id.btnMonth);
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMonth.setBackgroundTintList(context.getResources().getColorStateList(R.color.app_color));
                btnWeek.setBackgroundTintList(null);

                btnWeek.setTextColor(getResources().getColor(R.color.app_color));
                btnMonth.setTextColor(getResources().getColor(R.color.colorWhite));
                toggle = MONTH;
            }
        });
        btnWeek = rootView.findViewById(R.id.btnWeek);
        btnWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnWeek.setBackgroundTintList(context.getResources().getColorStateList(R.color.app_color));
                btnMonth.setBackgroundTintList(null);

                btnWeek.setTextColor(R.color.colorWhite);
                btnMonth.setTextColor(getResources().getColor(R.color.app_color));

                toggle = WEEK;
            }
        });

        setData();
        return rootView;
    }

    public void setData(){
        barChart.setDrawBarShadow(false);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawValueAboveBar(true);

        SessionRepo sessionRepo = new SessionRepo();
        List<Session> sessionList = sessionRepo.getSessionOfCurrentWeek();

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0; i < 7; i++ ){
            for (Session session: sessionList) {
                if(session.getSleepDate().get(Calendar.DAY_OF_WEEK) == i+1){
                    barEntries.add(new BarEntry(i,(float)session.getDurationInDecimal()));

                    if(!session.isHealthy()){
                        colors[i] = COLOR_RED;
                        Log.d(TAG, session.getDurationInDecimal()+" MUST BE RED(" + i + ")");
                    } else{
                        colors[i] = COLOR_GREEN;
                    }
                } else {
                    barEntries.add(new BarEntry(i,0f));
                }
            }
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"Date Set 1");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);


        ArrayList<String> labels = new ArrayList<>();
        labels.add("Sun");
        labels.add("Mon");
        labels.add("Tues");
        labels.add("Wed");
        labels.add("Thurs");
        labels.add("Fri");
        labels.add("Sat");

        BarData data = new BarData(barDataSet);
        data.setValueTextColor(Color.WHITE);
        data.setBarWidth(0.6f);

        barChart.setData(data);
        barChart.getXAxis().setGranularity(1);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        barChart.getXAxis().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getAxisRight().setTextColor(Color.WHITE);
        barChart.getLegend().setTextColor(Color.WHITE);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.setFitBars(true);
        barChart.invalidate();
        barChart.animateY(2000);
    }
}
