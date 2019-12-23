package com.bjjy.buildtalk.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.weight.player.PlayerWindowManager;

/**
 * @author power
 * @date 2019-12-13 12:03
 * @project BuildTalk
 * @description:
 */
public class StatusBarReceiver extends BroadcastReceiver {
    public static final String ACTION_STATUS_BAR = "com.bjjy.buildtalk.STATUS_BAR_ACTIONS";
    public static final String EXTRA = "extra";
    public static final String EXTRA_NEXT = "next";
    public static final String EXTRA_PLAY_PAUSE = "play_pause";
    public static final String EXTRA_LAST = "last";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.e("StatusBarReceiver"+intent.getStringExtra(EXTRA));
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {
            return;
        }
        String extra = intent.getStringExtra(EXTRA);
        if (TextUtils.equals(extra, EXTRA_NEXT)) {
            PlayerWindowManager.getInstance().receiverNextClick();
        } else if (TextUtils.equals(extra, EXTRA_PLAY_PAUSE)) {
            PlayerWindowManager.getInstance().receiverPlayClick();
        }else if (TextUtils.equals(extra, EXTRA_LAST)){
            PlayerWindowManager.getInstance().receiverLastClick();
        }
    }
}
