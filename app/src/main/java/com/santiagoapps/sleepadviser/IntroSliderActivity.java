package com.santiagoapps.sleepadviser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiagoapps.sleepadviser.activities.NavigationActivity;
import com.santiagoapps.sleepadviser.activities.RegisterActivity;
import com.santiagoapps.sleepadviser.adapter.SliderAdapter;

public class IntroSliderActivity extends AppCompatActivity implements IOnStartup{

    private ViewPager viewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;

    private TextView[] mDots;
    private Button previousBtn;
    private Button nextBtn;
    private int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);


        // if app already started previously
        if(!isFirstStart()){
            startNextActivity();
            finish();
        }

        viewPager = findViewById(R.id.slider);
        mDotLayout = findViewById(R.id.dotLayout);


        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage+1 == sliderAdapter.getCount()){
                    startNextActivity();
                }

                viewPager.setCurrentItem(mCurrentPage + 1);

            }
        });

        previousBtn = findViewById(R.id.previousBtn);
        previousBtn.setVisibility(View.INVISIBLE);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDotsIndicator(mCurrentPage);
        viewPager.addOnPageChangeListener(viewListener);


    }

    private void addDotsIndicator(int position){
        mDots = new TextView[sliderAdapter.getCount()];
        mDotLayout.removeAllViews();

        for (int i=0; i< mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(24);
            mDots[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    public boolean isFirstStart(){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("Dormie", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartFlag", true);
    }

    public void setFirstTimeStatus(boolean flag){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("Dormie", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTimeStartFlag", flag);
        editor.commit();
    }

    private void startNextActivity(){
        setFirstTimeStatus(false);
        startActivity(new Intent(IntroSliderActivity.this, ProfilingActivity_name.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;

            if(position == 0){
                previousBtn.setVisibility(View.INVISIBLE);
                previousBtn.setEnabled(false);
            } else {
                previousBtn.setVisibility(View.VISIBLE);
                previousBtn.setEnabled(true);
            }

            if(position == mDots.length - 1){
                nextBtn.setText("FINISH");
            } else {
                nextBtn.setText("NEXT");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
