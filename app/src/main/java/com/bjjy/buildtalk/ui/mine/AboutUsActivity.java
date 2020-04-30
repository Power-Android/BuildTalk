package com.bjjy.buildtalk.ui.mine;

import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity<AboutUsPresenter> implements AboutUsContract.View {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
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
        mToolbarBack.setOnClickListener(v -> finish());
        mTitle = getIntent().getStringExtra("title");
        mUrl = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(mTitle)){
            mToolbarTitle.setText(R.string.about_us);
        }else {
            if (!TextUtils.equals("web", mTitle)){
                mToolbarTitle.setText(mTitle);
            }
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
            mWebview.loadUrl("https://www.51jiantan.com/we");
        }else {
            mWebview.loadUrl(mUrl);
        }
    }

}
