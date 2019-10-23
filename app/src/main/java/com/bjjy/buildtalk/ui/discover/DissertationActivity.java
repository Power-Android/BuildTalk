package com.bjjy.buildtalk.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.DissertationAdapter;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.DissertationDetailEntity;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.weight.MyViewPagerAdapter;
import com.bjjy.buildtalk.weight.tablayout.TabLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DissertationActivity extends BaseActivity<DissertationPresenter> implements DissertationContract.View, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pic_iv)
    ImageView mPicIv;
    @BindView(R.id.toolbar_right_share)
    ImageView mToolbarRightShare;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.banquan_tv)
    TextView mBanquanTv;

    private String mId;
    private List<DissertationDetailEntity.DissertationAuthorBean> mList = new ArrayList<>();
    private DissertationAdapter mAdapter;
    private DissertationDetailEntity mEntity;
    private TextView mContentTv;
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dissertation;
    }

    @Override
    protected void initView() {
        mId = getIntent().getStringExtra("id");
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
//        mToolbarRightShare.setVisibility(View.VISIBLE);
//        mToolbarRightShare.setOnClickListener(this);
        mPresenter.tabData();
    }

    @Override
    public void handlerTabData(List<String> list, List<View> views) {
        mViewPager.setAdapter(new MyViewPagerAdapter(list, views));
        mTabLayout.setupWithViewPager(mViewPager);
        mContentTv = views.get(0).findViewById(R.id.content_tv);

        mRecyclerView = views.get(1).findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DissertationAdapter(R.layout.adapter_dissertation_layout, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void initEventAndData() {
        mPresenter.dissertation(mId);
    }

    @Override
    public void handlerDetail(DissertationDetailEntity detailEntity) {
        this.mEntity = detailEntity;
        mToolbarTitle.setText(detailEntity.getDissertation_title());
        Glide.with(this).load(detailEntity.getDissertation_pic().get(0).getPic_url()).into(mPicIv);
        mContentTv.setText(Html.fromHtml(detailEntity.getDissertation_desc()));

        mAdapter.setNewData(detailEntity.getDissertationAuthor());

        mBanquanTv.setText("@"+detailEntity.getCopyrightYear()+"-版权归承办方所有");

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<DissertationDetailEntity.DissertationAuthorBean> data = adapter.getData();
        Intent intent = new Intent(this, EveryTalkDetailActivity.class);
        intent.putExtra("article_id", data.get(position).getArticle_id() + "");
        intent.putExtra("type", "article");
        intent.putExtra("type_zhuanti", mEntity.getType());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        String path = "pages/sub_browse/pages/activity/activity?" + mId;
        DialogUtils.shareSmallProgram(path, mEntity.getDissertation_pic().get(0).getPic_url(), mEntity.getDissertation_title(),
                "", this, SHARE_MEDIA.WEIXIN);
    }
}
