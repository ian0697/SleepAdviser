package com.santiagoapps.sleepadviser.app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by IAN on 3/17/2018.
 */

public class AnimationUtil {

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown) {
        AnimatorSet aSet = new AnimatorSet();

        ObjectAnimator translateY = ObjectAnimator.ofFloat(holder.itemView, "translationY");
        translateY.setDuration(2000);

        aSet.playTogether(translateY);
        aSet.start();
    }


}
