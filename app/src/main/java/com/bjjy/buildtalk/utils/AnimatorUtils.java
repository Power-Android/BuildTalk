package com.bjjy.buildtalk.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

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

    /**
     * @param imageView
     * 换一换旋转动画
     */
    public static void setRotateAnimation(ImageView imageView) {
        RotateAnimation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(1000);//设置动画持续周期
        rotate.setRepeatCount(1);//设置重复次数
        rotate.setFillAfter(false);//动画执行完后是否停留在执行完的状态
        imageView.startAnimation(rotate);
    }
}
