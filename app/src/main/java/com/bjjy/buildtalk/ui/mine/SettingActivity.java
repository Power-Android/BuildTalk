package com.bjjy.buildtalk.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.bind_wechat)
    RelativeLayout mBindWechat;
    @BindView(R.id.about_us)
    RelativeLayout mAboutUs;
    @BindView(R.id.version_info)
    RelativeLayout mVersionInfo;
    @BindView(R.id.quit_tv)
    TextView mQuitTv;
    @BindView(R.id.version_tv)
    TextView mVersionTv;
    private BaseDialog mDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText(R.string.setting);
    }

    @Override
    protected void initEventAndData() {
        if (!TextUtils.isEmpty(mPresenter.mDataManager.getUser().getBindStatus()) && mPresenter.mDataManager.getUser().getBindStatus().equals("1")) {
            mNameTv.setText("已绑定");
        } else {
            mNameTv.setText("未绑定");
        }
        mVersionTv.setText("V" + getAppVersionName(this));
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName=null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }


    @OnClick({R.id.bind_wechat, R.id.about_us, R.id.version_info, R.id.quit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bind_wechat:
                if (mPresenter.mDataManager.getUser().getBindStatus().equals("1")) {
                    UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, mUMAuthListener);
                }
                break;
            case R.id.about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.version_info:
                break;
            case R.id.quit_tv:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        mDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    LoginHelper.loginOut(this, mPresenter.mDataManager);
                    mDialog.dismiss();
                })
                .builder();
        TextView textView = mDialog.getView(R.id.text);
        textView.setText("确定退出登录？");
        mDialog.show();
    }

    @Override
    public void handlerBind() {
        mNameTv.setText("已绑定");
    }

    @Override
    public void handlerSuccess(User user) {
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

}
