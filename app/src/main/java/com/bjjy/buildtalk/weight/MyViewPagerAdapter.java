package com.bjjy.buildtalk.weight;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author power
 * @date 2019/5/14 2:19 PM
 * @project BuildTalk
 * @description:
 */
public class MyViewPagerAdapter extends PagerAdapter {
    private List<String> tabList;
    private List<View> mViews;

    public MyViewPagerAdapter(List<String> tabList, List<View> mViews) {
        this.tabList = tabList;
        this.mViews = mViews;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = mViews.get(position);
//        if (v.getParent() == null)
//        container.addView(v);

        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
