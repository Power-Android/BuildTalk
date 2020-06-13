package com.bjjy.buildtalk.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.PdfInfoEntity;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.entity.ThemePdfBean;
import com.bjjy.buildtalk.ui.circle.PDFViewerActivity;
import com.bjjy.buildtalk.ui.circle.TopticDetailActivity;
import com.bjjy.buildtalk.utils.AllUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.weight.MultiImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.ctetin.expandabletextviewlibrary.app.StatusType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author power
 * @date 2020/5/11 9:36 AM
 * @project BuildTalk
 * @description:
 */
public class DiscoverHAdapter extends BaseQuickAdapter<ThemeInfoEntity.ThemeInfoBean, BaseViewHolder> {
    private String mUser_id;
    public DiscoverHAdapter(int layoutResId, @Nullable List<ThemeInfoEntity.ThemeInfoBean> data, String user_id) {
        super(layoutResId, data);
        mUser_id = user_id;
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeInfoEntity.ThemeInfoBean item) {
        boolean isDelete = item.getParent_isDelete() == 1;

        Glide.with(mContext)
                .load(item.getHeadImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.moren_face))
                .into((ImageView) helper.getView(R.id.item_face_iv));
        ExpandableTextView contentTv = helper.getView(R.id.item_content_tv);
        contentTv.setContent(isDelete ? "该主题已被圈主删除" : item.getParent_themeInfo().getTheme_content());
        //添加展开和收回操作 只触发点击 不真正触发展开和收回操作
        contentTv.setExpandOrContractClickListener(type -> {
            if (type.equals(StatusType.STATUS_CONTRACT)) {
                LogUtils.e("收回操作，不真正触发收回操作");
            } else {
                LogUtils.e("展开操作，不真正触发展开操作");
                Intent intent = new Intent(mContext, TopticDetailActivity.class);
                intent.putExtra("title", item.getName());
                intent.putExtra("theme_id", item.getTheme_id() + "");
                intent.putExtra("circle_id", item.getCircle_id());
                mContext.startActivity(intent);
            }
        }, false);
        helper.setText(R.id.item_name_tv, item.getName())
                .setVisible(R.id.item_tag_iv, "1".equals(item.getIs_circleMaster()))
                .setText(R.id.item_time_tv, TimeUtils.getFriendlyTimeSpanByNow(item.getPublish_time()))
                .setVisible(R.id.item_from_tv, 0 != item.getReprint_themeId())
                .setGone(R.id.item_content_tv, isDelete ||
                        !TextUtils.isEmpty(item.getParent_themeInfo().getTheme_content()))
                .setText(R.id.item_from_tv, isDelete ? "" :
                        "  转自 " + (TextUtils.isEmpty(mUser_id) ? "" : mUser_id.equals(String.valueOf(item.getParent_themeInfo().getUser_id())) ?
                                "我" : "@" + item.getParent_themeInfo().getName()))
                .setGone(R.id.item_grid_view, !isDelete &&
                        item.getParent_themeInfo().getTheme_image().size() > 0)
                .setGone(R.id.item_vertical_cl, !isDelete &&
                        item.getParent_themeInfo().getTheme_video().size() > 0 &&
                        !TextUtils.isEmpty(item.getParent_themeInfo().getTheme_video().get(0).getVideo_height()) &&
                        !TextUtils.isEmpty(item.getParent_themeInfo().getTheme_video().get(0).getVideo_width()) &&
                        Integer.parseInt(item.getParent_themeInfo().getTheme_video().get(0).getVideo_height()) >=
                                Integer.parseInt(item.getParent_themeInfo().getTheme_video().get(0).getVideo_width()))
                .setGone(R.id.item_horizontal_cl, !isDelete &&
                        item.getParent_themeInfo().getTheme_video().size() > 0 &&
                        !TextUtils.isEmpty(item.getParent_themeInfo().getTheme_video().get(0).getVideo_height()) &&
                        !TextUtils.isEmpty(item.getParent_themeInfo().getTheme_video().get(0).getVideo_width()) &&
                        Integer.parseInt(item.getParent_themeInfo().getTheme_video().get(0).getVideo_height()) <
                                Integer.parseInt(item.getParent_themeInfo().getTheme_video().get(0).getVideo_width()))
                .setGone(R.id.item_pdf_recycler_view, !isDelete &&
                        item.getParent_themeInfo().getTheme_pdf().size() > 0)
                .setText(R.id.item_praise_tv, String.valueOf(item.getCountParise()))
                .setText(R.id.item_record_tv, String.valueOf(item.getCountComment()))
                .addOnClickListener(R.id.item_face_iv)
                .addOnClickListener(R.id.item_from_tv)
                .addOnClickListener(R.id.item_atten_cl)
                .addOnClickListener(R.id.item_more_iv)
                .addOnClickListener(R.id.item_vertical_video_view)
                .addOnClickListener(R.id.item_horizontal_video_view)
                .addOnClickListener(R.id.item_praise_ll)
                .addOnClickListener(R.id.item_record_ll)
                .addOnClickListener(R.id.item_share_ll);
        //是否关注
        ConstraintLayout attenCl = helper.getView(R.id.item_atten_cl);
        ImageView attenIv =  helper.getView(R.id.item_atten_iv);
        TextView attenTv =  helper.getView(R.id.item_atten_tv);
        if (0 == item.getIs_attention()) {
            attenIv.setImageResource(R.drawable.attention_sel);
            attenTv.setText("关注");
            attenTv.setTextColor(mContext.getResources().getColor(R.color.oranger3));
            attenCl.setBackground(mContext.getResources().getDrawable(R.drawable.shape_orange_13radius));
        } else if (1 == item.getIs_attention()) {
            attenIv.setImageResource(R.drawable.attention_def);
            attenTv.setText("已关注");
            attenTv.setTextColor(mContext.getResources().getColor(R.color.text_color6));
            attenCl.setBackground(mContext.getResources().getDrawable(R.drawable.shape_gray_13radius));
        } else if (2 == item.getIs_attention()){
            helper.setVisible(R.id.item_atten_cl, false);
        }
        //是否点赞
        if (1 == item.getIs_parise()) {
            helper.setImageResource(R.id.item_praise_iv, R.drawable.praise_sel);
        } else {
            helper.setImageResource(R.id.item_praise_iv, R.drawable.praise_def);
        }

