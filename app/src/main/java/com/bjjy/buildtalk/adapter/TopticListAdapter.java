package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @author power
 * @date 2019/5/7 9:25 AM
 * @project BuildTalk
 * @description:
 */
public class TopticListAdapter extends BaseQuickAdapter<CourseEntity.CircleInfoBean, BaseViewHolder> {

    public TopticListAdapter(int layoutResId, @Nullable List<CourseEntity.CircleInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseEntity.CircleInfoBean item) {
        Glide.with(mContext).load(item.getCircle_image().getPic_url()).into((ImageView) helper.getView(R.id.item_img_iv));
        helper.setText(R.id.item_title_tv, item.getCircle_name())
                .setText(R.id.item_name_tv, "圈主： " + item.getName());

        if ("1".equals(String.valueOf(item.getIs_author()))) {
            helper.setGone(R.id.item_tag_iv, true);
        } else {
            helper.setGone(R.id.item_tag_iv, false);
        }

        List<String> mCircle_tags = item.getCircle_tags();
        TagFlowLayout flowLayout = helper.getView(R.id.flow_layout);
        flowLayout.setAdapter(new TagAdapter<String>(mCircle_tags) {
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
