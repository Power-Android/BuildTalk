package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/4/28 1:40 PM
 * @project BuildTalk
 * @description: 每日一谈 Adapter
 */
public class EveryTalkAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public EveryTalkAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
