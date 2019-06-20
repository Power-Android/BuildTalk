package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.MemberEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/6/12 10:45 AM
 * @project BuildTalk
 * @description:
 */
public class CircleMemberAdapter extends BaseQuickAdapter<MemberEntity.CircleUserBean, BaseViewHolder> {

    public CircleMemberAdapter(int layoutResId, @Nullable List<MemberEntity.CircleUserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberEntity.CircleUserBean item) {
        Glide.with(mContext).load(item.getHeadImage()).into((ImageView) helper.getView(R.id.item_face_iv));
        helper.setGone(R.id.item_master_tv, 0 == helper.getAdapterPosition() ? true : false);
        helper.setGone(R.id.item_tag_iv, 1 == item.getIs_author() ? true : false);
        helper.setText(R.id.item_name_tv, item.getName());
    }
}
