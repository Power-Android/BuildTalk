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
 * @date 2019/5/7 11:43 AM
 * @project BuildTalk
 * @description:
 */
public class CourseListAdapter extends BaseQuickAdapter<CourseEntity.CircleInfoBean, BaseViewHolder> {

    public CourseListAdapter(int layoutResId, @Nullable List<CourseEntity.CircleInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseEntity.CircleInfoBean item) {
        Glide.with(mContext).load(item.getCircle_image().getPic_url()).into((ImageView) helper.getView(R.id.item_img_iv));
        helper.setText(R.id.item_title_tv, item.getCircle_name())
                .setText(R.id.item_period_tv, "更新至"+item.getCountCourse()+"讲")
                .setText(R.id.item_name_tv, item.getName())
                .setText(R.id.item_job_tv, item.getAuthor_desc())
                .setText(R.id.item_price_tv, "￥"+item.getCourse_money())
                .setText(R.id.item_num_tv, item.getCountUser()+"人已加入");
        if ("1".equals(String.valueOf(item.getIs_author()))) {
            helper.setGone(R.id.item_tag_iv, true);
        } else {
            helper.setGone(R.id.item_tag_iv, false);
        }
    }
}
