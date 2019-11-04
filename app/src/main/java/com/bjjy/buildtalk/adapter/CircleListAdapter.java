package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.DataManager;
import com.bjjy.buildtalk.entity.CircleListEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/5/10 3:47 PM
 * @project BuildTalk
 * @description:
 */
public class CircleListAdapter extends BaseQuickAdapter<CircleListEntity.CircleInfoBean, BaseViewHolder> {
    private DataManager mDataManager;

    public CircleListAdapter(int layoutResId, @Nullable List<CircleListEntity.CircleInfoBean> data, DataManager dataManager) {
        super(layoutResId, data);
        this.mDataManager = dataManager;
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleListEntity.CircleInfoBean item) {
        Glide.with(mContext).load(item.getHeadImage()).into((ImageView) helper.getView(R.id.item_face_iv));
        helper.setText(R.id.item_name_tv, item.getName())
                .setText(R.id.item_num_tv, item.getCountAttention()+"")
                .setGone(R.id.item_tag_iv, 1 == item.getIs_author() ? true : false)
                .addOnClickListener(R.id.item_focus_ll);
        if (mDataManager.getUser() != null && !TextUtils.isEmpty(mDataManager.getUser().getUser_id())){
            helper.setGone(R.id.item_focus_ll, mDataManager.getUser().getUser_id().equals(String.valueOf(item.getUser_id())) ? false : true);
        }
        TextView themeCountTv = helper.getView(R.id.item_theme_num_tv);
        if (item.getCountThemeNum() <= 999){
            themeCountTv.setText(item.getCountThemeNum()+"");
        }else {
            int count = item.getCountThemeNum();
            double d = count / 1000;
            java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.0");
            String str = myformat.format(d);
            themeCountTv.setText(str + "K+");
        }
        LinearLayout itemFocusLl = helper.getView(R.id.item_focus_ll);
        int is_attention = item.getIs_attention();
        if (TextUtils.equals("1", is_attention+"")){
            itemFocusLl.setBackgroundResource(R.drawable.shape_gray_13radius);
            helper.setGone(R.id.item_focus_iv, true);
            helper.setText(R.id.item_focus_tv, "已关注");
            helper.setTextColor(R.id.item_focus_tv, mContext.getResources().getColor(R.color.text_drak));
        }else {
            itemFocusLl.setBackgroundResource(R.drawable.shape_orange_13radius);
            helper.setGone(R.id.item_focus_iv, false);
            helper.setText(R.id.item_focus_tv, "+关注");
            helper.setTextColor(R.id.item_focus_tv, mContext.getResources().getColor(R.color.orange));
        }
    }
}
