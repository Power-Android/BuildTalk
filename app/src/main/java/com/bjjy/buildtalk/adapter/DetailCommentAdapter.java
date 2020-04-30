package com.bjjy.buildtalk.adapter;

import android.support.annotation.Nullable;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2020/4/26 2:22 PM
 * @project BuildTalk
 * @description:
 */
public class DetailCommentAdapter extends BaseQuickAdapter<CommentContentBean.ReplyCommentInfoBean, BaseViewHolder> {

    public DetailCommentAdapter(int layoutResId, @Nullable List<CommentContentBean.ReplyCommentInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentContentBean.ReplyCommentInfoBean item) {
        if (item.getComment_type() == 1){//评论
            SpanUtils.with(helper.getView(R.id.comment_content_tv))
                    .append(item.getName() + "：")
                    .setForegroundColor(item.getIs_circleMaster() == 1 ?
                            mContext.getResources().getColor(R.color.orange) :
                            mContext.getResources().getColor(R.color.text_color4))
                    .append(item.getContent())
                    .setForegroundColor(mContext.getResources().getColor(R.color.text_color5))
                    .create();
        }else {//回复
            SpanUtils.with(helper.getView(R.id.comment_content_tv))
                    .append(item.getName())
                    .setForegroundColor(item.getIs_circleMaster() == 1 ?
                            mContext.getResources().getColor(R.color.orange) :
                            mContext.getResources().getColor(R.color.text_color4))
                    .append("回复").setForegroundColor(mContext.getResources().getColor(R.color.blue_mid))
                    .append(item.getReply_name())
                    .setForegroundColor(item.getReply_isCircleMaster() == 1 ?
                            mContext.getResources().getColor(R.color.orange) :
                            mContext.getResources().getColor(R.color.text_color4))
                    .append("：" + item.getContent())
                    .setForegroundColor(mContext.getResources().getColor(R.color.text_color4))
                    .create();
        }
    }
}
