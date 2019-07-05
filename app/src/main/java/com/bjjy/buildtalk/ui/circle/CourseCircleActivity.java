package com.bjjy.buildtalk.ui.circle;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.bjjy.buildtalk.entity.CourseListEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.utils.SizeUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MyBadgeViewPagerAdapter;
import com.bjjy.buildtalk.weight.tablayout.TabLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import de.hdodenhof.circleimageview.CircleImageView;

public class CourseCircleActivity extends BaseActivity<CourseCirclePresenter> implements CourseCircleContract.View, AppBarLayout.OnOffsetChangedListener, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener {

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
    @BindView(R.id.pre_top_rl)
    RelativeLayout mPreTopRl;
    @BindView(R.id.view1)
    TextView mView1;
    @BindView(R.id.qz_expand_iv)
    ImageView mQzExpandIv;
    @BindView(R.id.recommend_tv)
    TextView mRecommendTv;
    @BindView(R.id.view2)
    TextView mView2;
    @BindView(R.id.ml_expand_iv)
    ImageView mMlExpandIv;
    @BindView(R.id.pre_mid_rl)
    RelativeLayout mPreMidRl;
    @BindView(R.id.formal_title_tv)
    TextView mFormalTitleTv;
    @BindView(R.id.formal_name_tv)
    TextView mFormalNameTv;
    @BindView(R.id.formal_date_tv)
    TextView mFormalDateTv;
    @BindView(R.id.view3)
    TextView mView3;
    @BindView(R.id.formal_ml_expand_iv)
    ImageView mFormalMlExpandIv;
    @BindView(R.id.formal_ml_recycler)
    RecyclerView mFormalMlRecycler;
    @BindView(R.id.formal_rl)
    RelativeLayout mFormalRl;
    @BindView(R.id.min_rl)
    RelativeLayout mMinRl;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.screen_tv)
    TextView mScreenTv;
    @BindView(R.id.screen_rl)
    RelativeLayout mScreenRl;
    @BindView(R.id.tab_rl)
    RelativeLayout mTabRl;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.viewpager_rl)
    RelativeLayout mViewpagerRl;
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
    @BindView(R.id.join_tv)
    TextView mJoinTv;
    @BindView(R.id.publis_rl)
    RelativeLayout mPublisRl;
    @BindView(R.id.formal_face_iv)
    CircleImageView mFormalFaceIv;
    @BindView(R.id.view7)
    ImageView mView7;
    @BindView(R.id.item_name_tv)
    TextView mItemNameTv;
    @BindView(R.id.ml_rl)
    RelativeLayout mMlRl;
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.mc_qz_expand_iv)
    ImageView mMcQzExpandIv;
    @BindView(R.id.mc_recommend_tv)
    TextView mMcRecommendTv;
    @BindView(R.id.mc_ld_rl)
    TextView mMcLdRl;
    @BindView(R.id.mc_ld_tv)
    TextView mMcLdTv;
    @BindView(R.id.mc_ff_rl)
    TextView mMcFfRl;
    @BindView(R.id.mc_ff_tv)
    TextView mMcFfTv;
    @BindView(R.id.expand_rl)
    RelativeLayout mExpandRl;
    @BindView(R.id.mc_jieshao_rl)
    RelativeLayout mMcJieshaoRl;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.mc_ml_expand_iv)
    ImageView mMcMlExpandIv;
    @BindView(R.id.mc_ml_num_tv)
    TextView mMcMlNumTv;
    @BindView(R.id.mc_ml_recycler)
    RecyclerView mMcMlRecycler;
    @BindView(R.id.mc_table_rl)
    RelativeLayout mMcTableRl;
    @BindView(R.id.mc_rl)
    RelativeLayout mMcRl;
    @BindView(R.id.ml_num_tv)
    TextView mMlNumTv;

    private boolean isQzExpand = false;
    private boolean isMlExpand = false;
    private int rotationAngle = 180;

    private MyBadgeViewPagerAdapter mPagerAdapter;
    private List<View> mViews = new ArrayList<>();
    private List<CourseListEntity.CourselistBean> mList = new ArrayList<>();
    private String mCircle_id;
    private int coursePage = 1;
    private CircleInfoEntity mCircleInfoEntity;
    private String mIsJoin;
    private SmartRefreshLayout mRefreshLayout;
    private int page = 1, pageCount = 1;
    private String type = "1";
    private CircleTopticAdapter mTopticAdapter;
    private List<ThemeInfoEntity.ThemeInfoBean> mThemeInfoList = new ArrayList<>();
    private DirectoryAdapter mDirectoryAdapter;
    private AppBarLayout.LayoutParams mParams;
    private int mCourse_page_count;
    private BaseDialog mPayDialog;
    private IWXAPI wxapi;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PayEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.PAY_SUCCESS)) {
            recreate();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_circle;
    }

    @Override
    protected void initView() {
        mCircle_id = getIntent().getStringExtra("circle_id");
        StatusBarUtils.changeStatusBar(this, true, false);
        mTopTitleRl.setPadding(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        //设置appbar滚动布局的最小高度 因为getHeight可能为0，所以直接加上导航栏和tablayout的高度
        mMinRl.setMinimumHeight(StatusBarUtils.getStatusBarHeight() + (int) getResources().getDimension(R.dimen.dp_44));
        mAppBarLayout.addOnOffsetChangedListener(this);
        mParams = (AppBarLayout.LayoutParams) mMinRl.getLayoutParams();
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
        mPresenter.courseList(circleInfoEntity.getCircleInfo().getData_id() + "", coursePage);
        mMcMlRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMcMlRecycler.addItemDecoration(new RecyclerItemDecoration(0, 1));
        mDirectoryAdapter = new DirectoryAdapter(R.layout.adapter_directory_layout, mList);
        mMcMlRecycler.setAdapter(mDirectoryAdapter);
        mDirectoryAdapter.setOnLoadMoreListener(this, mMcMlRecycler);
        mDirectoryAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            Intent intent = new Intent(CourseCircleActivity.this, CourseDetailActivity.class);
            startActivity(intent);
        });
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
            mPreNameTv.setText("圈主：" + circleInfoEntity.getCircleInfo().getName());
            mPreDateTv.setText("创建 " + circleInfoEntity.getCircleInfo().getCreate_day() + "天");
            mJoinTv.setText("加入圈子￥" + circleInfoEntity.getCircleInfo().getCourse_money());
            if (!TextUtils.isEmpty(circleInfoEntity.getCircleInfo().getLightSpot())) {
                mMcLdTv.setText(Html.fromHtml(circleInfoEntity.getCircleInfo().getLightSpot()));//亮点
            }
            List<String> circle_tags = circleInfoEntity.getCircleInfo().getCircle_tags();
            mFlowLayout.setAdapter(new TagAdapter<String>(circle_tags) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    if ("".equals(s)) {
                        return new View(CourseCircleActivity.this);
                    }
                    TextView tv = (TextView) LayoutInflater.from(CourseCircleActivity.this)
                            .inflate(R.layout.flow_blue_layout_tv, parent, false);
                    tv.setText(s);
                    return tv;
                }
            });
            mRecommendTv.setText(circleInfoEntity.getCircleInfo().getCourse_desc());
            mMcRecommendTv.setText(circleInfoEntity.getCircleInfo().getCourse_desc());
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
        setUpTabBadge();

        RecyclerView recyclerView = views.get(0).findViewById(R.id.recycler_view);
        mRefreshLayout = views.get(0).findViewById(R.id.refresh_Layout);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        mTopticAdapter = new CircleTopticAdapter(R.layout.adapter_article_toptic, mThemeInfoList, mIsJoin, this);
        recyclerView.setAdapter(mTopticAdapter);
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
        pageCount = themeInfoEntity.getPage_count();
        mThemeInfoList = themeInfoEntity.getThemeInfo();

        if ("0".equals(mIsJoin) && mThemeInfoList.size() > 5) {
            mThemeInfoList = mThemeInfoList.subList(0, 5);
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
    public void handlerCourseList(CourseListEntity courseListEntity) {
        mCourse_page_count = courseListEntity.getPage_count();
        int countUpdateCourse = courseListEntity.getCountUpdateCourse();
        int countCourse = courseListEntity.getCountCourse();
        mMlNumTv.setText("更新至" + countUpdateCourse + "讲/全" + countCourse + "讲");
        mMcMlNumTv.setText("更新至" + countUpdateCourse + "讲/全" + countCourse + "讲");
        mItemNameTv.setText(courseListEntity.getCourselist().get(0).getArticle_title());
        mDirectoryAdapter.addData(courseListEntity.getCourselist());
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();

        if (offset > 0) {
            mTitleTv.setText(mPreTitleTv.getText());
            mTitleTv.setVisibility(View.VISIBLE);
            mTopTitleRl.setBackgroundColor(getResources().getColor(R.color.blue_mid));
        } else {
            mTitleTv.setVisibility(View.GONE);
            mTopTitleRl.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    @OnClick({R.id.qz_expand_iv, R.id.ml_expand_iv, R.id.formal_ml_expand_iv, R.id.back_iv,
            R.id.pre_share_iv, R.id.share_iv, R.id.more_iv, R.id.join_tv, R.id.publis_rl,
            R.id.mc_qz_expand_iv, R.id.mc_ml_expand_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qz_expand_iv:
                mMcRl.setVisibility(View.VISIBLE);
                mMcQzExpandIv.setImageResource(R.drawable.sanjiao_top_icon);
                mParams.setScrollFlags(0);//不能伸缩
                mMinRl.setLayoutParams(mParams);

                mExpandRl.setVisibility(View.VISIBLE);
                break;
            case R.id.ml_expand_iv:
                mParams.setScrollFlags(0);//不能伸缩
                mMinRl.setLayoutParams(mParams);
                mMcMlExpandIv.setImageResource(R.drawable.sanjiao_top_icon);
                mMcRl.setVisibility(View.VISIBLE);
                mMcTableRl.setVisibility(View.VISIBLE);
                break;
            case R.id.formal_ml_expand_iv:
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
            case R.id.join_tv:
                showPayDialog();
                break;
            case R.id.publis_rl:
                break;
            case R.id.mc_qz_expand_iv:
                mMcQzExpandIv.setImageResource(R.drawable.sanjiao_icon);

                mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                mMinRl.setLayoutParams(mParams);

                mExpandRl.setVisibility(View.GONE);
                mMcRl.setVisibility(View.GONE);
                break;
            case R.id.mc_ml_expand_iv:
                mMcQzExpandIv.setImageResource(R.drawable.sanjiao_icon);
                mMcMlExpandIv.setImageResource(R.drawable.sanjiao_icon);
                mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                mMinRl.setLayoutParams(mParams);

                mMcTableRl.setVisibility(View.GONE);
                mMcRl.setVisibility(View.GONE);
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

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }

    @Override
    public void onLoadMoreRequested() {
        if (coursePage < mCourse_page_count) {
            coursePage++;
            mPresenter.courseList(mCircle_id, coursePage);
            mDirectoryAdapter.loadMoreEnd();
        } else {
            mDirectoryAdapter.loadMoreComplete();
        }
    }

    public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
        private int itemSpace;
        private int itemNum;

        /**
         * @param itemSpace item间隔
         * @param itemNum   每行item的个数
         */
        public RecyclerItemDecoration(int itemSpace, int itemNum) {
            this.itemSpace = itemSpace;
            this.itemNum = itemNum;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (isLastRow(view, parent)) {
                outRect.bottom = SizeUtils.dp2px(50);
            }
        }

        private boolean isLastRow(View view, RecyclerView parent) {
            int position = parent.getChildAdapterPosition(view);
            int count = parent.getAdapter().getItemCount();
            return position == count - 1;
        }
    }

}
