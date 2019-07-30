package com.bjjy.buildtalk.ui.circle;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleTopticAdapter;
import com.bjjy.buildtalk.adapter.ThemeTypeAdapter;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.entity.ThemeTypeEntity;
import com.bjjy.buildtalk.ui.talk.CircleManDetailActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MyBadgeViewPagerAdapter;
import com.bjjy.buildtalk.weight.tablayout.TabLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TopticCircleActivity extends BaseActivity<TopticCirclePresenter> implements
        TopticCircleContract.View, AppBarLayout.OnOffsetChangedListener, OnRefreshLoadMoreListener,
        BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.toptic_bg)
    ImageView mTopticBg;
    @BindView(R.id.pre_face_iv)
    RoundedImageView mPreFaceIv;
    @BindView(R.id.pre_title_tv)
    TextView mPreTitleTv;
    @BindView(R.id.pre_name_tv)
    TextView mPreNameTv;
    @BindView(R.id.pre_date_tv)
    TextView mPreDateTv;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.view1)
    TextView mView1;
    @BindView(R.id.expand_iv)
    ImageView mExpandIv;
    @BindView(R.id.recommend_tv)
    TextView mRecommendTv;
    @BindView(R.id.min_rl)
    RelativeLayout mMinRl;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.share_iv)
    ImageView mShareIv;
    @BindView(R.id.top_title_rl)
    RelativeLayout mTopTitleRl;
    @BindView(R.id.tab_rl)
    RelativeLayout mTabRl;
    @BindView(R.id.viewpager_rl)
    RelativeLayout mViewpagerRl;
    @BindView(R.id.join_tv)
    TextView mJoinTv;
    @BindView(R.id.pre_top_rl)
    RelativeLayout mPreTopRl;
    @BindView(R.id.pre_mid_rl)
    RelativeLayout mPreMidRl;
    @BindView(R.id.formal_title_tv)
    TextView mFormalTitleTv;
    @BindView(R.id.formal_name_tv)
    TextView mFormalNameTv;
    @BindView(R.id.formal_date_tv)
    TextView mFormalDateTv;
    @BindView(R.id.formal_rl)
    RelativeLayout mFormalRl;
    @BindView(R.id.screen_tv)
    TextView mScreenTv;
    @BindView(R.id.screen_rl)
    RelativeLayout mScreenRl;
    @BindView(R.id.pre_share_iv)
    ImageView mPreShareIv;
    @BindView(R.id.more_iv)
    ImageView mMoreIv;
    @BindView(R.id.publis_rl)
    RelativeLayout mPublisRl;
    @BindView(R.id.formal_face_iv)
    CircleImageView mFormalFaceIv;

    CoordinatorLayout.LayoutParams mLayoutParams;

    private boolean isExpand = false;
    private int rotationAngle = 180;

    private MyBadgeViewPagerAdapter mPagerAdapter;
    private List<View> mViews = new ArrayList<>();
    private String mCircle_id;
    private CircleInfoEntity mCircleInfoEntity;
    private String mIsJoin;
    private List<ThemeInfoEntity.ThemeInfoBean> mThemeInfoList = new ArrayList<>();
    private SmartRefreshLayout mRefreshLayout;
    private int page = 1, pageCount = 1;
    private String type = "1";
    private CircleTopticAdapter mTopticAdapter;
    private BaseDialog mMInputDialog, mEditDailog;
    private TextView mCollect_tv;
    private Intent mIntent;
    private BaseDialog mDeleteDialog;
    private int mViewpager_position;
    private String mUrl;
    private NestedScrollView mEmptyView;
    private RecyclerView mRecyclerView;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(RefreshEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.TOPTIC_REFRESH)) {
            onRefresh(mRefreshLayout);
        }
        if (TextUtils.equals(eventBean.getMsg(), Constants.TOPTIC_REFRESH_ALL)) {
            mPresenter.CircleInfo(mCircle_id);
        }
        if (TextUtils.equals(eventBean.getMsg(), Constants.QUIT_CIRCLE)) {
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_toptic_circle;
    }

    @Override
    protected void initView() {
        mCircle_id = getIntent().getStringExtra("circle_id");
        StatusBarUtils.changeStatusBar(this, true, false);
        mTopTitleRl.setPadding(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        //设置appbar滚动布局的最小高度 因为getHeight可能为0，所以直接加上导航栏和tablayout的高度
        mMinRl.setMinimumHeight(StatusBarUtils.getStatusBarHeight() + (int) getResources().getDimension(R.dimen.dp_44));
        mAppBarLayout.addOnOffsetChangedListener(this);
        mUrl = "https://jt.chinabim.com/share/#/topic/" + mCircle_id + "?suid=" + mPresenter.mDataManager.getUser().getUser_id();
        EventBus.getDefault().register(this);
        KeyboardUtils.registerSoftInputChangedListener(this, height -> {
            if (height == 0 && mMInputDialog != null) {
                mMInputDialog.dismiss();
            }
        });
    }

    @Override
    protected void initEventAndData() {
        mPresenter.CircleInfo(mCircle_id);
    }

    @Override
    public void handlerCircleInfo(CircleInfoEntity circleInfoEntity) {
        this.mCircleInfoEntity = circleInfoEntity;
        mIsJoin = circleInfoEntity.getIsJoin();
        mPresenter.tabData();
        if (TextUtils.equals("0", mIsJoin)) {//未加入
            mFormalRl.setVisibility(View.GONE);
            mPreTopRl.setVisibility(View.VISIBLE);
            mShareIv.setVisibility(View.GONE);
            mMoreIv.setVisibility(View.GONE);
            mPreShareIv.setVisibility(View.VISIBLE);
            mJoinTv.setVisibility(View.VISIBLE);
            mPublisRl.setVisibility(View.GONE);
            mScreenRl.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.circle_bg_icon).into(mTopticBg);
            if (circleInfoEntity.getCircleInfo().getCircle_image() != null) {
                Glide.with(this).load(circleInfoEntity.getCircleInfo().getCircle_image().getPic_url()).into(mPreFaceIv);
            }
            mPreTitleTv.setText(circleInfoEntity.getCircleInfo().getCircle_name());
            mPreNameTv.setText("圈主：" + circleInfoEntity.getCircleInfo().getName());
            mPreDateTv.setText("创建 " + circleInfoEntity.getCircleInfo().getCreate_day() + "天");
            List<String> circle_tags = circleInfoEntity.getCircleInfo().getCircle_tags();
            mFlowLayout.setAdapter(new TagAdapter<String>(circle_tags) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    if ("".equals(s)) {
                        return new View(TopticCircleActivity.this);
                    }
                    TextView tv = (TextView) LayoutInflater.from(TopticCircleActivity.this)
                            .inflate(R.layout.flow_blue_layout_tv, parent, false);
                    tv.setText(s);
                    return tv;
                }
            });
            mRecommendTv.setText(circleInfoEntity.getCircleInfo().getCircle_desc());
            mRecommendTv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (mRecommendTv.getLineCount() < 2) {
                        mExpandIv.setVisibility(View.GONE);
                    } else {
                        mExpandIv.setVisibility(View.VISIBLE);
                    }
                    //这个回调会调用多次，获取完行数记得注销监听
                    mRecommendTv.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        } else {//已加入
            mPreTopRl.setVisibility(View.GONE);
            mFormalRl.setVisibility(View.VISIBLE);
            mPreShareIv.setVisibility(View.GONE);
            mShareIv.setVisibility(View.VISIBLE);
            mMoreIv.setVisibility(View.VISIBLE);
            mJoinTv.setVisibility(View.GONE);
            mPublisRl.setVisibility(View.VISIBLE);
            mScreenRl.setVisibility(View.VISIBLE);
            if (circleInfoEntity.getCircleInfo().getCircle_image() != null) {
                Glide.with(this).load(circleInfoEntity.getCircleInfo().getCircle_image().getPic_url()).into(mTopticBg);
            }
            Glide.with(this).load(circleInfoEntity.getCircleInfo().getMaster_pic()).into(mFormalFaceIv);
            mFormalTitleTv.setText(circleInfoEntity.getCircleInfo().getCircle_name());
            mFormalNameTv.setText("圈主：" + circleInfoEntity.getCircleInfo().getName());
            mFormalDateTv.setText("创建 " + circleInfoEntity.getCircleInfo().getCreate_day() + "天");
        }
    }

    @Override
    public void handlerTab(List<String> titleList, List<View> views, List<Integer> badgeCountList) {
        this.mViews = views;
        mPagerAdapter = new MyBadgeViewPagerAdapter(titleList, views, badgeCountList);
        mViewpager.setAdapter(mPagerAdapter);
        mTablayout.setupWithViewPager(mViewpager);
        mViewpager.addOnPageChangeListener(this);
        setUpTabBadge();

        mRecyclerView = views.get(0).findViewById(R.id.recycler_view);
        mEmptyView = views.get(0).findViewById(R.id.emptyView);
        mRefreshLayout = views.get(0).findViewById(R.id.refresh_Layout);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
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
        mPresenter.themeInfo(mCircle_id, page, type, false);
    }

    @Override
    public void handlerThemeInfo(ThemeInfoEntity themeInfoEntity, boolean isRefresh) {
        if (mThemeInfoList != null) {
            mThemeInfoList.clear();
        }
        pageCount = themeInfoEntity.getPage_count();
        mThemeInfoList = themeInfoEntity.getThemeInfo();

        if ("0".equals(mIsJoin) && mThemeInfoList.size() > 5) {
            mThemeInfoList = mThemeInfoList.subList(0, 5);
        }
        if (mThemeInfoList.size() > 0) {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            if (isRefresh) {
                mTopticAdapter.setNewData(mThemeInfoList);
            } else {
                mTopticAdapter.addData(mThemeInfoList);
            }
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置Tablayout上的标题的角标
     */
    private void setUpTabBadge() {
        for (int i = 0; i < mViews.size(); i++) {
            TabLayout.Tab tab = mTablayout.getTabAt(i);

            // 更新Badge前,先remove原来的customView,否则Badge无法更新
            View customView = tab.getCustomView();
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(customView);
                }
            }
            // 更新CustomView
            tab.setCustomView(mPagerAdapter.getTabItemView(i));
        }

        // 需加上以下代码,不然会出现更新Tab角标后,选中的Tab字体颜色不是选中状态的颜色
        mTablayout.getTabAt(mTablayout.getSelectedTabPosition()).getCustomView().setSelected(true);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset > 0) {
            if (mCircleInfoEntity.getCircleInfo() != null) {
                mTitleTv.setText(mCircleInfoEntity.getCircleInfo().getCircle_name());
            }
            mTitleTv.setVisibility(View.VISIBLE);
            mTopTitleRl.setBackgroundColor(getResources().getColor(R.color.blue_mid));
        } else {
            mTitleTv.setVisibility(View.GONE);
            mTopTitleRl.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    @OnClick({R.id.expand_iv, R.id.back_iv, R.id.share_iv, R.id.join_tv, R.id.pre_share_iv, R.id.more_iv, R.id.screen_rl, R.id.publis_rl, R.id.formal_face_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.expand_iv:
                mLayoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mMinRl.getLayoutParams();
                mExpandIv.animate().setDuration(200).rotation(rotationAngle);
                rotationAngle += 180;
                if (isExpand) {
                    mLayoutParams.height = CoordinatorLayout.LayoutParams.WRAP_CONTENT;
                    mAppBarLayout.setLayoutParams(mLayoutParams);
                    mTabRl.setVisibility(View.VISIBLE);
                    mViewpagerRl.setVisibility(View.VISIBLE);
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                    mMinRl.setLayoutParams(params);
                    mRecommendTv.setMaxLines(2);
                } else {
                    mLayoutParams.height = CoordinatorLayout.LayoutParams.MATCH_PARENT;
                    mAppBarLayout.setLayoutParams(mLayoutParams);
                    mTabRl.setVisibility(View.GONE);
                    mViewpagerRl.setVisibility(View.GONE);
                    params.setScrollFlags(0);//不能伸缩
                    mMinRl.setLayoutParams(params);
                    mRecommendTv.setMaxLines(Integer.MAX_VALUE);
                }
                isExpand = !isExpand;
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.share_iv:
                DialogUtils.showShareDialog(this, mUrl, mCircleInfoEntity.getCircleInfo().getCircle_name(),
                        mCircleInfoEntity.getCircleInfo().getCircle_image().getPic_url(), mCircleInfoEntity.getCircleInfo().getCircle_desc());
                break;
            case R.id.join_tv:
                LoginHelper.login(this, mPresenter.mDataManager, () -> mPresenter.joinCircle(mCircle_id));
                break;
            case R.id.pre_share_iv:
                DialogUtils.showShareDialog(this, mUrl, mCircleInfoEntity.getCircleInfo().getCircle_name(),
                        mCircleInfoEntity.getCircleInfo().getCircle_image().getPic_url(), mCircleInfoEntity.getCircleInfo().getCircle_desc());
                break;
            case R.id.more_iv:
                mIntent = new Intent(this, CircleInfoActivity.class);
                mIntent.putExtra("circle_id", mCircle_id);
                mIntent.putExtra("operate_user", mCircleInfoEntity.getCircleInfo().getUser_id() + "");
                mIntent.putExtra("jieshao", mCircleInfoEntity.getCircleInfo().getCircle_desc());
                startActivity(mIntent);
                break;
            case R.id.screen_rl:
                if (mViewpager_position == 0) {
                    showThemeTypeDialog();
                } else {
                    ToastUtils.showShort("暂未开放");
                }
                break;
            case R.id.publis_rl:
                mIntent = new Intent(this, PublishActivity.class);
                mIntent.putExtra("circle_id", mCircle_id);
                startActivity(mIntent);
                break;
            case R.id.formal_face_iv:
                if (mCircleInfoEntity.getCircleInfo().getIs_author() == 1) {
                    mIntent = new Intent(this, MasterDetailActivity.class);
                    mIntent.putExtra("user_id", mCircleInfoEntity.getCircleInfo().getUser_id() + "");
                    startActivity(mIntent);
                } else {
                    mIntent = new Intent(this, CircleManDetailActivity.class);
                    mIntent.putExtra("user_id", mCircleInfoEntity.getCircleInfo().getUser_id() + "");
                    startActivity(mIntent);
                }
                break;
        }
    }

    @Override
    public void handlerJoinSuccess(IEntity iEntity) {
        initEventAndData();
    }

    private void showThemeTypeDialog() {
        BaseDialog mDialog = new BaseDialog.Builder(this)
                .setViewId(R.layout.dialog_theme_type)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.nomal_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx((int) getResources().getDimension(R.dimen.dp_309), ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        List<ThemeTypeEntity> list = new ArrayList<>();
        list.add(new ThemeTypeEntity("全部主题", R.drawable.qbzt_icon));
        list.add(new ThemeTypeEntity("图片主题", R.drawable.tpzt_icon));
        if (!mPresenter.mDataManager.getUser().getUser_id().equals(mCircleInfoEntity.getCircleInfo().getUser_id() + "")) {
            list.add(new ThemeTypeEntity("只看圈主", R.drawable.zkqz_icon));
        }
        list.add(new ThemeTypeEntity("我的主题", R.drawable.wdzt_icon));
        RecyclerView recyclerView = mDialog.getView(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        ThemeTypeAdapter typeAdapter = new ThemeTypeAdapter(R.layout.adapter_theme_type, list);
        recyclerView.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            List<ThemeTypeEntity> data = baseQuickAdapter.getData();
            page = 1;
            type = String.valueOf(i + 1);
            mScreenTv.setText(data.get(i).getName());
            mPresenter.themeInfo(mCircle_id, page, type, true);
            mDialog.dismiss();
        });
        mDialog.show();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < pageCount) {
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
                String mUrl = "https://jt.chinabim.com/share/#/theme/" + data.get(i).getUser_id() + "/" + data.get(i).getTheme_id();
                DialogUtils.showShareDialog(this, mUrl, mCircleInfoEntity.getCircleInfo().getCircle_name(),
                        data.get(i).getHeadImage(), data.get(i).getTheme_content());
                break;
            case R.id.more_tv:
                Intent intent = new Intent(this, TopticDetailActivity.class);
                intent.putExtra("title", mCircleInfoEntity.getCircleInfo().getCircle_name());
                intent.putExtra("theme_id", data.get(i).getTheme_id() + "");
                startActivity(intent);
                break;
            case R.id.item_face_iv:

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
                Intent intent = new Intent(TopticCircleActivity.this, PublishActivity.class);
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
        mMInputDialog = new BaseDialog.Builder(TopticCircleActivity.this)
                .setGravity(Gravity.BOTTOM)
                .setViewId(R.layout.dialog_input_layout)
                .setWidthHeightdp(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dp_129))
                .isOnTouchCanceled(true)
                .builder();
        EditText mInputEt = mMInputDialog.getView(R.id.comment_et);
        TextView publishTv = mMInputDialog.getView(R.id.publish_tv);
        mMInputDialog.setOnShowListener(dialog -> mInputEt.postDelayed(() -> {
            mInputEt.requestFocus();
            KeyboardUtils.showSoftInput(mInputEt);
        }, 200));
        mMInputDialog.setOnDismissListener(dialog -> {
            mInputEt.getText().clear();
            KeyboardUtils.toggleSoftInput();
        });
        mMInputDialog.show();
        publishTv.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mInputEt.getText().toString().trim())) {
                ToastUtils.showShort("请输入评论内容");
                return;
            }
            mPresenter.publishComment(mInputEt.getText().toString().trim(), String.valueOf(theme_id), i, data);
            if (mMInputDialog != null) {
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        KeyboardUtils.unregisterSoftInputChangedListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mViewpager_position = i;
        if (i == 0) {
            mScreenTv.setText("全部主题");
            if (TextUtils.equals("1", mIsJoin))
                mPublisRl.setVisibility(View.VISIBLE);
        } else {
            mScreenTv.setText("全部精华");
            mPublisRl.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
