package com.santiagoapps.sleepadviser.class_library;

/**
 * Created by Ian on 10/23/2017.
 */

public class Music {

    private String music_name;
    private String category;
    private int song;

    public Music(String music_name, String category, int song) {
        this.music_name = music_name;
        this.category = category;
        this.song = song;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }
}
