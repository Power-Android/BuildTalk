package com.bjjy.buildtalk.ui.talk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleSearchResultAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.MasterDetailEntity;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.SizeUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MyViewPagerAdapter;
import com.bjjy.buildtalk.weight.tablayout.TabLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CircleManDetailActivity extends BaseActivity<CircleManDetailPresenter> implements CircleManDetailContract.View, AppBarLayout.OnOffsetChangedListener, OnRefreshLoadMoreListener, ViewPager.OnPageChangeListener {

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
    @BindView(R.id.fans_num_tv)
    TextView mFansNumTv;
    @BindView(R.id.collect_num_tv)
    TextView mCollectNumTv;
    @BindView(R.id.focus_num_tv)
    TextView mFocusNumTv;
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
    @BindView(R.id.top_title_rl)
    RelativeLayout mTopTitleRl;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private String mUser_id;//被查看用户的ID
    private CircleSearchResultAdapter mAdapter, mAdapter1;
    private List<SearchResultEntity.CircleInfoBean> mList = new ArrayList<>();
    private List<SearchResultEntity.CircleInfoBean> mList1 = new ArrayList<>();
    private int create_page = 1, join_page = 1;
    private int create_Page_count, join_page_count;
    private int page_Position = 0;
    private MasterDetailEntity mMasterDetailEntity;
    private Intent mIntent;
    private BaseDialog mDialog;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(RefreshEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.FANS_REFRESH)) {
            recreate();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_man_detail;
    }

    @Override
    protected void initView() {
        mUser_id = getIntent().getStringExtra("user_id");
        StatusBarUtils.changeStatusBar(this, true, true);
        mTopTitleRl.setPadding(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        //设置appbar滚动布局的最小高度 因为getHeight可能为0，所以直接加上导航栏和tablayout的高度
        mMinRl.setMinimumHeight(StatusBarUtils.getStatusBarHeight() + (int) getResources().getDimension(R.dimen.dp_44));
        mAppBarLayout.addOnOffsetChangedListener(this);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mViewpager.addOnPageChangeListener(this);
    }

    @Override
    protected void initEventAndData() {
        EventBus.getDefault().register(this);
        mPresenter.tabData();
    }

    @Override
    public void handlerTab(List<String> titleList, List<View> views) {
        if (mPresenter.mDataManager.getUser().getUser_id() != null && mPresenter.mDataManager.getUser().getUser_id().equals(mUser_id)){
            mFocusLl.setVisibility(View.GONE);
        }else {
            mFocusLl.setVisibility(View.VISIBLE);
        }

        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(titleList, views);
        mViewpager.setAdapter(viewPagerAdapter);
        mTablayout.setupWithViewPager(mViewpager);

        RecyclerView create_recyclerView = views.get(0).findViewById(R.id.recycler_view);
        create_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CircleSearchResultAdapter(R.layout.adapter_circle_search_result, mList);
        create_recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            List<SearchResultEntity.CircleInfoBean> data = baseQuickAdapter.getData();
            if ("1".equals(data.get(i).getType())){
                Intent intent = new Intent(CircleManDetailActivity.this, TopticCircleActivity.class);
                intent.putExtra("circle_id", data.get(i).getCircle_id()+"");
                startActivity(intent);
            }else {
                Intent intent = new Intent(CircleManDetailActivity.this, CourseCircleActivity.class);
                intent.putExtra("circle_id", data.get(i).getCircle_id()+"");
                startActivity(intent);
            }
        });

        RecyclerView join_recyclerView = views.get(1).findViewById(R.id.recycler_view);
        join_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter1 = new CircleSearchResultAdapter(R.layout.adapter_circle_search_result, mList1);
        join_recyclerView.setAdapter(mAdapter1);
        mAdapter1.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            List<SearchResultEntity.CircleInfoBean> data = baseQuickAdapter.getData();
            if ("1".equals(data.get(i).getType())){
                Intent intent = new Intent(CircleManDetailActivity.this, TopticCircleActivity.class);
                intent.putExtra("circle_id", data.get(i).getCircle_id()+"");
                startActivity(intent);
            }else {
                Intent intent = new Intent(CircleManDetailActivity.this, CourseCircleActivity.class);
                intent.putExtra("circle_id", data.get(i).getCircle_id()+"");
                startActivity(intent);
            }
        });

        mPresenter.userDetail(mUser_id);
        mPresenter.createList(mUser_id, create_page, false);
        mPresenter.joinList(mUser_id, join_page, false);

    }

    @Override
    public void handlerUserDetail(MasterDetailEntity detailEntity) {
        this.mMasterDetailEntity = detailEntity;
        mTitleTv.setText(detailEntity.getName());
        Glide.with(this).load(detailEntity.getBg_pic()).into(mMasterBg);
        Glide.with(this).load(detailEntity.getHeadImage()).into(mFaceIv);
        mNameTv.setText(detailEntity.getName());
        if (1 == detailEntity.getIs_author()){
            mTagIv.setVisibility(View.VISIBLE);
        }else {
            mTagIv.setVisibility(View.GONE);
        }
        mJobTv.setText(detailEntity.getAuthor_desc());
        if (1 == detailEntity.getIs_attention()){
            mFocusIv.setVisibility(View.VISIBLE);
            mFocusTv.setText("已关注");
        }else {
            mFocusIv.setVisibility(View.GONE);
            mFocusTv.setText("+关注");
        }
        mFansNumTv.setText(detailEntity.getCountMyFans() + "\n粉丝");
        mCollectNumTv.setText(detailEntity.getCountMyCollect() + "\n收藏");
        mFocusNumTv.setText(detailEntity.getCountMyAttention() + "\n关注");
    }

    @Override
    public void handlerCreate(SearchResultEntity resultEntity, boolean isRefresh) {
        create_Page_count = resultEntity.getPage_count();
        mList = resultEntity.getCircleInfo();
        if (isRefresh){
            mAdapter.setNewData(mList);
        }else {
            mAdapter.addData(mList);
        }
    }

    @Override
    public void handlerJoin(SearchResultEntity resultEntity, boolean isRefresh) {
        join_page_count = resultEntity.getPage_count();
        mList1 = resultEntity.getCircleInfo();
        if (isRefresh){
            mAdapter1.setNewData(mList1);
        }else {
            mAdapter1.addData(mList1);
        }
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

    @OnClick({R.id.face_iv, R.id.focus_ll, R.id.fans_num_tv, R.id.collect_num_tv, R.id.focus_num_tv, R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.face_iv:
                break;
            case R.id.focus_ll:
                LoginHelper.login(this, mPresenter.mDataManager, () -> {
                    if (1 == mMasterDetailEntity.getIs_attention()){
                        showDialog();
                    }else {
                        mPresenter.attention(mUser_id);
                    }
                });

                break;
            case R.id.fans_num_tv:
                mIntent = new Intent(this, FansFocusActivity.class);
                mIntent.putExtra("user_id", mUser_id);
                mIntent.putExtra("name", mMasterDetailEntity.getName());
                mIntent.putExtra("type", "fans");
                startActivity(mIntent);
                break;
            case R.id.collect_num_tv:
                mIntent = new Intent(this,MasterCollectActivity.class);
                mIntent.putExtra("user_id", mUser_id);
                mIntent.putExtra("name", mMasterDetailEntity.getName());
                startActivity(mIntent);
                break;
            case R.id.focus_num_tv:
                mIntent = new Intent(this, FansFocusActivity.class);
                mIntent.putExtra("user_id", mUser_id);
                mIntent.putExtra("name", mMasterDetailEntity.getName());
                mIntent.putExtra("type", "focus");
                startActivity(mIntent);
                break;
            case R.id.back_iv:
                finish();
                break;
        }
    }

    private void showDialog() {
        mDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    mPresenter.attention(mUser_id);
                    mDialog.dismiss();
                })
                .builder();
        TextView textView = mDialog.getView(R.id.text);
        textView.setText("确定取消关注？");
        mDialog.show();
    }

    @Override
    public void handlerAttrntion(BaseResponse<IEntity> baseResponse) {
        if (TextUtils.equals("关注成功", baseResponse.getErrorMsg())){
            mMasterDetailEntity.setIs_attention(1);
            mFocusIv.setVisibility(View.VISIBLE);
            mFocusTv.setText("已关注");
            ToastUtils.showCollect("关注成功", getResources().getDrawable(R.drawable.collect_success_icon));
        }else {
            mMasterDetailEntity.setIs_attention(0);
            mFocusIv.setVisibility(View.GONE);
            mFocusTv.setText("+关注");
            ToastUtils.showCollect("已取消关注", getResources().getDrawable(R.drawable.collect_cancle_icon));
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (0 == page_Position){
            if (create_page < create_Page_count) {
                create_page++;
                mPresenter.createList(mUser_id, create_page, false);
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }else {
            if (join_page < join_page_count) {
                join_page++;
                mPresenter.joinList(mUser_id, join_page, false);
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (0 == page_Position){
            create_page = 1;
            mPresenter.createList(mUser_id, create_page, true);
        }else {
            join_page = 1;
            mPresenter.joinList(mUser_id, join_page, true);
        }
        refreshLayout.finishRefresh();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        page_Position = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
