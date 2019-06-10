package com.bjjy.buildtalk.ui.circle;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleTopticAdapter;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.utils.SizeUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MyBadgeViewPagerAdapter;
import com.bjjy.buildtalk.weight.tablayout.TabLayout;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TopticCircleActivity extends BaseActivity<TopticCirclePresenter> implements TopticCircleContract.View, AppBarLayout.OnOffsetChangedListener, OnRefreshLoadMoreListener {

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
            Glide.with(this).load(circleInfoEntity.getCircleInfo().getMaster_pic()).into(mPreFaceIv);
            mPreTitleTv.setText(circleInfoEntity.getCircleInfo().getCircle_name());
            mPreNameTv.setText("圈子：" + circleInfoEntity.getCircleInfo().getName());
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
        } else {//已加入
            mPreTopRl.setVisibility(View.GONE);
            mFormalRl.setVisibility(View.VISIBLE);
            mPreShareIv.setVisibility(View.GONE);
            mShareIv.setVisibility(View.VISIBLE);
            mMoreIv.setVisibility(View.VISIBLE);
            mJoinTv.setVisibility(View.GONE);
            mPublisRl.setVisibility(View.VISIBLE);
            mScreenRl.setVisibility(View.VISIBLE);
            Glide.with(this).load(circleInfoEntity.getCircleInfo().getCircle_image().getPic_url()).into(mTopticBg);
            Glide.with(this).load(circleInfoEntity.getCircleInfo().getMaster_pic()).into(mFormalFaceIv);
            mFormalTitleTv.setText(circleInfoEntity.getCircleInfo().getCircle_name());
            mFormalNameTv.setText("圈子：" + circleInfoEntity.getCircleInfo().getName());
            mFormalDateTv.setText("创建 " + circleInfoEntity.getCircleInfo().getCreate_day() + "天");
        }
    }

    @Override
    public void handlerTab(List<String> titleList, List<View> views, List<Integer> badgeCountList) {
        this.mViews = views;
        mPagerAdapter = new MyBadgeViewPagerAdapter(titleList, views, badgeCountList);
        mViewpager.setAdapter(mPagerAdapter);
        mTablayout.setupWithViewPager(mViewpager);
        setUpTabBadge();

        RecyclerView recyclerView = views.get(0).findViewById(R.id.recycler_view);
        mRefreshLayout = views.get(0).findViewById(R.id.refresh_Layout);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        mTopticAdapter = new CircleTopticAdapter(R.layout.adapter_article_toptic, mThemeInfoList, mIsJoin, this);
        recyclerView.setAdapter(mTopticAdapter);
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
        pageCount = themeInfoEntity.getPage_count();
        mThemeInfoList = themeInfoEntity.getThemeInfo();

        if ("0".equals(mIsJoin) && mThemeInfoList.size() > 5){
            mThemeInfoList = mThemeInfoList.subList(0,5);
        }
        if (isRefresh) {
            mTopticAdapter.setNewData(mThemeInfoList);
        } else {
            mTopticAdapter.addData(mThemeInfoList);
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
            mTitleTv.setText(mCircleInfoEntity.getCircleInfo().getCircle_name());
            mTitleTv.setVisibility(View.VISIBLE);
            mTopTitleRl.setBackgroundColor(getResources().getColor(R.color.blue_mid));
        } else {
            mTitleTv.setVisibility(View.GONE);
            mTopTitleRl.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    @OnClick({R.id.expand_iv, R.id.back_iv, R.id.share_iv, R.id.join_tv, R.id.pre_share_iv, R.id.more_iv, R.id.screen_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.expand_iv:
                mLayoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mMinRl.getLayoutParams();
                mExpandIv.animate().rotation(rotationAngle);
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
                break;
            case R.id.join_tv:
                mPresenter.joinCircle(mCircle_id);
                break;
            case R.id.pre_share_iv:
                break;
            case R.id.more_iv:
                break;
            case R.id.screen_rl:
                showThemeTypeDialog();
                break;
        }
    }

    @Override
    public void handlerJoinSuccess(IEntity iEntity) {
        recreate();
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
        page = 1;
        mDialog.getView(R.id.type1).setOnClickListener(v -> {
            type = "1";
            mPresenter.themeInfo(mCircle_id, page, type, true);
            mDialog.dismiss();
        });
        mDialog.getView(R.id.type2).setOnClickListener(v -> {
            type = "2";
            mPresenter.themeInfo(mCircle_id, page, type, true);
            mDialog.dismiss();
        });
        mDialog.getView(R.id.type3).setOnClickListener(v -> {
            type = "3";
            mPresenter.themeInfo(mCircle_id, page, type, true);
            mDialog.dismiss();
        });
        mDialog.getView(R.id.type4).setOnClickListener(v -> {
            type = "4";
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
}
