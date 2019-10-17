package com.bjjy.buildtalk.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.ui.circle.CircleFragment;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticDetailActivity;
import com.bjjy.buildtalk.ui.discover.DiscoverFragment;
import com.bjjy.buildtalk.ui.discover.EveryTalkDetailActivity;
import com.bjjy.buildtalk.ui.mine.MineFragment;
import com.bjjy.buildtalk.ui.talk.TalkFragment;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.fragment_group)
    FrameLayout mFragmentGroup;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;

    private int mLastFgIndex = -1;
    private int mCurrentFgIndex = 0;
    private DiscoverFragment mDiscoverFragment;
    private CircleFragment mCircleFragment;
    private TalkFragment mTalkFragment;
    private MineFragment mMineFragment;
    private Uri data;
    private long clickTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentFgIndex = savedInstanceState.getInt(Constants.CURRENT_FRAGMENT_KEY);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.CURRENT_FRAGMENT_KEY, mCurrentFgIndex);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        showFragment(mCurrentFgIndex);
        initNavigationView();
        String s = getIntent().getStringExtra("data");
        if (!TextUtils.isEmpty(s)){
            data = Uri.parse(s);
            scheme();
        }

        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
            e.onComplete();
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.e("onSubscribe");
            }

            @Override
            public void onNext(Integer o) {
                LogUtils.e(o);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("onError");
            }

            @Override
            public void onComplete() {
                LogUtils.e("onComplete");
            }
        });
    }

    private void initNavigationView() {
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.tab_discover:
                    showFragment(Constants.TYPE_DISCOVER);
                    break;
                case R.id.tab_circle:
                    showFragment(Constants.TYPE_CIRCLE);
                    break;
                case R.id.tab_talk:
                    showFragment(Constants.TYPE_TALK);
                    break;
                case R.id.tab_mine:
                    showFragment(Constants.TYPE_MINE);
                    break;
            }
            return true;
        });
    }

    private void showFragment(int index) {
        mCurrentFgIndex = index;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        mLastFgIndex = index;
        switch (index) {
            case Constants.TYPE_DISCOVER:
                if (mDiscoverFragment == null) {
                    mDiscoverFragment = DiscoverFragment.newInstance();
                    transaction.add(R.id.fragment_group, mDiscoverFragment);
                }
                transaction.show(mDiscoverFragment);
                break;
            case Constants.TYPE_CIRCLE:
                if (mCircleFragment == null) {
                    mCircleFragment = CircleFragment.newInstance();
                    transaction.add(R.id.fragment_group, mCircleFragment);
                }
                transaction.show(mCircleFragment);
                break;
            case Constants.TYPE_TALK:
                if (mTalkFragment == null) {
                    mTalkFragment = TalkFragment.newInstance();
                    transaction.add(R.id.fragment_group, mTalkFragment);
                }
                transaction.show(mTalkFragment);
                break;
            case Constants.TYPE_MINE:
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.newInstance();
                    transaction.add(R.id.fragment_group, mMineFragment);
                }
                transaction.show(mMineFragment);
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        switch (mLastFgIndex) {
            case Constants.TYPE_DISCOVER:
                if (mDiscoverFragment != null) {
                    transaction.hide(mDiscoverFragment);
                }
                break;
            case Constants.TYPE_CIRCLE:
                if (mCircleFragment != null) {
                    transaction.hide(mCircleFragment);
                }
                break;
            case Constants.TYPE_TALK:
                if (mTalkFragment != null) {
                    transaction.hide(mTalkFragment);
                }
                break;
            case Constants.TYPE_MINE:
                if (mMineFragment != null) {
                    transaction.hide(mMineFragment);
                }
                break;
        }
    }

    @Override
    protected void initEventAndData() {
//        mPresenter.test();
    }

    @Override
    public void handleSuccess() {
        LogUtils.e("handleSuccess");
    }

    /**
     * 处理回退事件
     */
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - clickTime) > Constants.DOUBLE_INTERVAL_TIME) {
            ToastUtils.showShort(getString(R.string.double_click_exit_toast));
            clickTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    private void scheme() {
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
                        intent.putExtra("circle_id", "");
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
