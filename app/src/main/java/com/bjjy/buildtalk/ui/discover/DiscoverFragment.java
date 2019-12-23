package com.bjjy.buildtalk.ui.discover;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.DiscoverAdapter;
import com.bjjy.buildtalk.adapter.EveryTalkAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.bjjy.buildtalk.core.event.PlayerEvent;
import com.bjjy.buildtalk.core.service.PlayerService;
import com.bjjy.buildtalk.entity.ActivityEntity;
import com.bjjy.buildtalk.entity.BannerEntity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.entity.DiscoverEntity;
import com.bjjy.buildtalk.entity.DissertationEntity;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.bjjy.buildtalk.entity.SongsEntity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.utils.AnimatorUtils;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.PlayerHelper;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.player.PlayerWindowManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author power
 * @date 2019/4/26 4:34 PM
 * @project BuildTalk
 * @description: 发现 模块
 */
public class DiscoverFragment extends BaseFragment<DiscoverPresenter> implements DiscoverContract.View,
        OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener, DiscoverAdapter.OnChildRecyclerItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.discover_recyclerView)
    RecyclerView mDiscoverRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_reload)
    TextView mTvReload;
    @BindView(R.id.noNetView)
    RelativeLayout mNoNetView;

    private List<DiscoverEntity> discoverEntityList = new ArrayList<>();
    private DiscoverAdapter mDiscoverAdapter;
    public static int HOT_TOPTIC_PAGE = 1;
    public static int COURSE_PAGE = 1;
    private List<EveryTalkEntity> mData;
    private EveryTalkAdapter everyTalkAdapter;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PlayerEvent eventBean) {
        if (mData != null && mData.size() > 0) {
            for (int i = 0; i < mData.size(); i++) {
                if (PlayerWindowManager.getInstance().getBinder().isPlaying()) {//先判断是不是正在播放
                    //如果id相同设置true
                    if (TextUtils.equals(PlayerWindowManager.getInstance().getSongId(), String.valueOf(mData.get(i).getArticle_id()))) {
                        mData.get(i).setChecked(true);
                    } else {
                        mData.get(i).setChecked(false);
                    }
                } else {//暂停状态都设置成false
                    mData.get(i).setChecked(false);
                }
            }
            everyTalkAdapter.notifyDataSetChanged();
        }
    }

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mToolbarBack.setVisibility(View.GONE);
        mToolbarTitle.setText(R.string.discover);
        mRefreshLayout.setOnRefreshListener(this);
        mDiscoverRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDiscoverAdapter = new DiscoverAdapter(discoverEntityList);
        mDiscoverRecyclerView.setAdapter(mDiscoverAdapter);
        mDiscoverAdapter.setOnItemChildClickListener(this);
        mTvReload.setOnClickListener(v -> onRefresh(mRefreshLayout));
        mDiscoverAdapter.setOnChildRecyclerItemClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.discoverType(discoverEntityList);
        mPresenter.getActivity();
        netWork();
    }

    private void netWork() {
//        if (!NetworkUtils.isConnected()){
//            mRefreshLayout.setVisibility(View.GONE);
//            mNoNetView.setVisibility(View.VISIBLE);
//        }else {
            mNoNetView.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mPresenter.discoverBanner();
            mPresenter.discoverEveryTalk();
            mPresenter.discoverToptic();
            mPresenter.discoverCourse();
            mPresenter.discoverDissertation();
//        }
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
        mPresenter.getSongs(everyTalkEntities.get(0).getArticle_id(), 0);
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
    public void handlerDissertation(List<DissertationEntity> dissertationEntities) {
        mDiscoverAdapter.setDissertationEntities(dissertationEntities);
    }

    @Override
    public void handlerActivitySuccess(ActivityEntity activityEntity) {
        if (1 == activityEntity.getIs_show()) {
            DialogUtils.showActivityDialog(mContext, activityEntity);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        HOT_TOPTIC_PAGE = 1;
        COURSE_PAGE = 1;
        netWork();
        refreshLayout.finishRefresh(1500);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.et_all_tv://每日一谈----查看全部
                startActivity(new Intent(mContext, EveryTalkListActivity.class));
                break;
            case R.id.toptic_all_tv://热门话题圈----查看全部
                startActivity(new Intent(mContext, TopticListActivity.class));
                break;
            case R.id.toptic_change_ll://热门话题圈----换一换
                ImageView topticChangeIv = view.findViewById(R.id.toptic_change_iv);
                AnimatorUtils.setRotateAnimation(topticChangeIv);
                view.setClickable(false);
                new Handler().postDelayed(() -> {
                    HOT_TOPTIC_PAGE++;
                    mPresenter.discoverToptic();
                    view.setClickable(true);
                }, 1000);
                break;
            case R.id.course_all_tv://精品课程----查看全部
                startActivity(new Intent(mContext, CourseListActivity.class));
                break;
            case R.id.course_change_ll://精品课程----换一换
                ImageView courseChangeIv = view.findViewById(R.id.course_change_iv);
                AnimatorUtils.setRotateAnimation(courseChangeIv);
                view.setClickable(false);
                new Handler().postDelayed(() -> {
                    COURSE_PAGE++;
                    mPresenter.discoverCourse();
                    view.setClickable(true);
                }, 1000);
                break;
            case R.id.project_all_tv://精彩专题----查看全部
                startActivity(new Intent(mContext, DissertationListActivity.class));
                break;
        }
    }

    @Override
    public void onEveryTalkItemClick(BaseQuickAdapter adapter, View view, int position) {
        everyTalkAdapter = (EveryTalkAdapter) adapter;
        mData = adapter.getData();
        //先查询数据是否为null，如果null-->先请求数据，后播放，如果不为null-->播放
        SongsEntity songsEntity = PlayerHelper.querySongs(String.valueOf(mData.get(position).getArticle_id()), mPresenter.mDataManager);
        if (songsEntity != null) {
            //如果点击的是相同的音频
            if (String.valueOf(mData.get(position).getArticle_id()).equals(PlayerWindowManager.getInstance().getSongId())) {
                if (PlayerWindowManager.getInstance().getBinder().isPlaying()) {
                    PlayerWindowManager.getInstance().getBinder().pause();
                } else {
                    PlayerWindowManager.getInstance().getBinder().resume();
                }
            } else {//如果是不相同的音频
                showPlayer(String.valueOf(mData.get(position).getArticle_id()));
            }
        } else {
            PlayerWindowManager.getInstance().requestSongs(mData.get(position).getArticle_id() + "", "1");
        }
    }

    @Override
    public void handlerSongs(List<SongsEntity> songsEntities, int position) {
        mPresenter.mDataManager.addSongsData(songsEntities);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
