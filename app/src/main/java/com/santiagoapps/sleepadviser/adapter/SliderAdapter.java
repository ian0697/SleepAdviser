package com.santiagoapps.sleepadviser.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.santiagoapps.sleepadviser.R;

/**
 * Created by IAN on 3/29/2018.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    //Arrays
    private int[] slide_images = {
        R.drawable.logo_dormie_happy,
        R.drawable.sleeping1,
        R.drawable.sleeping_01
    };

    private String[] slide_headings = {
            "HI! I'M DORMIE!",
            "SLEEP",
            "MONITOR"
    };

    private String[] slide_desc = {
            "Have problem sleeping at night? Don't worry, I will be your personal sleep assistant!",
            "Dormie will help you sleep better at night.",
            "Dormie will monitor all your sleeping patterns and give you advice about it."
    };

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);

        //instantiate the objects from the layout xml
        ImageView sImageView = (ImageView) view.findViewById(R.id.imageSlider);
        TextView headerText = (TextView) view.findViewById(R.id.slider_header);
        TextView sliderText = (TextView) view.findViewById(R.id.slider_desc);

        //set the values of the objects
        sImageView.setImageResource(slide_images[position]);
        headerText.setText(slide_headings[position]);
        sliderText.setText(slide_desc[position]);

        //put the view to the container
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
