package com.bjjy.buildtalk.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/4/28 1:40 PM
 * @project BuildTalk
 * @description: 每日一谈 Adapter
 */
public class EveryTalkAdapter extends BaseQuickAdapter<EveryTalkEntity,BaseViewHolder> {

    public EveryTalkAdapter(int layoutResId, @Nullable List<EveryTalkEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EveryTalkEntity item) {
        String article_title = item.getArticle_title();
        SpannableString spannableString = new SpannableString(article_title);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF8F8F8F"));
        spannableString.setSpan(colorSpan,0,article_title.indexOf("期")+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        helper.setText(R.id.period_tv,spannableString);
    }
}
