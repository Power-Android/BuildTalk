package com.bjjy.buildtalk.ui.main;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.ui.mine.EditBindPhoneActivity;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
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

    @OnClick({R.id.phone_tv, R.id.wechat_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_tv:
                startActivityForResult(new Intent(this, PhoneLoginActivity.class),101);
                break;
            case R.id.wechat_tv:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, mUMAuthListener);
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
            mPresenter.checkIsBind(unionid,openid,name,iconurl);
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
        if (resultCode == RESULT_OK){
            if (requestCode == 101){
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
