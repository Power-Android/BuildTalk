package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.ImgOptionEntity;
import com.bjjy.buildtalk.entity.PariseNickNameBean;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.ui.main.ViewPagerActivity;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MyGridAdapter;
import com.bjjy.buildtalk.weight.NoScrollGridView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TopticDetailActivity extends BaseActivity<TopticDetailPresenter> implements TopticDetailContract.View, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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
    NoScrollGridView mItemGridView;
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
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText(mTitle);
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
        mCommentAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void handlerThemeInfo(ThemeInfoEntity.ThemeInfoBean themeInfoEntity) {
        this.themeInfoEntity = themeInfoEntity;
        Glide.with(this).load(themeInfoEntity.getHeadImage()).into(mItemFaceIv);
        mItemNameTv.setText(themeInfoEntity.getName());
        mThemeCountParise = themeInfoEntity.getThemeCountParise();
        if ("1".equals(themeInfoEntity.getIs_circleMaster())) {
            mItemJobTv.setVisibility(View.VISIBLE);
        }
        mItemTimeTv.setText(themeInfoEntity.getPublish_time());
        mItemContentTv.setText(themeInfoEntity.getTheme_content());
        List<ThemeImageBean> themeImageBeanList = themeInfoEntity.getTheme_image();
        if (themeImageBeanList.size() == 4) {
            mItemGridView.setNumColumns(2);
        } else {
            mItemGridView.setNumColumns(3);
        }
        mItemGridView.setAdapter(new MyGridAdapter(themeImageBeanList, false));

        mItemGridView.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<ImageView> imgDatas = new ArrayList<>();
            for (int i = 0; i < mItemGridView.getChildCount(); i++) {
                RelativeLayout relativeLayout = (RelativeLayout) mItemGridView.getChildAt(i);
                ImageView imageView = relativeLayout.findViewById(R.id.image);
                imgDatas.add(imageView);
            }
            List<ImgOptionEntity> optionEntities = new ArrayList<>();
            int[] screenLocationS = new int[2];
            for (int i = 0; i < imgDatas.size(); i++) {
                ImageView img = imgDatas.get(i);
                //获取当前ImageView 在屏幕中的位置 宽高
                img.getLocationOnScreen(screenLocationS);
                ImgOptionEntity entity = new
                        ImgOptionEntity(screenLocationS[0], screenLocationS[1], img.getWidth(), img.getHeight());
                entity.setImgUrl(themeImageBeanList.get(i).getPic_url());
                optionEntities.add(entity);
            }

            Intent bIntent = new Intent(this, ViewPagerActivity.class);
            bIntent.putExtra("positon", position);
            bIntent.putExtra("optionEntities", (Serializable) optionEntities);
            startActivity(bIntent);
            //取消原有默认的Activity到Activity的过渡动画
            overridePendingTransition(0, 0);
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
        if (themeInfoBean.getComment_content().size() > 0){
            mPage_count = themeInfoBean.getPage_count();
            mCountCommentNum = themeInfoBean.getCountCommentNum();
            mRecordNumTv.setText(mCountCommentNum + "");
            mRecordEt.setHint(mCountCommentNum + "评论");
            mComment_content = themeInfoBean.getComment_content();
            if (isRefresh) {
                mCommentAdapter.setNewData(themeInfoBean.getComment_content());
            } else {
                mCommentAdapter.addData(themeInfoBean.getComment_content());
            }
        }

        mRecordEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND){
                if (TextUtils.isEmpty(mRecordEt.getText().toString().trim())) {
                    ToastUtils.showShort("请输入评论内容");
                    return false;
                }
                mPresenter.publishComment(mRecordEt.getText().toString().trim(), themeInfoEntity.getTheme_id());
                mRecordEt.clearFocus();
                mRecordEt.getText().clear();
                KeyboardUtils.hideSoftInput(this);
            }
            return false;
        });
    }

    @OnClick({R.id.item_more_iv, R.id.praise_ll, R.id.share_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_more_iv:
                showEditDialog(themeInfoEntity);
                break;
            case R.id.praise_ll:
                mPresenter.praise(themeInfoEntity.getTheme_id() + "", "1", null);
                break;
            case R.id.share_iv:
                break;
        }
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
        TextView edit_tv = mEditDailog.getView(R.id.edit_tv);
        edit_tv.setOnClickListener(v -> {
            if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                Intent intent = new Intent(TopticDetailActivity.this, PublishActivity.class);
                intent.putExtra("themeInfo", data);
                intent.putExtra("circle_name", mTitle);
                startActivity(intent);
            }
            mEditDailog.dismiss();
        });
        TextView delete_tv = mEditDailog.getView(R.id.delete_tv);
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
                mPresenter.praise(mComment_content.get(i).getComment_id() + "", "2",  mComment_content);
                break;
            case R.id.item_delete_iv:
                mPresenter.deleteComment(mComment_content.get(i).getComment_id() + "", mComment_content);
                break;
        }
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
            if (mComment_content.get(itemPosition).getIsPraise() == 1){
                mComment_content.get(itemPosition).setIsPraise(0);
                if (countpraise > 0){
                    countpraise--;
                    mComment_content.get(itemPosition).setCountpraise(countpraise);
                }
            }else {
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
        if (mCountCommentNum > 0){
            mCountCommentNum--;
            mRecordNumTv.setText(mCountCommentNum + "");
            mRecordEt.setHint(mCountCommentNum + "评论");
        }
    }

    @Override
    public void handlerCommentSuccess(List<CommentContentBean> commentInfo) {
        mPresenter.commentList(mTheme_id, 1, true);
    }
}
