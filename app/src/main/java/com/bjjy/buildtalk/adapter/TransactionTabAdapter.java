package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.TransactionTabEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/5/29 9:59 AM
 * @project BuildTalk
 * @description:
 */
public class TransactionTabAdapter extends BaseQuickAdapter<TransactionTabEntity, BaseViewHolder> {

    public TransactionTabAdapter(int layoutResId, @Nullable List<TransactionTabEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransactionTabEntity transactionTabEntity) {

        TextView nameTv = helper.getView(R.id.item_name_tv);
        nameTv.setText(transactionTabEntity.getContent());
        if (transactionTabEntity.isSelected()){
            nameTv.setBackground(mContext.getResources().getDrawable(R.drawable.shape_blue_storke_17radius));
            nameTv.setTextColor(mContext.getResources().getColor(R.color.blue_mid));
        }else {
            nameTv.setBackground(mContext.getResources().getDrawable(R.drawable.shape_white_stroke_17radius));
            nameTv.setTextColor(mContext.getResources().getColor(R.color.text_mid));
        }
    }
}
