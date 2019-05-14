package com.bjjy.buildtalk.contains.discover;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.DiscoverAdapter;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.bjjy.buildtalk.entity.BannerEntity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.entity.DiscoverEntity;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.bjjy.buildtalk.utils.AnimatorUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author power
 * @date 2019/4/26 4:34 PM
 * @project BuildTalk
 * @description: 发现 模块
 */
public class DiscoverFragment extends BaseFragment<DiscoverPresenter> implements DiscoverContract.View, OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.discover_recyclerView)
    RecyclerView mDiscoverRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.normalView)
    LinearLayout mNormalView;

    private List<DiscoverEntity> discoverEntityList = new ArrayList<>();
    private DiscoverAdapter mDiscoverAdapter;
    public static int HOT_TOPTIC_PAGE = 1;
    public static int COURSE_PAGE = 1;

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView() {
        mToolbarTitle.setText(R.string.discover);
        mRefreshLayout.setOnRefreshListener(this);
        mDiscoverRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDiscoverAdapter = new DiscoverAdapter(discoverEntityList);
        mDiscoverRecyclerView.setAdapter(mDiscoverAdapter);
        mDiscoverAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.discoverType(discoverEntityList);
        mPresenter.discoverBanner();
        mPresenter.discoverEveryTalk();
        mPresenter.discoverToptic();
        mPresenter.discoverCourse();

    }

    @Override
    public void handlerDiscoverType(List<DiscoverEntity> discoverEntityList) {
        mDiscoverAdapter.setNewData(discoverEntityList);
    }

    @Override
    public void handlerBanner(List<BannerEntity> bannerEntities) {
        List<String> bannerImageList = new ArrayList<>();
        for (BannerEntity bannerData : bannerEntities) {
            bannerImageList.add(bannerData.getPic_url());
        }
        mDiscoverAdapter.setBannerEntities(bannerEntities, bannerImageList);
    }

    @Override
    public void handlerEveryTalk(List<EveryTalkEntity> everyTalkEntities) {
        mDiscoverAdapter.setEveryTalkEntities(everyTalkEntities);
    }

    @Override
    public void handlerToptic(CourseEntity courseEntities) {
        mDiscoverAdapter.setTopticEntities(courseEntities);
    }

    @Override
    public void handlerCourse(CourseEntity courseEntities) {
        mDiscoverAdapter.setCourseEntities(courseEntities);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(2000);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.et_all_tv:
                startActivity(new Intent(mContext, EveryTalkListActivity.class));
                break;
            case R.id.toptic_all_tv:
                startActivity(new Intent(mContext, TopticListActivity.class));
                break;
            case R.id.toptic_change_ll:
                ImageView topticChangeIv = view.findViewById(R.id.toptic_change_iv);
                AnimatorUtils.setRotateAnimation(topticChangeIv);
                view.setClickable(false);
                new Handler().postDelayed(() -> {
                    HOT_TOPTIC_PAGE++;
                    mPresenter.discoverToptic();
                    view.setClickable(true);
                }, 2000);
                break;
            case R.id.course_all_tv:
                startActivity(new Intent(mContext, CourseListActivity.class));
                break;
            case R.id.course_change_ll:
                ImageView courseChangeIv = view.findViewById(R.id.course_change_iv);
                AnimatorUtils.setRotateAnimation(courseChangeIv);
                view.setClickable(false);
                new Handler().postDelayed(() -> {
                    COURSE_PAGE++;
                    mPresenter.discoverCourse();
                    view.setClickable(true);
                }, 1000);
                break;
        }
    }

}
