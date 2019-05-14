package com.bjjy.buildtalk.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.TalkEntity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author power
 * @date 2019/4/30 11:56 AM
 * @project BuildTalk
 * @description:
 */
public class TalkAdapter extends BaseMultiItemQuickAdapter<TalkEntity, BaseViewHolder> {

    public static final int BODY_MASTER = 0;            //行业大咖
    public static final int BODY_CIRCLE_MAN = 1;        //人气圈主

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TalkAdapter(List<TalkEntity> data) {
        super(data);
        addItemType(BODY_MASTER, R.layout.talk_body_master_layout);
        addItemType(BODY_CIRCLE_MAN, R.layout.talk_body_circle_man_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, TalkEntity item) {
        switch (item.getItemType()) {
            case BODY_MASTER:
                List<String> master_list = new ArrayList<>();
                master_list.add("");
                master_list.add("");
                master_list.add("");
                RecyclerView master_recyclerView = helper.getView(R.id.master_recyclerView);
                master_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                TalkMasterAdapter masterAdapter = new TalkMasterAdapter(R.layout.adapter_talk_master,master_list);
                master_recyclerView.setAdapter(masterAdapter);
                helper.addOnClickListener(R.id.master_all_tv)
                        .addOnClickListener(R.id.master_change_ll);
                break;
            case BODY_CIRCLE_MAN:
                List<String> circle_list = new ArrayList<>();
                circle_list.add("");
                circle_list.add("");
                circle_list.add("");
                RecyclerView circle_recyclerView = helper.getView(R.id.circle_recyclerView);
                circle_recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                TalkCircleAdapter circleAdapter = new TalkCircleAdapter(R.layout.adapter_talk_circle,circle_list);
                circle_recyclerView.setAdapter(circleAdapter);
                helper.addOnClickListener(R.id.circle_all_tv);
                break;
        }
    }
}
