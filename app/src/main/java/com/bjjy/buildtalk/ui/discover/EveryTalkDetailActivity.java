package com.bjjy.buildtalk.ui.discover;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.EveryTalkDetailAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class EveryTalkDetailActivity extends BaseActivity<EveryTalkDetailPresenter> implements EveryTalkDetailContract.View, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.face_iv)
    ImageView mFaceIv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.time_tv)
    TextView mTimeTv;
    @BindView(R.id.play_iv)
    ImageView mPlayIv;
    @BindView(R.id.playing_iv)
    ImageView mPlayingIv;
    @BindView(R.id.media_name_tv)
    TextView mMediaNameTv;
    @BindView(R.id.media_time_tv)
    TextView mMediaTimeTv;
    @BindView(R.id.media_size_tv)
    TextView mMediaSizeTv;
    @BindView(R.id.media_rl)
    RelativeLayout mMediaRl;
    @BindView(R.id.content_tv)
    TextView mContentTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.record_et)
    EditText mRecordEt;
    @BindView(R.id.praise_iv)
    ImageView mPraiseIv;
    @BindView(R.id.praise_tv)
    TextView mPraiseTv;
    @BindView(R.id.praise_ll)
    LinearLayout mPraiseLl;
    @BindView(R.id.share_iv)
    ImageView mShareIv;
    @BindView(R.id.videoplayer)
    JzvdStd mVideoplayer;
    @BindView(R.id.fl_VideoPlayer)
    FrameLayout mFlVideoPlayer;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;
    @BindView(R.id.record_num_tv)
    TextView mRecordNumTv;

    private SimpleExoPlayer simpleExoPlayer;
    private EveryTalkDetailAdapter mEveryTalkDetailAdapter;
    private List<EveryTalkDetailEntity.GuestbookInfoBean> mList = new ArrayList<>();
    private String mArticle_id;
    private EveryTalkDetailEntity.NewsInfoBean mMNewsInfo;
    private int mCountCollect = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_every_talk_detail;
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeStatusBar(this, true, true);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mIncludeToolbar.getLayoutParams());
        lp.setMargins(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        mIncludeToolbar.setLayoutParams(lp);
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText("每日一谈");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEveryTalkDetailAdapter = new EveryTalkDetailAdapter(R.layout.adapter_every_talk_detail, mList);
        mRecyclerView.setAdapter(mEveryTalkDetailAdapter);
        mEveryTalkDetailAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mArticle_id = getIntent().getStringExtra("article_id");
        mPresenter.everyTalkDetail(mArticle_id);
    }

    @Override
    public void handlerTalkDetail(EveryTalkDetailEntity everyTalkDetailEntity) {
        mMNewsInfo = everyTalkDetailEntity.getNewsInfo();
        mList = everyTalkDetailEntity.getGuestbookInfo();

        mEveryTalkDetailAdapter.setNewData(mList);

        mTitleTv.setText(mMNewsInfo.getArticle_title());
        Glide.with(this).load(mMNewsInfo.getAuthor_pic()).into(mFaceIv);
        mNameTv.setText(mMNewsInfo.getAuthor_name());
        mTimeTv.setText(mMNewsInfo.getPublish_time());
        mMediaNameTv.setText(mMNewsInfo.getArticle_title());
        if (!TextUtils.isEmpty(mMNewsInfo.getAudio_duration())) {
            mMediaRl.setVisibility(View.VISIBLE);
            int size = Integer.parseInt(mMNewsInfo.getAudio_duration());
            int m = size / 60;
            int s = size % 60;
            String s1 = m + "分" + s + "秒";
            mMediaTimeTv.setText(s1);
            mMediaSizeTv.setText(mMNewsInfo.getAudio_size() + "M");
            Glide.with(this).load(R.drawable.playing).into(mPlayingIv);
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
            DefaultDataSourceFactory defaultDataSourceFactory =
                    new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));
            ExtractorMediaSource.Factory factory = new ExtractorMediaSource.Factory(defaultDataSourceFactory);
            Uri uri = Uri.parse(mMNewsInfo.getAudio_url());
            simpleExoPlayer.prepare(factory.createMediaSource(uri));
            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playWhenReady) {
                        mPlayIv.setImageResource(R.drawable.media_stop_icon);
                        mPlayingIv.setVisibility(View.VISIBLE);
                        if (playbackState == Player.STATE_ENDED) {
                            simpleExoPlayer.prepare(factory.createMediaSource(uri));
                            simpleExoPlayer.setPlayWhenReady(!playWhenReady);
                        }
                    } else {
                        mPlayIv.setImageResource(R.drawable.media_play_icon);
                        mPlayingIv.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        if (!TextUtils.isEmpty(mMNewsInfo.getVideo_url())) {
            mFlVideoPlayer.setVisibility(View.VISIBLE);
            Glide.with(this.getApplicationContext())
                    .setDefaultRequestOptions(
                            new RequestOptions()
                                    .frame(1000000))
                    .load(mMNewsInfo.getVideo_url())
                    .into(mVideoplayer.thumbImageView);
            mVideoplayer.setUp(new JZDataSource(mMNewsInfo.getVideo_url()), Jzvd.SCREEN_WINDOW_NORMAL);
        }
        mContentTv.setText(Html.fromHtml(mMNewsInfo.getContent()));
        mRecordNumTv.setText(String.valueOf(mList.size()));
        mRecordEt.setHint(mList.size()+"评论");
        if ("0".equals(String.valueOf(mMNewsInfo.getIsCollect()))){
            mPraiseIv.setImageResource(R.drawable.praise_def);
        }else {
            mPraiseIv.setImageResource(R.drawable.praise_sel);
        }
        mCountCollect = mMNewsInfo.getCountCollect();
        mPraiseTv.setText( mCountCollect + "赞");
        mRecordEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND){
                mPresenter.saveRecord(mMNewsInfo.getArticle_id(),mRecordEt.getText().toString().trim());
                mRecordEt.clearFocus();
                mRecordEt.getText().clear();
                KeyboardUtils.hideSoftInput(this);
            }
            return false;
        });
    }

    @Override
    public void handlerSaveRecord() {
        mPresenter.everyTalkDetail(mArticle_id);
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeyboardUtils.hideSoftInput(this);
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
    }

    @OnClick({R.id.play_iv, R.id.praise_ll, R.id.share_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_iv:
                if (simpleExoPlayer != null) {
                    simpleExoPlayer.setPlayWhenReady(!simpleExoPlayer.getPlayWhenReady());
                }
                break;
            case R.id.praise_ll:
                boolean isCollect = "1".equals(String.valueOf(mMNewsInfo.getIsCollect()));
                mPresenter.collectArticle(mArticle_id,isCollect);
                break;
            case R.id.share_iv:
                break;
        }
    }

    @Override
    public void collectSuccess(boolean isSuccess, boolean isCollect) {
        if (isCollect){
            if (mCountCollect >= 1){
                mPraiseTv.setText(String.valueOf(--mCountCollect + "赞"));
            }else {
                mPraiseTv.setText(String.valueOf( 0 + "赞"));
            }
            mPraiseIv.setImageResource(R.drawable.praise_def);
            mMNewsInfo.setIsCollect(0);
        }else {
            mPraiseTv.setText(String.valueOf(++mCountCollect + "赞"));
            mPraiseIv.setImageResource(R.drawable.praise_sel);
            mMNewsInfo.setIsCollect(1);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        if (view.getId() == R.id.item_praise_iv){
            boolean isPraise = "1".equals(mEveryTalkDetailAdapter.getData().get(position).getIsPraise()+"");
            mPresenter.praiseRecord(mList.get(position).getGuestbook_id(),position,isPraise);
        }
    }

    @Override
    public void praiseSuccess(boolean isSuccess, int position, boolean isPraise) {
        if (isPraise){
            mEveryTalkDetailAdapter.getData().get(position).setIsPraise(0);
            mEveryTalkDetailAdapter.setData(position,mEveryTalkDetailAdapter.getData().get(position));
        }else {
            mEveryTalkDetailAdapter.getData().get(position).setIsPraise(1);
            mEveryTalkDetailAdapter.setData(position,mEveryTalkDetailAdapter.getData().get(position));
        }
    }
}
