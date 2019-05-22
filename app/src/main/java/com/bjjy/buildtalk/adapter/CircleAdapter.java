package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CircleEntity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

/**
 * @author power
 * @date 2019/4/28 5:43 PM
 * @project BuildTalk
 * @description:
 */
public class CircleAdapter extends BaseMultiItemQuickAdapter<CircleEntity,BaseViewHolder> {
    public static final int BODY_CONTENT = 0;
    public static final int BODY_FOOTER = 1;

    public CircleAdapter(List<CircleEntity> data) {
        super(data);
        addItemType(BODY_CONTENT, R.layout.adapter_circle_layout);
        addItemType(BODY_FOOTER, R.layout.circle_footer_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleEntity item) {

    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return BODY_FOOTER;
        }
        return super.getItemViewType(position);
    }
}
