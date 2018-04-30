package com.santiagoapps.sleepadviser.activities;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiagoapps.sleepadviser.R;

public class MusicActivity extends Fragment {

    View rootView;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_music, container, false);
        context = getActivity();

        return rootView;
    }


}
