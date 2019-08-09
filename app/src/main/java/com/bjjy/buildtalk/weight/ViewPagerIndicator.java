package com.bjjy.buildtalk.weight;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerIndicator implements ViewPager.OnPageChangeListener {
    private Context context;
    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private int size;
    private float imgSize = 13;
    int img1 = R.drawable.shape_indicator_ture;
    int img2 = R.drawable.shape_indicator_false;

    private List<View> dotViewLists = new ArrayList<>();

    public ViewPagerIndicator(Context context, ViewPager viewPager, LinearLayout dotLayout, int size) {
        this.context = context;
        this.viewPager = viewPager;
        this.dotLayout = dotLayout;
        this.size = size;

//        for (int i = 0; i < size; i++) {
//            View imageView = new View(context);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            //为小圆点左右添加间距
//            params.leftMargin = SizeUtils.dp2px(6);
//            params.rightMargin = SizeUtils.dp2px(6);
//            //手动给小圆点一个大小
//            params.height = params.width = SizeUtils.dp2px(imgSize);
//            if (i == 0) {
//                imageView.setBackground(context.getResources().getDrawable(img1));
//            } else {
//                imageView.setBackgroundResource(img2);
//            }
//            //为LinearLayout添加ImageView
//            dotLayout.addView(imageView, params);
//            dotViewLists.add(imageView);
//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        if (position == 2){
//            dotLayout.setVisibility(View.GONE);
//        }else {
//            dotLayout.setVisibility(View.VISIBLE);
//        }
//        for (int i = 0; i < size; i++) {
//            //选中的页面改变小圆点为选中状态，反之为未选中
//            if ((position % size) == i) {
//                dotViewLists.get(i).setBackground(context.getResources().getDrawable(img1));
//            } else {
//                dotViewLists.get(i).setBackgroundResource(img2);
//            }
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
