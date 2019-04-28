package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/4/28 2:51 PM
 * @project BuildTalk
 * @description: 热门话题 adapter
 */
public class TopticAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public TopticAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
