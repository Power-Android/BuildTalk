package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.MasterListEntity;
import com.bjjy.buildtalk.entity.SearchResultEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
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

        helper.setText(R.id.item_name_tv, item.getNickName())
                .setText(R.id.item_num_tv, item.getCountAttention()+"");
        TagFlowLayout flowLayout = helper.getView(R.id.flow_layout);
    }
}
