package com.bjjy.buildtalk.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author power
 * @date 2019/4/25 9:29 AM
 * @project BuildTalk
 * @description: 动画工具类
 */
public class AnimatorUtils {

    public static void showByAlpha(final View view) {
        int shortAnimTime = 200;
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(1.0F).setDuration((long)shortAnimTime).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        }).start();
    }

    public static void hideByAlpha(final View view) {
        int shortAnimTime = 200;
        view.animate().alpha(0.0F).setDuration((long)shortAnimTime).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        }).start();
    }

    /**
     * 放大复原动画
     * @param scale 放大值，传入-1使用默认值
     */
    public static void scale(View view, float scale){
        if(scale == -1) scale = 1.3f;
        int shortTime = 300;
        PropertyValuesHolder phv1 = PropertyValuesHolder.ofFloat("scaleX", scale, 1f);
        PropertyValuesHolder phv2 = PropertyValuesHolder.ofFloat("scaleY", scale, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, phv1, phv2);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(shortTime);
        animator.start();
    }
}
