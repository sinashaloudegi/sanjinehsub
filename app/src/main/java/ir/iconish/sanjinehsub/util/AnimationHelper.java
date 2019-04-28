package ir.iconish.sanjinehsub.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class AnimationHelper {

    public static  void fadInFadeout(View view){

        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(500);
        anim.setRepeatCount(0);
        anim.setRepeatMode(Animation.REVERSE);
        view.startAnimation(anim);
    }
}
