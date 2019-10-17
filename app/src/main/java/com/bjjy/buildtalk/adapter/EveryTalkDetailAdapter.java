package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.GuestBookEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/5/7 4:36 PM
 * @project BuildTalk
 * @description:
 */
public class EveryTalkDetailAdapter extends BaseQuickAdapter<GuestBookEntity.GuestbookInfoBean, BaseViewHolder> {

    public EveryTalkDetailAdapter(int layoutResId, @Nullable List<GuestBookEntity.GuestbookInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GuestBookEntity.GuestbookInfoBean item) {
        Glide.with(mContext).load(item.getHeadimage())
                .apply(new RequestOptions().error(R.drawable.moren_face))
                .into((ImageView) helper.getView(R.id.item_face_iv));
        helper.setText(R.id.item_name_tv, item.getNickname())
                .setText(R.id.item_time_tv, item.getGuestbook_time())
                .setText(R.id.item_content_tv, item.getContent())
                .setText(R.id.item_praise_tv, String.valueOf(item.getCountpraise()))
                .setGone(R.id.item_delete_iv, 1 == item.getIsDelete() ? true : false)
                .addOnClickListener(R.id.item_praise_ll)
                .addOnClickListener(R.id.item_delete_iv);
        if ("1".equals(String.valueOf(item.getIsPraise()))){
            helper.setImageResource(R.id.item_praise_iv, R.drawable.praise_sel);
        }else {
            helper.setImageResource(R.id.item_praise_iv, R.drawable.praise_def);
        }
    }
}
