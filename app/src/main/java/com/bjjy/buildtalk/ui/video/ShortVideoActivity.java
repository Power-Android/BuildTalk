package com.bjjy.buildtalk.ui.video;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.weight.recycler.OnPagerListener;
import com.bjjy.buildtalk.weight.recycler.PagerLayoutManager;
import com.bjjy.buildtalk.weight.recycler.ShortVideoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShortVideoActivity extends BaseActivity<ShortVideoPresenter> implements ShortVideoContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.more_iv)
    ImageView mMoreIv;

    private List<IEntity> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_short_video;
    }

    @Override
    protected void initView() {
        PagerLayoutManager pagerLayoutManager = new PagerLayoutManager(
                this, OrientationHelper.VERTICAL);
        ShortVideoAdapter videoAdapter = new ShortVideoAdapter(R.layout.adapter_short_video_layout, mList);
        mRecyclerView.setLayoutManager(pagerLayoutManager);
        mRecyclerView.setAdapter(videoAdapter);
        pagerLayoutManager.setOnViewPagerListener(new OnPagerListener() {
            @Override
            public void onInitComplete() {
                LogUtils.e("OnPagerListener---onInitComplete--"+"初始化完成");
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                LogUtils.e("OnPagerListener---onPageRelease--"+position+"-----"+isNext);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                LogUtils.e("OnPagerListener---onPageSelected--"+position+"-----"+isBottom);
            }
        });
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
                //view被回收，销毁videoplayer
            }
        });
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.back_iv, R.id.more_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.more_iv:
                break;
        }
    }
}
