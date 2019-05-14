package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author power
 * @date 2019/5/10 11:18 AM
 * @project BuildTalk
 * @description:
 */
public class SearchResultAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearchResultAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        TagFlowLayout flowLayout = helper.getView(R.id.flow_layout);
        List<String> list = new ArrayList<>();
        list.add("BIM");
        list.add("模型数据");
        list.add("装配式钢结构");
        flowLayout.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                if ("".equals(s)){
                    return new View(mContext);
                }
                TextView tv = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.flow_layout_tv, parent, false);
                tv.setText(s);
                return tv;
            }
        });
    }
}
