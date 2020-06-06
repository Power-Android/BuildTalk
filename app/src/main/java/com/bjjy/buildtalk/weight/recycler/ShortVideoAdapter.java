package com.bjjy.buildtalk.weight.recycler;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.ShortVideoEntity;
import com.bjjy.buildtalk.ui.circle.TopticDetailActivity;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.ctetin.expandabletextviewlibrary.app.StatusType;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.List;

import static com.tencent.rtmp.TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;

/**
 * @author power
 * @date 2020/5/12 4:41 PM
 * @project BuildTalk
 * @description:
 */
public class ShortVideoAdapter extends BaseQuickAdapter<ShortVideoEntity.ThemeInfoBean, BaseViewHolder> {

    public ShortVideoAdapter(int layoutResId, @Nullable List<ShortVideoEntity.ThemeInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShortVideoEntity.ThemeInfoBean item) {
        ShortVideoEntity.ThemeInfoBean.ParentThemeInfoBean info = item.getParent_themeInfo();

        if (info.getTheme_video() == null) {
            ToastUtils.showShort("视频不见了...");
        } else {
            ShortVideoEntity.ThemeInfoBean.ParentThemeInfoBean.ThemeVideoBean videoInfo = info.getTheme_video().get(0);
            if (TextUtils.isEmpty(info.getTheme_video().get(0).getCoverURL())) {
                helper.setGone(R.id.cover_iv, false);
            } else {
                Glide.with(mContext).load(videoInfo.getCoverURL()).into((ImageView) helper.getView(R.id.cover_iv));
            }
        }

        Glide.with(mContext)
                .load(item.getHeadImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.moren_face)
                        .error(R.drawable.moren_face))
                .into((ImageView) helper.getView(R.id.item_face_iv));
        TextView praiseTv = helper.getView(R.id.item_praise_tv);
        TextView collectTv = helper.getView(R.id.item_collect_tv);
        if (0 == info.getIs_parise()) {
            Drawable defDrawable = mContext.getResources().getDrawable(R.drawable.video_prise_def_icon);
            defDrawable.setBounds(0, 0, defDrawable.getMinimumWidth(), defDrawable.getMinimumHeight());
            praiseTv.setCompoundDrawables(null, defDrawable, null, null);
        } else {
            Drawable selDrawable = mContext.getResources().getDrawable(R.drawable.video_praise_sel_icon);
            selDrawable.setBounds(0, 0, selDrawable.getMinimumWidth(), selDrawable.getMinimumHeight());
            praiseTv.setCompoundDrawables(null, selDrawable, null, null);
        }
        if (0 == info.getIs_collect()){
            Drawable defDrawable = mContext.getResources().getDrawable(R.drawable.video_collect_icon);
            defDrawable.setBounds(0, 0, defDrawable.getMinimumWidth(), defDrawable.getMinimumHeight());
            collectTv.setCompoundDrawables(null, defDrawable, null, null);
        }else {
            Drawable selDrawable = mContext.getResources().getDrawable(R.drawable.video_collect_sel_icon);
            selDrawable.setBounds(0, 0, selDrawable.getMinimumWidth(), selDrawable.getMinimumHeight());
            collectTv.setCompoundDrawables(null, selDrawable, null, null);
        }
        ExpandableTextView contentTv = helper.getView(R.id.item_content_tv);
        contentTv.setContent(item.getTheme_content());
        contentTv.setExpandOrContractClickListener(type -> {
            if (type.equals(StatusType.STATUS_CONTRACT)) {
                LogUtils.e("收回操作，不真正触发收回操作");
            } else {
                LogUtils.e("展开操作，不真正触发展开操作");
                Intent intent = new Intent(mContext, TopticDetailActivity.class);
                intent.putExtra("title", item.getName());
                intent.putExtra("theme_id", item.getTheme_id() + "");
                intent.putExtra("circle_id", item.getCircle_id());
                mContext.startActivity(intent);
            }
        }, false);
        helper.setText(R.id.item_praise_tv, String.valueOf(item.getCountParise()))
                .setText(R.id.item_comment_tv, String.valueOf(item.getCountComment()))
                .setText(R.id.item_collect_tv, String.valueOf(item.getCountCollect()))
                .setText(R.id.item_name_tv, "@" + item.getName())
                .setVisible(R.id.item_tag_iv, 1 == item.getIs_author())
                .setText(R.id.item_time_tv, TimeUtils.getFriendlyTimeSpanByNow(item.getPublish_time()))
                .setText(R.id.item_from_tv, !TextUtils.isEmpty(item.getCircle_name()) ? "来自圈子 " + item.getCircle_name() : "");

    }
}
