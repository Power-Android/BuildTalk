package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/4/28 5:43 PM
 * @project BuildTalk
 * @description:
 */
public class CircleAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public CircleAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
