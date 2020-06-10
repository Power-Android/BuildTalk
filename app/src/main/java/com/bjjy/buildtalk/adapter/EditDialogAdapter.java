package com.bjjy.buildtalk.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.entity.DisrOrAttenEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author power
 * @date 2019-12-03 09:19
 * @project BuildTalk
 * @description:
 */
public class EditDialogAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private ThemeInfoEntity.ThemeInfoBean mThemeInfoBean;
    private DisrOrAttenEntity.ThemeInfoBean mThemeInfoBean1;
    private Drawable mDrawable;

    public EditDialogAdapter(int layoutResId, @Nullable List<String> data, ThemeInfoEntity.ThemeInfoBean themeInfoBean) {
        super(layoutResId, data);
        mThemeInfoBean = themeInfoBean;
    }

    public EditDialogAdapter(int layoutResId, @Nullable List<String> data, DisrOrAttenEntity.ThemeInfoBean themeInfoBean) {
        super(layoutResId, data);
        mThemeInfoBean1 = themeInfoBean;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView textView = helper.getView(R.id.item_name_tv);
        switch (item){
            case "收藏":
                if (mThemeInfoBean == null ? 1 == mThemeInfoBean1.getIs_collect() : 1 == mThemeInfoBean.getIs_collect()) {
                    mDrawable = mContext.getResources().getDrawable(R.drawable.collect_sel_icon);
                    mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
                    textView.setCompoundDrawables(null, mDrawable, null, null);
                } else {
                    mDrawable = mContext.getResources().getDrawable(R.drawable.collect_def_icon);
                    mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
                    textView.setCompoundDrawables(null, mDrawable, null, null);
                }
                break;
            case "修改":
                mDrawable = mContext.getResources().getDrawable(R.drawable.edit_icon);
                mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
                textView.setCompoundDrawables(null, mDrawable, null, null);
                textView.setText(item);
                break;
            case "置顶":
                if (mThemeInfoBean == null ? 1 ==mThemeInfoBean1.getIs_top() : 1 == mThemeInfoBean.getIs_top()) {
                    mDrawable = mContext.getResources().getDrawable(R.drawable.qxzhiding_icon);
                    mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
                    textView.setCompoundDrawables(null, mDrawable, null, null);
                    textView.setText("取消置顶");
                } else {
                    mDrawable = mContext.getResources().getDrawable(R.drawable.zhiding_icon);
                    mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
                    textView.setCompoundDrawables(null, mDrawable, null, null);
                    textView.setText("置顶");
                }
                break;
            case "加精":
                if (mThemeInfoBean == null ? 1 == mThemeInfoBean1.getIs_choiceness() : 1 == mThemeInfoBean.getIs_choiceness()) {
                    mDrawable = mContext.getResources().getDrawable(R.drawable.qxjiajing_icon);
                    mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
                    textView.setCompoundDrawables(null, mDrawable, null, null);
                    textView.setText("取消精华");
                } else {
                    mDrawable = mContext.getResources().getDrawable(R.drawable.jiajing_icon);
                    mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
                    textView.setCompoundDrawables(null, mDrawable, null, null);
                    textView.setText("加精");
                }
                break;
            case "删除":
            case "不喜欢":
                mDrawable = mContext.getResources().getDrawable(R.drawable.delete_icon);
                mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
                textView.setCompoundDrawables(null, mDrawable, null, null);
                textView.setText(item);
                break;
            case "投诉":
                mDrawable = mContext.getResources().getDrawable(R.drawable.tousu_icon);
                mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
                textView.setCompoundDrawables(null, mDrawable, null, null);
                textView.setText(item);
                break;
        }
    }
}
