package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/5/29 10:07 AM
 * @project BuildTalk
 * @description:
 */
public class TransactionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TransactionAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {

    }
}
