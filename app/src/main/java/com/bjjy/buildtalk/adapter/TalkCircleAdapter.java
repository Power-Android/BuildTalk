package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CircleMasterEntity;
import com.bjjy.buildtalk.entity.FansFocusEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author power
 * @date 2019/4/30 1:40 PM
 * @project BuildTalk
 * @description: 人气圈主 adapter
 */
public class TalkCircleAdapter extends BaseQuickAdapter<CircleMasterEntity,BaseViewHolder> {

    public TalkCircleAdapter(int layoutResId, @Nullable List<CircleMasterEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleMasterEntity item) {
        Glide.with(mContext).load(item.getHeadImage()).into((ImageView) helper.getView(R.id.item_face_iv));
        helper.setText(R.id.item_name_tv, item.getName())
                .setText(R.id.item_num_tv, item.getCountAttention()+"")
                .setGone(R.id.item_tag_iv, 1 == item.getIs_author() ? true : false)
                .addOnClickListener(R.id.item_focus_ll);
        if (1 == item.getIs_attention()){
            helper.setGone(R.id.item_focus_iv, true);
            helper.setText(R.id.item_focus_tv, "已关注");
        }else {
            helper.setGone(R.id.item_focus_iv, false);
            helper.setText(R.id.item_focus_tv, "+关注");
        }
    }
}
