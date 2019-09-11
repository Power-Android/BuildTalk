package com.bjjy.buildtalk.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity<AboutUsPresenter> implements AboutUsContract.View {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;
    @BindView(R.id.webview)
    WebView mWebview;
    private String mUrl;
    private String mTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mTitle = getIntent().getStringExtra("title");
        mUrl = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(mTitle)){
            mToolbarTitle.setText(R.string.about_us);
        }else {
            mToolbarTitle.setText(mTitle);
        }
    }

    @Override
    protected void initEventAndData() {
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (TextUtils.isEmpty(mUrl)){
            mWebview.loadUrl("https://jt.chinabim.com/we");
        }else {
            mWebview.loadUrl(mUrl);
        }
    }

}
