package com.bjjy.buildtalk.ui.circle;

import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;

import butterknife.BindView;

public class CircleAgreementActvity extends BaseActivity<CircleAgreementPresenter> implements CircleAgreementContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_agreement_actvity;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText(R.string.circle_rules);
    }

    @Override
    protected void initEventAndData() {

    }

}
