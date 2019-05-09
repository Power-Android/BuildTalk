package com.bjjy.buildtalk.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.EveryTalkListEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/5/5 1:58 PM
 * @project BuildTalk
 * @description:
 */
public class EveryTalkListAdapter extends BaseQuickAdapter<EveryTalkListEntity.NewsInfoBean, BaseViewHolder> {

    public EveryTalkListAdapter(int layoutResId, @Nullable List<EveryTalkListEntity.NewsInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EveryTalkListEntity.NewsInfoBean item) {
        String article_title = item.getArticle_title();
        SpannableString spannableString = new SpannableString(article_title);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF8F8F8F"));
        spannableString.setSpan(colorSpan,0,article_title.indexOf("期")+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        helper.setText(R.id.item_period_tv,spannableString)
            .setText(R.id.item_time_tv, item.getPublish_time())
            .setText(R.id.item_num_tv, item.getBrowsecount()+"阅读");
    }
}
