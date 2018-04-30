package com.santiagoapps.sleepadviser.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.data.model.VideoSrc;

import java.util.ArrayList;


//  1. sleep_tips
//  2. tips_sleep
//  3. sleep_teens
//  4. tricks_sleep
//  5. perfect_nap
//  6. sleep_debt
//  7. effect_of_stress


public class SleepVideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView tvTitle;
    private TextView tvSubtitle;
    private TextView tvContents;
    private Button btnPlay;
    private MediaController mediaController;
    private int pos;
    private int[] path;
    private int[] subtitles;
    private int[] contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_video);
        initData();

        Bundle extras = getIntent().getExtras();
        pos = (int) extras.get("position");

        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(VideoSrc.titles[pos]);

        tvSubtitle = findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(subtitles[pos]);

        tvContents = findViewById(R.id.tvContent);
        tvContents.setText(contents[pos]);

        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(this);

        String path_ = "android.resource://com.santiagoapps.sleepadviser/" + path[pos];
        Uri uri = Uri.parse(path_);
        videoView.setVideoURI(uri);


        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlay_onClick();
            }
        });
    }


    private void initData(){

        path = new int[]{
                R.raw.tips_sleep,
                R.raw.sleep_tips,
                R.raw.sleep_teenagers,
                R.raw.tricks_sleep,
                R.raw.perfect_nap,
                R.raw.sleep_debt,
                R.raw.effect_of_stress
        };
        subtitles = new int[]{
                R.string.subtitle,
                R.string.subtitle_2,
                R.string.subtitle_3,
                R.string.subtitle_4,
                R.string.subtitle_5,
                R.string.subtitle_6
        };
        contents = new int[]{
                R.string.list_1,
                R.string.list_2,
                R.string.list_3,
                R.string.list_4,
                R.string.list_5,
                R.string.list_6
        };

    }

    private void btnPlay_onClick(){
        if(videoView.isPlaying()){
            videoView.stopPlayback();
            btnPlay.setText("Play");
        }
        else{
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.start();
            btnPlay.setText("Stop");
        }
    }
}
