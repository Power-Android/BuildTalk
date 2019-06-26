package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.IndustryMasterEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/4/30 1:39 PM
 * @project BuildTalk
 * @description: 行业大咖 adapter
 */
public class TalkMasterAdapter extends BaseQuickAdapter<IndustryMasterEntity.MasterInfoBean, BaseViewHolder> {

    public TalkMasterAdapter(int layoutResId, @Nullable List<IndustryMasterEntity.MasterInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndustryMasterEntity.MasterInfoBean item) {
        Glide.with(mContext).load(item.getMaster_pic()).into((ImageView) helper.getView(R.id.item_img_iv));
    }
}
