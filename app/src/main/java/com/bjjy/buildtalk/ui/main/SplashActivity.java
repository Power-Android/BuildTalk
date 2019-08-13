package com.bjjy.buildtalk.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticDetailActivity;
import com.bjjy.buildtalk.ui.discover.EveryTalkDetailActivity;
import com.bjjy.buildtalk.utils.LogUtils;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    private boolean isExist = false;
    private Uri data;

    @Override
    protected int getLayoutId() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏

        String action = getIntent().getAction();
        String scheme = getIntent().getScheme();
        if (isExistMainActivity(MainActivity.class)) {
            isExist = true;
        } else {
            isExist = false;
        }
        if (isExist) {//如果存在，就直接跳转后finish掉
            if (!TextUtils.isEmpty(action) && TextUtils.equals(action, Intent.ACTION_VIEW) &&
                    !TextUtils.isEmpty(scheme) && TextUtils.equals(scheme, "jiantanapp")) {
                scheme();
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        } else {//如果不存在，直接运行动画后进入mainactivity
            initAnimation();
        }
    }

    private void initAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3F, 1.0F);
        alphaAnimation.setDuration(2000);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isGuide = mPresenter.mDataManager.getIsGuide();
                if (isGuide) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    data = getIntent().getData();
                    if (data != null){//去mainactivity跳转到指定页面
                        String s = data.toString();
                        intent.putExtra("data", s);
                    }
                    startActivity(intent);

                } else {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                }
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.splash_layout).startAnimation(alphaAnimation);
    }

    @Override
    protected void initEventAndData() {

    }

    private void scheme() {
        data = getIntent().getData();
        if (data != null) {
            //获得参数值
            String type = data.getQueryParameter("type");
            String circleId = data.getQueryParameter("circleId");
            String articleOrNewsId = data.getQueryParameter("articleOrNewsId");
            String themeId = data.getQueryParameter("themeId");
            String circleName = data.getQueryParameter("circleName");
            LogUtils.e("type: " + type + "circleId: " + circleId + "articleOrNewsId: " + articleOrNewsId + "themeId: " + themeId + "circleName:" + circleName);
            if (TextUtils.isEmpty(type)) {
                return;
            }
            switch (type) {
                case "1"://话题圈
                    if (!TextUtils.isEmpty(circleId)) {
                        Intent intent = new Intent(this, TopticCircleActivity.class);
                        intent.putExtra("circle_id", circleId);
                        startActivity(intent);
                    }
                    break;
                case "2"://每日一谈
                    if (!TextUtils.isEmpty(articleOrNewsId)) {
                        Intent intent = new Intent(this, EveryTalkDetailActivity.class);
                        intent.putExtra("article_id", articleOrNewsId);
                        startActivity(intent);
                    }
                    break;
                case "3"://主题详情
                    if (!TextUtils.isEmpty(themeId)) {
                        Intent intent = new Intent(this, TopticDetailActivity.class);
                        intent.putExtra("title", circleName);
                        intent.putExtra("theme_id", themeId);
                        startActivity(intent);
                    }
                    break;
                case "4"://课程圈
                    if (!TextUtils.isEmpty(circleId)) {
                        Intent intent = new Intent(this, CourseCircleActivity.class);
                        intent.putExtra("circle_id", circleId);
                        startActivity(intent);
                    }
                    break;
                case "5"://精品文章
                    if (!TextUtils.isEmpty(articleOrNewsId)) {
                        Intent intent = new Intent(this, EveryTalkDetailActivity.class);
                        intent.putExtra("type", "article");
                        intent.putExtra("circle_id", circleId);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }
}
