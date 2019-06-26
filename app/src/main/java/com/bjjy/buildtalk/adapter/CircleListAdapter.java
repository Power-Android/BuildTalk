package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
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

    public CircleListAdapter(int layoutResId, @Nullable List<CircleListEntity.CircleInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleListEntity.CircleInfoBean item) {
        Glide.with(mContext).load(item.getHeadImage()).into((ImageView) helper.getView(R.id.item_face_iv));
        helper.setText(R.id.item_name_tv, item.getName())
                .setText(R.id.item_num_tv, item.getCountAttention()+"");
        int is_attention = item.getIs_attention();
        if (TextUtils.equals("1", is_attention+"")){
            helper.setGone(R.id.item_focus_iv, true);
            helper.setText(R.id.item_focus_tv, "已关注");
        }else {
            helper.setGone(R.id.item_focus_iv, false);
            helper.setText(R.id.item_focus_tv, "+关注");
        }
    }
}
