package com.bjjy.buildtalk.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.entity.ImgOptionEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.ui.main.ViewPagerActivity;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.weight.MyGridAdapter;
import com.bjjy.buildtalk.weight.NoScrollGridView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author power
 * @date 2019/5/23 2:07 PM
 * @project BuildTalk
 * @description:
 */
public class CircleTopticAdapter extends BaseQuickAdapter<ThemeInfoEntity.ThemeInfoBean, BaseViewHolder> {
    private String isJoin;
    private Activity mActivity;

    public CircleTopticAdapter(int layoutResId, @Nullable List<ThemeInfoEntity.ThemeInfoBean> data, String isJoin, Activity activity) {
        super(layoutResId, data);
        this.isJoin = isJoin;
        this.mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeInfoEntity.ThemeInfoBean item) {

        Glide.with(mContext).load(item.getHeadImage()).into((ImageView) helper.getView(R.id.item_face_iv));
        helper.setText(R.id.item_name_tv, item.getName())
                .setGone(R.id.item_job_tv, "1".equals(item.getIs_circleMaster()) ? true : false)
                .setGone(R.id.item_more_iv, "1".equals(isJoin) ? true : false)
                .setGone(R.id.column_rl, "1".equals(isJoin) ? true : false)
                .setGone(R.id.praise_rl, "1".equals(isJoin) ? true : false)
                .setGone(R.id.comment_rl, "1".equals(isJoin) ? true : false)
                .setText(R.id.item_time_tv, item.getPublish_time())
                .setText(R.id.item_content_tv, item.getTheme_content());
        List<ThemeInfoEntity.ThemeInfoBean.ThemeImageBean> themeImageBeanList = item.getTheme_image();
        NoScrollGridView gridView = helper.getView(R.id.item_grid_view);
        if (themeImageBeanList.size() == 4) {
            gridView.setNumColumns(2);
        } else {
            gridView.setNumColumns(3);
        }
        gridView.setAdapter(new MyGridAdapter(themeImageBeanList));

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<ImageView> imgDatas = new ArrayList<>();
            LogUtils.e(gridView.getChildCount());
            for (int i = 0; i < gridView.getChildCount(); i++) {
                RelativeLayout relativeLayout = (RelativeLayout) gridView.getChildAt(i);
                ImageView imageView = relativeLayout.findViewById(R.id.image);
                imgDatas.add(imageView);
            }
            List<ImgOptionEntity> optionEntities = new ArrayList<>();
            int[] screenLocationS = new int[2];
            for (int i = 0; i < imgDatas.size(); i++) {
                ImageView img = imgDatas.get(i);
                //获取当前ImageView 在屏幕中的位置 宽高
                img.getLocationOnScreen(screenLocationS);
                ImgOptionEntity entity = new
                        ImgOptionEntity(screenLocationS[0], screenLocationS[1], img.getWidth(), img.getHeight());
                entity.setImgUrl(themeImageBeanList.get(i).getPic_url());
                optionEntities.add(entity);
            }

            Intent bIntent = new Intent(mContext, ViewPagerActivity.class);
            bIntent.putExtra("positon", position);
            bIntent.putExtra("optionEntities", (Serializable) optionEntities);
            mContext.startActivity(bIntent);
            //取消原有默认的Activity到Activity的过渡动画
            mActivity.overridePendingTransition(0, 0);
        });

        if (1 == item.getIs_parise()) {
            helper.setImageResource(R.id.item_praise_iv, R.drawable.praise_sel);
        } else {
            helper.setImageResource(R.id.item_praise_iv, R.drawable.praise_def);
        }
        List<ThemeInfoEntity.ThemeInfoBean.PariseNickNameBean> praiseList = item.getParise_nickName();
        LinearLayout pariseRl = helper.getView(R.id.praise_rl);
        if ("1".equals(isJoin) && praiseList.size() > 0) {
            pariseRl.setVisibility(View.VISIBLE);
            String praiseStr = StringUtils.listToString1(praiseList, ',');
            helper.setText(R.id.praise_tv, praiseStr);
        } else {
            pariseRl.setVisibility(View.GONE);
        }
        helper.setGone(R.id.more_tv, item.getCommentPage_count() > 1 ? true : false);
        RecyclerView commentRecycler = helper.getView(R.id.recycler_view);
        commentRecycler.setNestedScrollingEnabled(false);
        commentRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        CommentAdapter commentAdapter = new CommentAdapter(R.layout.adapter_circle_comment, item.getComment_content());
        commentRecycler.setAdapter(commentAdapter);
    }

    private class CommentAdapter extends BaseQuickAdapter<ThemeInfoEntity.ThemeInfoBean.CommentContentBean, BaseViewHolder> {

        public CommentAdapter(int layoutResId, @Nullable List<ThemeInfoEntity.ThemeInfoBean.CommentContentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ThemeInfoEntity.ThemeInfoBean.CommentContentBean item) {
            helper.setText(R.id.comment_name_tv, item.getName() + "：")
                    .setText(R.id.comment_content_tv, item.getContent());
        }
    }

}
