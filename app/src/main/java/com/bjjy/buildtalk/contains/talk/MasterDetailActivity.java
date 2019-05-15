package com.bjjy.buildtalk.contains.talk;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.utils.SizeUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.weight.MyViewPagerAdapter;
import com.bjjy.buildtalk.weight.tablayout.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MasterDetailActivity extends BaseActivity<MasterDetailPresenter> implements MasterDetailContract.View, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.master_bg)
    ImageView mMasterBg;
    @BindView(R.id.face_iv)
    CircleImageView mFaceIv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.tag_iv)
    ImageView mTagIv;
    @BindView(R.id.job_tv)
    TextView mJobTv;
    @BindView(R.id.focus_iv)
    ImageView mFocusIv;
    @BindView(R.id.focus_tv)
    TextView mFocusTv;
    @BindView(R.id.focus_ll)
    LinearLayout mFocusLl;
    @BindView(R.id.circle_num_tv)
    TextView mCircleNumTv;
    @BindView(R.id.fans_num_tv)
    TextView mFansNumTv;
    @BindView(R.id.collect_num_tv)
    TextView mCollectNumTv;
    @BindView(R.id.focus_num_tv)
    TextView mFocusNumTv;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.top_title_rl)
    RelativeLayout mTopTitleRl;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.min_rl)
    RelativeLayout mMinRl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_master_detail;
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeStatusBar(this, true, true);
        mTopTitleRl.setPadding(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        //设置appbar滚动布局的最小高度 因为getHeight可能为0，所以直接加上导航栏和tablayout的高度
        mMinRl.setMinimumHeight(StatusBarUtils.getStatusBarHeight() + (int) getResources().getDimension(R.dimen.dp_44));
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.tabData();
    }

    @Override
    public void handlerTab(List<String> list, List<View> views) {
        mViewpager.setAdapter(new MyViewPagerAdapter(list, views));
        mTablayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();

        if (offset > SizeUtils.dp2px(getResources().getDimension(R.dimen.dp_44))) {
            mTitleTv.setText(mNameTv.getText());
            mBackIv.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_black_icon));
            mTitleTv.setVisibility(View.VISIBLE);
        } else {
            mBackIv.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_white_icon));
            mTitleTv.setVisibility(View.GONE);
        }

        float scale = (float) offset / scrollRange;
        int alpha = (int) (255 * scale);
        mTopTitleRl.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
    }

    @OnClick({R.id.face_iv, R.id.focus_ll, R.id.circle_num_tv, R.id.fans_num_tv, R.id.collect_num_tv, R.id.focus_num_tv, R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.face_iv:
                break;
            case R.id.focus_ll:
                break;
            case R.id.circle_num_tv:
                startActivity(new Intent(this,MasterCircleActivity.class));
                break;
            case R.id.fans_num_tv:
                startActivity(new Intent(this,FansFocusActivity.class));
                break;
            case R.id.collect_num_tv:
                startActivity(new Intent(this,MasterCollectActivity.class));
                break;
            case R.id.focus_num_tv:
                startActivity(new Intent(this,FansFocusActivity.class));
                break;
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
