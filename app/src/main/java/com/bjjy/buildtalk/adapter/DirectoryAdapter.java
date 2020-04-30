package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CourseListEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author power
 * @date 2019/5/24 11:45 AM
 * @project BuildTalk
 * @description:
 */
public class DirectoryAdapter extends BaseQuickAdapter<CourseListEntity.CourselistBean, BaseViewHolder> {
    private String mIsJoin;

    public DirectoryAdapter(int layoutResId, @Nullable List<CourseListEntity.CourselistBean> data, String isJoin) {
        super(layoutResId, data);
        this.mIsJoin = isJoin;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseListEntity.CourselistBean item) {
        DecimalFormat df = new DecimalFormat("00");
        String str = df.format(helper.getAdapterPosition()+1);
        helper.setText(R.id.item_name_tv, str + "讲  "+item.getArticle_title());
        TextView nameTv = helper.getView(R.id.item_name_tv);
        if (item.isSelected()){
//            nameTv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            nameTv.setTextColor(mContext.getResources().getColor(R.color.orange));
        }else {
//            nameTv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            nameTv.setTextColor(mContext.getResources().getColor(R.color.text_drak));
        }
        if (mIsJoin.equals("1")){
            helper.setGone(R.id.item_sk_iv, false);
            helper.setGone(R.id.item_sd_iv, false);
        }else {
            if (item.getIs_audition() == 1){
                helper.setGone(R.id.item_sk_iv, true);
                helper.setGone(R.id.item_sd_iv, false);
            }else {
                helper.setGone(R.id.item_sk_iv, false);
                helper.setGone(R.id.item_sd_iv, true);
            }
        }
    }
}
