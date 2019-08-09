package com.bjjy.buildtalk.ui.circle;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircleAgreementActvity extends BaseActivity<CircleAgreementPresenter> implements CircleAgreementContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;
    @BindView(R.id.webView)
    WebView mWebView;
    private String mUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_agreement_actvity;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        String type = getIntent().getStringExtra("type");
        mUrl = getIntent().getStringExtra("url");
        switch (type){
            case "qzgz":
                mToolbarTitle.setText(R.string.circle_rules);
                break;
            case "yhxy":
                mToolbarTitle.setText("用户协议");
                break;
            case "yszc":
                mToolbarTitle.setText("隐私政策");
                break;
        }
    }

    @Override
    protected void initEventAndData() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.loadUrl(mUrl);
    }

}
