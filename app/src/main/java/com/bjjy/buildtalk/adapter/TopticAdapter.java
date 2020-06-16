package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/4/28 2:51 PM
 * @project BuildTalk
 * @description: 热门话题 adapter
 */
public class TopticAdapter extends BaseQuickAdapter<CourseEntity.CircleInfoBean,BaseViewHolder> {

    public TopticAdapter(int layoutResId, @Nullable List<CourseEntity.CircleInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseEntity.CircleInfoBean item) {
        Glide.with(mContext).load(item.getCircle_image().getPic_url()).into((ImageView) helper.getView(R.id.toptic_iv));
        helper.setText(R.id.item_title_tv,item.getCircle_name());
//        helper.setGone(R.id.item_tag_iv, 1 == item.getIs_author() ? true : false);
    }
}
