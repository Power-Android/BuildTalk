package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.MasterListEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * @author power
 * @date 2020/6/16 10:51 AM
 * @project BuildTalk
 * @description:
 */
public class MasterAAdapter extends BaseQuickAdapter<MasterListEntity.MasterInfoBean, BaseViewHolder> {

    public MasterAAdapter(int layoutResId, @Nullable List<MasterListEntity.MasterInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MasterListEntity.MasterInfoBean item) {
        Glide.with(mContext).load(item.getAuthor_pic()).into((RoundedImageView) helper.getView(R.id.item_img_iv));
        helper.setText(R.id.item_name_tv, item.getAuthor_name())
                .setText(R.id.item_job_tv, item.getAuthor_desc())
                .addOnClickListener(R.id.item_atten_cl)
                .setGone(R.id.item_atten_cl, item.getIs_attention() == 0);
        if (1 == item.getIs_attention()){
            helper.getView(R.id.item_atten_cl).setBackgroundResource(R.drawable.shape_gray_13radius);
            helper.setGone(R.id.item_atten_iv, false);
            helper.setText(R.id.item_atten_tv, "已关注");
            helper.setTextColor(R.id.item_atten_tv, mContext.getResources().getColor(R.color.text_drak));
        }else {
            helper.getView(R.id.item_atten_cl).setBackgroundResource(R.drawable.shape_orange_13radiu);
            helper.setGone(R.id.item_atten_iv, true);
            helper.setText(R.id.item_atten_tv, "关注");
            helper.setTextColor(R.id.item_atten_tv, mContext.getResources().getColor(R.color.white));
        }
    }
}
