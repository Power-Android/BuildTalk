package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.DissertationListEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * @author power
 * @date 2019-11-19 09:49
 * @project BuildTalk
 * @description:
 */
public class DissertationListAdapter extends BaseQuickAdapter<DissertationListEntity.DissertationInfoBean, BaseViewHolder> {

    public DissertationListAdapter(int layoutResId, @Nullable List<DissertationListEntity.DissertationInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DissertationListEntity.DissertationInfoBean item) {
        Glide.with(mContext).load(item.getPic_url()).into((RoundedImageView) helper.getView(R.id.item_img_iv));
    }
}
