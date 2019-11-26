package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.DissertationEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * @author power
 * @date 2019/4/28 4:40 PM
 * @project BuildTalk
 * @description: 精彩专题 adapter
 */
public class ProjectAdapter extends BaseQuickAdapter<DissertationEntity,BaseViewHolder> {

    public ProjectAdapter(int layoutResId, @Nullable List<DissertationEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DissertationEntity item) {
        Glide.with(mContext).load(item.getPic_url()).into((RoundedImageView) helper.getView(R.id.project_iv));
        helper.setText(R.id.course_title_tv, item.getDissertation_title());
    }
}
