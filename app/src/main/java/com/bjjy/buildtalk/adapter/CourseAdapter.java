package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/4/28 4:03 PM
 * @project BuildTalk
 * @description: 精品课程 adapter
 */
public class CourseAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public CourseAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
