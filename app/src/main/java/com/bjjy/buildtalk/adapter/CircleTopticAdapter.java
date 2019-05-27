package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.weight.MyGridAdapter;
import com.bjjy.buildtalk.weight.NoScrollGridView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author power
 * @date 2019/5/23 2:07 PM
 * @project BuildTalk
 * @description:
 */
public class CircleTopticAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public CircleTopticAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.test_banner);
        list.add(R.drawable.test_course);
        list.add(R.drawable.test_project);
        list.add(R.drawable.test_talk_master);
        list.add(R.drawable.test_toptic);

        NoScrollGridView gridView = helper.getView(R.id.item_grid_view);
        if (list.size() == 4) {
            gridView.setNumColumns(2);
        } else {
            gridView.setNumColumns(3);
        }
        gridView.setAdapter(new MyGridAdapter(list));
    }

}
