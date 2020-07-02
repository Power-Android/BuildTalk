package com.bjjy.buildtalk.ui.main;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.ui.circle.CircleFragment;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.bjjy.buildtalk.ui.circle.PublishActivity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticDetailActivity;
import com.bjjy.buildtalk.ui.discover.DiscoverFragment;
import com.bjjy.buildtalk.ui.discover.EveryTalkDetailActivity;
import com.bjjy.buildtalk.ui.home.HomeFragment;
import com.bjjy.buildtalk.ui.mine.MineFragment;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.fragment_group)
    FrameLayout mFragmentGroup;
    @BindView(R.id.home_iv)
    ImageView mHomeIv;
    @BindView(R.id.home_tv)
    TextView mHomeTv;
    @BindView(R.id.home_cl)
    ConstraintLayout mHomeCl;
    @BindView(R.id.circle_iv)
    ImageView mCircleIv;
    @BindView(R.id.circle_tv)
    TextView mCircleTv;
    @BindView(R.id.circle_cl)
    ConstraintLayout mCircleCl;
    @BindView(R.id.add_iv)
    ImageView mAddIv;
    @BindView(R.id.add_cl)
    ConstraintLayout mAddCl;
    @BindView(R.id.discover_iv)
    ImageView mDiscoverIv;
    @BindView(R.id.discover_tv)
    TextView mDiscoverTv;
    @BindView(R.id.discover_cl)
    ConstraintLayout mDiscoverCl;
    @BindView(R.id.mine_iv)
    ImageView mMineIv;
    @BindView(R.id.mine_tv)
    TextView mMineTv;
    @BindView(R.id.mine_cl)
    ConstraintLayout mMineCl;

    private int mLastFgIndex = -1;
    private int mCurrentFgIndex = 0;
    private DiscoverFragment mDiscoverFragment;
    private CircleFragment mCircleFragment;
    //    private TalkFragment mTalkFragment;
    private MineFragment mMineFragment;
    private HomeFragment mHomeFragment;
    private Uri data;
    private long clickTime;
    private long mLastClickPubTS = 0;

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
        String s = getIntent().getStringExtra("data");
        if (!TextUtils.isEmpty(s)) {
            data = Uri.parse(s);
            scheme();
        }
    }

    @Override
    protected void onResume() {
        setIsMargin(true);
        super.onResume();
    }

    @OnClick({R.id.home_cl, R.id.circle_cl, R.id.add_cl, R.id.discover_cl, R.id.mine_cl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_cl:
                showFragment(Constants.TYPE_DISCOVER);
                break;
            case R.id.circle_cl:
                showFragment(Constants.TYPE_CIRCLE);
                break;
            case R.id.add_cl:
                // 防止多次点击
                if (System.currentTimeMillis() - mLastClickPubTS > 1000) {
                    mLastClickPubTS = System.currentTimeMillis();
                    LoginHelper.getInstance().login(this, mPresenter.mDataManager, () ->
                            new RxPermissions(MainActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .subscribe(new Observer<Boolean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(Boolean aBoolean) {
                                            if (aBoolean) {
                                                Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                                                intent.putExtra("publish_type", "2");
                                                startActivity(intent);
                                            } else {
                                                ToastUtils.showShort("权限被拒绝");
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    }));
                }
                break;
            case R.id.discover_cl:
                showFragment(Constants.TYPE_FIND);
                break;
            case R.id.mine_cl:
                showFragment(Constants.TYPE_MINE);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int index = getIntent().getIntExtra("mCurrentFgIndex", -2);
        if (index == 0)
            showFragment(0);
    }

    private void showFragment(int index) {
        mCurrentFgIndex = index;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        mLastFgIndex = index;
        switch (index) {
            case Constants.TYPE_DISCOVER:
                mHomeIv.setImageResource(R.drawable.home_sel);
                mHomeTv.setTextColor(getResources().getColor(R.color.blue_mid));
                mCircleIv.setImageResource(R.drawable.circle_def);
                mCircleTv.setTextColor(getResources().getColor(R.color.text_color6));
                mDiscoverIv.setImageResource(R.drawable.discover_def);
                mDiscoverTv.setTextColor(getResources().getColor(R.color.text_color6));
                mMineIv.setImageResource(R.drawable.mine_def);
                mMineTv.setTextColor(getResources().getColor(R.color.text_color6));
                if (mDiscoverFragment == null) {
                    mDiscoverFragment = DiscoverFragment.newInstance();
                    transaction.add(R.id.fragment_group, mDiscoverFragment);
                }
                transaction.show(mDiscoverFragment);
                break;
            case Constants.TYPE_CIRCLE:
                mHomeIv.setImageResource(R.drawable.home_del);
                mHomeTv.setTextColor(getResources().getColor(R.color.text_color6));
                mCircleIv.setImageResource(R.drawable.circle_sel);
                mCircleTv.setTextColor(getResources().getColor(R.color.blue_mid));
                mDiscoverIv.setImageResource(R.drawable.discover_def);
                mDiscoverTv.setTextColor(getResources().getColor(R.color.text_color6));
                mMineIv.setImageResource(R.drawable.mine_def);
                mMineTv.setTextColor(getResources().getColor(R.color.text_color6));
                if (mCircleFragment == null) {
                    mCircleFragment = CircleFragment.newInstance();
                    transaction.add(R.id.fragment_group, mCircleFragment);
                }
                transaction.show(mCircleFragment);
                break;
            case Constants.TYPE_FIND:
                mHomeIv.setImageResource(R.drawable.home_del);
                mHomeTv.setTextColor(getResources().getColor(R.color.text_color6));
                mCircleIv.setImageResource(R.drawable.circle_def);
                mCircleTv.setTextColor(getResources().getColor(R.color.text_color6));
                mDiscoverIv.setImageResource(R.drawable.discover_sel);
                mDiscoverTv.setTextColor(getResources().getColor(R.color.blue_mid));
                mMineIv.setImageResource(R.drawable.mine_def);
                mMineTv.setTextColor(getResources().getColor(R.color.text_color6));
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance();
                    transaction.add(R.id.fragment_group, mHomeFragment);
                }
                transaction.show(mHomeFragment);
                break;
            case Constants.TYPE_MINE:
                mHomeIv.setImageResource(R.drawable.home_del);
                mHomeTv.setTextColor(getResources().getColor(R.color.text_color6));
                mCircleIv.setImageResource(R.drawable.circle_def);
                mCircleTv.setTextColor(getResources().getColor(R.color.text_color6));
                mDiscoverIv.setImageResource(R.drawable.discover_def);
                mDiscoverTv.setTextColor(getResources().getColor(R.color.text_color6));
                mMineIv.setImageResource(R.drawable.mine_sel);
                mMineTv.setTextColor(getResources().getColor(R.color.blue_mid));
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
            case Constants.TYPE_FIND:
                if (mHomeFragment != null) {
                    transaction.hide(mHomeFragment);
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
            getPlayerWindowManager().getBinder().stop();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPlayerWindowManager().getBinder().stop();
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
