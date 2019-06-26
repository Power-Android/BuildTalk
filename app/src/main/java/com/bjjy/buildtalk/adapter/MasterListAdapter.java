package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.MasterListEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @author power
 * @date 2019/6/21 10:00 AM
 * @project BuildTalk
 * @description:
 */
public class MasterListAdapter extends BaseQuickAdapter<MasterListEntity.MasterInfoBean, BaseViewHolder> {

    public MasterListAdapter(int layoutResId, @Nullable List<MasterListEntity.MasterInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MasterListEntity.MasterInfoBean item) {
        Glide.with(mContext).load(item.getAuthor_pic()).into((ImageView) helper.getView(R.id.item_face_iv));
        if (!TextUtils.isEmpty(item.getEducation())){
            helper.setText(R.id.item_job_tv, item.getEducation() + " "+item.getAuthor_desc());
        }else {
            helper.setText(R.id.item_job_tv, item.getAuthor_desc());
        }
        helper.setText(R.id.item_name_tv, item.getAuthor_name())
                .setText(R.id.item_content_tv, item.getAuthor_intro());
        TagFlowLayout flowLayout = helper.getView(R.id.flow_layout);
        List<String> circle_tags = item.getSign();
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
