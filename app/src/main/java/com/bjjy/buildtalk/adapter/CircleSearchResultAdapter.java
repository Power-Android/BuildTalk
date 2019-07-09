package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.core.greendao.CircleHistoryData;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author power
 * @date 2019/5/15 10:28 AM
 * @project BuildTalk
 * @description:
 */
public class CircleSearchResultAdapter extends BaseQuickAdapter<SearchResultEntity.CircleInfoBean, BaseViewHolder> {

    public CircleSearchResultAdapter(int layoutResId, @Nullable List<SearchResultEntity.CircleInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResultEntity.CircleInfoBean item) {
        Glide.with(mContext).load(item.getCircle_image().getPic_url()).into((ImageView) helper.getView(R.id.item_face_iv));
        helper.setText(R.id.item_title_tv, item.getCircle_name())
                .setText(R.id.item_name_tv, "圈主：" + item.getName());
        helper.setGone(R.id.isCourse, "话题".equals(item.getType()) ? false : true)
                .setGone(R.id.isMaster, 1 == item.getIs_author() ? true : false);
        TagFlowLayout flowLayout = helper.getView(R.id.flow_layout);
        List<String> circle_tags = item.getCircle_tags();
        flowLayout.setAdapter(new TagAdapter<String>(circle_tags) {
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
