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
 * @date 2019/4/28 4:03 PM
 * @project BuildTalk
 * @description: 精品课程 adapter
 */
public class CourseAdapter extends BaseQuickAdapter<CourseEntity.CircleInfoBean,BaseViewHolder> {

    public CourseAdapter(int layoutResId, @Nullable List<CourseEntity.CircleInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseEntity.CircleInfoBean item) {
        Glide.with(mContext).load(item.getCircle_image().getPic_url()).into((ImageView) helper.getView(R.id.course_iv));
        helper.setText(R.id.course_title_tv,item.getCircle_name())
                .setText(R.id.course_name_tv,item.getName())
                .setText(R.id.course_job_tv,item.getAuthor_desc());
        if ("1".equals(item.getIs_author())){
            helper.setGone(R.id.course_tag_iv,true);
        }else {
            helper.setGone(R.id.course_tag_iv,false);
        }
    }
}
