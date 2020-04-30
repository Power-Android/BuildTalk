package com.bjjy.buildtalk.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.ui.circle.CircleAgreementActvity;
import com.bjjy.buildtalk.ui.mine.EditBindPhoneActivity;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.phone_tv)
    TextView mPhoneTv;
    @BindView(R.id.wechat_tv)
    TextView mWechatTv;
    @BindView(R.id.yhxy_tv)
    TextView mYhxyTv;
    @BindView(R.id.yszc_tv)
    TextView mYszcTv;
    private Intent mIntent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeStatusBar(this, true, false);
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.phone_tv, R.id.wechat_tv,R.id.yhxy_tv, R.id.yszc_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_tv:
                startActivityForResult(new Intent(this, PhoneLoginActivity.class), 101);
                break;
            case R.id.wechat_tv:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, mUMAuthListener);
                break;
            case R.id.yhxy_tv:
                mIntent = new Intent(this, CircleAgreementActvity.class);
                mIntent.putExtra("type","yhxy");
                mIntent.putExtra("url", "https://www.51jiantan.com/yh");
                startActivity(mIntent);
                break;
            case R.id.yszc_tv:
                mIntent = new Intent(this, CircleAgreementActvity.class);
                mIntent.putExtra("type","yszc");
                mIntent.putExtra("url", "https://www.51jiantan.com/ys");
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void handlerBindPhone(String type, String unionid, String openid, String iconurl, String name) {
        Intent intent = new Intent(this, EditBindPhoneActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("unionid", unionid);
        intent.putExtra("openid", openid);
        intent.putExtra("iconurl", iconurl);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();
    }

    @Override
    public void handlerSuccess(User user) {
        if (LoginHelper.callBack != null) {
            LoginHelper.callBack.onLogin();
        }
        finish();
    }

    private UMAuthListener mUMAuthListener = new UMAuthListener() {
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
            mPresenter.mDataManager.getUser().setBindStatus("1");
            mPresenter.checkIsBind(unionid, openid, name, iconurl);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                finish();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
