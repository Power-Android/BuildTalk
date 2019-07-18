package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.ThemeTypeEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/7/16 9:53 AM
 * @project BuildTalk
 * @description:
 */
public class ThemeTypeAdapter extends BaseQuickAdapter<ThemeTypeEntity, BaseViewHolder> {

    public ThemeTypeAdapter(int layoutResId, @Nullable List<ThemeTypeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeTypeEntity item) {
        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.img));
        helper.setText(R.id.type1, item.getName());
    }
}
