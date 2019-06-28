package com.bjjy.buildtalk.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity<BindPhonePresenter> implements BindPhoneContract.View {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.phone_tv)
    TextView mPhoneTv;
    @BindView(R.id.edit_tv)
    TextView mEditTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initView() {
        mToolbarLeftTitle.setText(R.string.cancle);
        mToolbarTitle.setText("绑定手机号");
        mPhoneTv.setText(mPresenter.mDataManager.getUser().getMobile());
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.toolbar_left_title, R.id.edit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.edit_tv:
                startActivity(new Intent(this, EditBindPhoneActivity.class));
                finish();
                break;
        }
    }
}
