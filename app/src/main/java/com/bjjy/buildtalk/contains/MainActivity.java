package com.bjjy.buildtalk.contains;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.contains.circle.CircleFragment;
import com.bjjy.buildtalk.contains.discover.DiscoverFragment;
import com.bjjy.buildtalk.contains.mine.MineFragment;
import com.bjjy.buildtalk.contains.talk.TalkFragment;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.fragment_group)
    FrameLayout mFragmentGroup;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.normalView)
    RelativeLayout mNormalView;

    private int mLastFgIndex = -1;
    private int mCurrentFgIndex = 0;
    private DiscoverFragment mDiscoverFragment;
    private CircleFragment mCircleFragment;
    private TalkFragment mTalkFragment;
    private MineFragment mMineFragment;

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
    protected void initToolbar() {

    }

    @Override
    protected void initView() {
        showFragment(mCurrentFgIndex);
        initNavigationView();
    }

    private void initNavigationView() {
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
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
        mPresenter.test();
    }

    @Override
    public void handleSuccess() {
        LogUtils.e("handleSuccess");
    }

}
