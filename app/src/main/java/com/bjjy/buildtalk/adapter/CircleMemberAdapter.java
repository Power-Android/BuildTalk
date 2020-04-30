package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.MemberEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    private int type;

    public CircleMemberAdapter(int layoutResId, @Nullable List<MemberEntity.CircleUserBean> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberEntity.CircleUserBean item) {
        Glide.with(mContext)
                .load(item.getHeadImage())
                .apply(new RequestOptions()
                        .error(R.drawable.moren_face))
                .into((ImageView) helper.getView(R.id.item_face_iv));
        helper.setGone(R.id.item_master_tv, item.getCircle_master().equals("1"));
        helper.setGone(R.id.item_tag_iv, 1 == item.getIs_author());
        helper.setText(R.id.item_name_tv, item.getName());
        TextView sortTv = helper.getView(R.id.item_sort_tv);
        switch (helper.getAdapterPosition()){
            case 0:
                helper.setText(R.id.item_sort_tv, "1");
                sortTv.setBackground(mContext.getResources().getDrawable(R.drawable.num1_member_icon));
                break;
            case 1:
                helper.setText(R.id.item_sort_tv, "2");
                sortTv.setBackground(mContext.getResources().getDrawable(R.drawable.num2_member_icon));
                break;
            case 2:
                helper.setText(R.id.item_sort_tv, "3");
                sortTv.setBackground(mContext.getResources().getDrawable(R.drawable.num3_member_icon));
                break;
            default:
                helper.setText(R.id.item_sort_tv, String.valueOf(helper.getAdapterPosition()+1));
                sortTv.setBackground(mContext.getResources().getDrawable(R.drawable.num4_member_icon));
                break;
        }
        switch (type){
            case 1:
                helper.setText(R.id.item_flag_tv, "活跃度");
                helper.setText(R.id.item_num_tv, item.getLiveness());
                break;
            case 2:
                helper.setText(R.id.item_flag_tv, "点赞数");
                helper.setText(R.id.item_num_tv, item.getCountThemeParise());
                break;
            case 3:
                helper.setText(R.id.item_flag_tv, "评论数");
                helper.setText(R.id.item_num_tv, item.getCountThemeCommentNum());
                break;
            default:
                helper.setText(R.id.item_flag_tv, "活跃度");
                helper.setText(R.id.item_num_tv, "0");
                break;
        }
    }
}
