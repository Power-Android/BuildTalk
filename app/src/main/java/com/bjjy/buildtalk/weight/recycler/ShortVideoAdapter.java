package com.bjjy.buildtalk.weight.recycler;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.entity.IEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2020/5/12 4:41 PM
 * @project BuildTalk
 * @description:
 */
public class ShortVideoAdapter extends BaseQuickAdapter<IEntity, BaseViewHolder> {

    public ShortVideoAdapter(int layoutResId, @Nullable List<IEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IEntity item) {

    }
}
