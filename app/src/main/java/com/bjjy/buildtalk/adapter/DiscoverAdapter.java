package com.bjjy.buildtalk.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.DiscoverEntity;
import com.bjjy.buildtalk.utils.GlideUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author power
 * @date 2019/4/28 10:19 AM
 * @project BuildTalk
 * @description:
 */
public class DiscoverAdapter extends BaseMultiItemQuickAdapter<DiscoverEntity, BaseViewHolder> {
    public static final int BODY_BANNER = 0;            //轮播图
    public static final int BODY_EVERYDAY_TALK = 1;     //每日一谈
    public static final int BODY_HOT_TOPTIC = 2;        //热门话题
    public static final int BODY_COURSE = 3;            //精品课程
    public static final int BODY_PROJECT = 4;           //精彩专题

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DiscoverAdapter(List<DiscoverEntity> data) {
        super(data);
        addItemType(BODY_BANNER, R.layout.discover_body_banner_layout);
        addItemType(BODY_EVERYDAY_TALK, R.layout.discover_body_everyday_layout);
        addItemType(BODY_HOT_TOPTIC, R.layout.discover_body_toptic_layout);
        addItemType(BODY_COURSE, R.layout.discover_body_course_layout);
        addItemType(BODY_PROJECT, R.layout.discover_body_project_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscoverEntity item) {
        switch (item.getItemType()) {
            case BODY_BANNER:
                List<Integer> banner_list = new ArrayList<>();
                banner_list.add(R.drawable.test_banner);
                banner_list.add(R.drawable.test_banner);
                banner_list.add(R.drawable.test_banner);
                Banner banner = helper.getView(R.id.banner);
                banner.setImages(banner_list).setImageLoader(new GlideUtils()).start();
                banner.setOnBannerListener(position -> {
                    ToastUtils.showShort("轮播图" + position);
                });
                break;
            case BODY_EVERYDAY_TALK:
                List<String> et_list = new ArrayList<>();
                et_list.add("");
                et_list.add("");
                et_list.add("");
                RecyclerView et_RecyclerView = helper.getView(R.id.et_recyclerView);
                et_RecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                EveryTalkAdapter everyTalkAdapter = new EveryTalkAdapter(R.layout.adapter_every_talk, et_list);
                et_RecyclerView.setAdapter(everyTalkAdapter);
                helper.addOnClickListener(R.id.et_all_tv);
                everyTalkAdapter.setOnItemClickListener((adapter, view, position) -> {
                    ToastUtils.showShort("每日一谈：" + position);
                });
                break;
            case BODY_HOT_TOPTIC:
                List<String> toptic_list = new ArrayList<>();
                toptic_list.add("");
                toptic_list.add("");
                toptic_list.add("");
                RecyclerView toptic_RecyclerView = helper.getView(R.id.toptic_recyclerView);
                toptic_RecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                TopticAdapter topticAdapter = new TopticAdapter(R.layout.adapter_hot_toptic, toptic_list);
                toptic_RecyclerView.setAdapter(topticAdapter);
                helper.addOnClickListener(R.id.toptic_all_tv)
                        .addOnClickListener(R.id.toptic_change_ll);
                topticAdapter.setOnItemClickListener((adapter, view, position) -> {
                    ToastUtils.showShort("热门话题圈：" + position);
                });
                break;
            case BODY_COURSE:
                List<String> course_list = new ArrayList<>();
                course_list.add("");
                course_list.add("");
                course_list.add("");
                course_list.add("");
                RecyclerView course_RecyclerView = helper.getView(R.id.course_recyclerView);
                course_RecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                CourseAdapter courseAdapter = new CourseAdapter(R.layout.adapter_quality_course, course_list);
                course_RecyclerView.setAdapter(courseAdapter);
                helper.addOnClickListener(R.id.course_all_tv)
                        .addOnClickListener(R.id.course_change_ll);
                courseAdapter.setOnItemClickListener((adapter, view, position) -> {
                    ToastUtils.showShort("精品课程：" + position);
                });
                break;
            case BODY_PROJECT:
                List<String> project_list = new ArrayList<>();
                project_list.add("");
                project_list.add("");
                RecyclerView project_RecyclerView = helper.getView(R.id.project_recyclerView);
                project_RecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                ProjectAdapter projectAdapter = new ProjectAdapter(R.layout.adapter_good_project, project_list);
                project_RecyclerView.setAdapter(projectAdapter);
                projectAdapter.setOnItemClickListener((adapter, view, position) -> {
                    ToastUtils.showShort("精彩专题：" + position);
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return BODY_BANNER;
            case 1:
                return BODY_EVERYDAY_TALK;
            case 2:
                return BODY_HOT_TOPTIC;
            case 3:
                return BODY_COURSE;
            case 4:
                return BODY_PROJECT;
        }
        return super.getItemViewType(position);
    }
}
