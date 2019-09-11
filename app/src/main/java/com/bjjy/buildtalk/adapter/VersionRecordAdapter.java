package com.bjjy.buildtalk.adapter;

import android.view.View;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.VersionRecordEntity;
import com.bjjy.buildtalk.utils.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019-09-03 11:14
 * @project BuildTalk
 * @description:
 */
public class VersionRecordAdapter extends BaseMultiItemQuickAdapter<VersionRecordEntity, BaseViewHolder> {

    public static final int BODY_CURRENT = 0;     //当前版本
    public static final int BODY_HISTORY = 1;     //历史版本

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public VersionRecordAdapter(List<VersionRecordEntity> data) {
        super(data);
        addItemType(BODY_CURRENT, R.layout.version_record_current_layout);
        addItemType(BODY_HISTORY, R.layout.version_record_history_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, VersionRecordEntity item) {
        switch (item.getItemType()){
            case BODY_CURRENT:
                helper.setText(R.id.current_version_tv, item.getUpdate_version());
                List<String> update_desc = item.getUpdate_desc();
                String content = StringUtils.listToString3(update_desc);
                helper.setText(R.id.current_content_tv, content);
                helper.setGone(R.id.view2, getData().size() <= 1 ? false : true);
                break;
            case BODY_HISTORY:
                helper.setText(R.id.history_version_tv, item.getUpdate_version());
                List<String> update_desc1 = item.getUpdate_desc();
                String content1 = StringUtils.listToString3(update_desc1);
                helper.setText(R.id.content_tv, content1);
                TextView contentTv = helper.getView(R.id.content_tv);
                helper.getView(R.id.expand_rl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.isExpand()){
                            helper.setImageDrawable(R.id.arrow_iv, mContext.getResources().getDrawable(R.drawable.arrow_down_icon));
                            contentTv.setVisibility(View.GONE);
                            item.setExpand(false);
                        }else {
                            helper.setImageDrawable(R.id.arrow_iv, mContext.getResources().getDrawable(R.drawable.arrow_top_cicon));
                            contentTv.setVisibility(View.VISIBLE);
                            item.setExpand(true);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return BODY_CURRENT;
        }else {
            return BODY_HISTORY;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
