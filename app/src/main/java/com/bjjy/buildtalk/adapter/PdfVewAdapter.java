package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.PdfInfoEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019-11-20 17:16
 * @project BuildTalk
 * @description:
 */
public class PdfVewAdapter extends BaseQuickAdapter<PdfInfoEntity, BaseViewHolder> {

    public PdfVewAdapter(int layoutResId, @Nullable List<PdfInfoEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PdfInfoEntity item) {
        helper.setText(R.id.item_name_tv, item.getName());
    }
}
