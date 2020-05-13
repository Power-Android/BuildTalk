package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.entity.IEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2020/5/11 9:39 AM
 * @project BuildTalk
 * @description:
 */
public class AttentionHAdapter extends BaseQuickAdapter<IEntity, BaseViewHolder> {

    public AttentionHAdapter(int layoutResId, @Nullable List<IEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IEntity item) {

    }
}
