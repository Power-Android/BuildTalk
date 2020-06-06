package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CommentAdapter;
import com.bjjy.buildtalk.adapter.DetailCommentAdapter;
import com.bjjy.buildtalk.adapter.PdfVewAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PariseNickNameBean;
import com.bjjy.buildtalk.entity.PdfInfoEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.entity.ThemePdfBean;
import com.bjjy.buildtalk.entity.ThemeVideoBean;
import com.bjjy.buildtalk.utils.AllUtils;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.SpanUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MultiImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TopticDetailActivity extends BaseActivity<TopticDetailPresenter> implements TopticDetailContract.View, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, CommentAdapter.onCommentItemlistener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_right_search)
    ImageView mToolBarRightIv;
    @BindView(R.id.item_face_iv)
    CircleImageView mItemFaceIv;
    @BindView(R.id.item_name_tv)
    TextView mItemNameTv;
    @BindView(R.id.item_job_tv)
    TextView mItemJobTv;
    @BindView(R.id.item_time_tv)
    TextView mItemTimeTv;
    @BindView(R.id.item_content_tv)
    TextView mItemContentTv;
    @BindView(R.id.item_img_iv)
    ImageView mItemImgIv;
    @BindView(R.id.item_grid_view)
    MultiImageView mItemGridView;
    @BindView(R.id.pdf_recyclerView)
    RecyclerView mPdfRecyclerView;
    @BindView(R.id.praise_str_tv)
    TextView mPraiseStrTv;
    @BindView(R.id.praise_rl)
    LinearLayout mPraiseRl;
    @BindView(R.id.record_num_tv)
    TextView mRecordNumTv;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadmore_layout)
    SmartRefreshLayout mLoadmoreLayout;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.record_et)
    EditText mRecordEt;
    @BindView(R.id.praise_iv)
    ImageView mPraiseIv;
    @BindView(R.id.praise_tv)
    TextView mPraiseTv;
    @BindView(R.id.praise_ll)
    LinearLayout mPraiseLl;
    @BindView(R.id.share_iv)
    TextView mShareIv;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;
    @BindView(R.id.record_ll)
    LinearLayout mRecordLl;
    @BindView(R.id.record_bottom_ll)
    LinearLayout mRecordBottomLl;
    @BindView(R.id.emptyView)
    NestedScrollView mEmptyView;
    @BindView(R.id.from_tv)
    TextView mFromTv;
    @BindView(R.id.from_tv1)
    TextView mFromTv1;
    @BindView(R.id.item_atten_iv)
    ImageView mItemAttenIv;
    @BindView(R.id.item_atten_tv)
    TextView mItemAttenTv;
    @BindView(R.id.item_atten_cl)
    ConstraintLayout mItemAttenCl;
    @BindView(R.id.item_vertical_video_view)
    ImageView mItemVerticalVideoView;
    @BindView(R.id.item_vertical_time)
    TextView mItemVerticalTime;
    @BindView(R.id.item_vertical_cl)
    ConstraintLayout mItemVerticalCl;
    @BindView(R.id.item_horizontal_video_view)
    ImageView mItemHorizontalVideoView;
    @BindView(R.id.item_horizontal_time)
    TextView mItemHorizontalTime;
    @BindView(R.id.item_horizontal_cl)
    ConstraintLayout mItemHorizontalCl;

    private String mTheme_id;
    private ThemeInfoEntity.ThemeInfoBean themeInfoEntity;
    private int page = 1;
    private int mPage_count;
    private List<CommentContentBean> mComment_content = new ArrayList<>();
    private CommentAdapter mCommentAdapter;
    private BaseDialog mEditDailog;
    private TextView mCollect_tv;
    private String mTitle;
    private BaseDialog mDeleteDialog;
    private int mThemeCountParise;
    private int itemPosition = 0;
    private int mCountCommentNum = 0;
    private boolean isGone = false;
    private String mUrl;
    private String mEndUrl;
    private String mPath1;//主题
    private String themePath;//主题拼接完成url
    private String mCircle_id;
    private int mComment_id;
    private String mParentCommentId;
    public static int mFlag = 0;
    BottomSheetDialog mBottomSheetDialog;
    BottomSheetBehavior mBehavior;
    private View mView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_toptic_detail;
    }

    @Override
    protected void onResume() {
        setIsMargin(true);
        super.onResume();
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeStatusBar(this, true, true);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mIncludeToolbar.getLayoutParams());
        lp.setMargins(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        mIncludeToolbar.setLayoutParams(lp);
        mTitle = getIntent().getStringExtra("title");
        mTheme_id = getIntent().getStringExtra("theme_id");
        mCircle_id = getIntent().getStringExtra("circle_id");
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarTitle.setText(mTitle);
        mToolBarRightIv.setImageResource(R.drawable.title_more_icon);
        mToolBarRightIv.setVisibility(View.VISIBLE);
        KeyboardUtils.registerSoftInputChangedListener(this, height -> {
            if (height <= 0) {
                mRecordBottomLl.setVisibility(View.VISIBLE);
                isGone = false;
            }
        });
        mPath1 = "pages/sub_circle/pages/subjectDetails/subjectDetails?";

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.unregisterSoftInputChangedListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.searchThemeDetail(mTheme_id);
        mPresenter.commentList(mTheme_id, page, false);
        mRefreshLayout.setOnRefreshListener(this);
        mLoadmoreLayout.setOnLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentAdapter = new CommentAdapter(R.layout.adapter_circle_detail_comment, mComment_content);
        mRecyclerView.setAdapter(mCommentAdapter);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mCommentAdapter.setOnItemChildClickListener(this);
        mCommentAdapter.setCommentClickListener(this);
    }

    @Override
    public void handlerThemeInfo(ThemeInfoEntity.ThemeInfoBean themeInfoEntity) {
        this.themeInfoEntity = themeInfoEntity;
        mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" + mPresenter.mDataManager.getUser().getUser_id() + "&theme_id=" + mTheme_id;
        mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) + "&response_type=code&scope=snsapi_userinfo&state=theme#wechat_redirect";
        Glide.with(this).load(themeInfoEntity.getHeadImage()).into(mItemFaceIv);
        mItemNameTv.setText(themeInfoEntity.getName());
        mThemeCountParise = themeInfoEntity.getThemeCountParise();
        changeAttentionStatus();
        if ("1".equals(themeInfoEntity.getIs_circleMaster())) {
            mItemJobTv.setVisibility(View.VISIBLE);
        } else {
            mItemJobTv.setVisibility(View.GONE);
        }
        mItemTimeTv.setText(TimeUtils.getFriendlyTimeSpanByNow(themeInfoEntity.getPublish_time()));
        mItemContentTv.setText(themeInfoEntity.getTheme_content());
        if (themeInfoEntity.getReprint_themeId() == 0) {
            mFromTv.setVisibility(View.GONE);
            mFromTv1.setVisibility(View.GONE);
        } else {
            if (themeInfoEntity.getIs_find() == 0) {
                mFromTv.setText("转自 @" + themeInfoEntity.getParent_name());
                SpanUtils.with(mFromTv1)
                        .append("转自圈子 ")
                        .setForegroundColor(getResources().getColor(R.color.text_color6))
                        .append(themeInfoEntity.getCircle_name())
                        .setForegroundColor(getResources().getColor(R.color.blue_mid))
                        .create();
            } else {
                SpanUtils.with(mFromTv1)
                        .append("转自发现 ")
                        .setForegroundColor(getResources().getColor(R.color.text_color6))
                        .create();
            }
            mFromTv.setVisibility(View.VISIBLE);
            mFromTv1.setVisibility(View.VISIBLE);
        }

        List<ThemeImageBean> themeImageBeanList = themeInfoEntity.getTheme_image();
        if (themeImageBeanList.size() > 0){
            List<String> list = new ArrayList<>();
            for (int i = 0; i < themeImageBeanList.size(); i++) {
                list.add(themeImageBeanList.get(i).getPic_url());
            }
            mItemGridView.setList(list);
            mItemGridView.setVisibility(View.VISIBLE);
            mItemGridView.setOnItemClickListener((view, position, imageViews) ->
                    AllUtils.startImagePage(this, list, Arrays.asList(imageViews), position));
        }

        List<ThemePdfBean> theme_pdf = themeInfoEntity.getTheme_pdf();
        if (theme_pdf.size() > 0){
            List<PdfInfoEntity> list1 = new ArrayList<>();
            for (int i = 0; i < theme_pdf.size(); i++) {
                list1.add(new PdfInfoEntity(theme_pdf.get(i).getPdf_name(), theme_pdf.get(i).getPdf_url()));
            }
            mPdfRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            PdfVewAdapter pdfVewAdapter = new PdfVewAdapter(R.layout.adapter_pdf_view, list1);
            mPdfRecyclerView.setAdapter(pdfVewAdapter);
            mPdfRecyclerView.setVisibility(View.VISIBLE);
            pdfVewAdapter.setOnItemClickListener((adapter, view, position) -> {
                List<PdfInfoEntity> data = adapter.getData();
                Intent intent = new Intent(TopticDetailActivity.this, PDFViewerActivity.class);
                intent.putExtra("data", data.get(position));
                intent.putExtra("theme_id", themeInfoEntity.getTheme_id() + "");
                intent.putExtra("isCollect", 0 == themeInfoEntity.getIs_collect());
                startActivity(intent);
            });
        }

        List<ThemeVideoBean> theme_video = themeInfoEntity.getTheme_video();
        if (theme_video.size() > 0){
            String videoWidth = theme_video.get(0).getVideo_width();
            String videoHeight = theme_video.get(0).getVideo_height();
            String video_duration = theme_video.get(0).getVideo_duration();
            float duration = 0f;
            if (!TextUtils.isEmpty(video_duration)){
                duration = Float.parseFloat(video_duration);
            }
            if (TextUtils.isEmpty(videoWidth) || TextUtils.isEmpty(videoHeight)) {
                mItemVerticalCl.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.color.black).into(mItemVerticalVideoView);
            } else {
                if (Integer.parseInt(videoWidth) > Integer.parseInt(videoHeight)) {
                    mItemHorizontalCl.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(theme_video.get(0).getCoverURL())
                            .into(mItemHorizontalVideoView);
                    mItemHorizontalTime.setText(TextUtils.isEmpty(video_duration) ?
                            "" : TimeUtils.stringForTime((int) duration));
                } else {
                    mItemVerticalCl.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(theme_video.get(0).getCoverURL())
                            .into(mItemVerticalVideoView);
                    mItemVerticalTime.setText(TextUtils.isEmpty(video_duration) ?
                            "" : TimeUtils.stringForTime((int) duration));
                }
            }
        }


        List<PariseNickNameBean> praiseList = themeInfoEntity.getParise_nickName();
        if (praiseList.size() > 0) {
            mPraiseRl.setVisibility(View.VISIBLE);
            String praiseStr = StringUtils.listToString1(praiseList, ',');
            mPraiseStrTv.setText(praiseStr);
        } else {
            mPraiseRl.setVisibility(View.GONE);
        }
        if (1 == themeInfoEntity.getIs_parise()) {
            mPraiseIv.setImageResource(R.drawable.praise_sel);
        } else {
            mPraiseIv.setImageResource(R.drawable.praise_def);
        }
        mPraiseTv.setText(themeInfoEntity.getThemeCountParise() + "");
    }

    private void changeAttentionStatus() {
        if (0 == themeInfoEntity.getIs_attention()) {
            mItemAttenIv.setImageResource(R.drawable.attention_sel);
            mItemAttenTv.setText("关注");
            mItemAttenTv.setTextColor(getResources().getColor(R.color.oranger3));
            mItemAttenCl.setBackground(getResources().getDrawable(R.drawable.shape_orange_13radius));
        } else if (1 == themeInfoEntity.getIs_attention()) {
            mItemAttenIv.setImageResource(R.drawable.attention_def);
            mItemAttenTv.setText("已关注");
            mItemAttenTv.setTextColor(getResources().getColor(R.color.text_color6));
            mItemAttenCl.setBackground(getResources().getDrawable(R.drawable.shape_gray_13radius));
        } else if (2 == themeInfoEntity.getIs_attention()) {
            mItemAttenCl.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void handlerCommentList(ThemeInfoEntity.ThemeInfoBean themeInfoBean, boolean isRefresh) {
        if (themeInfoBean.getComment_content().size() > 0) {
            mPage_count = themeInfoBean.getPage_count();
            mCountCommentNum = themeInfoBean.getCountCommentNum();
            mRecordNumTv.setText(mCountCommentNum + "");
            mComment_content = themeInfoBean.getComment_content();
            if (isRefresh) {
                mCommentAdapter.setNewData(themeInfoBean.getComment_content());
            } else {
                mCommentAdapter.addData(themeInfoBean.getComment_content());
            }
            mEmptyView.setVisibility(View.GONE);
            mLoadmoreLayout.setEnableLoadMore(true);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mLoadmoreLayout.setEnableLoadMore(false);
            mEmptyView.setVisibility(View.VISIBLE);
        }

        mRecordEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (TextUtils.isEmpty(mRecordEt.getText().toString().trim())) {
                    ToastUtils.showShort("请输入评论内容");
                    return false;
                }
                mPresenter.publishComment(mRecordEt.getText().toString().trim(), themeInfoEntity.getTheme_id(), mComment_id == 0 ? "" : mComment_id + "", mParentCommentId);
                mRecordEt.clearFocus();
                mRecordEt.getText().clear();
                mRecordBottomLl.setVisibility(View.VISIBLE);
                isGone = !isGone;
                KeyboardUtils.hideSoftInput(this);
            }
            return false;
        });
    }

    @OnClick({R.id.toolbar_right_search, R.id.praise_ll, R.id.share_iv, R.id.record_ll, R.id.wechat_iv,
            R.id.wechat_circle_iv, R.id.item_atten_cl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_search:
                if (themeInfoEntity != null)
                    LoginHelper.getInstance().login(this, mPresenter.mDataManager, () -> showEditDialog(themeInfoEntity));
                break;
            case R.id.praise_ll:
                if (themeInfoEntity != null) {
                    LoginHelper.getInstance().login(this, mPresenter.mDataManager, () -> mPresenter.praise(themeInfoEntity.getTheme_id() + "", "1", null));
                }
                break;
            case R.id.share_iv:
                if (themeInfoEntity != null) {
                    if (themeInfoEntity.getTheme_image().size() > 0) {
                        mPresenter.getThumb(themeInfoEntity.getTheme_image().get(0).getPic_url(), themeInfoEntity);
                    } else {
                        mPresenter.getThumb(themeInfoEntity.getHeadImage(), themeInfoEntity);
                    }
                }
                break;
            case R.id.record_ll:
                LoginHelper.getInstance().login(this, mPresenter.mDataManager, () -> {
                    if (!isGone) {
                        mRecordBottomLl.setVisibility(View.GONE);
                        mRecordEt.setFocusable(true);
                        mRecordEt.setFocusableInTouchMode(true);
                        mRecordEt.requestFocus();
                        mComment_id = 0;
                        mParentCommentId = "";
                        KeyboardUtils.showSoftInput(TopticDetailActivity.this);
                        isGone = !isGone;
                    }
                });
            case R.id.wechat_iv:
            case R.id.wechat_circle_iv:
                if (themeInfoEntity != null) {
                    if (themeInfoEntity.getTheme_image().size() > 0) {
                        mPresenter.getThumb(themeInfoEntity.getTheme_image().get(0).getPic_url(), themeInfoEntity);
                    } else {
                        mPresenter.getThumb(themeInfoEntity.getHeadImage(), themeInfoEntity);
                    }
                }
                break;
            case R.id.item_atten_cl:
                mPresenter.attenUser(themeInfoEntity);
                break;
        }
    }

    @Override
    public void handlerAttentUser(BaseResponse<IEntity> baseResponse, ThemeInfoEntity.ThemeInfoBean themeInfoEntity) {
        if (TextUtils.equals("关注成功", baseResponse.getErrorMsg())) {
            themeInfoEntity.setIs_attention(1);
        } else {
            themeInfoEntity.setIs_attention(0);
        }
        changeAttentionStatus();
    }

    @Override
    public void handlerThumbSuccess(String thumb_url, ThemeInfoEntity.ThemeInfoBean themeInfoEntity) {
        themePath = mPath1 + "theme_id=" + themeInfoEntity.getTheme_id() + "&circle_id=" + mCircle_id + "&num=1";
        showShareDialog(themePath, mEndUrl,
                TextUtils.isEmpty(themeInfoEntity.getTheme_content()) ? mTitle : themeInfoEntity.getTheme_content(),
                thumb_url, themeInfoEntity.getTheme_content(), true, true);
    }

    private void showEditDialog(ThemeInfoEntity.ThemeInfoBean data) {
        mEditDailog = new BaseDialog.Builder(this)
                .setGravity(Gravity.BOTTOM)
                .setViewId(R.layout.dialog_theme_edit1)
                .setWidthHeightpx(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimation(R.style.bottom_aniamtion)
                .isOnTouchCanceled(true)
                .builder();
        mCollect_tv = mEditDailog.getView(R.id.collect_tv);
        TextView edit_tv = mEditDailog.getView(R.id.edit_tv);
        TextView delete_tv = mEditDailog.getView(R.id.delete_tv);
        TextView jinghua_tv = mEditDailog.getView(R.id.jinghua_tv);

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
        if (1 == data.getIs_choiceness()) {
            Drawable drawable = getResources().getDrawable(R.drawable.qxjiajing_icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            jinghua_tv.setCompoundDrawables(null, drawable, null, null);
            jinghua_tv.setText("取消精华");
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.jiajing_icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            jinghua_tv.setCompoundDrawables(null, drawable, null, null);
            jinghua_tv.setText("加精");
        }
        mCollect_tv.setOnClickListener(v -> {
            mPresenter.collectTheme(data);
            mEditDailog.dismiss();
        });
        jinghua_tv.setOnClickListener(v -> {
            mPresenter.addChoiceness(data);
            mEditDailog.dismiss();
        });
        edit_tv.setOnClickListener(v -> {
            if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                Intent intent = new Intent(TopticDetailActivity.this, PublishActivity.class);
                intent.putExtra("themeInfo", data);
                intent.putExtra("circle_name", mTitle);
                startActivity(intent);
            }
            mEditDailog.dismiss();
        });
        delete_tv.setOnClickListener(v -> {
            if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                showQuitDialog(data);
            }
            mEditDailog.dismiss();
        });
        TextView cancle_tv = mEditDailog.getView(R.id.cancle_tv);
        cancle_tv.setOnClickListener(v -> mEditDailog.dismiss());
        mEditDailog.show();
    }

    private void showQuitDialog(ThemeInfoEntity.ThemeInfoBean data) {
        mDeleteDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mDeleteDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    mPresenter.deleteTheme(data);
                    mDeleteDialog.dismiss();
                })
                .builder();
        TextView textView = mDeleteDialog.getView(R.id.text);
        textView.setText("确定删除此主题？");
        mDeleteDialog.show();
    }

    @Override
    public void handlerCollectSuccess(IEntity iEntity) {
        if (0 == themeInfoEntity.getIs_collect()) {
            themeInfoEntity.setIs_collect(1);
            Drawable drawable = getResources().getDrawable(R.drawable.collect_sel_icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mCollect_tv.setCompoundDrawables(null, drawable, null, null);
            ToastUtils.showCollect("收藏成功", getResources().getDrawable(R.drawable.collect_success_icon));
        } else {
            themeInfoEntity.setIs_collect(0);
            Drawable drawable = getResources().getDrawable(R.drawable.collect_def_icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mCollect_tv.setCompoundDrawables(null, drawable, null, null);
            ToastUtils.showCollect("取消收藏", getResources().getDrawable(R.drawable.collect_cancle_icon));
        }
        EventBus.getDefault().post(new RefreshEvent(Constants.TOPTIC_REFRESH_ALL));
    }

    @Override
    public void handlerChoicenessSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data) {
        if (1 == themeInfoEntity.getIs_choiceness()) {
            themeInfoEntity.setIs_choiceness(0);
            ToastUtils.showShort("取消精华成功");
        } else {
            themeInfoEntity.setIs_choiceness(1);
            ToastUtils.showShort("主题加精成功");
        }
        EventBus.getDefault().post(new RefreshEvent(Constants.TOPTIC_REFRESH_ALL));
    }

    @Override
    public void handlerDeleteSuccess(IEntity iEntity) {
        EventBus.getDefault().post(new RefreshEvent(Constants.TOPTIC_REFRESH_ALL));
        finish();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.searchThemeDetail(mTheme_id);
        mPresenter.commentList(mTheme_id, page, true);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < mPage_count) {
            page++;
            mPresenter.commentList(mTheme_id, page, false);
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<CommentContentBean> mComment_content = baseQuickAdapter.getData();
        itemPosition = i;
        switch (view.getId()) {
            case R.id.item_praise_ll:
                mPresenter.praise(mComment_content.get(i).getComment_id() + "", "2", mComment_content);
                break;
            case R.id.item_delete_iv:
                showDeleteDialog(mComment_content, i);
                break;
            case R.id.item_content_tv:
                if (!String.valueOf(mComment_content.get(i).getUser_id()).equals(mPresenter.mDataManager.getUser().getUser_id())) {
                    if (!isGone) {
                        mRecordBottomLl.setVisibility(View.GONE);
                        mRecordEt.setFocusable(true);
                        mRecordEt.setFocusableInTouchMode(true);
                        mRecordEt.requestFocus();
                        mRecordEt.setHint("回复：" + mComment_content.get(i).getName());
                        mComment_id = mComment_content.get(i).getComment_id();
                        mParentCommentId = mComment_content.get(i).getParentCommentId();
                        mFlag = 0;
                        KeyboardUtils.showSoftInput(TopticDetailActivity.this);
                        isGone = !isGone;
                    }
                }
                break;
        }
    }

    private void showDeleteDialog(List<CommentContentBean> mComment_content, int i) {
        mDeleteDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mDeleteDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    mPresenter.deleteComment(mComment_content.get(i).getComment_id() + "", mComment_content);
                    mDeleteDialog.dismiss();
                })
                .builder();
        TextView textView = mDeleteDialog.getView(R.id.text);
        textView.setText("确定删除此条评论？");
        mDeleteDialog.show();
    }

    @Override
    public void handlerPraiseSuccess(PraiseEntity praiseEntity, String type, List<CommentContentBean> mComment_content) {
        if (TextUtils.equals("1", type)) {
            if (0 == themeInfoEntity.getIs_parise()) {
                themeInfoEntity.setIs_parise(1);
                mPraiseIv.setImageResource(R.drawable.praise_sel);
                mThemeCountParise++;
                mPraiseTv.setText(mThemeCountParise + "");
            } else {
                themeInfoEntity.setIs_parise(0);
                mPraiseIv.setImageResource(R.drawable.praise_def);
                if (mThemeCountParise > 0) {
                    mThemeCountParise--;
                    mPraiseTv.setText(mThemeCountParise + "");
                }
            }
            List<PariseNickNameBean> praiseList = praiseEntity.getNickName();
            if (praiseList.size() > 0) {
                mPraiseRl.setVisibility(View.VISIBLE);
                String praiseStr = StringUtils.listToString1(praiseList, ',');
                mPraiseStrTv.setText(praiseStr);
            } else {
                mPraiseRl.setVisibility(View.GONE);
            }
        } else {
            int countpraise = mComment_content.get(itemPosition).getCountpraise();
            if (mComment_content.get(itemPosition).getIsPraise() == 1) {
                mComment_content.get(itemPosition).setIsPraise(0);
                if (countpraise > 0) {
                    countpraise--;
                    mComment_content.get(itemPosition).setCountpraise(countpraise);
                }
            } else {
                mComment_content.get(itemPosition).setIsPraise(1);
                countpraise++;
                mComment_content.get(itemPosition).setCountpraise(countpraise);
            }
            mCommentAdapter.notifyItemChanged(itemPosition);
        }
    }

    @Override
    public void handlerDeleteComment(List<CommentContentBean> mComment_content) {
        mComment_content.remove(itemPosition);
        mCommentAdapter.notifyItemRemoved(itemPosition);
        if (mCountCommentNum > 0) {
            mCountCommentNum--;
            mRecordNumTv.setText(mCountCommentNum + "");
        }
    }

    @Override
    public void handlerCommentSuccess(List<CommentContentBean> commentInfo) {
        mPresenter.commentList(mTheme_id, 1, true);
    }

    @Override
    public void onCommentClick(int adapterPosition, DetailCommentAdapter adapter, View view, int position) {
        if (!String.valueOf(adapter.getData().get(position).getUser_id()).equals(mPresenter.mDataManager.getUser().getUser_id())) {
            if (!isGone) {
                mRecordBottomLl.setVisibility(View.GONE);
                mRecordEt.setFocusable(true);
                mRecordEt.setFocusableInTouchMode(true);
                mRecordEt.requestFocus();
                mFlag = 0;
                mRecordEt.setHint("回复：" + adapter.getData().get(position).getName());
                mComment_id = adapter.getData().get(position).getComment_id();
                mParentCommentId = adapter.getData().get(position).getParentCommentId();
                KeyboardUtils.showSoftInput(TopticDetailActivity.this);
                isGone = !isGone;
            }
        }
    }

    private void showShareDialog(String url, String weburl, String title, String imgUrl, String desc,
                                 boolean isSmall, boolean isVisible) {
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
                DialogUtils.shareSmallProgram(url, imgUrl, title, desc, TopticDetailActivity.this, SHARE_MEDIA.WEIXIN);
            } else {
                DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, TopticDetailActivity.this, SHARE_MEDIA.WEIXIN);
            }
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.wechat_circle_tv).setOnClickListener(v -> {
            DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, TopticDetailActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.discover_tv).setOnClickListener(v -> mBottomSheetDialog.dismiss());
        mView.findViewById(R.id.cancle_tv).setOnClickListener(v -> mBottomSheetDialog.dismiss());
    }
}
