package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleAdapter;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.bjjy.buildtalk.entity.CircleEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author power
 * @date 2019/4/26 4:33 PM
 * @project BuildTalk
 * @description: 圈子 模块
 */
public class CircleFragment extends BaseFragment<CirclePresenter> implements CircleContract.View, OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.circle_recyclerView)
    RecyclerView mCircleRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private List<CircleEntity> circle_list = new ArrayList<>();
    private CircleAdapter mCircleAdapter;

    BottomSheetDialog mBottomSheetDialog;
    BottomSheetBehavior mBehavior;
    private View mView;

    public static CircleFragment newInstance() {
        return new CircleFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initView() {
        mToolbarTitle.setText(R.string.circle);
        mRefreshLayout.setOnRefreshListener(this);
        mCircleRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mCircleAdapter = new CircleAdapter(circle_list);
        mCircleRecyclerView.setAdapter(mCircleAdapter);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.circle_header_view, null);
        mCircleAdapter.addHeaderView(headerView);
        headerView.setOnClickListener(v -> startActivity(new Intent(mContext, CircleSearchActivity.class)));
        mCircleAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.circleList(circle_list);
    }

    @Override
    public void handlerCircleList(List<CircleEntity> circle_list) {
        mCircleAdapter.setNewData(circle_list);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(2000);
    }

    private void createCircle() {
        if (mBottomSheetDialog == null) {
            mBottomSheetDialog = new BottomSheetDialog(mContext, R.style.bottom_sheet_dialog);
            mBottomSheetDialog.setCancelable(true);
            mBottomSheetDialog.setCanceledOnTouchOutside(true);
            mView = getLayoutInflater().inflate(R.layout.dialog_circle_agreenment, null);
            mBottomSheetDialog.setContentView(mView);
            mBehavior = BottomSheetBehavior.from((View) mView.getParent());
            mBehavior.setSkipCollapsed(true);
//            int peekHeight = getResources().getDisplayMetrics().heightPixels;
            //设置默认弹出高度为屏幕的0.4倍
//            mBehavior.setPeekHeight((int)(0.4 * peekHeight));
        }
        mBottomSheetDialog.show();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        CheckBox checkBox = mView.findViewById(R.id.checkbox);
        TextView agreement = mView.findViewById(R.id.agreement);
        TextView next = mView.findViewById(R.id.next);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                next.setBackground(getResources().getDrawable(R.drawable.shape_blue_5button));
                next.setClickable(true);
            } else {
                next.setBackground(getResources().getDrawable(R.drawable.shape_blue_light_5button));
                next.setClickable(false);
            }
        });
        agreement.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            startActivity(new Intent(mContext, CircleAgreementActvity.class));
        });
        next.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            startActivity(new Intent(mContext, CreateCircleActivity.class));
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        if (position == circle_list.size() - 1) {
            createCircle();
        }else if (position == 1){
            startActivity(new Intent(mContext, CourseCircleActivity.class));
        }else {
            startActivity(new Intent(mContext, TopticCircleActivity.class));
        }
    }
}
