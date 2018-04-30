package com.santiagoapps.sleepadviser.data.model;

import com.santiagoapps.sleepadviser.R;

public class VideoSrc {

    private int path;
    private String title;
    private String description;

    public VideoSrc(String title, String description, int path){
        this.title = title;
        this.description = description;
        this.path = path;
    }

    public static String[] titles = {
            "Sleeping tips",
            "10 tips for falling asleep",
            "Sleep deprivation of teenagers",
            "Scientific tricks for falling asleep",
            "Naps essentials",
            "Sleep debt",
            "Effects of Stress"
    };
    public static String[] descriptions = {
            "HuffingtonPost | Els van der Helm",
            "Buzfeed | Buzzfeed community",
            "Els van der Helm | Sleep expert",
            "Tech Insider | American academy of Sleep medicine",
            "Els van der Helm | Sleep expert",
            "Els van der Helm | Sleep expert",
            "Els van der Helm | Sleep expert"
    };
    public static int[] paths = {
            R.drawable.tips_sleep,
            R.drawable.sleep_tips,
            R.drawable.sleep_teens,
            R.drawable.sleep_tricks,
            R.drawable.perfect_nap,
            R.drawable.sleep_debt,
            R.drawable.sleep_tricks
    };


    public int getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
