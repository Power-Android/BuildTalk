package com.bjjy.buildtalk.ui.video;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CommentAdapter;
import com.bjjy.buildtalk.adapter.DetailCommentAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ShortVideoEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.ui.talk.CircleManDetailActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.SizeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.recycler.OnPagerListener;
import com.bjjy.buildtalk.weight.recycler.PagerLayoutManager;
import com.bjjy.buildtalk.weight.recycler.ShortVideoAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_BEGIN;
import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_PROGRESS;
import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED;
import static com.tencent.rtmp.TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;

public class ShortVideoActivity extends BaseActivity<ShortVideoPresenter> implements
        ShortVideoContract.View, OnPagerListener, BaseQuickAdapter.OnItemChildClickListener, CommentAdapter.onCommentItemlistener {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.more_iv)
    ImageView mMoreIv;

    private List<ShortVideoEntity.ThemeInfoBean> mList = new ArrayList<>();
    private int page = 1, commentPage = 1;
    private ShortVideoAdapter mVideoAdapter;
    private TXVodPlayer mVideoPlayer;
    private TXCloudVideoView mVideoView;
    private ImageView mCoverIv;
    private int pagePosition = 0;//记录滑动后的position（包括滑动后换页和滑动后没有换页）
    private ProgressBar mProgressBar;
    private ImageView mPlayIv;
    private BottomSheetDialog mCommentDialog;
    private View mCommentView;
    private BottomSheetBehavior mCommentBehavior;
    private CommentAdapter mCommentAdapter;
    private BaseDialog mInputDialog, mDeleteDialog;
    private TextView mCommentTv;
    private int mCountCommentNum;
    private TextView mShowSendTv;
    private String mTheme_id;
    private int mComment_id = 0;
    private String mParentCommentId = "";
    private int mPosition;
    BottomSheetDialog mBottomSheetDialog;
    BottomSheetBehavior mBehavior;
    private View mView;
    private Intent mIntent;

    @Override
    protected int getLayoutId() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_short_video;
    }

    @Override
    protected void initView() {
        String type_id = getIntent().getStringExtra("type_id");
        mTheme_id = getIntent().getStringExtra("theme_id");
        String user_id = getIntent().getStringExtra("user_id");

        mPresenter.getVideoList(type_id, mTheme_id, user_id, page);

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
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        pagerLayoutManager.setOnViewPagerListener(this);
        mVideoAdapter.setOnItemChildClickListener(this);
        mVideoAdapter.setOnLoadMoreListener(() -> {
            LogUtils.e("触发加载更多监听~");
            mPresenter.getVideoList(type_id, mTheme_id, user_id, ++page);
        }, mRecyclerView);
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void handlerVideoSuccess(ShortVideoEntity shortVideoEntity) {
        if (page == 1) {
            mVideoAdapter.setNewData(shortVideoEntity.getThemeInfo());
        } else {
            if (shortVideoEntity != null && shortVideoEntity.getThemeInfo().size() > 0) {
                mVideoAdapter.addData(shortVideoEntity.getThemeInfo());
                mVideoAdapter.loadMoreComplete();
            } else {
                ToastUtils.showShort("没有更多数据了...");
                mVideoAdapter.loadMoreEnd();
            }
        }
    }

    @OnClick({R.id.back_iv, R.id.more_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.more_iv:
                showShareDialog("","",mVideoAdapter.getData().get(mPosition).getTheme_content(),
                        mVideoAdapter.getData().get(mPosition).getParent_themeInfo().getTheme_video().get(0).getCoverURL(),
                        mVideoAdapter.getData().get(mPosition).getTheme_content(),true,false,
                        mVideoAdapter.getData().get(mPosition).getTheme_id(),mVideoAdapter.getData().get(mPosition).getCircle_id()+"");
                break;
        }
    }

    @Override
    public void onInitComplete() {
        if (mVideoPlayer == null){//adapter刷新时会重走init方法，判断一下 防止被重新初始化
            LogUtils.e("OnPagerListener---onInitComplete--" + "初始化完成");
            loadVideoInfo(0, true);
        }
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
        mPosition = position;
        loadVideoInfo(position, false);
        if (isBottom && position != 0) {//如果是最后一个视频时，滑动view时会一直调用此方法，因此做个判断
            ToastUtils.showShort("没有更多视频了...");
        }
        pagePosition = position;
    }

    private void loadVideoInfo(int position, boolean isFirst) {
        if (isFirst) {//第一次进来
            videoInfo(position);
        } else {
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
     *
     * @param position
     */
    private void videoInfo(int position) {
        List<ShortVideoEntity.ThemeInfoBean.ParentThemeInfoBean.ThemeVideoBean> theme_video =
                mVideoAdapter.getData().get(position).getParent_themeInfo().getTheme_video();
        //上传浏览记录
        mPresenter.videoBrowse(mVideoAdapter.getData().get(position).getTheme_id());

        mVideoView = (TXCloudVideoView) mVideoAdapter
                .getViewByPosition(mRecyclerView, position, R.id.video_view);
        mCoverIv = (ImageView) mVideoAdapter
                .getViewByPosition(mRecyclerView, position, R.id.cover_iv);
        mPlayIv = (ImageView) mVideoAdapter.getViewByPosition(mRecyclerView, position, R.id.play_iv);
        mProgressBar = (ProgressBar) mVideoAdapter.getViewByPosition(mRecyclerView, position, R.id.progress_bar_h);
        mVideoView.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        mVideoPlayer = new TXVodPlayer(this);
        mVideoPlayer.setPlayerView(mVideoView);
        mVideoPlayer.setRenderMode(RENDER_MODE_ADJUST_RESOLUTION);
        mVideoPlayer.setLoop(true);
        mVideoPlayer.setAutoPlay(true);
        if (!TextUtils.isEmpty(theme_video.get(0).getVideo_url())) {
            mVideoPlayer.startPlay(mVideoAdapter.getData().get(position).getParent_themeInfo()
                    .getTheme_video().get(0).getVideo_url());
            LogUtils.e("adapter-" + position + "=视频地址：" + theme_video.get(0).getVideo_url());
        } else {
            ToastUtils.showShort("视频不见了...");
        }

        mVideoPlayer.setVodListener(new ITXVodPlayListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle param) {
                switch (event) {
                    case PLAY_EVT_VOD_PLAY_PREPARED:
                        mVideoView.setVisibility(View.VISIBLE);
                        break;
                    case PLAY_EVT_PLAY_BEGIN://视频准备开始
                        break;
                    case PLAY_EVT_PLAY_PROGRESS://加载和播放进度-秒
                        // 加载进度, 单位是毫秒
                        int duration = param.getInt(TXLiveConstants.EVT_PLAYABLE_DURATION_MS);
                        // 播放进度, 单位是毫秒
                        int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS);
                        // 视频总长, 单位是毫秒 可以用于设置时长显示等等
                        int totalDuration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION_MS);
                        int pro = progress * 100 / totalDuration;
                        if (mProgressBar != null)
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
            mProgressBar.setProgress(0);
            mCoverIv = null;
            mPlayIv = null;
            mProgressBar = null;
            mVideoView = null;

        }
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<ShortVideoEntity.ThemeInfoBean> data = mVideoAdapter.getData();
        switch (view.getId()) {
            case R.id.item_atten_iv://关注
                mPresenter.attenUser(data, position);
                break;
            case R.id.item_praise_tv://点赞
                mPresenter.praise(data, "1", position);
                break;
            case R.id.item_comment_tv://留言
                showCommentDialog(data.get(position).getTheme_id(), commentPage, position);
                break;
            case R.id.item_collect_tv://收藏
                mPresenter.collectTheme(data, position);
                break;
            case R.id.item_share_tv://分享
            case R.id.item_more_iv://更多
                showShareDialog("","",data.get(position).getTheme_content(),
                        data.get(position).getParent_themeInfo().getTheme_video().get(0).getCoverURL(),
                        data.get(position).getTheme_content(),true,false,
                        data.get(position).getTheme_id(),data.get(position).getCircle_id()+"");
                break;
            case R.id.item_name_tv://跳转到个人主页
            case R.id.item_face_iv:
                if (data.get(position).getIs_author() == 1) {
                    mIntent = new Intent(this, MasterDetailActivity.class);
                    mIntent.putExtra("user_id", data.get(position).getUser_id() + "");
                    startActivity(mIntent);
                } else {
                    mIntent = new Intent(this, CircleManDetailActivity.class);
                    mIntent.putExtra("user_id", data.get(position).getUser_id() + "");
                    startActivity(mIntent);
                }
                break;
            case R.id.item_from_tv://跳转到根视频圈子
                mIntent = new Intent(this, TopticCircleActivity.class);
                mIntent.putExtra("circle_id", data.get(position).getCircle_id()+"");
                startActivity(mIntent);
                break;
            case R.id.play_iv://继续播放
                mVideoPlayer.resume();
                mPlayIv.setVisibility(View.GONE);
                break;
            case R.id.video_view://暂停
                if (mVideoPlayer.isPlaying()) {
                    mVideoPlayer.pause();
                    mPlayIv.setVisibility(View.VISIBLE);
                } else {
                    mVideoPlayer.resume();
                    mPlayIv.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void handlerCollectSuccess(IEntity iEntity, List<ShortVideoEntity.ThemeInfoBean> data, int position) {
        if (0 == data.get(position).getIs_collect()) {
            data.get(position).setIs_collect(1);
            data.get(position).setCountCollect(data.get(position).getCountCollect() + 1);
            ToastUtils.showCollect("收藏成功", getResources().getDrawable(R.drawable.collect_success_icon));
        } else {
            data.get(position).setIs_collect(0);
            if (data.get(position).getCountCollect() > 0){
                data.get(position).setCountCollect(data.get(position).getCountCollect() - 1);
            }
            ToastUtils.showCollect("取消收藏", getResources().getDrawable(R.drawable.collect_cancle_icon));
        }
        mVideoAdapter.notifyItemChanged(position);
    }

    private void showShareDialog(String url, String weburl, String title, String imgUrl,
                                 String desc, boolean isSmall, boolean isVisible, int theme_id, String circle_id) {
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
                DialogUtils.shareSmallProgram(url, imgUrl, title, desc, ShortVideoActivity.this, SHARE_MEDIA.WEIXIN);
            } else {
                DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, ShortVideoActivity.this, SHARE_MEDIA.WEIXIN);
            }
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.wechat_circle_tv).setOnClickListener(v -> {
            DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, ShortVideoActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.discover_tv).setOnClickListener(v ->{
//            mPresenter.shareTheme(theme_id, "0");
            EventBus.getDefault().post(new RefreshEvent(Constants.VIDEO_REFRESH));
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.cancle_tv).setOnClickListener(v -> mBottomSheetDialog.dismiss());
    }

    @Override
    public void handlerAttentUser(BaseResponse<IEntity> response, List<ShortVideoEntity.ThemeInfoBean> data, int position) {
        if (data.get(position).getIs_parise() == 0) {
            data.get(position).setIs_attention(1);
        } else {
            data.get(position).setIs_attention(0);
        }
        mVideoAdapter.notifyItemChanged(position);
    }

    @Override
    public void handlerPraiseSuccess(List<ShortVideoEntity.ThemeInfoBean> data, int position, PraiseEntity praiseEntity) {
        if (data.get(position).getIs_parise() == 0) {
            data.get(position).setIs_parise(1);
        } else {
            data.get(position).setIs_parise(0);
        }
        data.get(position).setCountParise(praiseEntity.getCountpraise());
        mVideoAdapter.notifyItemChanged(position);
    }

    private void showCommentDialog(int theme_id, int commentPage, int adapterPosition) {
        if (mCommentDialog == null) {
            mCommentDialog = new BottomSheetDialog(this, R.style.bottom_sheet_dialog);
            mCommentDialog.getWindow().getAttributes().windowAnimations =
                    R.style.bottom_sheet_dialog;
            mCommentDialog.setCancelable(true);
            mCommentDialog.setCanceledOnTouchOutside(true);
            mCommentView = getLayoutInflater().inflate(R.layout.dialog_comment_video, null);
            mCommentDialog.setContentView(mCommentView);
            RelativeLayout topLl = mCommentView.findViewById(R.id.top_rl);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) topLl.getLayoutParams();
            lp.setMargins(0, SizeUtils.dp2px(150), 0, 0);
            topLl.setLayoutParams(lp);
            mCommentBehavior = BottomSheetBehavior.from((View) mCommentView.getParent());
            mCommentBehavior.setSkipCollapsed(true);

            List<CommentContentBean> list = new ArrayList<>();
            RecyclerView recyclerView = mCommentView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mCommentAdapter = new CommentAdapter(R.layout.adapter_circle_detail_comment, list);
            recyclerView.setAdapter(mCommentAdapter);
            ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            mCommentAdapter.setOnItemChildClickListener(this);
            mCommentAdapter.setCommentClickListener(this);

            mPresenter.commentList(theme_id, commentPage, adapterPosition);
        }
        mCommentDialog.show();
        mCommentBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mCommentTv = mCommentView.findViewById(R.id.comment_tv);
        mCommentView.findViewById(R.id.close_iv).setOnClickListener(v -> mCommentDialog.dismiss());
        mShowSendTv = mCommentView.findViewById(R.id.comment_et);
        mShowSendTv.setOnClickListener(v ->
                showSendDialog("", mTheme_id, mComment_id == 0 ? "" : mComment_id + "", mParentCommentId, adapterPosition));
        mCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<CommentContentBean> mComment_content = adapter.getData();
            switch (view.getId()) {
                case R.id.item_praise_ll:
                    mPresenter.praise1(mComment_content, "2", position);
                    break;
                case R.id.item_delete_iv:
                    showDeleteDialog(mComment_content, position, adapterPosition);
                    break;
                case R.id.item_content_tv:
                    if (!String.valueOf(mComment_content.get(position).getUser_id())
                            .equals(mPresenter.mDataManager.getUser().getUser_id())) {
                        mComment_id = mComment_content.get(position).getComment_id();
                        mParentCommentId = mComment_content.get(position).getParentCommentId();
                        showSendDialog(mComment_content.get(position).getName(),
                                mComment_content.get(position).getTheme_id() + "",
                                mComment_content.get(position).getComment_id() + "",
                                mComment_content.get(position).getParentCommentId(), adapterPosition);
                    }
                    break;
            }
        });
    }

    @Override
    public void handlerPraiseSuccess1(PraiseEntity praiseEntity, String type_id, List<CommentContentBean> mComment_content, int position) {
        int countpraise = mComment_content.get(position).getCountpraise();
        if (mComment_content.get(position).getIsPraise() == 1) {
            mComment_content.get(position).setIsPraise(0);
            if (countpraise > 0) {
                countpraise--;
                mComment_content.get(position).setCountpraise(countpraise);
            }
        } else {
            mComment_content.get(position).setIsPraise(1);
            countpraise++;
            mComment_content.get(position).setCountpraise(countpraise);
        }
        mCommentAdapter.notifyItemChanged(position);
    }

    private void showDeleteDialog(List<CommentContentBean> mComment_content, int i, int adapterPosition) {
        mDeleteDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mDeleteDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    mPresenter.deleteComment(mComment_content.get(i).getComment_id() + "", mComment_content, i, adapterPosition);
                    mDeleteDialog.dismiss();
                })
                .builder();
        TextView textView = mDeleteDialog.getView(R.id.text);
        textView.setText("确定删除此条评论？");
        mDeleteDialog.show();
    }

    @Override
    public void handlerDeleteComment(List<CommentContentBean> mComment_content, int i, int adapterPosition) {
        mComment_content.remove(i);
        if (mCountCommentNum > 0) {
            mCountCommentNum--;
            mCommentTv.setText("全部评论 " + mCountCommentNum);
            mVideoAdapter.getData().get(adapterPosition).setCountComment(mCountCommentNum);
            mVideoAdapter.notifyItemChanged(adapterPosition);
            mCommentAdapter.notifyItemRemoved(i);
        }
    }

    private void showSendDialog(String name, String theme_id, String comment_id, String parentCommentId, int adapterPosition) {
        mInputDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.BOTTOM)
                .setViewId(R.layout.dialog_input_layout)
                .setWidthHeightdp(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dp_129))
                .isOnTouchCanceled(true)
                .builder();
        EditText mInputEt = mInputDialog.getView(R.id.comment_et);
        TextView publishTv = mInputDialog.getView(R.id.publish_tv);
        if (TextUtils.isEmpty(name)) {
            mInputEt.setHint("说点什么...");
        } else {
            mInputEt.setHint("回复：" + name);
        }
        mInputDialog.setOnShowListener(dialog -> mInputEt.postDelayed(() -> {
            mInputEt.requestFocus();
            KeyboardUtils.showSoftInput(mInputEt);
        }, 200));
        mInputDialog.setOnDismissListener(dialog -> {
            mInputEt.getText().clear();
            KeyboardUtils.toggleSoftInput();
        });
        KeyboardUtils.registerSoftInputChangedListener(this, height -> {
            if (height == 0 && mInputDialog != null) {
                mInputDialog.setOnDismissListener(null);
                mInputDialog.dismiss();
            }
        });
        mInputDialog.show();
        publishTv.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mInputEt.getText().toString().trim())) {
                ToastUtils.showShort("请输入评论内容");
                return;
            }
            mPresenter.publishComment(mInputEt.getText().toString().trim(),
                    theme_id, comment_id.equals("0") ? "" : comment_id + "", parentCommentId, adapterPosition);
            mInputEt.clearFocus();
            mInputEt.getText().clear();
            KeyboardUtils.hideSoftInput(this);
            if (mInputDialog != null) {
                mInputDialog.dismiss();
            }
        });
    }

    @Override
    public void handlerCommentSuccess(List<CommentContentBean> commentInfo, int adapterPosition) {
        mPresenter.commentList(Integer.parseInt(mTheme_id), 1, adapterPosition);
    }

    @Override
    public void handlerCommentList(ThemeInfoEntity.ThemeInfoBean themeInfoBean, int adapterPosition) {
        mCountCommentNum = themeInfoBean.getCountCommentNum();
        mCommentTv.setText("全部评论 " + themeInfoBean.getCountCommentNum());
        if (commentPage == 1) {
            mCommentAdapter.setNewData(themeInfoBean.getComment_content());
        } else {
            if (themeInfoBean != null && themeInfoBean.getComment_content().size() > 0) {
                mCommentAdapter.addData(themeInfoBean.getComment_content());
            } else {
                ToastUtils.showShort("没有更多评论了...");
            }
        }
        mVideoAdapter.getData().get(adapterPosition).setCountComment(mCountCommentNum);
        mVideoAdapter.notifyItemChanged(adapterPosition);
    }

    @Override
    public void onCommentClick(int adapterPosition, DetailCommentAdapter adapter, View view, int position) {
        if (!String.valueOf(adapter.getData().get(position).getUser_id())
                .equals(mPresenter.mDataManager.getUser().getUser_id())) {
            mComment_id = adapter.getData().get(position).getComment_id();
            mParentCommentId = adapter.getData().get(position).getParentCommentId();
            showSendDialog(adapter.getData().get(position).getName(),
                    adapter.getData().get(position).getTheme_id() + "",
                    adapter.getData().get(position).getComment_id() + "",
                    adapter.getData().get(position).getParentCommentId(), adapterPosition);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        EventBus.getDefault().post(new RefreshEvent(Constants.VIDEO_REFRESH, mPosition));
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
