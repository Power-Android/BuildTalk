package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleTopticAdapter;
import com.bjjy.buildtalk.adapter.DirectoryAdapter;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.PayEvent;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.CourseListEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.utils.GlideUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class CourseDetailActivity extends BaseActivity<CourseDetailPresenter> implements CourseDetailContarct.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener {

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
    @BindView(R.id.webview)
    WebView mWebView;
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
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.dir_rl)
    RelativeLayout mDirRl;
    @BindView(R.id.collapse_tv)
    RelativeLayout mCollapseTv;

    private int coursePage = 1, page = 1;
    private DirectoryAdapter mDirectoryAdapter;
    private List<CourseListEntity.CourselistBean> mList = new ArrayList<>();
    private int mDir_page_count = 1, mTheme_page_count = 1;
    private String mCircle_id;
    private String type = "1";
    private List<ThemeInfoEntity.ThemeInfoBean> mThemeInfoList = new ArrayList<>();
    private String mIsJoin;
    private CircleTopticAdapter mTopticAdapter;
    private BaseDialog mMInputDialog, mEditDailog, mDeleteDialog, mPayDialog;
    private TextView mCollect_tv;
    private boolean isExpand = false;
    private CourseListEntity.CourselistBean mData;
    private IWXAPI wxapi;
    private CircleInfoEntity mCircleInfoEntity;
    private String mPosition;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PayEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.PAY_SUCCESS)) {
            recreate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event1(RefreshEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.TOPTIC_REFRESH)) {
            onRefresh(mRefreshLayout);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeStatusBar(this, true, false);
        mTopTitleRl.setPadding(0, StatusBarUtils.getStatusBarHeight(), 0, 0);

        mData = (CourseListEntity.CourselistBean) getIntent().getSerializableExtra("bean");
        mPosition = getIntent().getStringExtra("position");
        mCircle_id = getIntent().getStringExtra("circle_id");
        GlideUtils.loadVideoScreenshot(this, mData.getVideoInfo().getVideo_url(), mVideoplayer.thumbImageView);
        mVideoplayer.setUp(new JZDataSource(mData.getVideoInfo().getVideo_url()), Jzvd.SCREEN_WINDOW_NORMAL);
    }

    @Override
    protected void initEventAndData() {
        EventBus.getDefault().register(this);
        mPresenter.CircleInfo(mCircle_id);
        mPresenter.themeInfo(mCircle_id, page, type, false);
        KeyboardUtils.registerSoftInputChangedListener(this, height -> {
            if (height == 0 && mMInputDialog != null) {
                mMInputDialog.dismiss();
            }
        });
    }

    @Override
    public void handlerCircleInfo(CircleInfoEntity circleInfoEntity) {
        this.mCircleInfoEntity = circleInfoEntity;

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTopticAdapter = new CircleTopticAdapter(R.layout.adapter_article_toptic, mThemeInfoList, mIsJoin, this);
        mRecyclerView.setAdapter(mTopticAdapter);
        mTopticAdapter.setOnItemClickListener(this);
        mTopticAdapter.setOnItemChildClickListener(this);
        if (TextUtils.equals("0", mIsJoin)) {
            mRefreshLayout.setEnableLoadMore(false);
            View footerView = LayoutInflater.from(App.getContext()).inflate(R.layout.footer_circle_toptic, null, false);
            mTopticAdapter.addFooterView(footerView);
        } else {
            mRefreshLayout.setEnableLoadMore(true);
        }

        mPresenter.courseList(mCircleInfoEntity.getCircleInfo().getData_id() + "", coursePage);

        mIsJoin = circleInfoEntity.getIsJoin();
        mChapterTitleTv.setText(mData.getArticle_title());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.loadData(getHtmlData(mData.getContent()), "text/html;charset=utf-8","utf-8");
        if (mIsJoin.equals("1")) {
            mJoinTv.setVisibility(View.GONE);
        } else {
            mJoinTv.setText("加入圈子￥" + mCircleInfoEntity.getCircleInfo().getCourse_money());
            mJoinTv.setVisibility(View.VISIBLE);
        }
        mChapterRecycler.setLayoutManager(new LinearLayoutManager(this));
        mDirectoryAdapter = new DirectoryAdapter(R.layout.adapter_directory_layout, mList, mIsJoin);
        mChapterRecycler.setAdapter(mDirectoryAdapter);
        mDirectoryAdapter.setOnLoadMoreListener(this, mChapterRecycler);
        mDirectoryAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            List<CourseListEntity.CourselistBean> mList = baseQuickAdapter.getData();

            if (mIsJoin.equals("0") && mList.get(i).getIs_audition() == 0) {
                ToastUtils.showShort("该课程还未解锁");
                return;
            }
            for (int j = 0; j < mList.size(); j++) {
                mList.get(j).setSelected(false);
            }
            mList.get(i).setSelected(true);
            mData = mList.get(i);
            Jzvd.releaseAllVideos();
            GlideUtils.loadVideoScreenshot(this, mList.get(i).getVideoInfo().getVideo_url(), mVideoplayer.thumbImageView);
            mVideoplayer.setUp(new JZDataSource(mList.get(i).getVideoInfo().getVideo_url()), Jzvd.SCREEN_WINDOW_NORMAL);
            mVideoplayer.startVideo();
            mChapterTitleTv.setText(mData.getArticle_title());
            mWebView.loadData(getHtmlData(mData.getContent()), "text/html;charset=utf-8","utf-8");
            mDirectoryAdapter.notifyDataSetChanged();
        });
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:15px;} body{word-wrap:break-word;font-size:13px;padding:0px;margin:0px} p{padding:0px;margin:0px;font-size:13px;color:FF656565;line-height:1.3;} img{padding:0px,margin:0px;max-width:100%; width:100%; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @Override
    public void handlerCourseList(CourseListEntity courseListEntity) {
        mDir_page_count = courseListEntity.getPage_count();
        int countUpdateCourse = courseListEntity.getCountUpdateCourse();
        int countCourse = courseListEntity.getCountCourse();
        courseListEntity.getCourselist().get(Integer.parseInt(mPosition)).setSelected(true);
        mList = courseListEntity.getCourselist();
        mUpdateTv.setText("更新至" + countUpdateCourse + "讲/全" + countCourse + "讲");
        mDirectoryAdapter.addData(courseListEntity.getCourselist());
    }

    @Override
    public void handlerThemeInfo(ThemeInfoEntity themeInfoEntity, boolean isRefresh) {
        mTheme_page_count = themeInfoEntity.getPage_count();
        mThemeInfoList = themeInfoEntity.getThemeInfo();

        if ("0".equals(mIsJoin) && mThemeInfoList.size() > 5) {
            mThemeInfoList = mThemeInfoList.subList(0, 5);
        }
        if (isRefresh) {
            mTopticAdapter.setNewData(mThemeInfoList);
        } else {
            mTopticAdapter.addData(mThemeInfoList);
        }
        mNumTv.setText(mThemeInfoList.size() + "");
    }

    @Override
    public void handlerThemeInfoEmpty(List<ThemeInfoEntity.ThemeInfoBean> themeInfo) {
        mTopticAdapter.setNewData(themeInfo);
    }

    @OnClick({R.id.more_ll, R.id.list_ll, R.id.join_tv, R.id.back_iv, R.id.pre_share_iv, R.id.share_iv, R.id.more_iv, R.id.collapse_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_ll:
                if (!isExpand) {
                    mContentScrollView.setVisibility(View.VISIBLE);
                    mXqExpandIv.setImageResource(R.drawable.sanjiao_top_icon);
                } else {
                    mContentScrollView.setVisibility(View.GONE);
                    mXqExpandIv.setImageResource(R.drawable.sanjiao_icon);
                }
                isExpand = !isExpand;
                break;
            case R.id.list_ll:
                mDirRl.setVisibility(View.VISIBLE);
                break;
            case R.id.collapse_tv:
                mDirRl.setVisibility(View.GONE);
                break;
            case R.id.join_tv:
                showPayDialog();
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

    private void showPayDialog() {
        mPayDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.BOTTOM)
                .setAnimation(R.style.bottom_aniamtion)
                .setViewId(R.layout.dialog_pay_layout)
                .setWidthHeightdp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mPayDialog.dismiss())
                .addViewOnClickListener(R.id.pay_ll, v -> {
                            mPresenter.payOrder("2", mCircleInfoEntity.getCircleInfo().getCourse_id(), mCircleInfoEntity.getCircleInfo().getCircle_name(),
                                    mCircleInfoEntity.getCircleInfo().getCourse_money());
                            mPayDialog.dismiss();
                        }
                )
                .builder();
        TextView payTv = mPayDialog.getView(R.id.pay_money_tv);
        payTv.setText("￥" + mCircleInfoEntity.getCircleInfo().getCourse_money());
        mPayDialog.show();
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

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.goOnPlayOnPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
        EventBus.getDefault().unregister(this);
        KeyboardUtils.unregisterSoftInputChangedListener(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mTheme_page_count) {
            page++;
            mPresenter.themeInfo(mCircle_id, page, type, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.themeInfo(mCircle_id, page, type, true);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<ThemeInfoEntity.ThemeInfoBean> data = baseQuickAdapter.getData();
        if ("1".equals(mIsJoin)) {
            Intent intent = new Intent(this, TopticDetailActivity.class);
            intent.putExtra("title", mCircleInfoEntity.getCircleInfo().getCircle_name());
            intent.putExtra("theme_id", data.get(i).getTheme_id() + "");
            startActivity(intent);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<ThemeInfoEntity.ThemeInfoBean> data = baseQuickAdapter.getData();
        switch (view.getId()) {
            case R.id.item_more_iv:
                showEditDialog(data.get(i), i, data);
                break;
            case R.id.item_praise_iv:
                mPresenter.praise(data, i);
                break;
            case R.id.item_comment_iv:
                showCommentDialog(data.get(i).getTheme_id(), i, data);
                break;
            case R.id.item_share_iv:
                ToastUtils.showShort("敬请期待");
                break;
            case R.id.more_tv:
                Intent intent = new Intent(this, TopticDetailActivity.class);
                intent.putExtra("title", mCircleInfoEntity.getCircleInfo().getCircle_name());
                intent.putExtra("theme_id", data.get(i).getTheme_id() + "");
                startActivity(intent);
                break;
        }
    }

    private void showEditDialog(ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        mEditDailog = new BaseDialog.Builder(this)
                .setGravity(Gravity.BOTTOM)
                .setViewId(R.layout.dialog_theme_edit)
                .setWidthHeightpx(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimation(R.style.bottom_aniamtion)
                .isOnTouchCanceled(true)
                .builder();
        mCollect_tv = mEditDailog.getView(R.id.collect_tv);
        TextView edit_tv = mEditDailog.getView(R.id.edit_tv);
        TextView delete_tv = mEditDailog.getView(R.id.delete_tv);

        if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
            mCollect_tv.setVisibility(View.GONE);
            edit_tv.setVisibility(View.VISIBLE);
            delete_tv.setVisibility(View.VISIBLE);
        } else {
            mCollect_tv.setVisibility(View.VISIBLE);
            edit_tv.setVisibility(View.GONE);
            delete_tv.setVisibility(View.GONE);
        }
        if (1 == data.getIs_collect()) {
            Drawable drawable = getResources().getDrawable(R.drawable.collect_sel_icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mCollect_tv.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.collect_def_icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mCollect_tv.setCompoundDrawables(null, drawable, null, null);
        }
        mCollect_tv.setOnClickListener(v -> {
            mPresenter.collectTheme(data, i);
            mEditDailog.dismiss();
        });
        edit_tv.setOnClickListener(v -> {
            if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                Intent intent = new Intent(CourseDetailActivity.this, PublishActivity.class);
                intent.putExtra("themeInfo", data);
                intent.putExtra("circle_name", mCircleInfoEntity.getCircleInfo().getCircle_name());
                startActivity(intent);
            }
            mEditDailog.dismiss();
        });
        delete_tv.setOnClickListener(v -> {
            if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                showQuitDialog(data, i, list);
            }
            mEditDailog.dismiss();
        });
        TextView cancle_tv = mEditDailog.getView(R.id.cancle_tv);
        cancle_tv.setOnClickListener(v -> mEditDailog.dismiss());
        mEditDailog.show();
    }

    private void showQuitDialog(ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        mDeleteDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mDeleteDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    mPresenter.deleteTheme(data, i, list);
                    mDeleteDialog.dismiss();
                })
                .builder();
        TextView textView = mDeleteDialog.getView(R.id.text);
        textView.setText("确定删除此主题？");
        mDeleteDialog.show();
    }

    private void showCommentDialog(int theme_id, int i, List<ThemeInfoEntity.ThemeInfoBean> data) {
        mMInputDialog = new BaseDialog.Builder(CourseDetailActivity.this)
                .setGravity(Gravity.BOTTOM)
                .setViewId(R.layout.dialog_input_layout)
                .setWidthHeightdp(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dp_129))
                .isOnTouchCanceled(false)
                .builder();
        EditText mInputEt = mMInputDialog.getView(R.id.comment_et);
        TextView publishTv = mMInputDialog.getView(R.id.publish_tv);
        mMInputDialog.setOnShowListener(dialog -> mInputEt.postDelayed(() -> {
            mInputEt.requestFocus();
            KeyboardUtils.showSoftInput(mInputEt);
        }, 200));
        mMInputDialog.setOnDismissListener(dialog -> {
            mInputEt.getText().clear();
            KeyboardUtils.hideSoftInput(mInputEt);
        });
        mMInputDialog.show();
        publishTv.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mInputEt.getText().toString().trim())) {
                ToastUtils.showShort("请输入评论内容");
                return;
            }
            mPresenter.publishComment(mInputEt.getText().toString().trim(), String.valueOf(theme_id), i, data);
            if (mMInputDialog != null) {
                KeyboardUtils.hideSoftInput(mInputEt);
                mMInputDialog.dismiss();
            }
        });
    }

    @Override
    public void handlerCommentSuccess(int position, List<ThemeInfoEntity.ThemeInfoBean> data, List<CommentContentBean> contentBeanList) {
        data.get(position).setComment_content(contentBeanList);
        mTopticAdapter.notifyItemChanged(position);
    }

    @Override
    public void handlerPraiseSuccess(List<ThemeInfoEntity.ThemeInfoBean> data, int i, PraiseEntity praiseEntity) {
        if (data.get(i).getIs_parise() == 0) {
            data.get(i).setIs_parise(1);
        } else {
            data.get(i).setIs_parise(0);
        }
        data.get(i).setParise_nickName(praiseEntity.getNickName());
        mTopticAdapter.notifyItemChanged(i);
    }

    @Override
    public void handlerCollectSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i) {
        if (0 == data.getIs_collect()) {
            data.setIs_collect(1);
            mTopticAdapter.notifyItemChanged(i);
            ToastUtils.showCollect("收藏成功", getResources().getDrawable(R.drawable.collect_success_icon));
        } else {
            data.setIs_collect(0);
            mTopticAdapter.notifyItemChanged(i);
            ToastUtils.showCollect("取消收藏", getResources().getDrawable(R.drawable.collect_cancle_icon));
        }
    }

    @Override
    public void handlerDeleteSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        list.remove(i);
        mTopticAdapter.notifyItemRemoved(i);
    }

    @Override
    public void onLoadMoreRequested() {
        if (coursePage < mDir_page_count) {
            coursePage++;
            mPresenter.courseList(mCircle_id, coursePage);
            mDirectoryAdapter.loadMoreEnd();
        } else {
            mDirectoryAdapter.loadMoreComplete();
        }
    }

}
