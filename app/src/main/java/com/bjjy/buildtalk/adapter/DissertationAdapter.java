package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.DissertationDetailEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019-09-16 11:21
 * @project BuildTalk
 * @description:
 */
public class DissertationAdapter extends BaseQuickAdapter<DissertationDetailEntity.DissertationAuthorBean, BaseViewHolder> {

    public DissertationAdapter(int layoutResId, @Nullable List<DissertationDetailEntity.DissertationAuthorBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DissertationDetailEntity.DissertationAuthorBean item) {
        Glide.with(mContext).load(item.getAuthor_pic()).into((ImageView) helper.getView(R.id.item_img_iv));
        helper.setText(R.id.item_title_tv, item.getArticle_title())
                .setText(R.id.item_name_tv, item.getAuthor_name() + "/" + item.getAuthor_desc())
                .setText(R.id.item_time_tv, TextUtils.isEmpty(item.getVideo_duration()) ? item.getPublish_time() : "时长："+item.getVideo_duration());
    }
}
