package com.bjjy.buildtalk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.DataManager;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.ui.main.LoginActivity;
import com.bjjy.buildtalk.ui.main.PhoneLoginActivity;
import com.bjjy.buildtalk.ui.mine.EditBindPhoneActivity;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.mobile.auth.gatewayauth.AuthRegisterViewConfig;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author power
 * @date 2019/5/30 6:46 PM
 * @project BuildTalk
 * @description:
 */
public class LoginHelper{
    public static CallBack callBack;
    private static Context mContext;
    private static LoginHelper instance = null;
    private TokenResultListener mTokenListener;
    private String token;
    private static PhoneNumberAuthHelper mAlicomAuthHelper;
    private boolean checkRet;
    private static BaseDialog mLoadingDialog;
    private static DataManager mDataManager;
    private static TextView switchTV;

    public static LoginHelper getInstance(){
        if (instance == null) {
            synchronized (LoginHelper.class) {
                if (instance == null) {
                    instance = new LoginHelper();
                }
            }
        }
        return instance;
    }

    public LoginHelper() {
        initToken();
    }

    public void initToken() {
        //1.初始化获取token实例
        mTokenListener = new TokenResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                Log.e("NumberAuth", "onTokenSuccess:" + s);
                mLoadingDialog.dismiss();
                TokenRet tokenRet = null;
                try {
                    tokenRet = JSON.parseObject(s, TokenRet.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //唤起授权页失败
                if (tokenRet != null && !("600001").equals(tokenRet.getCode())) {
                    mAlicomAuthHelper.quitLoginPage();
                }
                //点击登录按钮事件，获取token成功
                if (tokenRet != null && TextUtils.equals("600000", tokenRet.getCode())){
                    token = tokenRet.getToken();
                    requestMobile(token);
                }
            }

            @Override
            public void onTokenFailed(String s) {
                mLoadingDialog.dismiss();
                Log.e("NumberAuth", "onTokenFailed:" + s);
                TokenRet tokenRet = null;
                try {
                    tokenRet = JSON.parseObject(s, TokenRet.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(TextUtils.equals("700001", tokenRet.getCode())){//用户切换其他登录方式
                    Intent intent = new Intent(mContext, PhoneLoginActivity.class);
                    mContext.startActivity(intent);
                } else if (!TextUtils.equals("700000",tokenRet.getCode())){//用户取消登录
                    //验证失败后跳转原始登录界面
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }
            }
        };

        //2.初始化SDK实例
        mAlicomAuthHelper = PhoneNumberAuthHelper.getInstance(App.getContext(), mTokenListener);

        //3.设置SDK秘钥
        mAlicomAuthHelper.setAuthSDKInfo("sImdAJN6whIg8d39L4m11rCyQZdJECpds1EM3WTvVpQq7n/A4/C9t0FdMGlUtprLyvAXl6Au1Imp0jc46Carcc5uG+jk5f4RoF8iHSVXJI3e3pmRCcwKERHIuE57/wXKNtfIBJ7uTYXhYKxTzNNjdnOikKKeELvLC0KKz78V14+Z+FLdFs+REipHm9ZKCjoSLNMQ2exrBZQIxg4wGHhkiBQd/YcBDGryZhSB07ElnyW6LiwkwqqFbv2I44WQRDPmU8cGpqa8qYOX+D4ekG2+tzz0L2aE3dfF");

        //4.检测终端网络环境是否支持一键登录或者号码认证
//        checkRet = mAlicomAuthHelper.checkEnvAvailable();
//        if (!checkRet) {
//            ToastUtils.showShort("当前网络不支持，请检测蜂窝网络后重试");
//        }else {
//
//        }
        mAlicomAuthHelper.setAuthListener(mTokenListener);
    }

    public static void login(Context context, DataManager dataManager, CallBack loginCallBack) {
        if (!dataManager.getLoginStatus()) {
            mContext = context;
            callBack = loginCallBack;
            mDataManager = dataManager;
            initLoadingDialog();
            //5.若步骤4支持，则根据业务情况，调用预取号或者一键登录接口
            mLoadingDialog.show();
            configLoginToken();
            mAlicomAuthHelper.getLoginToken(context, 5000);
        } else {
            if (loginCallBack != null) {
                loginCallBack.onLogin();
            }
        }
    }

    private static void configLoginToken() {
        Resources resources = App.getContext().getResources();
        int authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        if (Build.VERSION.SDK_INT == 26) {
            authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_BEHIND;
        }

        TextView wechatTv = new TextView(mContext);
        RelativeLayout.LayoutParams mLayoutParams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                (int) resources.getDimension(R.dimen.dp_44));
        mLayoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mLayoutParams2.setMargins((int) resources.getDimension(R.dimen.dp_40), (int) resources.getDimension(R.dimen.dp_275), (int) resources.getDimension(R.dimen.dp_40), 0);
        wechatTv.setText("微信授权登录");
        wechatTv.setTextColor(Color.WHITE);
        wechatTv.setTextSize(SizeUtils.px2dp(resources.getDimension(R.dimen.sp_15)));
        wechatTv.setGravity(Gravity.CENTER);
        wechatTv.setBackgroundResource(R.drawable.shape_green_25button);
        wechatTv.setLayoutParams(mLayoutParams2);

        mAlicomAuthHelper.removeAuthRegisterXmlConfig();
        mAlicomAuthHelper.removeAuthRegisterViewConfig();
        mAlicomAuthHelper.addAuthRegistViewConfig("wechat_tv", new AuthRegisterViewConfig.Builder()
                .setView(wechatTv)
                .setRootViewId(AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY)
                .setCustomInterface(context -> {
                    UMShareAPI.get(mContext).getPlatformInfo((Activity) mContext, SHARE_MEDIA.WEIXIN, mUMAuthListener);
                    mAlicomAuthHelper.quitLoginPage();
                }).build());

        mAlicomAuthHelper.setAuthUIConfig(
                new AuthUIConfig.Builder()
                        .setStatusBarColor(Color.TRANSPARENT)
                        .setStatusBarUIFlag(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                        .setLightColor(false)
                        .setNavHidden(true)
                        .setPageBackgroundPath("login_bg")
                        .setNumberColor(Color.WHITE)
                        .setNumberSize(SizeUtils.px2dp(resources.getDimension(R.dimen.sp_32)))
                        .setNumFieldOffsetY(SizeUtils.px2dp(resources.getDimension(R.dimen.dp_100)))
                        .setSloganTextSize(SizeUtils.px2dp(resources.getDimension(R.dimen.sp_11)))
                        .setSloganTextColor(resources.getColor(R.color.border5))
                        .setSloganOffsetY(SizeUtils.px2dp(resources.getDimension(R.dimen.dp_150)))
                        .setLogBtnBackgroundPath("login_btn_bg")
                        .setLogBtnMarginLeftAndRight(SizeUtils.px2dp(resources.getDimension(R.dimen.dp_40)))
                        .setLogBtnText("本机号码一键登录")
                        .setLogBtnBackgroundPath("shape_white_25radius")
                        .setLogBtnTextColor(resources.getColor(R.color.blue2))
                        .setLogBtnTextSize(SizeUtils.px2dp(resources.getDimension(R.dimen.sp_15)))
                        .setLogBtnOffsetY(SizeUtils.px2dp(resources.getDimension(R.dimen.dp_220)))
                        .setSwitchAccText("其他手机号码登录")
                        .setSwitchAccTextSize(SizeUtils.px2dp(resources.getDimension(R.dimen.sp_15)))
                        .setSwitchAccTextColor(Color.WHITE)
                        .setSwitchOffsetY(SizeUtils.px2dp(resources.getDimension(R.dimen.dp_360)))
                        .setAppPrivacyOne("用户协议", "https://www.51jiantan.com/yh")
                        .setAppPrivacyTwo("隐私政策", "https://www.51jiantan.com/ys")
                        .setCheckboxHidden(true)
                        .setAppPrivacyColor(Color.WHITE, resources.getColor(R.color.orange))
                        .setPrivacyOffsetY(SizeUtils.px2dp(resources.getDimension(R.dimen.dp_422)))
                        .setPrivacyBefore("登录即同意")
                        .setPrivacyTextSize(SizeUtils.px2dp(resources.getDimension(R.dimen.sp_11)))
                        .setScreenOrientation(authPageOrientation)
                        .create());
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

    private static void initLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new BaseDialog.Builder(mContext)
                    .setViewId(R.layout.loading_view)
                    .setGravity(Gravity.CENTER)
                    .setStyle(R.style.dialog_style1)
                    .setAnimation(R.style.nomal_aniamtion)
                    .setWidthHeightdp(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .isOnTouchCanceled(false)
                    .builder();
        }
    }

    private void requestMobile(String token) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("AccessToken",token);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(mDataManager.getMobile(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .subscribeWith(new BaseObserver<User>() {
                    @Override
                    public void onSuccess(User user) {//微信登录已经绑定手机号
                        user.setLoginStatus(true);
                        mDataManager.addUser(user);
                        EventBus.getDefault().post(new RefreshEvent(Constants.INFO_REFRESH));
                        mAlicomAuthHelper.quitLoginPage();
                    }
                }));
    }

    private static UMAuthListener mUMAuthListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            LogUtils.e(data.toString());
            String unionid = data.get("unionid");
            String openid = data.get("openid");
            String name = data.get("name");
            String iconurl = data.get("iconurl");
            mDataManager.getUser().setBindStatus("1");
            checkIsBind(unionid, openid, name, iconurl);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param throwable 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable throwable) {
            LogUtils.e(throwable.getMessage());
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtils.showShort("已取消授权");
        }
    };

    private static void checkIsBind(String unionid, String openid, String name, String iconurl) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("unionId",unionid);
        paramas.put("openId",openid);
        paramas.put("headImage",iconurl);
        paramas.put("nickName",name);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(mDataManager.checkisBind(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .subscribeWith(new BaseObserver<User>() {
                    @Override
                    public void onSuccess(User user) {//微信登录已经绑定手机号
                        user.setLoginStatus(true);
                        mDataManager.addUser(user);
                        EventBus.getDefault().post(new RefreshEvent(Constants.INFO_REFRESH));
                    }

                    @Override
                    public void onNext(BaseResponse<User> baseResponse) {
                        super.onNext(baseResponse);
                        if (baseResponse.getErrorCode() == 0){//微信登录没有绑定手机号
                            Intent intent = new Intent(mContext, EditBindPhoneActivity.class);
                            intent.putExtra("type", "1");
                            intent.putExtra("unionid", unionid);
                            intent.putExtra("openid", openid);
                            intent.putExtra("iconurl", iconurl);
                            intent.putExtra("name", name);
                            mContext.startActivity(intent);
                        }
                    }
                }));
    }
}
