package com.bjjy.buildtalk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.DataManager;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.ui.main.LoginActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * @author power
 * @date 2019/5/30 6:46 PM
 * @project BuildTalk
 * @description:
 */
public class LoginHelper {
    public static CallBack callBack;

    public static void login(Context context, DataManager dataManager, CallBack loginCallBack) {
        if (!dataManager.getLoginStatus()) {
            callBack = loginCallBack;
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else {
            if (loginCallBack != null) {
                loginCallBack.onLogin();
            }
        }
    }

    public static void loginOut(Activity activity, DataManager dataManager) {
        dataManager.setLoginStatus(false);
        dataManager.loginOut();
        EventBus.getDefault().post(new RefreshEvent(Constants.INFO_REFRESH));
        activity.finish();
    }

    public interface CallBack {
        void onLogin();
    }
}
