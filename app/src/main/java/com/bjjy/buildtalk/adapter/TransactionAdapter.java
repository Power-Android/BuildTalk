package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.AleadyBuyEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/5/29 10:07 AM
 * @project BuildTalk
 * @description:
 */
public class TransactionAdapter extends BaseQuickAdapter<AleadyBuyEntity, BaseViewHolder> {

    public TransactionAdapter(int layoutResId, @Nullable List<AleadyBuyEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AleadyBuyEntity item) {
        helper.setText(R.id.item_title_tv, item.getOrder_name())
                .setText(R.id.item_time_tv, item.getOrder_time())
                .setText(R.id.item_money_tv, item.getOrder_price());
        if (item.getIs_pay() == 0){
            helper.setText(R.id.item_status_tv, "未支付");
        }else {
            helper.setText(R.id.item_status_tv, "支付成功");
        }
    }
}
