package com.bjjy.buildtalk.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.weight.ViewPagerIndicator;
import com.bumptech.glide.Glide;

import butterknife.BindView;

public class GuideActivity extends BaseActivity<GuidePresenter> implements GuideContract.View {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.liner)
    LinearLayout linear;
    private int[] images = {R.drawable.guide01, R.drawable.guide02, R.drawable.guide03};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeStatusBar(this, true, true);
        viewPager.setOnPageChangeListener(new ViewPagerIndicator(this, viewPager, linear, images.length));
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, final int position) {
                View view = View.inflate(GuideActivity.this, R.layout.item_guide, null);
                ImageView guideIv = view.findViewById(R.id.guide_iv);
                RelativeLayout guideInto = view.findViewById(R.id.guide_into);
                if (position == images.length - 1) {
                    if (guideInto != null)
                        guideInto.setVisibility(View.VISIBLE);
                } else {
                    if (guideInto != null)
                        guideInto.setVisibility(View.INVISIBLE);
                }
                Glide.with(App.getContext()).load(images[position]).into(guideIv);
                container.addView(view);
                assert guideInto != null;
                guideInto.setOnClickListener(v -> {
                    mPresenter.mDataManager.setIsGuide(true);
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    finish();
                });
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
    }

    @Override
    protected void initEventAndData() {

    }
}
