package com.bjjy.buildtalk.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.BannerEntity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.entity.DiscoverEntity;
import com.bjjy.buildtalk.entity.DissertationEntity;
import com.bjjy.buildtalk.entity.MasterListEntity;
import com.bjjy.buildtalk.entity.SongsEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.ui.discover.DissertationActivity;
import com.bjjy.buildtalk.ui.discover.EveryTalkDetailActivity;
import com.bjjy.buildtalk.ui.mine.AboutUsActivity;
import com.bjjy.buildtalk.ui.talk.CircleManDetailActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.utils.GlideUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;

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
    public static final int BODY_MASTER = 5;            //关注大咖
    public static final int BODY_ESSENCE = 6;           //精华主题

    private List<BannerEntity> mBannerEntities = new ArrayList<>();
    private List<String> mBannerImageList = new ArrayList<>();
    private List<SongsEntity> songsEntities = new ArrayList<>();
    private CourseEntity mTopticEntity;
    private List<CourseEntity.CircleInfoBean> mTopticEntities = new ArrayList<>();
    private CourseEntity mCourseEntity;
    private List<CourseEntity.CircleInfoBean> mCourseEntities = new ArrayList<>();
    private List<DissertationEntity> dissertationEntities = new ArrayList<>();
    private List<MasterListEntity.MasterInfoBean> mMasterList = new ArrayList<>();
    private List<ThemeInfoEntity.ThemeInfoBean> mEssenceList = new ArrayList<>();
    private Intent mIntent;
    private OnChildRecyclerItemClickListener mOnChildRecyclerItemClickListener;
    private String title;
    private MasterAAdapter mMasterAAdapter;

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
//        addItemType(BODY_PROJECT, R.layout.discover_body_project_layout);
        addItemType(BODY_MASTER, R.layout.discover_body_master_layout);
        addItemType(BODY_ESSENCE, R.layout.discover_body_essence_layout);
    }

    public interface OnChildRecyclerItemClickListener {
        void onMasterItemClick(BaseQuickAdapter adapter, View view, int position);
    }

    public void setOnChildRecyclerItemClickListener(OnChildRecyclerItemClickListener onChildRecyclerItemClickListener) {
        this.mOnChildRecyclerItemClickListener = onChildRecyclerItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscoverEntity item) {
        switch (item.getItemType()) {
            case BODY_BANNER:
                Banner banner = helper.getView(R.id.banner);
                banner.setOnBannerListener(position -> {
                    switch (mBannerEntities.get(position).getType_id()) {
                        case 1:
                            if ("1".equals(mBannerEntities.get(position).getCircle_type())) {
                                Intent intent = new Intent(mContext, TopticCircleActivity.class);
                                intent.putExtra("circle_id", mBannerEntities.get(position).getData_id() + "");
                                mContext.startActivity(intent);
                            } else {
                                Intent intent = new Intent(mContext, CourseCircleActivity.class);
                                intent.putExtra("circle_id", mBannerEntities.get(position).getData_id() + "");
                                mContext.startActivity(intent);
                            }
                            break;
                        case 2:
                            mIntent = new Intent(mContext, EveryTalkDetailActivity.class);
                            mIntent.putExtra("article_id", mBannerEntities.get(position).getData_id() + "");
                            mIntent.putExtra("type", "article");
                            mContext.startActivity(mIntent);
                            break;
                        case 3:
                            mIntent = new Intent(mContext, MasterDetailActivity.class);
                            mIntent.putExtra("user_id", mBannerEntities.get(position).getData_id() + "");
                            mContext.startActivity(mIntent);
                            break;
                        case 4:
                            mIntent = new Intent(mContext, DissertationActivity.class);
                            mIntent.putExtra("id", mBannerEntities.get(position).getData_id() + "");
                            mContext.startActivity(mIntent);
                            break;
                        case 5:
                            mIntent = new Intent(mContext, AboutUsActivity.class);
                            mIntent.putExtra("title", "web");
                            mIntent.putExtra("url", mBannerEntities.get(position).getUrl());
                            mContext.startActivity(mIntent);
                            break;
                    }
                });
                banner.setImages(mBannerImageList).setImageLoader(new GlideUtils()).start();
                break;
            case BODY_EVERYDAY_TALK:
                helper.addOnClickListener(R.id.bg_iv)
                        .addOnClickListener(R.id.talk_play_iv);
                if (TextUtils.isEmpty(title)) {
                    if (songsEntities.size() > 0) {
                        String article_title = songsEntities.get(0).getArticle_title();
                        SpannableString spannableString = new SpannableString(article_title);
                        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF8F8F8F"));
                        spannableString.setSpan(colorSpan, 0, article_title.indexOf("期") + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        helper.setText(R.id.content_tv, spannableString);
                    }
                } else {
                    SpannableString spannableString = new SpannableString(title);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF8F8F8F"));
                    spannableString.setSpan(colorSpan, 0, title.indexOf("期") + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    helper.setText(R.id.content_tv, spannableString);
                }
//                everyTalkAdapter.setOnItemClickListener((adapter, view, position) -> {
//                    Intent intent = new Intent(mContext, EveryTalkDetailActivity.class);
//                    intent.putExtra("article_id", mEveryTalkEntities.get(position).getArticle_id() + "");
//                    mContext.startActivity(intent);
//                });
//                everyTalkAdapter.setOnItemChildClickListener((adapter, view, position) ->
//                        mOnChildRecyclerItemClickListener.onEveryTalkItemClick(adapter, view, position));
                break;
            case BODY_HOT_TOPTIC:
                RecyclerView toptic_RecyclerView = helper.getView(R.id.toptic_recyclerView);
                if (toptic_RecyclerView == null)
                    break;
                toptic_RecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                TopticAdapter topticAdapter = new TopticAdapter(R.layout.adapter_hot_toptic, mTopticEntities);
                toptic_RecyclerView.setAdapter(topticAdapter);
                helper.addOnClickListener(R.id.toptic_change_rl);
                topticAdapter.setOnItemClickListener((adapter, view, position) -> {
                    Intent intent = new Intent(mContext, TopticCircleActivity.class);
                    intent.putExtra("circle_id", mTopticEntity.getCircleInfo().get(position).getCircle_id() + "");
                    mContext.startActivity(intent);
                });
                break;
            case BODY_COURSE:
                RecyclerView course_RecyclerView = helper.getView(R.id.course_recyclerview);
                if (course_RecyclerView == null)
                    break;
                course_RecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                CourseAdapter courseAdapter = new CourseAdapter(R.layout.adapter_quality_course, mCourseEntities);
                course_RecyclerView.setAdapter(courseAdapter);
                helper.addOnClickListener(R.id.course_all_tv);
                courseAdapter.setOnItemClickListener((adapter, view, position) -> {
                    List<CourseEntity.CircleInfoBean> mCourseEntities = adapter.getData();
                    Intent intent = new Intent(mContext, CourseCircleActivity.class);
                    intent.putExtra("circle_id", String.valueOf(mCourseEntities.get(position).getCircle_id()));
                    mContext.startActivity(intent);
                });
                break;
//            case BODY_PROJECT:
//                RecyclerView project_RecyclerView = helper.getView(R.id.project_recyclerView);
//                project_RecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//                ProjectAdapter projectAdapter = new ProjectAdapter(R.layout.adapter_good_project, dissertationEntities);
//                project_RecyclerView.setAdapter(projectAdapter);
//                projectAdapter.setOnItemClickListener((adapter, view, position) -> {
//                    List<DissertationEntity> data = adapter.getData();
//                    Intent intent = new Intent(mContext, DissertationActivity.class);
//                    intent.putExtra("id", data.get(position).getDissertation_id() + "");
//                    mContext.startActivity(intent);
//                });
//                helper.addOnClickListener(R.id.project_all_tv);
//                break;
            case BODY_MASTER:
                RecyclerView master_RecyclerView = helper.getView(R.id.master_recyclerView);
                if (master_RecyclerView == null)
                    break;
                master_RecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                        LinearLayoutManager.HORIZONTAL, false));
                mMasterAAdapter = new MasterAAdapter(R.layout.adapter_master_layout, mMasterList);
                master_RecyclerView.setAdapter(mMasterAAdapter);
                helper.addOnClickListener(R.id.master_change_rl)
                        .addOnClickListener(R.id.master_more_tv);
                mMasterAAdapter.setOnItemClickListener((adapter, view, position) -> {
                    Intent intent = new Intent(mContext, CircleManDetailActivity.class);
                    intent.putExtra("user_id", mMasterList.get(position).getUser_id() + "");
                    mContext.startActivity(intent);
                });
                mMasterAAdapter.setOnItemChildClickListener((adapter, view, position) ->
                        mOnChildRecyclerItemClickListener.onMasterItemClick(adapter, view, position));
                break;
            case BODY_ESSENCE:
                RecyclerView essence_recyclerView = helper.getView(R.id.essence_recyclerview);
                if (essence_recyclerView == null)
                    break;
                essence_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                EssenceAdapter essenceAdapter = new EssenceAdapter(R.layout.adapter_essence_layout, mEssenceList);
                essence_recyclerView.setAdapter(essenceAdapter);
                essenceAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(mContext, TopticCircleActivity.class);
                        intent.putExtra("circle_id", mEssenceList.get(position).getParent_themeInfo().getCircle_id()+"");
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }

    public void setBannerEntities(List<BannerEntity> bannerEntities, List<String> bannerImageList) {
        this.mBannerEntities = bannerEntities;
        this.mBannerImageList = bannerImageList;
        notifyItemChanged(0);
    }

    public void setEveryTalkEntities(List<SongsEntity> songsEntities) {
        this.songsEntities = songsEntities;
        notifyItemChanged(1);
    }

    public void setTopticEntities(CourseEntity topticEntities) {
        this.mTopticEntity = topticEntities;
        List<CourseEntity.CircleInfoBean> topticList = topticEntities.getCircleInfo();
        this.mTopticEntities = topticList;
        notifyItemChanged(2);
    }

    public void setCourseEntities(CourseEntity courseEntities) {
        this.mCourseEntity = courseEntities;
        List<CourseEntity.CircleInfoBean> courseList = courseEntities.getCircleInfo();
        this.mCourseEntities = courseList;
        notifyItemChanged(3);
    }

    public void setDissertationEntities(List<DissertationEntity> dissertationEntities) {
        this.dissertationEntities = dissertationEntities;
//        notifyItemChanged(4);
    }

    public void setAuthorEntities(List<MasterListEntity.MasterInfoBean> masterInfo) {
        this.mMasterList = masterInfo;
        notifyItemChanged(4);
    }

    public void setEssenceEntities(List<ThemeInfoEntity.ThemeInfoBean> themeInfoBeanList) {
        mEssenceList = themeInfoBeanList;
        notifyItemChanged(5);
    }

    public void setFocus(int i) {
        mMasterAAdapter.notifyItemChanged(i);
    }

    public void setMediaTitle(String title) {
        this.title = title;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
