package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CollectEntity;
import com.bjjy.buildtalk.entity.TalkEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019/6/23 5:16 PM
 * @project BuildTalk
 * @description:
 */
public class MaseterCollectAdapter extends BaseMultiItemQuickAdapter<CollectEntity.MyCollectInfoBean, BaseViewHolder> {

    public static final int BODY_TEXT = 0;            //文字
    public static final int BODY_IMAGE = 1;        //图文

    public MaseterCollectAdapter(List<CollectEntity.MyCollectInfoBean> data) {
        super(data);
        addItemType(BODY_TEXT, R.layout.adapter_master_collect_text);
        addItemType(BODY_IMAGE, R.layout.adapter_master_collect);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectEntity.MyCollectInfoBean item) {
        switch (item.getItemType()) {
            case BODY_IMAGE:
                Glide.with(mContext).load(item.getTheme_image().get(0).getPic_url()).into((ImageView) helper.getView(R.id.item_img_iv));
                helper.setText(R.id.item_content_tv, item.getTheme_content())
                        .setText(R.id.item_num_tv, "共" + item.getTheme_image().size() + "张")
                        .setText(R.id.item_name_tv, item.getName())
                        .setText(R.id.item_title_tv, item.getCircle_name());
                break;
            case BODY_TEXT:
                helper.setText(R.id.item_content_tv, item.getTheme_content())
                        .setText(R.id.item_name_tv, item.getName())
                        .setText(R.id.item_title_tv, item.getCircle_name());
                break;
        }
    }
}
