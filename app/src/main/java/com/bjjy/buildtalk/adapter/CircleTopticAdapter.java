package com.bjjy.buildtalk.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.PariseNickNameBean;
import com.bjjy.buildtalk.entity.PdfInfoEntity;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.entity.ThemePdfBean;
import com.bjjy.buildtalk.ui.circle.PDFViewerActivity;
import com.bjjy.buildtalk.utils.AllUtils;
import com.bjjy.buildtalk.utils.SpanUtils;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.weight.MultiImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author power
 * @date 2019/5/23 2:07 PM
 * @project BuildTalk
 * @description:
 */
public class CircleTopticAdapter extends BaseMultiItemQuickAdapter<ThemeInfoEntity.ThemeInfoBean, BaseViewHolder> {
    public static final int BODY_SHOUQI = 0;
    public static final int BODY_NOMAL = 1;
    private String isJoin;
    private Activity mActivity;

    public CircleTopticAdapter(@Nullable List<ThemeInfoEntity.ThemeInfoBean> data, String isJoin, Activity activity) {
        super(data);
        addItemType(BODY_SHOUQI, R.layout.adapter_toptic_shouqi);
        addItemType(BODY_NOMAL, R.layout.adapter_article_toptic);
        this.isJoin = isJoin;
        this.mActivity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeInfoEntity.ThemeInfoBean item) {
        switch (item.getItemType()){
            case BODY_NOMAL:
                Glide.with(mContext).load(item.getHeadImage()).into((ImageView) helper.getView(R.id.item_face_iv));
                helper.setText(R.id.item_name_tv, item.getName())
                        .setGone(R.id.content_ll, !TextUtils.isEmpty(item.getTheme_content()))
                        .setGone(R.id.item_job_tv, "1".equals(item.getIs_circleMaster()))
                        .setVisible(R.id.item_more_iv, "1".equals(isJoin))
                        .setGone(R.id.column_rl, "1".equals(isJoin))
                        .setGone(R.id.praise_rl, "1".equals(isJoin))
                        .setGone(R.id.comment_rl, "1".equals(isJoin))
                        .setGone(R.id.item_top_iv, 1 == item.getIs_top())
                        .setVisible(R.id.item_shouqi_iv, 1 == item.getIs_top())
                        .setGone(R.id.item_grid_view, item.getTheme_image().size() > 0)
                        .setGone(R.id.pdf_rl, item.getTheme_pdf().size() > 0)
                        .setVisible(R.id.item_share_iv, item.getTheme_pdf().size() <= 0)
                        .setText(R.id.item_time_tv, TimeUtils.getFriendlyTimeSpanByNow(item.getPublish_time()))
                        .setText(R.id.item_content_tv, item.getTheme_content())
                        .addOnClickListener(R.id.item_face_iv)
                        .addOnClickListener(R.id.item_more_iv)
                        .addOnClickListener(R.id.item_praise_iv)
                        .addOnClickListener(R.id.item_comment_iv)
                        .addOnClickListener(R.id.item_share_iv)
                        .addOnClickListener(R.id.content_more_tv)
                        .addOnClickListener(R.id.item_shouqi_iv)
                        .addOnClickListener(R.id.more_tv);
                MultiImageView multiImageView = helper.getView(R.id.item_grid_view);
                TextView itemCotentTv = helper.getView(R.id.item_content_tv);
                TextView contentMoreTv = helper.getView(R.id.content_more_tv);
                itemCotentTv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        if (itemCotentTv.getLineCount() < 18) {
                            contentMoreTv.setVisibility(View.GONE);
                        } else {
                            contentMoreTv.setVisibility(View.VISIBLE);
                        }
                        //这个回调会调用多次，获取完行数记得注销监听
                        itemCotentTv.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }
                });

                List<ThemeImageBean> themeImageBeanList = item.getTheme_image();
                List<String> list = new ArrayList<>();
                for (int i = 0; i < themeImageBeanList.size(); i++) {
                    list.add(themeImageBeanList.get(i).getPic_url());
                }
                multiImageView.setList(list);
                multiImageView.setOnItemClickListener((view, position, imageViews) -> AllUtils.startImagePage(mActivity, list, Arrays.asList(imageViews), position));

                List<ThemePdfBean> theme_pdf = item.getTheme_pdf();
                List<PdfInfoEntity> list1 = new ArrayList<>();
                for (int i = 0; i < theme_pdf.size(); i++) {
                    list1.add(new PdfInfoEntity(theme_pdf.get(i).getPdf_name(),theme_pdf.get(i).getPdf_url()));
                }
                RecyclerView pdf_recyclerView = helper.getView(R.id.pdf_recyclerView);
                pdf_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                PdfVewAdapter pdfVewAdapter = new PdfVewAdapter(R.layout.adapter_pdf_view, list1);
                pdf_recyclerView.setAdapter(pdfVewAdapter);
                pdfVewAdapter.setOnItemClickListener((adapter, view, position) -> {
                    List<PdfInfoEntity> data =  adapter.getData();
                    Intent intent = new Intent(mContext, PDFViewerActivity.class);
                    intent.putExtra("data", data.get(position));
                    intent.putExtra("theme_id", item.getTheme_id()+"");
                    intent.putExtra("isCollect", 0 == item.getIs_collect());
                    mContext.startActivity(intent);
                });
                if (1 == item.getIs_parise()) {
                    helper.setImageResource(R.id.item_praise_iv, R.drawable.praise_sel);
                } else {
                    helper.setImageResource(R.id.item_praise_iv, R.drawable.praise_def);
                }
                List<PariseNickNameBean> praiseList = item.getParise_nickName();
                LinearLayout pariseRl = helper.getView(R.id.praise_rl);
                if ("1".equals(isJoin) && praiseList.size() > 0) {
                    pariseRl.setVisibility(View.VISIBLE);
                    String praiseStr = StringUtils.listToString1(praiseList, ',');
                    helper.setText(R.id.praise_tv, " "+item.getCountParise() + "  " +praiseStr);
                } else {
                    pariseRl.setVisibility(View.GONE);
                }
                helper.setGone(R.id.more_tv, item.getCommentPage_count() > 1 ? true : false);
                RecyclerView commentRecycler = helper.getView(R.id.recycler_view);
                commentRecycler.setNestedScrollingEnabled(false);
                commentRecycler.setLayoutManager(new LinearLayoutManager(mContext));
                CommentAdapter commentAdapter = new CommentAdapter(R.layout.adapter_circle_comment, item.getComment_content());
                commentRecycler.setAdapter(commentAdapter);
                commentAdapter.setOnItemClickListener((adapter, view, position) ->
                        mOnCommentItemlistener.onCommentClick(helper.getAdapterPosition(),commentAdapter, view, position, getData()));
                break;
            case BODY_SHOUQI:
                helper.setText(R.id.item_content_tv, TextUtils.isEmpty(item.getTheme_content()) ? "[图片]" : item.getTheme_content())
                        .addOnClickListener(R.id.item_zhankai_iv);
                break;
        }
    }

    public class CommentAdapter extends BaseQuickAdapter<CommentContentBean, BaseViewHolder> {
        private List<CommentContentBean> data;
        public CommentAdapter(int layoutResId, @Nullable List<CommentContentBean> data) {
            super(layoutResId, data);
            this.data = data;
        }

        @Override
        public int getItemCount() {
            if (data.size()  > 5){
                return 5;
            }
            return super.getItemCount();
        }

        @Override
        protected void convert(BaseViewHolder helper, CommentContentBean item) {
            if (item.getComment_type() == 1){//评论
                SpanUtils.with(helper.getView(R.id.comment_content_tv))
                        .append(item.getName() + "：")
                        .setForegroundColor(item.getIs_circleMaster() == 1 ?
                                mContext.getResources().getColor(R.color.orange) :
                                mContext.getResources().getColor(R.color.text_color4))
                        .append(item.getContent())
                        .setForegroundColor(mContext.getResources().getColor(R.color.text_color5))
                        .create();
            }else {//回复
                SpanUtils.with(helper.getView(R.id.comment_content_tv))
                        .append(item.getName())
                        .setForegroundColor(item.getIs_circleMaster() == 1 ?
                                mContext.getResources().getColor(R.color.orange) :
                                mContext.getResources().getColor(R.color.text_color4))
                        .append("回复").setForegroundColor(mContext.getResources().getColor(R.color.blue_mid))
                        .append(TextUtils.isEmpty(item.getReply_name()) ? "" : item.getReply_name())
                        .setForegroundColor(item.getReply_isCircleMaster() == 1 ?
                                mContext.getResources().getColor(R.color.orange) :
                                mContext.getResources().getColor(R.color.text_color4))
                        .append("：" + (TextUtils.isEmpty(item.getContent()) ? "" : item.getContent()))
                        .setForegroundColor(mContext.getResources().getColor(R.color.text_color4))
                        .create();
            }
        }
    }

    public interface onCommentItemlistener{
        void onCommentClick(int adapterPosition, CommentAdapter adapter, View view, int position, List<ThemeInfoEntity.ThemeInfoBean> data);
    }

    private onCommentItemlistener mOnCommentItemlistener;

    public void setCommentClickListener(onCommentItemlistener commentClickListener){
        this.mOnCommentItemlistener = commentClickListener;
    }

}
