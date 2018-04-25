package com.santiagoapps.sleepadviser.fragments.nav;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.data.model.Music;
import com.santiagoapps.sleepadviser.adapter.MusicAdapter;

import java.util.ArrayList;


public class MusicSection extends Fragment {

    private View rootView;
    private Context context;
    private MusicAdapter musicAdapter;
    private ArrayList<Music> musicArrayList;
    private ListView songList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context= this.getContext();
        rootView = inflater.inflate(R.layout.fragment_playlist, container, false);

        songList = rootView.findViewById(R.id.songList);
        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        addMusic();

        return rootView;
    }

    private void addMusic(){
        musicArrayList = new ArrayList<>();
        musicArrayList.add(new Music("Piano Meditation", "Piano Music", R.raw.piano_music));
        musicArrayList.add(new Music("Piano Meditation 2", "Piano Music", R.raw.piano_music_2));
        musicArrayList.add(new Music("Clair de Lune", "Classical piano_image ic_music", R.raw.clairdelune));
        musicArrayList.add(new Music("Wisps of Whorls", "Meditation/Sleep relief ic_music", R.raw.wispofwhorls));
        musicArrayList.add(new Music("Sleep Music", "Meditation/Sleep relief ic_music", R.raw.sleepmusic));

        musicAdapter = new MusicAdapter(context, R.layout.music_view, musicArrayList);
        songList.setAdapter(musicAdapter);
    }



}