package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/4/30 1:40 PM
 * @project BuildTalk
 * @description: 人气圈主 adapter
 */
public class TalkCircleAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public TalkCircleAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