        if (isDelete) {//该主题被删除，直接return
            return;
        }

        //图片
        if (item.getParent_themeInfo().getTheme_image().size() > 0) {
            MultiImageView multiImageView = helper.getView(R.id.item_grid_view);
            List<ThemeImageBean> themeImageBeanList = item.getParent_themeInfo().getTheme_image();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < themeImageBeanList.size(); i++) {
                list.add(themeImageBeanList.get(i).getPic_url());
            }
            multiImageView.setList(list);
            multiImageView.setOnItemClickListener((view, position, imageViews)
                    -> AllUtils.startImagePage((Activity) mContext, list, Arrays.asList(imageViews), position));
        }

        //PDF
        if (item.getParent_themeInfo().getTheme_pdf().size() > 0) {
            List<ThemePdfBean> theme_pdf = item.getParent_themeInfo().getTheme_pdf();
            List<PdfInfoEntity> list1 = new ArrayList<>();
            for (int i = 0; i < theme_pdf.size(); i++) {
                list1.add(new PdfInfoEntity(theme_pdf.get(i).getPdf_name(), theme_pdf.get(i).getPdf_url()));
            }
            RecyclerView pdf_recyclerView = helper.getView(R.id.item_pdf_recycler_view);
            pdf_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            PdfVewAdapter pdfVewAdapter = new PdfVewAdapter(R.layout.adapter_pdf_view, list1);
            pdf_recyclerView.setAdapter(pdfVewAdapter);
            pdfVewAdapter.setOnItemClickListener((adapter, view, position) -> {
                List<PdfInfoEntity> data = adapter.getData();
                Intent intent = new Intent(mContext, PDFViewerActivity.class);
                intent.putExtra("data", data.get(position));
                intent.putExtra("theme_id", item.getTheme_id() + "");
                intent.putExtra("isCollect", 0 == item.getIs_collect());
                mContext.startActivity(intent);
            });
        }

        //视频
        if (item.getParent_themeInfo().getTheme_video().size() > 0) {
            String videoWidth = item.getParent_themeInfo().getTheme_video().get(0).getVideo_width();
            String videoHeight = item.getParent_themeInfo().getTheme_video().get(0).getVideo_height();
            String video_duration = item.getParent_themeInfo().getTheme_video().get(0).getVideo_duration();
            float duration = 0f;
            if (!TextUtils.isEmpty(video_duration)){
                duration = Float.parseFloat(video_duration);
            }
            if (TextUtils.isEmpty(videoWidth) || TextUtils.isEmpty(videoHeight)) {
                helper.setGone(R.id.item_vertical_cl, true);
                Glide.with(mContext).load(R.color.black).into((ImageView) helper.getView(R.id.item_vertical_video_view));
            } else {
                if (Integer.parseInt(videoWidth) > Integer.parseInt(videoHeight)) {
                    Glide.with(mContext)
                            .load(item.getParent_themeInfo().getTheme_video().get(0).getCoverURL())
                            .into((ImageView) helper.getView(R.id.item_horizontal_video_view));
                    helper.setText(R.id.item_horizontal_time, TextUtils.isEmpty(video_duration) ?
                            "" : TimeUtils.stringForTime((int) duration));
                } else {
                    Glide.with(mContext)
                            .load(item.getParent_themeInfo().getTheme_video().get(0).getCoverURL())
                            .into((ImageView) helper.getView(R.id.item_vertical_video_view));
                    helper.setText(R.id.item_vertical_time, TextUtils.isEmpty(video_duration) ?
                            "" : TimeUtils.stringForTime((int) duration));
                }
            }
        }
    }
}
