package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.IEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2020/6/2 4:43 PM
 * @project BuildTalk
 * @description:
 */
public class PublishCircleAdapter extends BaseQuickAdapter<IEntity, BaseViewHolder> {

    public PublishCircleAdapter(int layoutResId, @Nullable List<IEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IEntity item) {
        helper.setText(R.id.title_tv, item.getCircle_name())
                .setText(R.id.name_tv, "圈主：" + item.getName())
                .setChecked(R.id.checkbox, item.isChecked());
    }
}
