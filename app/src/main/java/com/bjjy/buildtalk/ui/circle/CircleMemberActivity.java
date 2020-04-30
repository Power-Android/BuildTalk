package com.bjjy.buildtalk.ui.circle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleMemberAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.MemberEntity;
import com.bjjy.buildtalk.ui.mine.IDCardActivity;
import com.bjjy.buildtalk.ui.mine.PersonInfoActivity;
import com.bjjy.buildtalk.ui.talk.CircleManDetailActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MyViewPagerAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class CircleMemberActivity extends BaseActivity<CircleMemberPresenter> implements CircleMemberContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private static final String[] CHANNELS = new String[]{"活跃度排名", "点赞排名", "评论排名"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private List<View> mViews = new ArrayList<>();

    private List<MemberEntity.CircleUserBean> mList1 = new ArrayList<>();
    private List<MemberEntity.CircleUserBean> mList2 = new ArrayList<>();
    private List<MemberEntity.CircleUserBean> mList3 = new ArrayList<>();
    private String mCircle_id;
    private int page1 = 1;
    private int page2 = 1;
    private int page3 = 1;
    private CircleMemberAdapter mMemberAdapter1;
    private CircleMemberAdapter mMemberAdapter2;
    private CircleMemberAdapter mMemberAdapter3;
    private int mPage_count;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_member;
    }

    @Override
    protected void initView() {
        mCircle_id = getIntent().getStringExtra("circle_id");
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarTitle.setText(R.string.circle_member);
        mToolbarRightTitle.setVisibility(View.VISIBLE);
        mToolbarRightTitle.setText("排序规则");
        mToolbarRightTitle.setOnClickListener(v -> showRule());

        View activityView = LayoutInflater.from(this).inflate(R.layout.circle_list_view, null, false);
        View praiseView = LayoutInflater.from(this).inflate(R.layout.circle_list_view, null, false);
        View commentView = LayoutInflater.from(this).inflate(R.layout.circle_list_view, null, false);
        mViews.add(activityView);
        mViews.add(praiseView);
        mViews.add(commentView);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(mDataList, mViews);
        mViewPager.setAdapter(viewPagerAdapter);
        initMagicIndicator();

        RecyclerView activityRv = activityView.findViewById(R.id.recycler_view);
        activityRv.setLayoutManager(new LinearLayoutManager(this));
        mMemberAdapter1 = new CircleMemberAdapter(R.layout.adapter_circle_member, mList1, 1);
        mMemberAdapter1.setOnItemClickListener(this);
        activityRv.setAdapter(mMemberAdapter1);

        RecyclerView praiseRv = praiseView.findViewById(R.id.recycler_view);
        praiseRv.setLayoutManager(new LinearLayoutManager(this));
        mMemberAdapter2 = new CircleMemberAdapter(R.layout.adapter_circle_member, mList2, 2);
        mMemberAdapter2.setOnItemClickListener(this);
        praiseRv.setAdapter(mMemberAdapter2);

        RecyclerView commentRv = commentView.findViewById(R.id.recycler_view);
        commentRv.setLayoutManager(new LinearLayoutManager(this));
        mMemberAdapter3 = new CircleMemberAdapter(R.layout.adapter_circle_member, mList3, 3);
        mMemberAdapter3.setOnItemClickListener(this);
        commentRv.setAdapter(mMemberAdapter3);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);

    }

    private void showRule() {
        BaseDialog ruleDialog = new BaseDialog.Builder(this)
                .setViewId(R.layout.dialog_member_rule_layout)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.nomal_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx((int)getResources().getDimension(R.dimen.dp_345), (int)getResources().getDimension(R.dimen.dp_279))
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                .builder();
        ruleDialog.show();
    }

    private void initMagicIndicator() {
        mMagicIndicator.setBackgroundResource(R.drawable.shape_gray_15radius);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#333333"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setOnClickListener(v -> mViewPager.setCurrentItem(index));
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                float navigatorHeight = context.getResources().getDimension(R.dimen.dp_30);
                float borderWidth = UIUtil.dip2px(context, 1);
                float lineHeight = navigatorHeight - 2 * borderWidth;
                indicator.setLineHeight(lineHeight);
                indicator.setRoundRadius(lineHeight / 2);
                indicator.setYOffset(borderWidth);
                indicator.setColors(Color.parseColor("#FFA738"));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.circleUser1(mCircle_id, page1, 1, false);
        mPresenter.circleUser2(mCircle_id, page2, 2, false);
        mPresenter.circleUser3(mCircle_id, page3, 3, false);
    }

    @Override
    public void handlerCircleUser1(MemberEntity memberEntity, boolean isRefresh) {
        mPage_count = memberEntity.getPage_count();
        if (isRefresh) {
            mMemberAdapter1.setNewData(memberEntity.getCircleUser());
        } else {
            mMemberAdapter1.addData(memberEntity.getCircleUser());
        }
    }

    @Override
    public void handlerCircleUser2(MemberEntity memberEntity, boolean isRefresh) {
        mPage_count = memberEntity.getPage_count();
        if (isRefresh) {
            mMemberAdapter2.setNewData(memberEntity.getCircleUser());
        } else {
            mMemberAdapter2.addData(memberEntity.getCircleUser());
        }
    }

    @Override
    public void handlerCircleUser3(MemberEntity memberEntity, boolean isRefresh) {
        mPage_count = memberEntity.getPage_count();
        if (isRefresh) {
            mMemberAdapter3.setNewData(memberEntity.getCircleUser());
        } else {
            mMemberAdapter3.addData(memberEntity.getCircleUser());
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page1 = 1;
        page2 = 1;
        page3 = 1;
        switch (mViewPager.getCurrentItem()){
            case 0:
                mPresenter.circleUser1(mCircle_id, page1, 1, true);
                break;
            case 1:
                mPresenter.circleUser2(mCircle_id, page2, 2, true);
                break;
            case 2:
                mPresenter.circleUser3(mCircle_id, page3, 3, true);
                break;
        }
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        switch (mViewPager.getCurrentItem()){
            case 0:
                if (page1 < mPage_count) {
                    page1++;
                    mPresenter.circleUser1(mCircle_id, page1, mViewPager.getCurrentItem() + 1, false);
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                break;
            case 1:
                if (page2 < mPage_count) {
                    page2++;
                    mPresenter.circleUser1(mCircle_id, page2, mViewPager.getCurrentItem() + 1, false);
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                break;
            case 2:
                if (page3 < mPage_count) {
                    page3++;
                    mPresenter.circleUser1(mCircle_id, page3, mViewPager.getCurrentItem() + 1, false);
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<MemberEntity.CircleUserBean> mList = baseQuickAdapter.getData();
        if (mList.get(i).getIs_author() == 0) {
            Intent intent = new Intent(this, CircleManDetailActivity.class);
            intent.putExtra("user_id", mList.get(i).getUser_id() + "");
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MasterDetailActivity.class);
            intent.putExtra("user_id", mList.get(i).getUser_id() + "");
            startActivity(intent);
        }
    }
}
