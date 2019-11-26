package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @author power
 * @date 2019/5/10 11:18 AM
 * @project BuildTalk
 * @description:
 */
public class SearchResultAdapter extends BaseQuickAdapter<SearchResultEntity.AuthorInfoBean, BaseViewHolder> {

    public SearchResultAdapter(int layoutResId, @Nullable List<SearchResultEntity.AuthorInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResultEntity.AuthorInfoBean item) {
        Glide.with(mContext).load(item.getHeadImage()).into((ImageView) helper.getView(R.id.item_face_iv));

        helper.setText(R.id.item_name_tv, item.getName())
                .setText(R.id.item_num_tv, item.getCountAttention()+"")
                .setGone(R.id.item_tag_iv, item.getIs_author() == 1 ? true : false);
        TagFlowLayout flowLayout = helper.getView(R.id.flow_layout);
    }
}
