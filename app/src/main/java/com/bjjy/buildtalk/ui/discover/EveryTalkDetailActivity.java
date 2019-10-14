package com.bjjy.buildtalk.ui.discover;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.EveryTalkDetailAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.GuestBookEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;
import com.bjjy.buildtalk.ui.circle.CourseDetailActivity;
import com.bjjy.buildtalk.ui.main.LoginActivity;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.GlideUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.CircleProgressView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import retrofit2.http.Url;

public class EveryTalkDetailActivity extends BaseActivity<EveryTalkDetailPresenter> implements EveryTalkDetailContract.View, BaseQuickAdapter.OnItemChildClickListener, OnLoadMoreListener, Player.EventListener {

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
    @BindView(R.id.loadmore_layout)
    SmartRefreshLayout mLoadMoreLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.record_ll)
    LinearLayout mRecordLl;
    @BindView(R.id.is_play)
    View misPlay;
    @BindView(R.id.progress_bar)
    CircleProgressView mProgressBar;
    @BindView(R.id.emptyView)
    NestedScrollView mEmptyView;
    @BindView(R.id.play_content_ll)
    LinearLayout mPlayContentLl;

    private SimpleExoPlayer simpleExoPlayer;
    private EveryTalkDetailAdapter mEveryTalkDetailAdapter;
    private List<GuestBookEntity.GuestbookInfoBean> mList = new ArrayList<>();
    private String mArticle_id;
    private EveryTalkDetailEntity.NewsInfoBean mMNewsInfo;
    private int mCountCollect = 0;
    private int page = 1;
    private Spanned mText;
    private boolean isGone = false;
    private int mPage_count = 1;
    private String mType;
    private String mUrl;
    private String mEndUrl;
    private BaseDialog mBuyDialog;
    private IWXAPI wxapi;
    private long curTime = 0;
    private boolean isPause = false;
    private boolean isEnd = false;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!isDestroyed()){
                Glide.with(EveryTalkDetailActivity.this).load(mBitmap).into(mVideoplayer.thumbImageView);
            }
            return true;
        }
    });
    private Bitmap mBitmap;
    private float mAllTime;
    private float mSize;
    private CountDownTimer mCountDownTimer;
    private boolean b = true;
    private ExtractorMediaSource.Factory mFactory;
    private Uri mUri;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_every_talk_detail;
    }

    @Override
    protected void initView() {
        mArticle_id = getIntent().getStringExtra("article_id");
        mType = getIntent().getStringExtra("type");
        StatusBarUtils.changeStatusBar(this, true, true);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mIncludeToolbar.getLayoutParams());
        lp.setMargins(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        mIncludeToolbar.setLayoutParams(lp);
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEveryTalkDetailAdapter = new EveryTalkDetailAdapter(R.layout.adapter_every_talk_detail, mList);
        mRecyclerView.setAdapter(mEveryTalkDetailAdapter);
        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mEveryTalkDetailAdapter.setOnItemChildClickListener(this);
        KeyboardUtils.registerSoftInputChangedListener(this, height -> {
            if (height <= 0){
                mRecordLl.setVisibility(View.VISIBLE);
                isGone = false;
            }
        });
        mLoadMoreLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.everyTalkDetail(mArticle_id);
        mPresenter.guestbook(mArticle_id, page);
    }

    @Override
    public void handlerTalkDetail(EveryTalkDetailEntity everyTalkDetailEntity) {
        mMNewsInfo = everyTalkDetailEntity.getNewsInfo();
        if (TextUtils.isEmpty(mType)){
            mToolbarTitle.setText("每日一谈");
        }else {
            mToolbarTitle.setText(mMNewsInfo.getArticle_title());
        }
        mTitleTv.setText(mMNewsInfo.getArticle_title());
        Glide.with(this).load(mMNewsInfo.getAuthor_pic()).apply(new RequestOptions().error(R.drawable.moren_face)).into(mFaceIv);
        mNameTv.setText(mMNewsInfo.getAuthor_name());
        mTimeTv.setText(mMNewsInfo.getPublish_time());
        mMediaNameTv.setText(mMNewsInfo.getArticle_title());
        if (!TextUtils.isEmpty(mMNewsInfo.getAudio_url())) {
            mMediaRl.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mMNewsInfo.getAudio_duration())){
                mSize = Float.parseFloat(mMNewsInfo.getAudio_duration());
                float parseLong = Float.parseFloat(mMNewsInfo.getAudio_duration());
                mAllTime = parseLong * 1000;

                mMediaTimeTv.setText(TimeUtils.getMinuteBySecond((int) mSize));

                mMediaSizeTv.setText(mMNewsInfo.getAudio_size() + "M");
                mPlayContentLl.setVisibility(View.VISIBLE);
            }else {
                mPlayContentLl.setVisibility(View.INVISIBLE);
            }

            Glide.with(this).load(R.drawable.playing).into(mPlayingIv);
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
            DefaultDataSourceFactory defaultDataSourceFactory =
                    new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));
            mFactory = new ExtractorMediaSource.Factory(defaultDataSourceFactory);
            mUri = Uri.parse(mMNewsInfo.getAudio_url());
            simpleExoPlayer.prepare(mFactory.createMediaSource(mUri));
            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.addListener(this);
        }
        if (!TextUtils.isEmpty(mMNewsInfo.getVideo_url())) {
            mFlVideoPlayer.setVisibility(View.VISIBLE);
            new Thread(() -> {
                mBitmap = GlideUtils.loadVideoScreenshot(mMNewsInfo.getVideo_url());
                if (mBitmap != null){
                    handler.sendEmptyMessage(1);
                }
            }).start();
            mVideoplayer.setUp(new JZDataSource(mMNewsInfo.getVideo_url()), Jzvd.SCREEN_WINDOW_NORMAL);
            if (!mPresenter.mDataManager.getLoginStatus() || mMNewsInfo.getIs_buy() == 0){
                misPlay.setVisibility(View.VISIBLE);
            }else {
                misPlay.setVisibility(View.GONE);
            }
            misPlay.setOnClickListener(v -> {
                if (!mPresenter.mDataManager.getLoginStatus()){
                    startActivity(new Intent(EveryTalkDetailActivity.this, LoginActivity.class));
                }else if (mMNewsInfo.getIs_buy() == 0){
                    showBuyDialog();
                }
            });
        }

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.loadData(getHtmlData(mMNewsInfo.getContent()), "text/html;charset=utf-8","utf-8");

        if ("0".equals(String.valueOf(mMNewsInfo.getIsCollect()))){
            mPraiseIv.setImageResource(R.drawable.praise_def);
        }else {
            mPraiseIv.setImageResource(R.drawable.praise_sel);
        }
        mCountCollect = mMNewsInfo.getCountCollect();
        mPraiseTv.setText( mCountCollect + "赞");
        mRecordEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND){
                if (TextUtils.isEmpty(mRecordEt.getText().toString().trim())){
                    ToastUtils.showShort("请输入评论内容");
                    return false;
                }
                mPresenter.saveRecord(mMNewsInfo.getArticle_id(),mRecordEt.getText().toString().trim());
                mRecordEt.clearFocus();
                mRecordEt.getText().clear();
                mRecordLl.setVisibility(View.VISIBLE);
                isGone = !isGone;
                KeyboardUtils.hideSoftInput(this);
            }
            return false;
        });
    }

    private void initCountDownTimer(long millisInFuture) {
        //倒计时显示操作
        // 进度条实现更新操作
        mCountDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                curTime = millisUntilFinished;
                //倒计时显示操作
                float second = curTime / 1000;
                mMediaTimeTv.setText(TimeUtils.getMinuteBySecond((int) (millisUntilFinished / 1000)));
                // 进度条实现更新操作
                second = (mAllTime/1000 - second) / (mAllTime/1000) * 100;
                mProgressBar.setCurrent((int) second);
            }

            @Override
            public void onFinish() {
                mProgressBar.setCurrent(0);
                curTime = 0;
                mMediaTimeTv.setText(TimeUtils.getMinuteBySecond((int) mSize));
            }
        };
    }


    private void showBuyDialog() {
        mBuyDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_buy_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mBuyDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    mPresenter.payOrder("1", mMNewsInfo.getArticle_id(), mMNewsInfo.getArticle_title(),
                            mMNewsInfo.getArticle_price());
                    mBuyDialog.dismiss();
                })
                .builder();
        mBuyDialog.show();
    }

    @Override
    public void handlerWxOrder(PayOrderEntity payOrderEntity) {
        if (wxapi == null) {
            wxapi = WXAPIFactory.createWXAPI(this, "wx24a51a57c203d22a", false);
        }
        PayReq request = new PayReq();
        request.appId = payOrderEntity.getAppid();
        request.partnerId = payOrderEntity.getPartnerid();
        request.prepayId = payOrderEntity.getPrepayid();
        request.packageValue = payOrderEntity.getPackageX();
        request.nonceStr = payOrderEntity.getNoncestr();
        request.timeStamp = payOrderEntity.getTimestamp();
        request.sign = payOrderEntity.getSign();
        wxapi.sendReq(request);
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:15px;} body{word-wrap:break-word;font-size:15px;padding:0px;margin:0px;line-height:2.0;} p{padding:0px;margin:0px;font-size:15px;color:FF656565;line-height:2.0;} img{padding:0px,margin:0px;max-width:100%; width:100%; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + ""+ bodyHTML + "</body></html>";
    }

    @Override
    public void handlerGuestBookList(GuestBookEntity guestBookEntity) {
        if (guestBookEntity.getGuestbookInfo().size() > 0){
            mList.addAll(guestBookEntity.getGuestbookInfo());
            mPage_count = guestBookEntity.getPage_count();
            mEveryTalkDetailAdapter.notifyDataSetChanged();
            mRecordNumTv.setText(guestBookEntity.getCountGuestbookNum());
            mEmptyView.setVisibility(View.GONE);
            mLoadMoreLayout.setEnableLoadMore(true);
            mRecyclerView.setVisibility(View.VISIBLE);
        }else {
            mRecyclerView.setVisibility(View.GONE);
            mLoadMoreLayout.setEnableLoadMore(false);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void handlerSaveRecord() {
        mPresenter.guestbook(mArticle_id, 1);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCountDownTimer != null){
            mCountDownTimer.cancel();
        }
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeyboardUtils.unregisterSoftInputChangedListener(this);
        KeyboardUtils.hideSoftInput(this);
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playWhenReady) {
            LogUtils.e(playbackState);
            if (curTime == 0 && playbackState == PlaybackState.STATE_PLAYING){
                initCountDownTimer((long)mAllTime);
                mCountDownTimer.start();
            }
            if (curTime != 0 && isPause) {
                //将上次当前剩余时间作为新的时长
                initCountDownTimer(curTime);
                mCountDownTimer.start();
            }
            mPlayIv.setImageResource(R.drawable.media_stop_icon);
            mPlayingIv.setVisibility(View.VISIBLE);
            if (playbackState == Player.STATE_ENDED) {
                simpleExoPlayer.prepare(mFactory.createMediaSource(mUri));
                simpleExoPlayer.setPlayWhenReady(!playWhenReady);
            }
            isPause = false;
        } else {
            if (!b && mCountDownTimer != null && playbackState == PlaybackState.STATE_PLAYING){
                mCountDownTimer.cancel();
            }
            mPlayIv.setImageResource(R.drawable.media_play_icon);
            mPlayingIv.setVisibility(View.INVISIBLE);
            if (b){
                b = false;
                return;
            }
            isPause = true;
        }
    }

    @OnClick({R.id.play_iv, R.id.praise_ll, R.id.share_iv, R.id.record_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_iv:
                LoginHelper.login(this, mPresenter.mDataManager, () -> {
                    if (mMNewsInfo.getIs_buy() == 0 && !TextUtils.isEmpty(mType)){
                        showBuyDialog();
                        return;
                    }
                    if (simpleExoPlayer != null) {
                        simpleExoPlayer.setPlayWhenReady(!simpleExoPlayer.getPlayWhenReady());
                    }
                });
                break;
            case R.id.praise_ll:
                LoginHelper.login(this, mPresenter.mDataManager, () -> {
                    boolean isCollect = "1".equals(String.valueOf(mMNewsInfo.getIsCollect()));
                    mPresenter.collectArticle(mArticle_id,isCollect);
                });
                break;
            case R.id.share_iv:
                if (TextUtils.isEmpty(mType)){
                    mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" + mPresenter.mDataManager.getUser().getUser_id() + "&news_id=" + mArticle_id;
                    mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) + "&response_type=code&scope=snsapi_userinfo&state=news#wechat_redirect";
                    DialogUtils.showShareDialog(this, mEndUrl, mEndUrl, mMNewsInfo.getArticle_title(), mMNewsInfo.getAuthor_pic(), "每日一谈", false);
                }else {
                    mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" + mPresenter.mDataManager.getUser().getUser_id() + "&article_id=" + mArticle_id;
                    mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) + "&response_type=code&scope=snsapi_userinfo&state=articlePay#wechat_redirect";
                    DialogUtils.showShareDialog(this, mEndUrl, mEndUrl, mMNewsInfo.getArticle_title(), mMNewsInfo.getAuthor_pic(), mMNewsInfo.getArticle_title(),false);
                }
                break;
            case R.id.record_ll:
                LoginHelper.login(this, mPresenter.mDataManager, () -> {
                    if (!isGone){
                        mRecordLl.setVisibility(View.GONE);
                        mRecordEt.setFocusable(true);
                        mRecordEt.setFocusableInTouchMode(true);
                        mRecordEt.requestFocus();
                        KeyboardUtils.showSoftInput(EveryTalkDetailActivity.this);
                        isGone = !isGone;
                    }
                });
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
        if (view.getId() == R.id.item_parise_ll){
            LoginHelper.login(this, mPresenter.mDataManager, () -> {
                boolean isPraise = "1".equals(mEveryTalkDetailAdapter.getData().get(position).getIsPraise()+"");
                mPresenter.praiseRecord(mList.get(position).getGuestbook_id(),position,isPraise);
            });
        }
    }

    @Override
    public void praiseSuccess(boolean isSuccess, int position, boolean isPraise) {
        int countpraise = mList.get(position).getCountpraise();
        if (isPraise){
            mEveryTalkDetailAdapter.getData().get(position).setIsPraise(0);
            if (countpraise > 0){
                mList.get(position).setCountpraise(--countpraise);
            }
            mEveryTalkDetailAdapter.setData(position,mEveryTalkDetailAdapter.getData().get(position));
        }else {
            mEveryTalkDetailAdapter.getData().get(position).setIsPraise(1);
            mList.get(position).setCountpraise(++countpraise);
            mEveryTalkDetailAdapter.setData(position,mEveryTalkDetailAdapter.getData().get(position));
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count){
            page++;
            mPresenter.guestbook(mArticle_id, page);
            refreshLayout.finishLoadMore();
        }else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }
}
