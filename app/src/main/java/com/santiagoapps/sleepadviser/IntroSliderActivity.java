package com.santiagoapps.sleepadviser;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiagoapps.sleepadviser.adapter.SliderAdapter;

public class IntroSliderActivity extends AppCompatActivity {

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

        viewPager = findViewById(R.id.slider);
        mDotLayout = (LinearLayout)findViewById(R.id.dotLayout);

        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        previousBtn = (Button)findViewById(R.id.previousBtn);
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

    protected void addDotsIndicator(int position){
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
