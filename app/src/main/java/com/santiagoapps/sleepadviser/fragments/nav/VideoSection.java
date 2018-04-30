package com.santiagoapps.sleepadviser.fragments.nav;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.adapter.MusicAdapter;
import com.santiagoapps.sleepadviser.adapter.VideoAdapter;
import com.santiagoapps.sleepadviser.data.model.VideoSrc;

import java.util.ArrayList;


public class VideoSection extends Fragment {

    private View rootview;
    private Context context;
    private ArrayList<VideoSrc> videoList;
    private VideoAdapter videoAdapter;
    private ListView videoListView;

    public VideoSection() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_video_section, container, false);
        context = this.getContext();
        videoListView = rootview.findViewById(R.id.listVideos);

        videoList = new ArrayList<>();
        for(int x = 0 ; x < 6; x++){
            videoList.add(new VideoSrc(VideoSrc.titles[x], VideoSrc.descriptions[x], VideoSrc.paths[x]));
        }

        videoAdapter = new VideoAdapter(context, R.layout.layout_video, videoList);
        videoListView.setAdapter(videoAdapter);
        videoListView.setDivider(null);
        videoListView.setDividerHeight(0);
        return rootview;
    }

}
