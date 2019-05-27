package com.bjjy.buildtalk.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
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
                startActivity(new Intent(this, PhoneLoginActivity.class));
                break;
            case R.id.wechat_tv:
                break;
        }
    }
}
