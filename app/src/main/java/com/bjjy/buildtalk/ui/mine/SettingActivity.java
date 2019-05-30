package com.bjjy.buildtalk.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;

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

    }

    @OnClick({R.id.bind_wechat, R.id.about_us, R.id.version_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bind_wechat:
                break;
            case R.id.about_us:
                startActivity(new Intent(this,AboutUsActivity.class));
                break;
            case R.id.version_info:
                break;
        }
    }
}
