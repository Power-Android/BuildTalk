package com.bjjy.buildtalk.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.gyf.immersionbar.ImmersionBar;

/**
 * @author power
 * @date 2019/5/5 2:50 PM
 * @project BuildTalk
 * @description:
 */
public class StatusBarUtils {

    public static int getStatusBarHeight() {
        Resources resources = App.getContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * @param activity
     * @param isTransparent
     * @param isDarkFont
     */
    public static void changeStatusBar(Activity activity, boolean isTransparent, boolean isDarkFont){
        if (isTransparent) {
            if (isDarkFont){
                ImmersionBar.with(activity)
                        .fitsSystemWindows(false)
                        .transparentStatusBar()
                        .statusBarDarkFont(true, 0.2f)
                        .keyboardEnable(true)
                        .init();
            }else {
                ImmersionBar.with(activity)
                        .fitsSystemWindows(false)
                        .transparentStatusBar()
                        .statusBarDarkFont(false, 0.2f)
                        .keyboardEnable(true)
                        .init();
            }

        } else {
            if (isDarkFont){
                ImmersionBar.with(activity)
                        .fitsSystemWindows(true)
                        .statusBarColor(R.color.white)
                        .statusBarDarkFont(true, 0.2f)
                        .keyboardEnable(true)
                        .init();
            }else {
                ImmersionBar.with(activity)
                        .fitsSystemWindows(true)
                        .statusBarColor(R.color.white)
                        .statusBarDarkFont(false, 0.2f)
                        .keyboardEnable(true)
                        .init();
            }
        }
    }

    /**
     * @param fragment
     * @param isTransparent
     * @param isDarkFont
     */
    public static void changeStatusBar(Fragment fragment, boolean isTransparent, boolean isDarkFont) {
        if (isTransparent) {
            if (isDarkFont) {
                ImmersionBar.with(fragment)
                        .fitsSystemWindows(false)
                        .transparentStatusBar()
                        .statusBarDarkFont(true, 0.2f)
                        .keyboardEnable(true)
                        .init();
            } else {
                ImmersionBar.with(fragment)
                        .fitsSystemWindows(false)
                        .transparentStatusBar()
                        .statusBarDarkFont(false, 0.2f)
                        .keyboardEnable(true)
                        .init();
            }

        } else {
            if (isDarkFont) {
                ImmersionBar.with(fragment)
                        .fitsSystemWindows(true)
                        .statusBarColor(R.color.white)
                        .statusBarDarkFont(true, 0.2f)
                        .keyboardEnable(true)
                        .init();
            } else {
                ImmersionBar.with(fragment)
                        .fitsSystemWindows(true)
                        .statusBarColor(R.color.white)
                        .statusBarDarkFont(false, 0.2f)
                        .keyboardEnable(true)
                        .init();
            }
        }
    }

    public static void changeFontColor(Activity activity, boolean isDarkFont){
        if (isDarkFont){
            ImmersionBar.with(activity)
                    .statusBarDarkFont(true, 0.2f)
                    .init();
        }else {
            ImmersionBar.with(activity)
                    .statusBarDarkFont(false, 0.2f)
                    .init();
        }
    }
}
