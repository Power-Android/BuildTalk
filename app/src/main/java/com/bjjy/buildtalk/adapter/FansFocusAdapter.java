package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.FansFocusEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/6/23 11:02 AM
 * @project BuildTalk
 * @description:
 */
public class FansFocusAdapter extends BaseQuickAdapter<FansFocusEntity.MyFansInfoBean, BaseViewHolder> {

    private String user_id;
    public FansFocusAdapter(int layoutResId, @Nullable List<FansFocusEntity.MyFansInfoBean> data, String user_id) {
        super(layoutResId, data);
        this.user_id = user_id;
    }

    @Override
    protected void convert(BaseViewHolder helper, FansFocusEntity.MyFansInfoBean item) {
        Glide.with(mContext).load(item.getHeadImage()).into((ImageView) helper.getView(R.id.item_face_iv));
        helper.setText(R.id.item_name_tv, item.getName())
                .setText(R.id.item_num_tv, item.getCountAttention()+"")
                .setGone(R.id.item_focus_ll, String.valueOf(item.getUser_id()).equals(user_id) ? false : true)
                .addOnClickListener(R.id.item_focus_ll);

        int is_attention = item.getIs_attention();
        if (TextUtils.equals("1", is_attention+"")){
            helper.setText(R.id.item_focus_tv, "取消关注");
        }else {
            helper.setText(R.id.item_focus_tv, "+关注");
        }
    }

}
