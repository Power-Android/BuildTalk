package com.bjjy.buildtalk.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.AttentionHAdapter;
import com.bjjy.buildtalk.adapter.DiscoverHAdapter;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.SizeUtils;
import com.bjjy.buildtalk.weight.MyViewPagerAdapter;
import com.bjjy.buildtalk.weight.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author power
 * @date 2020/5/9 2:16 PM
 * @project BuildTalk
 * @description: 发现
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    Unbinder unbinder;

    private List<String> mTitleList = new ArrayList<>();
    private List<View> mViewList = new ArrayList<>();
    View attentionView, discoverView;
    private RecyclerView mAttentionRv;
    private RecyclerView mDiscoverRv;
    private List<IEntity> mDisList = new ArrayList<>();
    private List<IEntity> mAtenList = new ArrayList<>();
    private DiscoverHAdapter mDiscoverHAdapter;
    private AttentionHAdapter mAttentionHAdapter;

    public static HomeFragment getInstance(){
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frament_home;
    }

    @Override
    protected void initView() {
        initIndicator();
    }

    private void initIndicator() {
        mTitleList.add("关注");
        mTitleList.add("发现");
        attentionView = LayoutInflater.from(mContext).inflate(R.layout.view_attention, null, false);
        discoverView = LayoutInflater.from(mContext).inflate(R.layout.view_discover, null, false);
        mViewList.add(attentionView);
        mViewList.add(discoverView);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(mTitleList, mViewList);
        mViewpager.setAdapter(myViewPagerAdapter);

        mAttentionRv = attentionView.findViewById(R.id.recycler_view);
        mDiscoverRv = discoverView.findViewById(R.id.recycler_view);

        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mTitleList.get(index));
                simplePagerTitleView.setTextSize(20);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.text_color6));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.text_color7));
                simplePagerTitleView.setOnClickListener(v -> mViewpager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.parseColor("#FFA738"));
                indicator.setRoundRadius(10f);
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewpager);
        mViewpager.setCurrentItem(1);

        mDiscoverRv.setLayoutManager(new LinearLayoutManager(mContext));
        mDiscoverHAdapter = new DiscoverHAdapter(R.layout.adapter_discover_layout, mDisList);
        View d_headerView = LayoutInflater.from(mContext).inflate(R.layout.adapter_header_layout, null, false);
        mDiscoverHAdapter.addHeaderView(d_headerView);
        mDiscoverRv.setAdapter(mDiscoverHAdapter);

        mAttentionRv.setLayoutManager(new LinearLayoutManager(mContext));
        mAttentionHAdapter = new AttentionHAdapter(R.layout.adapter_attention_layout, mAtenList);
        View a_headerView = LayoutInflater.from(mContext).inflate(R.layout.adapter_header_layout, null, false);
        mAttentionHAdapter.addHeaderView(a_headerView);
        mAttentionRv.setAdapter(mAttentionHAdapter);
    }

    @Override
    protected void initEventAndData() {
        for (int i = 0; i < 5; i++) {
            mDisList.add(new IEntity());
            mAtenList.add(new IEntity());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
