package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleTopticAdapter;
import com.bjjy.buildtalk.adapter.DirectoryAdapter;
import com.bjjy.buildtalk.adapter.ThemeTypeAdapter;
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
import com.bjjy.buildtalk.entity.ThemeTypeEntity;
import com.bjjy.buildtalk.ui.mine.FeedBackActivity;
import com.bjjy.buildtalk.ui.talk.CircleManDetailActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
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
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CourseCircleActivity extends BaseActivity<CourseCirclePresenter> implements CourseCircleContract.View, AppBarLayout.OnOffsetChangedListener, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener, ViewPager.OnPageChangeListener {

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
    @BindView(R.id.recommend_tv)
    TextView mRecommendTv;
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
    @BindView(R.id.min_rl)
    RelativeLayout mMinRl;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.screen_rl)
    RelativeLayout mScreenRl;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
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
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.mc_qz_expand_iv)
    ImageView mMcQzExpandIv;
    @BindView(R.id.mc_recommend_tv)
    TextView mMcRecommendTv;
    @BindView(R.id.mc_ld_tv)
    TextView mMcLdTv;
    @BindView(R.id.expand_rl)
    RelativeLayout mExpandRl;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
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
    @BindView(R.id.mc_formal_title_tv)
    TextView mMcFormalTitleTv;
    @BindView(R.id.mc_formal_name_tv)
    TextView mMcFormalNameTv;
    @BindView(R.id.mc_tag_iv)
    ImageView mMcTagIv;
    @BindView(R.id.mc_formal_date_tv)
    TextView mMcFormalDateTv;
    @BindView(R.id.mc_formal_top_rl)
    RelativeLayout mMcFormalTopRl;
    @BindView(R.id.mc_formal_face_iv)
    CircleImageView mMcFormalFaceIv;
    @BindView(R.id.tag_iv)
    ImageView mTagIv;
    @BindView(R.id.formal_ml_num_tv)
    TextView mFormalMlNumTv;
    @BindView(R.id.screen_tv)
    TextView mScreenTv;
    @BindView(R.id.pre_tag_iv)
    ImageView mPreTagIv;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.formal_ml_recycler)
    RecyclerView mFormalMlRecyclerView;
    @BindView(R.id.pre_ml_recycler)
    RecyclerView mPreMlRecyclerView;
    @BindView(R.id.course_collapse_rl)
    RelativeLayout mCourseCollapseRl;
    @BindView(R.id.mc_qz_expand_tv)
    TextView mMcQzExpandTv;

    private MyBadgeViewPagerAdapter mPagerAdapter;
    private List<View> mViews = new ArrayList<>();
    private List<CourseListEntity.CourselistBean> mList = new ArrayList<>();
    private List<CourseListEntity.CourselistBean> mList2 = new ArrayList<>();
    private List<CourseListEntity.CourselistBean> mList3 = new ArrayList<>();
    private String mCircle_id;
    private int coursePage = 1;
    private CircleInfoEntity mCircleInfoEntity;
    private String mIsJoin;
    private int page = 1, jhPage = 1, pageCount = 1, jhPageCount = 1;
    private String type = "1";
    private CircleTopticAdapter mTopticAdapter;
    private CircleTopticAdapter mTopticAdapter1;
    private List<ThemeInfoEntity.ThemeInfoBean> mThemeInfoList = new ArrayList<>();
    private List<ThemeInfoEntity.ThemeInfoBean> mEssenceInfoList = new ArrayList<>();
    private DirectoryAdapter mDirectoryAdapter;
    private DirectoryAdapter mDirectoryAdapter2;
    private DirectoryAdapter mDirectoryAdapter3;
    private AppBarLayout.LayoutParams mParams;
    private int mCourse_page_count;
    private BaseDialog mPayDialog;
    private IWXAPI wxapi;
    private Intent mIntent;
    private int mViewpager_position;
    private String mUrl;
    private String mEndUrl;
    private RecyclerView mRecyclerView;
    private NestedScrollView mEmptyView;
    private boolean isMlExpand = false;
    private String mPath;//话题圈
    private String circlePath;//话题圈拼接完成的url
    private String mPath1;//主题
    private String themePath;//主题拼接完成url
    private int mCountChoiceness_themeInfo;
    private RecyclerView mJhRecyclerView;
    private NestedScrollView mJhEmptyView;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PayEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.PAY_SUCCESS)) {
            mList.clear();
            mThemeInfoList.clear();
            initEventAndData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event1(RefreshEvent eventBean) {
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
        mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" + mPresenter.mDataManager.getUser().getUser_id() + "&circle_id=" + mCircle_id;
        mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) + "&response_type=code&scope=snsapi_userinfo&state=coursePay#wechat_redirect";
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
        mPresenter.courseList(circleInfoEntity.getCircleInfo().getData_id() + "", coursePage);

        //预览
        mPreMlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDirectoryAdapter2 = new DirectoryAdapter(R.layout.adapter_directory_layout, mList2, mIsJoin);
        View footView2 = LayoutInflater.from(this).inflate(R.layout.course_circle_list, null);
        footView2.setOnClickListener(v -> {
            mParams.setScrollFlags(0);//不能伸缩
            mMinRl.setLayoutParams(mParams);
            mMcRl.setVisibility(View.VISIBLE);
            mMcTableRl.setVisibility(View.VISIBLE);
            mJoinTv.setVisibility(View.GONE);
            mCourseCollapseRl.setVisibility(View.VISIBLE);
            isMlExpand = true;
        });
        mDirectoryAdapter2.setFooterView(footView2);
        mPreMlRecyclerView.setAdapter(mDirectoryAdapter2);
        mDirectoryAdapter2.setOnItemClickListener((adapter, view, position) -> {
            List<CourseListEntity.CourselistBean> mList = adapter.getData();
            if (mList.get(position).getIs_audition() == 0 && "0".equals(mIsJoin)) {
                ToastUtils.showShort("该课程还未解锁");
                return;
            }
            Intent intent = new Intent(CourseCircleActivity.this, CourseDetailActivity.class);
            if (mList.size() > 0) {
                intent.putExtra("video_url", mList.get(position).getVideoInfo().getVideo_url());
                intent.putExtra("article_title", mList.get(position).getArticle_title());
                intent.putExtra("content", mList.get(position).getContent());
                intent.putExtra("position", position);
                intent.putExtra("article_id", mList.get(position).getArticle_id()+"");
            }
            intent.putExtra("circle_id", mCircle_id);
            startActivity(intent);
        });
        //正式
        mFormalMlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDirectoryAdapter3 = new DirectoryAdapter(R.layout.adapter_directory_layout, mList3, mIsJoin);
        View footView3 = LayoutInflater.from(this).inflate(R.layout.course_circle_list, null);
        footView3.setOnClickListener(v -> {
            mParams.setScrollFlags(0);//不能伸缩
            mMinRl.setLayoutParams(mParams);
            mScrollView.setVisibility(View.INVISIBLE);
            mMcFormalTopRl.setVisibility(View.VISIBLE);
            mMcFormalFaceIv.setVisibility(View.VISIBLE);
            mMcRl.setVisibility(View.VISIBLE);
            mMcTableRl.setVisibility(View.VISIBLE);
            mPublisRl.setVisibility(View.GONE);
            mCourseCollapseRl.setVisibility(View.VISIBLE);
        });
        mDirectoryAdapter3.setFooterView(footView3);
        mFormalMlRecyclerView.setAdapter(mDirectoryAdapter3);
        mDirectoryAdapter3.setOnItemClickListener((adapter, view, position) -> {
            List<CourseListEntity.CourselistBean> mList = adapter.getData();
            if (mList.get(position).getIs_audition() == 0 && "0".equals(mIsJoin)) {
                ToastUtils.showShort("该课程还未解锁");
                return;
            }
            Intent intent = new Intent(CourseCircleActivity.this, CourseDetailActivity.class);
            if (mList.size() > 0) {
                intent.putExtra("video_url", mList.get(position).getVideoInfo().getVideo_url());
                intent.putExtra("article_title", mList.get(position).getArticle_title());
                intent.putExtra("content", mList.get(position).getContent());
                intent.putExtra("position", position);
                intent.putExtra("article_id", mList.get(position).getArticle_id()+"");
            }
            intent.putExtra("circle_id", mCircle_id);
            startActivity(intent);
        });
        //蒙层
        mMcMlRecycler.setLayoutManager(new LinearLayoutManager(this));
        mDirectoryAdapter = new DirectoryAdapter(R.layout.adapter_directory_layout, mList, mIsJoin);
        mMcMlRecycler.setAdapter(mDirectoryAdapter);
        mDirectoryAdapter.setOnLoadMoreListener(this, mMcMlRecycler);
        mDirectoryAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            List<CourseListEntity.CourselistBean> mList = baseQuickAdapter.getData();
            if (mList.get(i).getIs_audition() == 0 && "0".equals(mIsJoin)) {
                ToastUtils.showShort("该课程还未解锁");
                return;
            }
            Intent intent = new Intent(CourseCircleActivity.this, CourseDetailActivity.class);
            if (mList.size() > 0) {
                intent.putExtra("video_url", mList.get(i).getVideoInfo().getVideo_url());
                intent.putExtra("article_title", mList.get(i).getArticle_title());
                intent.putExtra("content", mList.get(i).getContent());
                intent.putExtra("position", i);
                intent.putExtra("article_id", mList.get(i).getArticle_id()+"");
            }
            intent.putExtra("circle_id", mCircle_id);
            startActivity(intent);
        });
        if (TextUtils.equals("0", mIsJoin)) {//未加入
            mFormalRl.setVisibility(View.GONE);
            mPreTopRl.setVisibility(View.VISIBLE);
            mPreMidRl.setVisibility(View.VISIBLE);
            mShareIv.setVisibility(View.GONE);
            mMoreIv.setVisibility(View.GONE);
            mPreShareIv.setVisibility(View.VISIBLE);
            mJoinTv.setVisibility(View.VISIBLE);
            mPublisRl.setVisibility(View.GONE);
            mScreenRl.setVisibility(View.GONE);
            if (circleInfoEntity.getCircleInfo().getIs_author() == 1) {
                mPreTagIv.setVisibility(View.VISIBLE);
            }
            Glide.with(this).load(R.drawable.circle_bg_icon).into(mTopticBg);
            Glide.with(this).load(circleInfoEntity.getCircleInfo().getCircle_image().getPic_url()).into(mPreFaceIv);
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
            mPreMidRl.setVisibility(View.GONE);
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
            if (circleInfoEntity.getCircleInfo().getIs_author() == 1) {
                mTagIv.setVisibility(View.VISIBLE);
                mMcTagIv.setVisibility(View.VISIBLE);
            }
            mFormalDateTv.setText("创建 " + circleInfoEntity.getCircleInfo().getCreate_day() + "天");
            Glide.with(this).load(circleInfoEntity.getCircleInfo().getMaster_pic()).into(mMcFormalFaceIv);
            mMcFormalTitleTv.setText(circleInfoEntity.getCircleInfo().getCircle_name());
            mMcFormalNameTv.setText("圈主：" + circleInfoEntity.getCircleInfo().getName());
            mMcFormalDateTv.setText("创建 " + circleInfoEntity.getCircleInfo().getCreate_day() + "天");
        }
    }

    @Override
    public void handlerTab(List<String> titleList, List<View> views, List<Integer> badgeCountList) {
        this.mViews = views;
        mPagerAdapter = new MyBadgeViewPagerAdapter(titleList, views, badgeCountList);
        mViewpager.setAdapter(mPagerAdapter);
        mViewpager.addOnPageChangeListener(this);
        mTablayout.setupWithViewPager(mViewpager);
        setUpTabBadge();

        mRecyclerView = views.get(0).findViewById(R.id.recycler_view);
        mEmptyView = views.get(0).findViewById(R.id.emptyView);
        mJhRecyclerView = views.get(1).findViewById(R.id.jh_recycler_view);
        mJhEmptyView = views.get(1).findViewById(R.id.jh_emptyView);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        mTopticAdapter = new CircleTopticAdapter(R.layout.adapter_article_toptic, mThemeInfoList, mIsJoin, this);
        mRecyclerView.setAdapter(mTopticAdapter);
        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mJhRecyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        mTopticAdapter1 = new CircleTopticAdapter(R.layout.adapter_article_toptic, mEssenceInfoList, mIsJoin, this);
        mJhRecyclerView.setAdapter(mTopticAdapter1);
        ((SimpleItemAnimator)mJhRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mTopticAdapter.setOnItemClickListener(this);
        mTopticAdapter.setOnItemChildClickListener(this);

        mTopticAdapter1.setOnItemClickListener(this);
        mTopticAdapter1.setOnItemChildClickListener(this);

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
        pageCount = themeInfoEntity.getPage_count();
        mThemeInfoList = themeInfoEntity.getThemeInfo();

        if ("0".equals(mIsJoin) && mThemeInfoList.size() > 5) {
            mThemeInfoList = mThemeInfoList.subList(0, 5);
        }
        if (mThemeInfoList.size() > 0){
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            if (isRefresh) {
                mTopticAdapter.setNewData(mThemeInfoList);
            } else {
                mTopticAdapter.addData(mThemeInfoList);
            }
        }else {
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
    public void handlerCourseList(CourseListEntity courseListEntity) {
        mCourse_page_count = courseListEntity.getPage_count();
        int countUpdateCourse = courseListEntity.getCountUpdateCourse();
        int countCourse = courseListEntity.getCountCourse();
        String s = "更新至" + countUpdateCourse + "讲 • 全" + countCourse + "讲";
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF32A7FF")),0,4+String.valueOf(countUpdateCourse).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mMlNumTv.setText(spannableString);
        mMcMlNumTv.setText(spannableString);
        mFormalMlNumTv.setText(spannableString);

        if (coursePage == 1 && courseListEntity.getCourselist().size() > 0){
            List<CourseListEntity.CourselistBean> courselist;
            if (courseListEntity.getCourselist().size() >= 3){
                courselist = courseListEntity.getCourselist().subList(0,3);
            }else if (courseListEntity.getCourselist().size() == 2){
                courselist = courseListEntity.getCourselist().subList(0,2);
            }else {
                courselist = courseListEntity.getCourselist().subList(0,1);
            }
            mDirectoryAdapter2.addData(courselist);
            mDirectoryAdapter3.addData(courselist);
        }
        mDirectoryAdapter.addData(courseListEntity.getCourselist());
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

    @OnClick({R.id.qz_expand_ll, R.id.back_iv, R.id.course_collapse_rl,
            R.id.pre_share_iv, R.id.share_iv, R.id.more_iv, R.id.join_tv, R.id.publis_rl,
            R.id.mc_qz_expand_ll, R.id.screen_rl, R.id.formal_face_iv, R.id.mc_formal_face_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qz_expand_ll://预览-----展开圈子介绍
                mMcRl.setVisibility(View.VISIBLE);
                mMcQzExpandTv.setText("收起");
                mMcQzExpandIv.setImageResource(R.drawable.sanjiao_blue);
                mParams.setScrollFlags(0);//不能伸缩
                mMinRl.setLayoutParams(mParams);
                mExpandRl.setVisibility(View.VISIBLE);
                break;
            case R.id.course_collapse_rl://课程-----收起目录
                mMcQzExpandTv.setText("展开");
                mMcQzExpandIv.setImageResource(R.drawable.dao_sanjiao_blue);
                mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                mMinRl.setLayoutParams(mParams);
                mMcTableRl.setVisibility(View.GONE);
                mMcRl.setVisibility(View.GONE);
                mCourseCollapseRl.setVisibility(View.GONE);
                if (TextUtils.equals("0", mIsJoin)){
                    mJoinTv.setVisibility(View.VISIBLE);
                }else {
                    mPublisRl.setVisibility(View.VISIBLE);
                }
                isMlExpand = false;
                break;
//            case R.id.ml_expand_iv://预览-----展开目录
//                mParams.setScrollFlags(0);//不能伸缩
//                mMinRl.setLayoutParams(mParams);
//                mMcMlExpandIv.setImageResource(R.drawable.sanjiao_top_icon);
//                mMcRl.setVisibility(View.VISIBLE);
//                mMcTableRl.setVisibility(View.VISIBLE);
//                isMlExpand = true;
//                break;
//            case R.id.formal_ml_expand_iv://加入-----展开目录
//                mParams.setScrollFlags(0);//不能伸缩
//                mMinRl.setLayoutParams(mParams);
//                mMcMlExpandIv.setImageResource(R.drawable.sanjiao_top_icon);
//                mScrollView.setVisibility(View.INVISIBLE);
//                mMcFormalTopRl.setVisibility(View.VISIBLE);
//                mMcFormalFaceIv.setVisibility(View.VISIBLE);
//                mMcRl.setVisibility(View.VISIBLE);
//                mMcTableRl.setVisibility(View.VISIBLE);
//                break;
            case R.id.mc_qz_expand_ll://蒙层----收起圈子介绍
                if (!isMlExpand){
                    mMcQzExpandTv.setText("展开");
                    mMcQzExpandIv.setImageResource(R.drawable.dao_sanjiao_blue);
                    mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                    mMinRl.setLayoutParams(mParams);
                    mMcTableRl.setVisibility(View.GONE);
                    mExpandRl.setVisibility(View.GONE);
                    mMcRl.setVisibility(View.GONE);
                }else {//如果蒙层的目录展开，点击时应该是展开圈子介绍
                    mMcRl.setVisibility(View.VISIBLE);
                    mMcTableRl.setVisibility(View.GONE);
                    mMcQzExpandTv.setText("收起");
                    mMcQzExpandIv.setImageResource(R.drawable.sanjiao_blue);
                    mParams.setScrollFlags(0);//不能伸缩
                    mMinRl.setLayoutParams(mParams);
                    mExpandRl.setVisibility(View.VISIBLE);
                    isMlExpand = false;
                }
                break;
//            case R.id.mc_ml_expand_iv://蒙层-----收起目录
//                mMcQzExpandIv.setImageResource(R.drawable.sanjiao_icon);
//                mMcMlExpandIv.setImageResource(R.drawable.sanjiao_icon);
//                mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
//                mMinRl.setLayoutParams(mParams);
//                mMcTableRl.setVisibility(View.GONE);
//                mMcRl.setVisibility(View.GONE);
//                isMlExpand = false;
//                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.pre_share_iv:
            case R.id.share_iv:
                circlePath = mPath + "circle_id=" + mCircle_id + "&num=1";
                DialogUtils.showShareDialog(this, circlePath, mEndUrl, mCircleInfoEntity.getCircleInfo().getCircle_name(),
                        mCircleInfoEntity.getCircleInfo().getCircle_image().getPic_url(), mCircleInfoEntity.getCircleInfo().getCircle_desc(),true);
                break;
            case R.id.more_iv:
                mIntent = new Intent(this, CircleInfoActivity.class);
                mIntent.putExtra("type", "course");
                mIntent.putExtra("circle_id", mCircle_id);
                mIntent.putExtra("operate_user", mCircleInfoEntity.getCircleInfo().getUser_id() + "");
                mIntent.putExtra("jieshao", mCircleInfoEntity.getCircleInfo().getCircle_desc());
                startActivity(mIntent);
                break;
//            case R.id.ml_rl://预览 目录
//            case R.id.formal_ml_rl://正式 目录
//                mIntent = new Intent(this, CourseDetailActivity.class);
//                mList = mDirectoryAdapter.getData();
//                if (mList.size() > 0) {
//                    mIntent.putExtra("video_url", mList.get(0).getVideoInfo().getVideo_url());
//                    mIntent.putExtra("article_title", mList.get(0).getArticle_title());
//                    mIntent.putExtra("content", mList.get(0).getContent());
//                    mIntent.putExtra("position", "0");
//                    mIntent.putExtra("article_id", mList.get(0).getArticle_id()+"");
//                }
//                mIntent.putExtra("circle_id", mCircle_id);
//                startActivity(mIntent);
//                break;
            case R.id.screen_rl:
                if (mViewpager_position == 0){
                    showThemeTypeDialog();
                }else {
                    ToastUtils.showShort("暂未开放");
                }
                break;
            case R.id.join_tv:
                LoginHelper.login(this, mPresenter.mDataManager, () -> showPayDialog());
                break;
            case R.id.publis_rl:
                mIntent = new Intent(this, PublishActivity.class);
                mIntent.putExtra("circle_id", mCircle_id);
                startActivity(mIntent);
                break;
            case R.id.formal_face_iv:
            case R.id.mc_formal_face_iv:
                if (mCircleInfoEntity.getCircleInfo().getIs_author() == 1){
                    mIntent = new Intent(this, MasterDetailActivity.class);
                    mIntent.putExtra("user_id", mCircleInfoEntity.getCircleInfo().getUser_id()+"");
                    startActivity(mIntent);
                }else {
                    mIntent = new Intent(this, CircleManDetailActivity.class);
                    mIntent.putExtra("user_id", mCircleInfoEntity.getCircleInfo().getUser_id()+"");
                    startActivity(mIntent);
                }
                break;
        }
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
        if (mViewpager_position == 0){
            if (page < pageCount) {
                page++;
                mPresenter.themeInfo(mCircle_id, page, type, false);
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }else {
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
        if (mViewpager_position == 0){
            page = 1;
            mPresenter.themeInfo(mCircle_id, page, type, true);
        }else {
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
            case R.id.item_more_iv:
                DialogUtils.showEditDialog(this,data.get(i),i,data,null, mPresenter, mCircleInfoEntity);
                break;
            case R.id.item_praise_iv:
                mPresenter.praise(data, i);
                break;
            case R.id.item_comment_iv:
                DialogUtils.showCommentDialog(data.get(i).getTheme_id(), i, data, this, null, mPresenter);
                break;
            case R.id.item_share_iv:
                if (data.get(i).getTheme_image().size() > 0){
                    mPresenter.getThumb(data.get(i).getTheme_image().get(0).getPic_url(), data, i);
                }else {
                    mPresenter.getThumb(data.get(i).getHeadImage(), data, i);
                }
                break;
            case R.id.more_tv:
                Intent intent = new Intent(this, TopticDetailActivity.class);
                intent.putExtra("title", mCircleInfoEntity.getCircleInfo().getCircle_name());
                intent.putExtra("theme_id", data.get(i).getTheme_id() + "");
                intent.putExtra("circle_id", mCircle_id);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void handlerThumbSuccess(String thumb_url, List<ThemeInfoEntity.ThemeInfoBean> data, int i) {
        String mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" + mPresenter.mDataManager.getUser().getUser_id() + "&theme_id=" + data.get(i).getTheme_id();
        String mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) + "&response_type=code&scope=snsapi_userinfo&state=theme#wechat_redirect";
        themePath = mPath1 + "theme_id=" + data.get(i).getTheme_id() + "&circle_id=" + mCircle_id +"&num=1";
        DialogUtils.showShareDialog(this, themePath, mEndUrl,
                TextUtils.isEmpty(data.get(i).getTheme_content()) ? mCircleInfoEntity.getCircleInfo().getCircle_name() : data.get(i).getTheme_content(),
                thumb_url, data.get(i).getTheme_content(), true);
    }

    /**
     * 刷新精华列表
     */
    private void refreshChoiceness() {
        jhPage = 1;
        mPresenter.essenceInfo(mCircle_id, jhPage, "1", true);
    }

    @Override
    public void handlerCommentSuccess(int position, List<ThemeInfoEntity.ThemeInfoBean> data, List<CommentContentBean> contentBeanList) {
        data.get(position).setComment_content(contentBeanList);
        if (mViewpager_position == 0){//主题列表
            mTopticAdapter.notifyItemChanged(position);
            //如果评论的这条主题是精华，那么刷新精华列表数据
            if (data.get(position).getIs_choiceness() == 1){
                refreshChoiceness();
            }
        }else {//精华列表,局部刷新主题数据
            mTopticAdapter1.notifyItemChanged(position);
            int theme_id = data.get(position).getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int i = 0; i < topticAdapterData.size(); i++) {
                if (theme_id == topticAdapterData.get(i).getTheme_id()){
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
        if (mViewpager_position == 0){//主题列表
            mTopticAdapter.notifyItemChanged(i);
            //如果点赞的这条主题是精华，那么刷新精华列表数据
            if (data.get(i).getIs_choiceness() == 1){
                refreshChoiceness();
            }
        }else {//精华列表,局部刷新主题数据
            mTopticAdapter1.notifyItemChanged(i);
            int theme_id = data.get(i).getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()){
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
        if (mViewpager_position == 0){//主题列表
            mTopticAdapter.notifyItemChanged(i);
            //如果点赞的这条主题是精华，那么刷新精华列表数据
            if (data.getIs_choiceness() == 1){
                refreshChoiceness();
            }
        }else {//精华列表,局部刷新主题数据
            mTopticAdapter1.notifyItemChanged(i);
            int theme_id = data.getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()){
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
        if (mViewpager_position == 0){//主题列表
            mTopticAdapter.notifyItemRemoved(i);
            //如果点赞的这条主题是精华，那么刷新精华列表数据
            if (data.getIs_choiceness() == 1){
                refreshChoiceness();
            }
        }else {//精华列表,局部刷新主题数据
            mTopticAdapter1.notifyItemRemoved(i);
            int theme_id = data.getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()){
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
        if (mViewpager_position == 0){//主题列表
            mTopticAdapter.notifyItemChanged(i);
            refreshChoiceness();
        }else {//精华列表,局部刷新主题数据
            mTopticAdapter1.remove(i);
            mTopticAdapter1.notifyItemChanged(i);
            int theme_id = data.getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mTopticAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()){
                    topticAdapterData.get(j).setIs_choiceness(0);
                }
            }
        }
        setUpTabBadge();
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
    public void onLoadMoreRequested() {
        if (coursePage < mCourse_page_count) {
            coursePage++;
            mPresenter.courseList(mCircleInfoEntity.getCircleInfo().getData_id()+"", coursePage);
            mDirectoryAdapter.loadMoreComplete();
        } else {
            mDirectoryAdapter.loadMoreEnd();
        }
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
}
