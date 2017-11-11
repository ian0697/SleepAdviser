package com.santiagoapps.sleepadviser.nav_section;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.class_library.Music;
import com.santiagoapps.sleepadviser.class_library.MusicAdapter;

import java.util.ArrayList;


public class MusicSection extends Fragment {

    private View rootView;
    private Context context;
    private MediaPlayer mediaPlayer;

    private MusicAdapter musicAdapter;
    private ArrayList<Music> musicArrayList;
    private ListView songList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context= this.getContext();
        rootView = inflater.inflate(R.layout.fragment_page4, container, false);

        songList = (ListView) rootView.findViewById(R.id.songList);
        musicArrayList = new ArrayList<>();

        addMusic();

        musicAdapter = new MusicAdapter(context,R.layout.music_view, musicArrayList);
        songList.setAdapter(musicAdapter);

        return rootView;
    }

    private void addMusic(){
        musicArrayList.add(new Music("Piano Meditation", "Piano", R.raw.piano_music));
        musicArrayList.add(new Music("Piano Meditation 2", "Piano", R.raw.piano_music_2));
    }

}