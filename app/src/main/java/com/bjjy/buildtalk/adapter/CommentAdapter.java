package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/6/19 11:15 AM
 * @project BuildTalk
 * @description:
 */
public class CommentAdapter extends BaseQuickAdapter<CommentContentBean, BaseViewHolder> {

    public CommentAdapter(int layoutResId, @Nullable List<CommentContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentContentBean item) {
        Glide.with(mContext).load(item.getHeadImage()).into((ImageView) helper.getView(R.id.item_face_iv));
        helper.setText(R.id.item_name_tv, item.getName())
                .setGone(R.id.item_master_tv, 1 == item.getIs_circleMaster() ? true : false)
                .setGone(R.id.item_delete_iv, 1 == item.getIsDelete() ? true : false)
                .setText(R.id.item_time_tv, item.getGuestbook_time())
                .setText(R.id.item_content_tv, item.getContent())
                .setText(R.id.item_praise_tv, item.getCountpraise()+"")
                .addOnClickListener(R.id.item_praise_ll)
                .addOnClickListener(R.id.item_delete_iv);
        if (item.getIsPraise() == 1){
            helper.setImageResource(R.id.item_praise_iv, R.drawable.praise_sel);
        }else {
            helper.setImageResource(R.id.item_praise_iv, R.drawable.praise_def);
        }

    }
}
