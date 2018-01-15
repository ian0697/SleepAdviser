package com.santiagoapps.sleepadviser.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.santiagoapps.sleepadviser.R;

/**
 * Created by Ian on 10/2/2017.
 */

public class TestFragment extends Fragment{

    private ImageButton btnSleep;
    private View rootView;
    private int mode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_test, container, false);
        init();
        mode = 1;
        return rootView;

    }

    private void init(){
        btnSleep = (ImageButton)rootView.findViewById(R.id.btnSleep);
        btnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mode){
                    case 1:
                        btnSleep.setImageResource(R.drawable.wake_btn);
                        mode=2;
                        break;
                    case 2:
                        btnSleep.setImageResource(R.drawable.sleep_btn2);
                        mode=1;
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
}
