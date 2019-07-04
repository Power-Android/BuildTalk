package com.bjjy.buildtalk.ui.circle;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class CourseDetailActivity extends BaseActivity<CourseDetailPresenter> implements CourseDetailContarct.View {

    @BindView(R.id.videoplayer)
    JzvdStd mVideoplayer;
    @BindView(R.id.fl_VideoPlayer)
    FrameLayout mFlVideoPlayer;
    @BindView(R.id.chapter_title_tv)
    TextView mChapterTitleTv;
    @BindView(R.id.xq_expand_iv)
    ImageView mXqExpandIv;
    @BindView(R.id.more_ll)
    LinearLayout mMoreLl;
    @BindView(R.id.view7)
    ImageView mView7;
    @BindView(R.id.item_name_tv)
    TextView mItemNameTv;
    @BindView(R.id.only_play_rl)
    RelativeLayout mOnlyPlayRl;
    @BindView(R.id.update_tv)
    TextView mUpdateTv;
    @BindView(R.id.gx_expand_iv)
    ImageView mGxExpandIv;
    @BindView(R.id.list_ll)
    LinearLayout mListLl;
    @BindView(R.id.num_tv)
    TextView mNumTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    @BindView(R.id.join_tv)
    TextView mJoinTv;
    @BindView(R.id.chapter_recycler)
    RecyclerView mChapterRecycler;
    @BindView(R.id.chapter_content_tv)
    TextView mChapterContentTv;
    @BindView(R.id.content_scroll_view)
    NestedScrollView mContentScrollView;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.pre_share_iv)
    ImageView mPreShareIv;
    @BindView(R.id.share_iv)
    ImageView mShareIv;
    @BindView(R.id.more_iv)
    ImageView mMoreIv;
    @BindView(R.id.top_title_rl)
    RelativeLayout mTopTitleRl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeStatusBar(this, true, false);
        mTopTitleRl.setPadding(0, StatusBarUtils.getStatusBarHeight(), 0, 0);

        String url = "http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4";
        Glide.with(this.getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000))
                .load(url)
                .into(mVideoplayer.thumbImageView);
        mVideoplayer.setUp(new JZDataSource(url), Jzvd.SCREEN_WINDOW_NORMAL);
    }

    @Override
    protected void initEventAndData() {

    }


    @OnClick({R.id.more_ll, R.id.only_play_rl, R.id.list_ll, R.id.join_tv, R.id.back_iv, R.id.pre_share_iv, R.id.share_iv, R.id.more_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_ll:
                break;
            case R.id.only_play_rl:
                break;
            case R.id.list_ll:
                break;
            case R.id.join_tv:
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.pre_share_iv:
                break;
            case R.id.share_iv:
                break;
            case R.id.more_iv:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
    }
}
