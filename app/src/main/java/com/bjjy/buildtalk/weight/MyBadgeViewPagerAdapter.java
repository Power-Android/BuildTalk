package com.bjjy.buildtalk.weight;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;

import java.util.List;

/**
 * @author power
 * @date 2019/5/23 11:50 AM
 * @project BuildTalk
 * @description:
 */
public class MyBadgeViewPagerAdapter extends PagerAdapter {

    private List<String> tabList;
    private List<View> mViews;
    private List<Integer> mBadgeCountList;

    public MyBadgeViewPagerAdapter(List<String> tabList, List<View> mViews, List<Integer> mCounts) {
        this.tabList = tabList;
        this.mViews = mViews;
        this.mBadgeCountList = mCounts;
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

    public View getTabItemView(int position) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.tab_layout_item, null);
        TextView textView = view.findViewById(R.id.textview);
        textView.setText(tabList.get(position));

        TextView target = view.findViewById(R.id.badgeview_target);
        target.setText(formatBadgeNumber(mBadgeCountList.get(position)));

        return view;
    }

    public static String formatBadgeNumber(int value) {
        if (value <= 0) {
            return null;
        }

        if (value < 100) {
            // equivalent to String#valueOf(int);
            return Integer.toString(value);
        }

        // my own policy
        return "99+";
    }
}
