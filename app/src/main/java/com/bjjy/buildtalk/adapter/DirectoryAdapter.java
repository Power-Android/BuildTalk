package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CourseListEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/5/24 11:45 AM
 * @project BuildTalk
 * @description:
 */
public class DirectoryAdapter extends BaseQuickAdapter<CourseListEntity.CourselistBean, BaseViewHolder> {

    public DirectoryAdapter(int layoutResId, @Nullable List<CourseListEntity.CourselistBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseListEntity.CourselistBean item) {
        helper.setText(R.id.item_name_tv, item.getArticle_title());
        if (item.getIs_audition() == 1){
            helper.setGone(R.id.item_sk_iv, true);
            helper.setGone(R.id.item_sd_iv, false);
        }else {
            helper.setGone(R.id.item_sk_iv, false);
            helper.setGone(R.id.item_sd_iv, true);
        }
    }
}
