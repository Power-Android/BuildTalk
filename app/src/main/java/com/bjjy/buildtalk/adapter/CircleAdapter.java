package com.bjjy.buildtalk.adapter;

import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CircleEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/4/28 5:43 PM
 * @project BuildTalk
 * @description:
 */
public class CircleAdapter extends BaseMultiItemQuickAdapter<CircleEntity.CircleInfoBean, BaseViewHolder> {
    public static final int BODY_CONTENT = 0;
    public static final int BODY_FOOTER = 1;

    public CircleAdapter(List<CircleEntity.CircleInfoBean> data) {
        super(data);
        addItemType(BODY_CONTENT, R.layout.adapter_circle_layout);
        addItemType(BODY_FOOTER, R.layout.circle_footer_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleEntity.CircleInfoBean item) {
        if (item != null && item.getItemType() == BODY_CONTENT) {
            if (item.getCircle_image() != null){
                Glide.with(mContext).load(item.getCircle_image().getPic_url()).into((ImageView) helper.getView(R.id.circle_iv));
            }
            helper.setText(R.id.circle_title_tv, item.getCircle_name())
                    .setText(R.id.course_name_tv, item.getName());
            helper.setGone(R.id.isCourse, 2 == item.getType() ? true : false)
                    .setGone(R.id.isMaster, 1 == item.getIs_author() ? true : false);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
