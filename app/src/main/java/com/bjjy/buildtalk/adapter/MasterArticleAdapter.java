package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.MasterDetailEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/5/14 3:21 PM
 * @project BuildTalk
 * @description:
 */
public class MasterArticleAdapter extends BaseQuickAdapter<MasterDetailEntity.ArticleInfoBean, BaseViewHolder> {

    public MasterArticleAdapter(int layoutResId, @Nullable List<MasterDetailEntity.ArticleInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MasterDetailEntity.ArticleInfoBean item) {
        Glide.with(mContext).load(item.getArticle_pic()).into((ImageView) helper.getView(R.id.item_img_iv));
        helper.setText(R.id.item_title_tv, item.getArticle_title())
                .setText(R.id.item_content_tv, item.getArticle_desc());
    }
}
