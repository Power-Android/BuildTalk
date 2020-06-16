package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2020/6/16 4:37 PM
 * @project BuildTalk
 * @description:
 */
public class EssenceAdapter extends BaseQuickAdapter<ThemeInfoEntity.ThemeInfoBean, BaseViewHolder> {

    public EssenceAdapter(int layoutResId, @Nullable List<ThemeInfoEntity.ThemeInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeInfoEntity.ThemeInfoBean item) {
        helper.setText(R.id.item_content_tv, item.getTheme_content())
                .setText(R.id.item_title_tv, item.getName() + " | " + item.getCircle_name());
        if (item.getTheme_image() != null && item.getTheme_image().size() > 0) {
            Glide.with(mContext)
                    .load(item.getTheme_image().get(0).getPic_url())
                    .into((ImageView) helper.getView(R.id.item_img_iv));
        } else if (item.getTheme_pdf() != null && item.getTheme_pdf().size() > 0) {
            Glide.with(mContext)
                    .load(R.drawable.pdf_icon)
                    .into((ImageView) helper.getView(R.id.item_img_iv));
        } else if (item.getTheme_video() != null && item.getTheme_video().size() > 0) {
            Glide.with(mContext)
                    .load(item.getTheme_video().get(0).getCoverURL())
                    .apply(new RequestOptions().error(R.drawable.video_more_icon))
                    .into((ImageView) helper.getView(R.id.item_img_iv));
        }else {
            helper.setGone(R.id.item_img_iv, false);
        }
    }
}
