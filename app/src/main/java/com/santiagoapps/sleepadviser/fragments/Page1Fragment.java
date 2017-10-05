package com.santiagoapps.sleepadviser.fragments;

/**
 * Created by Ian on 10/2/2017.
 */


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santiagoapps.sleepadviser.R;


public class Page1Fragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_page1, container, false);
        return rootView;

    }

}
