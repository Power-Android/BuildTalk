package com.bjjy.buildtalk.ui.discover;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.EveryTalkDetailAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.PayEvent;
import com.bjjy.buildtalk.core.event.PlayerEvent;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.GuestBookEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;
import com.bjjy.buildtalk.entity.SongsEntity;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.bjjy.buildtalk.ui.main.LoginActivity;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.GlideUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.PlayerHelper;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.CircleProgressView;
import com.bjjy.buildtalk.weight.player.PlayerWindowManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class EveryTalkDetailActivity extends BaseActivity<EveryTalkDetailPresenter> implements
        EveryTalkDetailContract.View, BaseQuickAdapter.OnItemChildClickListener, OnLoadMoreListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
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

    private EveryTalkDetailAdapter mEveryTalkDetailAdapter;
    private List<GuestBookEntity.GuestbookInfoBean> mList = new ArrayList<>();
    private String mArticle_id;
    private EveryTalkDetailEntity.NewsInfoBean mNewsInfo;
    private int mCountCollect = 0;
    private int page = 1;
    private boolean isGone = false;
    private int mPage_count = 1;
    private String mType;
    private String mUrl;
    private String mEndUrl;
    private BaseDialog mBuyDialog;
    private IWXAPI wxapi;
    private BaseDialog mDeleteDialog;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!isDestroyed()) {
                Glide.with(EveryTalkDetailActivity.this).load(mBitmap).into(mVideoplayer.thumbImageView);
            }
            return true;
        }
    });
    private Bitmap mBitmap;
    private String mType_zhuanti;
    private String mCountGuestbookNum;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean isPlaying;
    private int mSort;

    BottomSheetDialog mBottomSheetDialog;
    BottomSheetBehavior mBehavior;
    private View mView;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PlayerEvent eventBean) {
        updatePlayStatus();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PayEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.PAY_SUCCESS)) {
            initEventAndData();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_every_talk_detail;
    }

    @Override
    protected void onResume() {
        setIsMargin(true);
        super.onResume();
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mArticle_id = getIntent().getStringExtra("article_id");
        mType = getIntent().getStringExtra("type");
        mType_zhuanti = getIntent().getStringExtra("type_zhuanti");
        mSort = getIntent().getIntExtra("sort", 1);
        StatusBarUtils.changeStatusBar(this, true, true);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mIncludeToolbar.getLayoutParams());
        lp.setMargins(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        mIncludeToolbar.setLayoutParams(lp);
        mToolbarBack.setOnClickListener(v -> finish());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEveryTalkDetailAdapter = new EveryTalkDetailAdapter(R.layout.adapter_every_talk_detail, mList);
        mRecyclerView.setAdapter(mEveryTalkDetailAdapter);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mEveryTalkDetailAdapter.setOnItemChildClickListener(this);
        KeyboardUtils.registerSoftInputChangedListener(this, height -> {
            if (height <= 0) {
                mRecordLl.setVisibility(View.VISIBLE);
                isGone = false;
            }
        });
        mLoadMoreLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.everyTalkDetail(mArticle_id, mType_zhuanti);
        mPresenter.guestbook(mArticle_id, page, false);
    }

    @Override
    public void handlerTalkDetail(EveryTalkDetailEntity everyTalkDetailEntity) {
        mNewsInfo = everyTalkDetailEntity.getNewsInfo();
        if (TextUtils.isEmpty(mType)) {
            mToolbarTitle.setText("每日一谈");
        } else {
            mToolbarTitle.setText(mNewsInfo.getArticle_title());
        }
        mTitleTv.setText(mNewsInfo.getArticle_title());
        Glide.with(this).load(mNewsInfo.getAuthor_pic()).apply(new RequestOptions().error(R.drawable.moren_face)).into(mFaceIv);
        mNameTv.setText(mNewsInfo.getAuthor_name());
        mTimeTv.setText(TimeUtils.getFriendlyTimeSpanByNow(mNewsInfo.getPublish_time()));
        mMediaNameTv.setText(mNewsInfo.getArticle_title());
        if (!TextUtils.isEmpty(mNewsInfo.getAudio_url())) {
            mMediaRl.setVisibility(View.VISIBLE);
            mMediaSizeTv.setText(String.format("%sM", mNewsInfo.getAudio_size()));
            Glide.with(this).load(R.drawable.playing).into(mPlayingIv);
            try {
                mediaPlayer.setDataSource(mNewsInfo.getAudio_url());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnPreparedListener(mp -> mMediaTimeTv.setText(TimeUtils.stringForTime(mp.getDuration())));
            mediaPlayer.prepareAsync();
        }
        if (!TextUtils.isEmpty(mNewsInfo.getVideo_url())) {
            mFlVideoPlayer.setVisibility(View.VISIBLE);
            new Thread(() -> {
                mBitmap = GlideUtils.loadVideoScreenshot(mNewsInfo.getVideo_url());
                if (mBitmap != null) {
                    handler.sendEmptyMessage(1);
                }
            }).start();
            mVideoplayer.setUp(new JZDataSource(mNewsInfo.getVideo_url()), Jzvd.SCREEN_WINDOW_NORMAL);
            if (!mPresenter.mDataManager.getLoginStatus() || mNewsInfo.getIs_buy() == 0) {
                misPlay.setVisibility(View.VISIBLE);
            } else {
                misPlay.setVisibility(View.GONE);
            }
            misPlay.setOnClickListener(v -> {
                if (!mPresenter.mDataManager.getLoginStatus()) {
                    startActivity(new Intent(EveryTalkDetailActivity.this, LoginActivity.class));
                } else if (mNewsInfo.getIs_buy() == 0) {
                    showBuyDialog();
                }
            });
        }

        if (!TextUtils.isEmpty(mNewsInfo.getContent())) {
            WebSettings settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            mWebView.loadData(getHtmlData(mNewsInfo.getContent()), "text/html;charset=utf-8", "utf-8");
        }

        if ("0".equals(String.valueOf(mNewsInfo.getIsCollect()))) {
            mPraiseIv.setImageResource(R.drawable.praise_def);
        } else {
            mPraiseIv.setImageResource(R.drawable.praise_sel);
        }
        mCountCollect = mNewsInfo.getCountCollect();
        mPraiseTv.setText(String.format("%d赞", mCountCollect));
        mRecordEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (TextUtils.isEmpty(mRecordEt.getText().toString().trim())) {
                    ToastUtils.showShort("请输入评论内容");
                    return false;
                }
                mPresenter.saveRecord(mNewsInfo.getArticle_id(), mRecordEt.getText().toString().trim(), mType_zhuanti);
                mRecordEt.clearFocus();
                mRecordEt.getText().clear();
                mRecordLl.setVisibility(View.VISIBLE);
                isGone = !isGone;
                KeyboardUtils.hideSoftInput(this);
            }
            return false;
        });
    }

    private void updatePlayStatus() {
        if (getPlayerWindowManager().getBinder().isPlaying() && getPlayerWindowManager().getSongId().equals(mArticle_id)){
            mPlayingIv.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.media_stop_icon).into(mPlayIv);
        }else {
            mPlayingIv.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.media_play_icon).into(mPlayIv);
        }
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
                    mPresenter.payOrder("1", mNewsInfo.getArticle_id(), mNewsInfo.getArticle_title(),
                            mNewsInfo.getArticle_price());
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
        return "<html>" + head + "<body>" + "" + bodyHTML + "</body></html>";
    }

    @Override
    public void handlerGuestBookList(GuestBookEntity guestBookEntity, boolean isRefresh) {
        if (guestBookEntity.getGuestbookInfo().size() > 0) {
            mPage_count = guestBookEntity.getPage_count();
            if (isRefresh) {
                mEveryTalkDetailAdapter.setNewData(guestBookEntity.getGuestbookInfo());
            } else {
                mEveryTalkDetailAdapter.addData(guestBookEntity.getGuestbookInfo());
            }
            mCountGuestbookNum = guestBookEntity.getCountGuestbookNum();
            mRecordNumTv.setText(mCountGuestbookNum);
            mEmptyView.setVisibility(View.GONE);
            mLoadMoreLayout.setEnableLoadMore(true);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mLoadMoreLayout.setEnableLoadMore(false);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void handlerSaveRecord() {
        mPresenter.guestbook(mArticle_id, 1, true);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeyboardUtils.unregisterSoftInputChangedListener(this);
        KeyboardUtils.hideSoftInput(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.play_rl, R.id.praise_ll, R.id.share_iv, R.id.record_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_rl:
                LoginHelper.getInstance().login(this, mPresenter.mDataManager, () -> {
                    if (mNewsInfo.getIs_buy() == 0 && !TextUtils.isEmpty(mType)) {
                        showBuyDialog();
                        return;
                    }
                    if (mSort == 1){
                        if (!mPresenter.mDataManager.getIsShowPlayer() | PlayerHelper.querySongs(mArticle_id, mPresenter.mDataManager) == null){
                            showPlayer(mArticle_id);
                        }else {
                            if (TextUtils.equals(mArticle_id, getPlayerWindowManager().getSongId())){
                                if (getPlayerWindowManager().getBinder().isPlaying()){
                                    getPlayerWindowManager().getBinder().pause();
                                }else {
                                    getPlayerWindowManager().getBinder().resume();
                                }
                            }else {
                                showPlayer(mArticle_id);
                            }
                        }
                    }else {
                        mPresenter.requestSongs(mArticle_id);
                    }
                });
                break;
            case R.id.praise_ll:
                LoginHelper.getInstance().login(this, mPresenter.mDataManager, () -> {
                    boolean isCollect = "1".equals(String.valueOf(mNewsInfo.getIsCollect()));
                    mPresenter.collectArticle(mArticle_id, isCollect, mType_zhuanti);
                });
                break;
            case R.id.share_iv:
                if (TextUtils.isEmpty(mType)) {
                    mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" + mPresenter.mDataManager.getUser().getUser_id() + "&news_id=" + mArticle_id;
                    mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) + "&response_type=code&scope=snsapi_userinfo&state=news#wechat_redirect";
                    showShareDialog(mEndUrl, mEndUrl, mNewsInfo.getArticle_title(), mNewsInfo.getAuthor_pic(), "每日一谈", false, false);
                } else {
                    mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" + mPresenter.mDataManager.getUser().getUser_id() + "&article_id=" + mArticle_id;
                    mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) + "&response_type=code&scope=snsapi_userinfo&state=articlePay#wechat_redirect";
                    showShareDialog(mEndUrl, mEndUrl, mNewsInfo.getArticle_title(), mNewsInfo.getAuthor_pic(), mNewsInfo.getArticle_title(), false, false);
                }
                break;
            case R.id.record_ll:
                LoginHelper.getInstance().login(this, mPresenter.mDataManager, () -> {
                    if (!isGone) {
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
    public void handlerSongs() {
        if (!mPresenter.mDataManager.getIsShowPlayer() | PlayerHelper.querySongs(mArticle_id, mPresenter.mDataManager) == null){
            showPlayer(mArticle_id);
        }else {
            if (TextUtils.equals(mArticle_id, getPlayerWindowManager().getSongId())){
                if (getPlayerWindowManager().getBinder().isPlaying()){
                    getPlayerWindowManager().getBinder().pause();
                }else {
                    getPlayerWindowManager().getBinder().resume();
                }
            }else {
                showPlayer(mArticle_id);
            }
        }
    }

    @Override
    public void collectSuccess(boolean isSuccess, boolean isCollect) {
        if (isCollect) {
            if (mCountCollect >= 1) {
                mPraiseTv.setText(--mCountCollect + "赞");
            } else {
                mPraiseTv.setText(0 + "赞");
            }
            mPraiseIv.setImageResource(R.drawable.praise_def);
            mNewsInfo.setIsCollect(0);
        } else {
            mPraiseTv.setText(++mCountCollect + "赞");
            mPraiseIv.setImageResource(R.drawable.praise_sel);
            mNewsInfo.setIsCollect(1);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        List<GuestBookEntity.GuestbookInfoBean> data = mEveryTalkDetailAdapter.getData();
        switch (view.getId()) {
            case R.id.item_praise_ll:
                LoginHelper.getInstance().login(this, mPresenter.mDataManager, () -> {
                    boolean isPraise = "1".equals(mEveryTalkDetailAdapter.getData().get(position).getIsPraise() + "");
                    mPresenter.praiseRecord(mType_zhuanti, data.get(position).getGuestbook_id(), position, isPraise);
                });
                break;
            case R.id.item_delete_iv:
                showDeleteDialog(data, position);
                break;
        }
    }

    private void showDeleteDialog(List<GuestBookEntity.GuestbookInfoBean> mComment_content, int i) {
        mDeleteDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mDeleteDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    mPresenter.deleteComment(mType_zhuanti, mComment_content.get(i).getGuestbook_id() + "", mComment_content, i);
                    mDeleteDialog.dismiss();
                })
                .builder();
        TextView textView = mDeleteDialog.getView(R.id.text);
        textView.setText("确定删除此条评论？");
        mDeleteDialog.show();
    }

    @Override
    public void handlerDeleteComment(List<GuestBookEntity.GuestbookInfoBean> mComment_content, int i) {
        mComment_content.remove(i);
        mEveryTalkDetailAdapter.notifyItemRemoved(i);
        int count = Integer.parseInt(mCountGuestbookNum);
        if (count > 0) {
            count--;
            mCountGuestbookNum = String.valueOf(count);
            mRecordNumTv.setText(count + "");
        }
    }

    @Override
    public void praiseSuccess(boolean isSuccess, int position, boolean isPraise) {
        List<GuestBookEntity.GuestbookInfoBean> data = mEveryTalkDetailAdapter.getData();
        int countpraise = data.get(position).getCountpraise();
        if (isPraise) {
            mEveryTalkDetailAdapter.getData().get(position).setIsPraise(0);
            if (countpraise > 0) {
                data.get(position).setCountpraise(--countpraise);
            }
            mEveryTalkDetailAdapter.setData(position, mEveryTalkDetailAdapter.getData().get(position));
        } else {
            mEveryTalkDetailAdapter.getData().get(position).setIsPraise(1);
            data.get(position).setCountpraise(++countpraise);
            mEveryTalkDetailAdapter.setData(position, mEveryTalkDetailAdapter.getData().get(position));
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            mPresenter.guestbook(mArticle_id, page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    private void showShareDialog(String url, String weburl, String title, String imgUrl, String desc,
                                 boolean isSmall, boolean isVisible){
        if (mBottomSheetDialog == null) {
            mBottomSheetDialog = new BottomSheetDialog(this, R.style.bottom_sheet_dialog);
            mBottomSheetDialog.getWindow().getAttributes().windowAnimations =
                    R.style.bottom_sheet_dialog;
            mBottomSheetDialog.setCancelable(true);
            mBottomSheetDialog.setCanceledOnTouchOutside(true);
            mView = getLayoutInflater().inflate(R.layout.dialog_share_layout, null);
            mBottomSheetDialog.setContentView(mView);
            mBehavior = BottomSheetBehavior.from((View) mView.getParent());
            mBehavior.setSkipCollapsed(true);
//            int peekHeight = getResources().getDisplayMetrics().heightPixels;
            //设置默认弹出高度为屏幕的0.4倍
//            mBehavior.setPeekHeight((int)(0.4 * peekHeight));
        }
        mView.findViewById(R.id.discover_tv).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mBottomSheetDialog.show();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mView.findViewById(R.id.wechat_tv).setOnClickListener(v -> {
            if (isSmall) {
                DialogUtils.shareSmallProgram(url, imgUrl, title, desc, EveryTalkDetailActivity.this, SHARE_MEDIA.WEIXIN);
            } else {
                DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, EveryTalkDetailActivity.this, SHARE_MEDIA.WEIXIN);
            }
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.wechat_circle_tv).setOnClickListener(v -> {
            DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, EveryTalkDetailActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.discover_tv).setOnClickListener(v -> mBottomSheetDialog.dismiss());
        mView.findViewById(R.id.cancle_tv).setOnClickListener(v -> mBottomSheetDialog.dismiss());
    }
}
