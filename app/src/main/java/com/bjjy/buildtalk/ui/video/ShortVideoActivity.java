package com.bjjy.buildtalk.ui.video;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.ShortVideoEntity;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.recycler.OnPagerListener;
import com.bjjy.buildtalk.weight.recycler.PagerLayoutManager;
import com.bjjy.buildtalk.weight.recycler.ShortVideoAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_BEGIN;
import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_PROGRESS;
import static com.tencent.rtmp.TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;

public class ShortVideoActivity extends BaseActivity<ShortVideoPresenter> implements
        ShortVideoContract.View, OnPagerListener {
    @BindView(R.id.normalView)
    ConstraintLayout mNormalView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.more_iv)
    ImageView mMoreIv;

    private List<ShortVideoEntity.ThemeInfoBean> mList = new ArrayList<>();
    private int page = 1;
    private ShortVideoAdapter mVideoAdapter;
    private TXVodPlayer mVideoPlayer;
    private TXCloudVideoView mVideoView;
    private ImageView mCoverIv;
    private int pagePosition = 0;//记录滑动后的position（包括滑动后换页和滑动后没有换页）
    private ProgressBar mProgressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_short_video;
    }

    @Override
    protected void initView() {
        String type_id = getIntent().getStringExtra("type_id");
        String theme_id = getIntent().getStringExtra("theme_id");
        String user_id = getIntent().getStringExtra("user_id");

        mPresenter.getVideoList(type_id, theme_id, user_id, page);

        ImmersionBar.with(this)
                .fitsSystemWindows(false)
                .transparentStatusBar()
                .statusBarDarkFont(false, 0.2f)
                .keyboardEnable(true)
                .init();

        PagerLayoutManager pagerLayoutManager = new PagerLayoutManager(
                this, OrientationHelper.VERTICAL);
        mVideoAdapter = new ShortVideoAdapter(R.layout.adapter_short_video_layout, mList);
        mRecyclerView.setLayoutManager(pagerLayoutManager);
        mRecyclerView.setAdapter(mVideoAdapter);
        pagerLayoutManager.setOnViewPagerListener(this);
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void handlerVideoSuccess(ShortVideoEntity shortVideoEntity) {
        if (page == 1) {
            mVideoAdapter.setNewData(shortVideoEntity.getThemeInfo());
        }
    }

    @OnClick({R.id.back_iv, R.id.more_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.more_iv:
                break;
        }
    }

    @Override
    public void onInitComplete() {
        LogUtils.e("OnPagerListener---onInitComplete--" + "初始化完成");
        loadVideoInfo(0, true);
    }

    /**
     * @param isNext   上一个page是否被释放
     * @param position 索引
     */
    @Override
    public void onPageRelease(boolean isNext, int position) {
//        LogUtils.e("OnPagerListener---onPageRelease--" + position + "-----" + isNext);
    }

    /**
     * @param position 索引
     * @param isBottom 是否到了底部
     */
    @Override
    public void onPageSelected(int position, boolean isBottom) {
        LogUtils.e("OnPagerListener---onPageSelected--" + position + "-----" + isBottom +
                "-----" + pagePosition);
        loadVideoInfo(position, false);
        if (isBottom && position != 0) {//如果是最后一个视频时，滑动view时会一直调用此方法，因此做个判断
            ToastUtils.showShort("没有更多视频了...");
        }
        pagePosition = position;
    }

    private void loadVideoInfo(int position, boolean isFirst) {
        if (isFirst){//第一次进来
            videoInfo(position);
        }else {
            //上滑后pagePosition != position != 0，先置空上个视频，然后再加载
            if (position != pagePosition || position == 0) {
                //position=0说明上滑到了第一个，滑动第一个视频但没有换页，此时return，继续播放
                if (position == pagePosition)
                    return;
                releaseVideoInfo();
                videoInfo(position);
            }
        }
    }

    /**
     * 设置播放器info
     * @param position
     */
    private void videoInfo(int position) {
        List<ShortVideoEntity.ThemeInfoBean.ParentThemeInfoBean.ThemeVideoBean> theme_video =
                mVideoAdapter.getData().get(position).getParent_themeInfo().getTheme_video();
        mVideoView = (TXCloudVideoView) mVideoAdapter
                .getViewByPosition(mRecyclerView, position, R.id.video_view);
        mCoverIv = (ImageView) mVideoAdapter
                .getViewByPosition(mRecyclerView, position, R.id.cover_iv);
        mProgressBar = (ProgressBar) mVideoAdapter.getViewByPosition(mRecyclerView, position, R.id.progress_bar_h);
        mVideoPlayer = new TXVodPlayer(this);
        mVideoPlayer.setPlayerView(mVideoView);
        mVideoPlayer.setRenderMode(RENDER_MODE_ADJUST_RESOLUTION);
        mVideoPlayer.setLoop(true);
        mVideoPlayer.setAutoPlay(true);
        if (!TextUtils.isEmpty(theme_video.get(0).getVideo_url())){
            mVideoPlayer.startPlay(mVideoAdapter.getData().get(position).getParent_themeInfo()
                    .getTheme_video().get(0).getVideo_url());
            LogUtils.e("adapter-" + position + "=视频地址：" + theme_video.get(0).getVideo_url());
        }else {
            ToastUtils.showShort("视频不见了...");
        }

        mVideoPlayer.setVodListener(new ITXVodPlayListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle param) {
                switch (event){
                    case PLAY_EVT_PLAY_BEGIN://视频准备开始
                        mCoverIv.setVisibility(View.GONE);
                        break;
                    case PLAY_EVT_PLAY_PROGRESS://加载和播放进度-秒
                        // 加载进度, 单位是毫秒
                        int duration = param.getInt(TXLiveConstants.EVT_PLAYABLE_DURATION_MS);
                        // 播放进度, 单位是毫秒
                        int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS);
                        // 视频总长, 单位是毫秒 可以用于设置时长显示等等
                        int totalDuration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION_MS);
                        int pro = progress * 100 / totalDuration;
                        mProgressBar.setProgress(pro, true);
                        break;
                }
            }

            @Override
            public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

            }
        });
    }

    /**
     * 置空，防止内存泄漏
     */
    private void releaseVideoInfo() {
        if (mVideoPlayer != null) {
            mVideoPlayer.pause();
            mVideoPlayer.stopPlay(true);
            mVideoView.onDestroy();
            mVideoPlayer.setVodListener(null);
            mVideoPlayer = null;
            mVideoView = null;
            mCoverIv = null;
            mProgressBar = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoPlayer != null)
            mVideoPlayer.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoPlayer != null)
            mVideoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseVideoInfo();
    }
}
