package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.os.Handler;
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
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleTopticAdapter;
import com.bjjy.buildtalk.adapter.EditDialogAdapter;
import com.bjjy.buildtalk.adapter.ThemeTypeAdapter;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.PublishEvent;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.entity.ActivityEntity;
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
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TopticCircleActivity extends BaseActivity<TopticCirclePresenter> implements
        TopticCircleContract.View, AppBarLayout.OnOffsetChangedListener, OnRefreshLoadMoreListener,
        BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, ViewPager.OnPageChangeListener, CircleTopticAdapter.onCommentItemlistener {

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
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    CoordinatorLayout.LayoutParams mLayoutParams;

    private boolean isExpand = false;
    private int rotationAngle = 180;

    private MyBadgeViewPagerAdapter mPagerAdapter;
    private List<View> mViews = new ArrayList<>();
    private String mCircle_id;
    private CircleInfoEntity mCircleInfoEntity;
    private String mIsJoin;
    private List<ThemeInfoEntity.ThemeInfoBean> mThemeInfoList = new ArrayList<>();
    private List<ThemeInfoEntity.ThemeInfoBean> mEssenceInfoList = new ArrayList<>();
    //    private SmartRefreshLayout mRefreshLayout;
    private int page = 1, jhPage = 1, pageCount = 1, jhPageCount = 1;
    private String type = "1";
    private CircleTopticAdapter mTopticAdapter;
    private CircleTopticAdapter mTopticAdapter1;
    private BaseDialog mMInputDialog;
    private Intent mIntent;
    private BaseDialog mDeleteDialog;
    private int mViewpager_position;
    private String mUrl;
    private String mEndUrl;
    private NestedScrollView mEmptyView;
    private RecyclerView mRecyclerView;
    private String mPath;//话题圈
    private String circlePath;//话题圈拼接完成的url
    private String mPath1;//主题
    private String themePath;//主题拼接完成url
    private int mCountChoiceness_themeInfo;
    private RecyclerView mJhRecyclerView;
    private NestedScrollView mJhEmptyView;
    private String thumb_url;
    BottomSheetDialog mBottomSheetDialog;
    BottomSheetBehavior mBehavior;
    private View mView;
    BottomSheetDialog mEditDialog;
    BottomSheetBehavior mEditBehavior;
    private View mEditView;
    private List<String> mItemList;
    private EditDialogAdapter mAdapter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(RefreshEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.TOPTIC_REFRESH)) {
            onRefresh(mRefreshLayout);
        }
        if (TextUtils.equals(eventBean.getMsg(), Constants.TOPTIC_REFRESH_ALL)) {
            mPresenter.CircleInfo(mCircle_id);
        }
        if (TextUtils.equals(eventBean.getMsg(), Constants.INFO_REFRESH)) {
            mPresenter.CircleInfo(mCircle_id);
        }
        if (TextUtils.equals(eventBean.getMsg(), Constants.QUIT_CIRCLE)) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PublishEvent eventBean) {
        if (TextUtils.equals(eventBean.getType(), Constants.PUBLISH_ACTIVITY)) {
            ActivityEntity entity = new ActivityEntity();
            entity.setIs_show(1);
            entity.setPic_url(eventBean.getUrl());
            entity.setType_name("发表");
            new Handler().postDelayed(() -> DialogUtils.showActivityDialog(TopticCircleActivity.this, entity), 1500);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_toptic_circle;
    }

    @Override
    protected void onResume() {
        setIsMargin(true);
        super.onResume();
    }

    @Override
    protected void initView() {
        mCircle_id = getIntent().getStringExtra("circle_id");
        StatusBarUtils.changeStatusBar(this, true, false);
        mTopTitleRl.setPadding(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        //设置appbar滚动布局的最小高度 因为getHeight可能为0，所以直接加上导航栏和tablayout的高度
        mMinRl.setMinimumHeight(StatusBarUtils.getStatusBarHeight() + (int) getResources().getDimension(R.dimen.dp_44));
        mAppBarLayout.addOnOffsetChangedListener(this);
        mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" + mPresenter.mDataManager.getUser().getUser_id() + "&circle_id=" + mCircle_id;
        mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) + "&response_type=code&scope=snsapi_userinfo&state=topic#wechat_redirect";
        mPath = "pages/sub_circle/pages/circleDetails/circleDetails?";
        mPath1 = "pages/sub_circle/pages/subjectDetails/subjectDetails?";

        EventBus.getDefault().register(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.CircleInfo(mCircle_id);
    }

    @Override
    public void handlerCircleInfo(CircleInfoEntity circleInfoEntity) {
        this.mCircleInfoEntity = circleInfoEntity;
        mIsJoin = circleInfoEntity.getIsJoin();
        mCountChoiceness_themeInfo = circleInfoEntity.getCountChoiceness_themeInfo();
        mPresenter.tabData(circleInfoEntity);
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
        mJhRecyclerView = views.get(1).findViewById(R.id.jh_recycler_view);
        mJhEmptyView = views.get(1).findViewById(R.id.jh_emptyView);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        mTopticAdapter = new CircleTopticAdapter(mThemeInfoList, mIsJoin, this);
        mRecyclerView.setAdapter(mTopticAdapter);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mJhRecyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        mTopticAdapter1 = new CircleTopticAdapter(mEssenceInfoList, mIsJoin, this);
        mJhRecyclerView.setAdapter(mTopticAdapter1);
        ((SimpleItemAnimator) mJhRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mTopticAdapter.setOnItemClickListener(this);
        mTopticAdapter.setOnItemChildClickListener(this);
        mTopticAdapter.setCommentClickListener(this);

        mTopticAdapter1.setOnItemClickListener(this);
        mTopticAdapter1.setOnItemChildClickListener(this);
        mTopticAdapter1.setCommentClickListener(this);

        if (TextUtils.equals("0", mIsJoin)) {
            mRefreshLayout.setEnableLoadMore(false);
            View footerView = LayoutInflater.from(App.getContext()).inflate(R.layout.footer_circle_toptic, null, false);
            View footerView1 = LayoutInflater.from(App.getContext()).inflate(R.layout.footer_circle_toptic, null, false);
            mTopticAdapter.addFooterView(footerView);
            mTopticAdapter1.addFooterView(footerView1);
        } else {
            mRefreshLayout.setEnableLoadMore(true);
        }
        mPresenter.themeInfo(mCircle_id, page, type, false);
        mPresenter.essenceInfo(mCircle_id, jhPage, "1", false);
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

    @Override
    public void handlerEssenceInfo(ThemeInfoEntity themeInfoEntity, boolean isRefresh) {
        if (mEssenceInfoList != null) {
            mEssenceInfoList.clear();
        }
        jhPageCount = themeInfoEntity.getPage_count();
        mEssenceInfoList = themeInfoEntity.getThemeInfo();

        if ("0".equals(mIsJoin) && mEssenceInfoList.size() > 5) {
            mEssenceInfoList = mEssenceInfoList.subList(0, 5);
        }
        if (mEssenceInfoList.size() > 0) {
            mJhEmptyView.setVisibility(View.GONE);
            mJhRecyclerView.setVisibility(View.VISIBLE);
            if (isRefresh) {
                mTopticAdapter1.setNewData(mEssenceInfoList);
            } else {
                mTopticAdapter1.addData(mEssenceInfoList);
            }
        } else {
            mJhRecyclerView.setVisibility(View.GONE);
            mJhEmptyView.setVisibility(View.VISIBLE);
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
            mTopTitleRl.setBackgroundColor(getResources().getColor(R.color.colorAccent));
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
            case R.id.pre_share_iv:
                circlePath = mPath + "circle_id=" + mCircle_id + "&num=1";
                showShareDialog(circlePath, mEndUrl, mCircleInfoEntity.getCircleInfo().getCircle_name(),
                        mCircleInfoEntity.getCircleInfo().getCircle_image().getPic_url(),
                        mCircleInfoEntity.getCircleInfo().getCircle_desc(), true, false, -1, null);
                break;
            case R.id.join_tv:
                LoginHelper.getInstance().login(this, mPresenter.mDataManager, () -> mPresenter.joinCircle(mCircle_id));
                break;
            case R.id.more_iv:
                mIntent = new Intent(this, CircleInfoActivity.class);
                mIntent.putExtra("type", "toptic");
                mIntent.putExtra("circle_id", mCircle_id);
                mIntent.putExtra("operate_user", mCircleInfoEntity.getCircleInfo().getUser_id() + "");
                mIntent.putExtra("jieshao", mCircleInfoEntity.getCircleInfo().getCircle_desc());
                startActivity(mIntent);
                break;
            case R.id.screen_rl:
                showThemeTypeDialog();
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
        if (mViewpager_position == 0) {
            if (page < pageCount) {
                page++;
                mPresenter.themeInfo(mCircle_id, page, type, false);
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        } else {
            if (jhPage < jhPageCount) {
                jhPage++;
                mPresenter.essenceInfo(mCircle_id, jhPage, "1", false);
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mViewpager_position == 0) {
            page = 1;
            mPresenter.themeInfo(mCircle_id, page, type, true);
        } else {
            jhPage = 1;
            mPresenter.essenceInfo(mCircle_id, jhPage, "1", true);
        }
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<ThemeInfoEntity.ThemeInfoBean> data = baseQuickAdapter.getData();
        if ("1".equals(mIsJoin)) {
            Intent intent = new Intent(this, TopticDetailActivity.class);
            intent.putExtra("title", mCircleInfoEntity.getCircleInfo().getCircle_name());
            intent.putExtra("theme_id", data.get(i).getTheme_id() + "");
            intent.putExtra("circle_id", mCircle_id);
            startActivity(intent);
        } else {
            ToastUtils.showShort("加入圈子，方可查看~");
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        /**
         * * 先判断是主题的item还是精华的item
         * 1.如果这条主题是精华，那么做任何操作都要刷新精华列表的数据
         * 2.如果这条主题不是精华，那么只有在点击加精/取消加精的时候，刷新精华列表的数据
         * 3.如果这是精华的主题，那么做任何操作都要刷新单个主题的数据
         */
        List<ThemeInfoEntity.ThemeInfoBean> data = baseQuickAdapter.getData();
        switch (view.getId()) {
            case R.id.item_more_iv://编辑
                if (data.get(i).getTheme_image().size() > 0) {
                    mPresenter.getThumb(data.get(i).getTheme_image().get(0).getPic_url(), data, i, true);
                } else {
                    mPresenter.getThumb(data.get(i).getHeadImage(), data, i, true);
                }
                break;
            case R.id.item_praise_iv://点赞
                mPresenter.praise(data, i);
                break;
            case R.id.item_comment_iv://评论 评论主题，commentid传空
                DialogUtils.showCommentDialog(i, data.get(i).getTheme_id(), "", i, data, "", "", this, mPresenter, null);
                break;
            case R.id.item_share_iv://分享
                if (data.get(i).getTheme_image().size() > 0) {
                    mPresenter.getThumb(data.get(i).getTheme_image().get(0).getPic_url(), data, i, false);
                } else {
                    mPresenter.getThumb(data.get(i).getHeadImage(), data, i, false);
                }
                break;
            case R.id.more_tv://查看更多
            case R.id.content_more_tv:
                if ("1".equals(mIsJoin)) {
                    Intent intent = new Intent(this, TopticDetailActivity.class);
                    intent.putExtra("title", mCircleInfoEntity.getCircleInfo().getCircle_name());
                    intent.putExtra("theme_id", data.get(i).getTheme_id() + "");
                    intent.putExtra("circle_id", mCircle_id);
                    startActivity(intent);
                } else {
                    ToastUtils.showShort("加入圈子，方可查看~");
                }
                break;
            case R.id.item_face_iv:
                break;
            case R.id.item_shouqi_iv://收起
            case R.id.item_zhankai_iv://展开
                mPresenter.themeRetract(data.get(i).getTheme_id(), data.get(i).getIs_retract(), i);
                break;
        }
    }

    @Override
    public void handlerThumbSuccess(String thumb_url, List<ThemeInfoEntity.ThemeInfoBean> data, int i, boolean isEdit) {
        String mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" + mPresenter.mDataManager.getUser().getUser_id() + "&theme_id=" + data.get(i).getTheme_id();
        String mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) + "&response_type=code&scope=snsapi_userinfo&state=theme#wechat_redirect";
        themePath = mPath1 + "theme_id=" + data.get(i).getTheme_id() + "&circle_id=" + mCircle_id + "&num=1";
        if (isEdit){
            showEditDialog(data.get(i), i, data, mCircleInfoEntity,themePath, mEndUrl,TextUtils.isEmpty(data.get(i).getTheme_content()) ? mCircleInfoEntity.getCircleInfo().getCircle_name() : data.get(i).getTheme_content(),
                    thumb_url, data.get(i).getTheme_content(), true, true,
                    data.get(i).getTheme_id(), mCircle_id);
        }else {
            showShareDialog(themePath, mEndUrl,
                    TextUtils.isEmpty(data.get(i).getTheme_content()) ? mCircleInfoEntity.getCircleInfo().getCircle_name() : data.get(i).getTheme_content(),
                    thumb_url, data.get(i).getTheme_content(), true, true,
                    data.get(i).getTheme_id(), mCircle_id);
        }
    }

    /**
     * 刷新精华列表
     */
    private void refreshChoiceness() {
        jhPage = 1;
        mPresenter.essenceInfo(mCircle_id, jhPage, "1", true);
    }

    /**
     * @param adapterPosition 列表position
     * @param position        评论列表position
     * @param data
     * @param contentBeanList
     */
    @Override
    public void handlerCommentSuccess(int adapterPosition, int position, List<ThemeInfoEntity.ThemeInfoBean> data, List<CommentContentBean> contentBeanList) {
        data.get(adapterPosition).setComment_content(contentBeanList);
        if (mViewpager_position == 0) {//主题列表
            mTopticAdapter.notifyItemChanged(adapterPosition);
            //如果评论的这条主题是精华，那么刷新精华列表数据
            if (data.get(adapterPosition).getIs_choiceness() == 1) {
                refreshChoiceness();
            }
        } else {//精华列表,局部刷新主题数据
            mTopticAdapter1.notifyItemChanged(adapterPosition);
            int theme_id = data.get(adapterPosition).getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int i = 0; i < topticAdapterData.size(); i++) {
                if (theme_id == topticAdapterData.get(i).getTheme_id()) {
                    topticAdapterData.get(i).setComment_content(contentBeanList);
                    mTopticAdapter.notifyItemChanged(i);
                }
            }
        }
    }

    @Override
    public void handlerPraiseSuccess(List<ThemeInfoEntity.ThemeInfoBean> data, int i, PraiseEntity praiseEntity) {
        if (data.get(i).getIs_parise() == 0) {
            data.get(i).setIs_parise(1);
        } else {
            data.get(i).setIs_parise(0);
        }
        data.get(i).setParise_nickName(praiseEntity.getNickName());
        data.get(i).setCountParise(praiseEntity.getCountpraise());
        if (mViewpager_position == 0) {//主题列表
            mTopticAdapter.notifyItemChanged(i);
            //如果点赞的这条主题是精华，那么刷新精华列表数据
            if (data.get(i).getIs_choiceness() == 1) {
                refreshChoiceness();
            }
        } else {//精华列表,局部刷新主题数据
            mTopticAdapter1.notifyItemChanged(i);
            int theme_id = data.get(i).getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()) {
                    if (topticAdapterData.get(j).getIs_parise() == 0) {
                        topticAdapterData.get(j).setIs_parise(1);
                    } else {
                        topticAdapterData.get(j).setIs_parise(0);
                    }
                    topticAdapterData.get(j).setParise_nickName(praiseEntity.getNickName());
                    mTopticAdapter.notifyItemChanged(j);
                }
            }
        }
    }

    @Override
    public void handlerCollectSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i) {
        if (0 == data.getIs_collect()) {
            data.setIs_collect(1);
            ToastUtils.showCollect("收藏成功", getResources().getDrawable(R.drawable.collect_success_icon));
        } else {
            data.setIs_collect(0);
            ToastUtils.showCollect("取消收藏", getResources().getDrawable(R.drawable.collect_cancle_icon));
        }
        if (mViewpager_position == 0) {//主题列表
            mTopticAdapter.notifyItemChanged(i);
            //如果点赞的这条主题是精华，那么刷新精华列表数据
            if (data.getIs_choiceness() == 1) {
                refreshChoiceness();
            }
        } else {//精华列表,局部刷新主题数据
            mTopticAdapter1.notifyItemChanged(i);
            int theme_id = data.getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()) {
                    if (0 == topticAdapterData.get(j).getIs_collect()) {
                        topticAdapterData.get(j).setIs_collect(1);
                    } else {
                        topticAdapterData.get(j).setIs_collect(0);
                    }
                    mTopticAdapter.notifyItemChanged(j);
                }
            }
        }

    }

    @Override
    public void handlerDeleteSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        list.remove(i);
        if (mViewpager_position == 0) {//主题列表
            mTopticAdapter.notifyItemRemoved(i);
            //如果点赞的这条主题是精华，那么刷新精华列表数据
            if (data.getIs_choiceness() == 1) {
                refreshChoiceness();
            }
        } else {//精华列表,局部刷新主题数据
            mTopticAdapter1.notifyItemRemoved(i);
            int theme_id = data.getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()) {
                    topticAdapterData.remove(j);
                    mTopticAdapter.notifyItemRemoved(j);
                }
            }
        }
    }

    @Override
    public void handlerChoicenessSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i) {
        if (0 == data.getIs_choiceness()) {
            data.setIs_choiceness(1);
            mPagerAdapter.setCount(++mCountChoiceness_themeInfo);
            ToastUtils.showShort("主题加精成功");
        } else {
            data.setIs_choiceness(0);
            mPagerAdapter.setCount(--mCountChoiceness_themeInfo);
            ToastUtils.showShort("取消精华成功");
        }
        if (mViewpager_position == 0) {//主题列表
            mTopticAdapter.notifyItemChanged(i);
            refreshChoiceness();
        } else {//精华列表,局部刷新主题数据
            mTopticAdapter1.remove(i);
            mTopticAdapter1.notifyItemChanged(i);
            int theme_id = data.getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()) {
                    topticAdapterData.get(j).setIs_choiceness(0);
                }
            }
        }
        setUpTabBadge();
    }

    @Override
    public void handlerTopOperateSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i) {
        if (0 == data.getIs_top()) {
            ToastUtils.showShort("主题置顶成功");
        } else {
            ToastUtils.showShort("取消置顶成功");
        }
        onRefresh(mRefreshLayout);
    }

    @Override
    public void handleruserShieldRecordSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        list.remove(i);
        if (mViewpager_position == 0) {//主题列表
            mTopticAdapter.notifyItemRemoved(i);
            //如果点赞的这条主题是精华，那么刷新精华列表数据
            if (data.getIs_choiceness() == 1) {
                refreshChoiceness();
            }
        } else {//精华列表,局部刷新主题数据
            mTopticAdapter1.notifyItemRemoved(i);
            int theme_id = data.getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()) {
                    topticAdapterData.remove(j);
                    mTopticAdapter.notifyItemRemoved(j);
                }
            }
        }
    }

    @Override
    public void handlerRetractSuccess(int i) {
        onRefresh(mRefreshLayout);
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
            mScreenRl.setVisibility(View.VISIBLE);
            if (TextUtils.equals("1", mIsJoin))
                mPublisRl.setVisibility(View.VISIBLE);
        } else {
            mScreenRl.setVisibility(View.GONE);
            mScreenTv.setText("全部精华");
            mPublisRl.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCommentClick(int adapterPosition, CircleTopticAdapter.CommentAdapter adapter, View view, int position, List<ThemeInfoEntity.ThemeInfoBean> data) {
        if (!String.valueOf(adapter.getData().get(position).getUser_id()).equals(mPresenter.mDataManager.getUser().getUser_id())) {
            DialogUtils.showCommentDialog(adapterPosition, adapter.getData().get(position).getTheme_id(), adapter.getData().get(position).getParentCommentId(),
                    position, data, adapter.getData().get(position).getComment_id() + "", adapter.getData().get(position).getName(),
                    this, mPresenter, null);
        }
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
                DialogUtils.shareSmallProgram(url, imgUrl, title, desc, TopticCircleActivity.this, SHARE_MEDIA.WEIXIN);
            } else {
                DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, TopticCircleActivity.this, SHARE_MEDIA.WEIXIN);
            }
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.wechat_circle_tv).setOnClickListener(v -> {
            DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, TopticCircleActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.discover_tv).setOnClickListener(v ->{
            mPresenter.shareTheme(theme_id, "0");
            EventBus.getDefault().post(new RefreshEvent(Constants.VIDEO_REFRESH));
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.cancle_tv).setOnClickListener(v -> mBottomSheetDialog.dismiss());
    }

    public void showEditDialog(ThemeInfoEntity.ThemeInfoBean data, int i,
                               List<ThemeInfoEntity.ThemeInfoBean> list,
                               CircleInfoEntity circleInfoEntity,
                               String url, String weburl, String title, String imgUrl,
                               String desc, boolean isSmall, boolean isVisible, int theme_id, String circle_id) {
        if (mEditDialog == null) {
            mEditDialog = new BottomSheetDialog(this, R.style.bottom_sheet_dialog);
            mEditDialog.getWindow().getAttributes().windowAnimations =
                    R.style.bottom_sheet_dialog;
            mEditDialog.setCancelable(true);
            mEditDialog.setCanceledOnTouchOutside(true);
            mEditView = getLayoutInflater().inflate(R.layout.dialog_theme_edit, null);
            mEditDialog.setContentView(mEditView);
            mEditBehavior = BottomSheetBehavior.from((View) mEditView.getParent());
            mEditBehavior.setSkipCollapsed(true);
//            int peekHeight = getResources().getDisplayMetrics().heightPixels;
            //设置默认弹出高度为屏幕的0.4倍
//            mBehavior.setPeekHeight((int)(0.4 * peekHeight));
            mItemList = new ArrayList<>();
            RecyclerView recyclerView = mEditView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            mAdapter = new EditDialogAdapter(R.layout.adapter_edit_dialog, mItemList, data);
            recyclerView.setAdapter(mAdapter);
        }
        mEditView.findViewById(R.id.discover_tv).setVisibility(isVisible ? View.VISIBLE : View.GONE);

        if (mPresenter.mDataManager.getUser().getUser_id().equals(circleInfoEntity.getCircleInfo().getUser_id() + "")) {//如果是圈主
            if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                //如果是自己的主题----收藏、修改、置顶、加精、删除
                mItemList.clear();
                mItemList.add("收藏");
                mItemList.add("修改");
                mItemList.add("置顶");
                mItemList.add("加精");
                mItemList.add("删除");
                mAdapter.setNewData(mItemList);
            } else {
                //不是自己的主题----收藏、置顶、加精、不喜欢、投诉
                mItemList.clear();
                mItemList.add("收藏");
                mItemList.add("置顶");
                mItemList.add("加精");
                mItemList.add("不喜欢");
                mItemList.add("投诉");
                mAdapter.setNewData(mItemList);
            }
        } else {//如果是成员
            if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                //如果是自己的主题----收藏、修改、删除
                mItemList.clear();
                mItemList.add("收藏");
                mItemList.add("删除");
                mItemList.add("修改");
                mAdapter.setNewData(mItemList);
            } else {
                //不是自己的主题----收藏、不喜欢、投诉
                mItemList.clear();
                mItemList.add("收藏");
                mItemList.add("不喜欢");
                mItemList.add("投诉");
                mAdapter.setNewData(mItemList);
            }
        }
        mAdapter.setOnItemClickListener((adapter1, view, position) -> {
            List<String> item = adapter1.getData();
            switch (item.get(position)) {
                case "收藏":
                    mPresenter.collectTheme(data, i);
                    mEditDialog.dismiss();
                    break;
                case "修改":
                    if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                        Intent intent = new Intent(this, PublishActivity.class);
                        intent.putExtra("themeInfo", data);
                        intent.putExtra("circle_name", circleInfoEntity.getCircleInfo().getCircle_name());
                        startActivity(intent);
                    }
                    mEditDialog.dismiss();
                    break;
                case "置顶":
                    mPresenter.themeTopOperate(data, i);
                    mEditDialog.dismiss();
                    break;
                case "加精":
                    mPresenter.addChoiceness(data, i);
                    mEditDialog.dismiss();
                    break;
                case "删除":
                    if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                        DialogUtils.showDeleteDialog(data, i, list, this, mPresenter, null);
                    }
                    mEditDialog.dismiss();
                    break;
                case "不喜欢":
                    mPresenter.userShieldRecord(data, i, list);
                    mEditDialog.dismiss();
                    break;
                case "投诉":
                    Intent intent = new Intent(this, ComplaintReasonActivity.class);
                    intent.putExtra("data_id", data.getTheme_id() + "");
                    startActivity(intent);
                    mEditDialog.dismiss();
                    break;
            }
        });
        mEditDialog.show();
        mEditBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mEditView.findViewById(R.id.wechat_tv).setOnClickListener(v -> {
            if (isSmall) {
                DialogUtils.shareSmallProgram(url, imgUrl, title, desc, TopticCircleActivity.this, SHARE_MEDIA.WEIXIN);
            } else {
                DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, TopticCircleActivity.this, SHARE_MEDIA.WEIXIN);
            }
            mEditDialog.dismiss();
        });
        mEditView.findViewById(R.id.wechat_circle_tv).setOnClickListener(v -> {
            DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, TopticCircleActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
            mEditDialog.dismiss();
        });
        mEditView.findViewById(R.id.discover_tv).setOnClickListener(v -> {
            LoginHelper.getInstance().login(this, mPresenter.mDataManager, () -> {
                if (data.getUser_id().equals(mPresenter.mDataManager.getUser().getUser_id())){
                    mPresenter.shareTheme(theme_id, "0");
                    EventBus.getDefault().post(new RefreshEvent(Constants.VIDEO_REFRESH));
                }else {
                    ToastUtils.showShort("只有圈主可以公开至发现");
                }
            });
            mEditDialog.dismiss();
        });
        mEditView.findViewById(R.id.cancle_tv).setOnClickListener(v -> mEditDialog.dismiss());
    }
}
