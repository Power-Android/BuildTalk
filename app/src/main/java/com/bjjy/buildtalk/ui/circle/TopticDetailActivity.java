package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
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
import com.bjjy.buildtalk.adapter.PdfVewAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PariseNickNameBean;
import com.bjjy.buildtalk.entity.PdfInfoEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.entity.ThemePdfBean;
import com.bjjy.buildtalk.utils.AllUtils;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MultiImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TopticDetailActivity extends BaseActivity<TopticDetailPresenter> implements TopticDetailContract.View, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.item_face_iv)
    CircleImageView mItemFaceIv;
    @BindView(R.id.item_name_tv)
    TextView mItemNameTv;
    @BindView(R.id.item_job_tv)
    TextView mItemJobTv;
    @BindView(R.id.item_more_iv)
    ImageView mItemMoreIv;
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
    ImageView mShareIv;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;
    @BindView(R.id.record_ll)
    LinearLayout mRecordLl;
    @BindView(R.id.emptyView)
    NestedScrollView mEmptyView;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_toptic_detail;
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
        KeyboardUtils.registerSoftInputChangedListener(this, height -> {
            if (height <= 0) {
                mRecordLl.setVisibility(View.VISIBLE);
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
        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mCommentAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void handlerThemeInfo(ThemeInfoEntity.ThemeInfoBean themeInfoEntity) {
        this.themeInfoEntity = themeInfoEntity;
        mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" + mPresenter.mDataManager.getUser().getUser_id() + "&theme_id=" + mTheme_id;
        mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) + "&response_type=code&scope=snsapi_userinfo&state=theme#wechat_redirect";
        Glide.with(this).load(themeInfoEntity.getHeadImage()).into(mItemFaceIv);
        mItemNameTv.setText(themeInfoEntity.getName());
        mThemeCountParise = themeInfoEntity.getThemeCountParise();
        if ("1".equals(themeInfoEntity.getIs_circleMaster())) {
            mItemJobTv.setVisibility(View.VISIBLE);
        }else{
            mItemJobTv.setVisibility(View.GONE);
        }
        mItemTimeTv.setText(themeInfoEntity.getPublish_time());
        mItemContentTv.setText(themeInfoEntity.getTheme_content());

        if (themeInfoEntity.getTheme_pdf().size() > 0){
            mItemGridView.setVisibility(View.GONE);
            mPdfRecyclerView.setVisibility(View.VISIBLE);
        }

        List<ThemeImageBean> themeImageBeanList = themeInfoEntity.getTheme_image();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < themeImageBeanList.size(); i++) {
            list.add(themeImageBeanList.get(i).getPic_url());
        }
        mItemGridView.setList(list);
        mItemGridView.setOnItemClickListener((view, position, imageViews) -> AllUtils.startImagePage(this, list, Arrays.asList(imageViews), position));

        List<ThemePdfBean> theme_pdf = themeInfoEntity.getTheme_pdf();
        List<PdfInfoEntity> list1 = new ArrayList<>();
        for (int i = 0; i < theme_pdf.size(); i++) {
            list1.add(new PdfInfoEntity(theme_pdf.get(i).getPdf_name(),theme_pdf.get(i).getPdf_url()));
        }
        mPdfRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PdfVewAdapter pdfVewAdapter = new PdfVewAdapter(R.layout.adapter_pdf_view, list1);
        mPdfRecyclerView.setAdapter(pdfVewAdapter);
        pdfVewAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<PdfInfoEntity> data =  adapter.getData();
            Intent intent = new Intent(TopticDetailActivity.this, PDFViewerActivity.class);
            intent.putExtra("data", data.get(position));
            intent.putExtra("theme_id", themeInfoEntity.getTheme_id()+"");
            intent.putExtra("isCollect", 0 == themeInfoEntity.getIs_collect());
            startActivity(intent);
        });

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
        mPraiseTv.setText(themeInfoEntity.getThemeCountParise() + "赞");
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
                mPresenter.publishComment(mRecordEt.getText().toString().trim(), themeInfoEntity.getTheme_id());
                mRecordEt.clearFocus();
                mRecordEt.getText().clear();
                mRecordLl.setVisibility(View.VISIBLE);
                isGone = !isGone;
                KeyboardUtils.hideSoftInput(this);
            }
            return false;
        });
    }

    @OnClick({R.id.item_more_iv, R.id.praise_ll, R.id.share_iv, R.id.record_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_more_iv:
                if (themeInfoEntity != null)
                    LoginHelper.login(this, mPresenter.mDataManager, () -> showEditDialog(themeInfoEntity));
                break;
            case R.id.praise_ll:
                if (themeInfoEntity != null) {
                    LoginHelper.login(this, mPresenter.mDataManager, () -> mPresenter.praise(themeInfoEntity.getTheme_id() + "", "1", null));
                }
                break;
            case R.id.share_iv:
                if (themeInfoEntity != null){
                    if (themeInfoEntity.getTheme_image().size() > 0){
                        mPresenter.getThumb(themeInfoEntity.getTheme_image().get(0).getPic_url(), themeInfoEntity);
                    }else {
                        mPresenter.getThumb(themeInfoEntity.getHeadImage(), themeInfoEntity);
                    }
                }
                break;
            case R.id.record_ll:
                LoginHelper.login(this, mPresenter.mDataManager, () -> {
                    if (!isGone) {
                        mRecordLl.setVisibility(View.GONE);
                        mRecordEt.setFocusable(true);
                        mRecordEt.setFocusableInTouchMode(true);
                        mRecordEt.requestFocus();
                        KeyboardUtils.showSoftInput(TopticDetailActivity.this);
                        isGone = !isGone;
                    }
                });
        }
    }

    @Override
    public void handlerThumbSuccess(String thumb_url, ThemeInfoEntity.ThemeInfoBean themeInfoEntity) {
        themePath = mPath1 + "theme_id=" + themeInfoEntity.getTheme_id() + "&circle_id=" + mCircle_id + "&num=1";
        DialogUtils.showShareDialog(this, themePath, mEndUrl,
                TextUtils.isEmpty(themeInfoEntity.getTheme_content()) ? mTitle : themeInfoEntity.getTheme_content(),
                thumb_url, themeInfoEntity.getTheme_content(), true);
    }

    private void showEditDialog(ThemeInfoEntity.ThemeInfoBean data) {
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
            mPresenter.collectTheme(data);
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
                mPraiseTv.setText(mThemeCountParise + "赞");
            } else {
                themeInfoEntity.setIs_parise(0);
                mPraiseIv.setImageResource(R.drawable.praise_def);
                if (mThemeCountParise > 0) {
                    mThemeCountParise--;
                    mPraiseTv.setText(mThemeCountParise + "赞");
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
}
