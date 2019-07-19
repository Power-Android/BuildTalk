package com.bjjy.buildtalk.adapter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CircleMasterEntity;
import com.bjjy.buildtalk.entity.IndustryMasterEntity;
import com.bjjy.buildtalk.entity.TalkEntity;
import com.bjjy.buildtalk.ui.talk.CircleManDetailActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.ui.talk.TalkPresnter;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
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

    private List<IndustryMasterEntity.MasterInfoBean> mMasterInfo = new ArrayList<>();
    private List<CircleMasterEntity> mCircleMasterEntities = new ArrayList<>();

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private TalkCircleAdapter mCircleAdapter;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i);
    }

    public void setOnFocusClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

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
                RecyclerView master_recyclerView = helper.getView(R.id.master_recyclerView);
                master_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                TalkMasterAdapter masterAdapter = new TalkMasterAdapter(R.layout.adapter_talk_master, mMasterInfo);
                master_recyclerView.setAdapter(masterAdapter);
                masterAdapter.setOnItemClickListener((baseQuickAdapter, view, position) -> {
                    Intent intent = new Intent(mContext, MasterDetailActivity.class);
                    intent.putExtra("user_id", mMasterInfo.get(position).getUser_id() + "");
                    mContext.startActivity(intent);
                });
                helper.addOnClickListener(R.id.master_all_tv)
                        .addOnClickListener(R.id.master_change_ll);
                break;
            case BODY_CIRCLE_MAN:
                RecyclerView circle_recyclerView = helper.getView(R.id.circle_recyclerView);
                circle_recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                mCircleAdapter = new TalkCircleAdapter(R.layout.adapter_talk_circle, mCircleMasterEntities);
                circle_recyclerView.setAdapter(mCircleAdapter);
                mCircleAdapter.setOnItemClickListener((baseQuickAdapter, view, position) -> {
                    Intent intent = new Intent(mContext, CircleManDetailActivity.class);
                    intent.putExtra("user_id", mCircleMasterEntities.get(position).getUser_id() + "");
                    mContext.startActivity(intent);
                });
                mCircleAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(baseQuickAdapter, view, i);
                    }
                });
                helper.addOnClickListener(R.id.circle_all_tv);
                break;
        }
    }

    public void setMasterEntities(IndustryMasterEntity masterEntities) {
        mMasterInfo = masterEntities.getMasterInfo();
        notifyDataSetChanged();
    }

    public void setCircleMasterEntities(List<CircleMasterEntity> circleMasterEntities) {
        mCircleMasterEntities = circleMasterEntities;
        notifyDataSetChanged();
    }

    public void setFocus(int i) {
        mCircleAdapter.notifyItemChanged(i);
    }
}
