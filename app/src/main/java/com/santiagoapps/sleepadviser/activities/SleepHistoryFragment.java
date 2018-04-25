package com.santiagoapps.sleepadviser.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

public class SleepHistoryFragment extends Fragment {

    private final static int WEEK = 1;
    private final static int MONTH = 2;
    private int toggle = 1;

    private View rootView;
    private Context context;
    private BarChart barChart;

    private Button btnMonth, btnWeek;


    public SleepHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sleep_history, container, false);
        context = getActivity().getBaseContext();
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

        List<Session> sessionList = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            Session session = new Session();
            session.setDurationInMills(28800 - (i*1000));
            sessionList.add(session);
        }

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, (float)sessionList.get(0).getDurationInDecimal()));
        barEntries.add(new BarEntry(1, (float)sessionList.get(1).getDurationInDecimal()));
        barEntries.add(new BarEntry(2, (float)sessionList.get(2).getDurationInDecimal()));
        barEntries.add(new BarEntry(3, (float)sessionList.get(3).getDurationInDecimal()));
        barEntries.add(new BarEntry(4, (float)sessionList.get(4).getDurationInDecimal()));
        barEntries.add(new BarEntry(5, (float)sessionList.get(5).getDurationInDecimal()));
        barEntries.add(new BarEntry(6, (float)sessionList.get(6).getDurationInDecimal()));

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
