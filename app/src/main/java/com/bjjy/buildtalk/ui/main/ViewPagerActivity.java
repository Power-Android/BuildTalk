package com.bjjy.buildtalk.ui.main;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.ImgOptionEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerActivity extends BaseActivity<ViewpagerPresenter> implements ViewPagerContract.View, ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.index_tv)
    TextView mIndexTv;
    @BindView(R.id.bg_rl)
    RelativeLayout bgRl;
    private List<ImgOptionEntity> mOptionEntities;
    private int mPositon;
    private List<PhotoView> photoViewList;
    //开始的坐标值
    private int startY;
    private int startX;
    //开始的宽高
    private int startWidth;
    private int startHeight;
    //X、Y的移动距离
    private int xDelta;
    private int yDelta;
    //X、Y的缩放比例
    private float mWidthScale;
    private float mHeightScale;
    //背景色
    private ColorDrawable colorDrawable;
    //当前选中的photoView
    private PhotoView curPhotoView;
    private static final int DURATION = 250;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initView() {
        mOptionEntities = (List<ImgOptionEntity>) getIntent().getSerializableExtra("optionEntities");
        mPositon = getIntent().getIntExtra("positon", 0);
        photoViewList = new ArrayList<>();
    }

    @Override
    protected void initEventAndData() {
        if (mOptionEntities != null && !mOptionEntities.isEmpty()) {
            //设置选中的位置来初始化动画
            ImgOptionEntity entity = mOptionEntities.get(mPositon);

            startY = entity.getTop();
            startX = entity.getLeft();
            startWidth = entity.getWidth();
            startHeight = entity.getHeight();

            mIndexTv.setText(mPositon + 1 + "/" + mOptionEntities.size());
            for (int i = 0; i < mOptionEntities.size(); i++) {
                addItemPhotoView(mOptionEntities.get(i).getImgUrl());
            }
            if (mOptionEntities.size() == 1) mIndexTv.setVisibility(View.GONE);
            else mIndexTv.setVisibility(View.VISIBLE);
        }

        ImageListAdapter imageListAdapter = new ImageListAdapter();
        mViewPager.setAdapter(imageListAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(mPositon);

        if (!photoViewList.isEmpty()) {
            curPhotoView = photoViewList.get(mPositon);
            //注册一个回调函数，当一个视图树将要绘制时调用这个回调函数。
            ViewTreeObserver observer = curPhotoView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    curPhotoView.getViewTreeObserver().removeOnPreDrawListener(this);
                    int[] screenLocation = new int[2];
                    curPhotoView.getLocationOnScreen(screenLocation);
                    //动画需要移动的距离
                    xDelta = startX - screenLocation[0];
                    yDelta = startY - screenLocation[1];
                    //计算缩放比例
                    mWidthScale = (float) startWidth / curPhotoView.getWidth();
                    mHeightScale = (float) startHeight / curPhotoView.getHeight();
                    enterAnimation(new Runnable() {
                        @Override
                        public void run() {
                            //开始动画之后要做的操作
                        }
                    });
                    //返回 true 继续绘制，返回false取消。
                    return true;
                }
            });
        }
    }

    private void enterAnimation(final Runnable enterAction) {
        //放大动画
        curPhotoView.setPivotX(0);
        curPhotoView.setPivotY(0);
        curPhotoView.setScaleX(mWidthScale);
        curPhotoView.setScaleY(mHeightScale);
        curPhotoView.setTranslationX(xDelta);
        curPhotoView.setTranslationY(yDelta);
        TimeInterpolator sDecelerator = new DecelerateInterpolator();
        curPhotoView.animate().setDuration(DURATION).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator).withEndAction(enterAction);
        //设置背景渐变成你设置的颜色
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(getResources().getColor(R.color.black), "alpha", 0, 255);
        bgAnim.setDuration(DURATION);
        bgAnim.start();
    }

    @Override
    public void onBackPressed() {
        int[] screenLocation = new int[2];
        curPhotoView.getLocationOnScreen(screenLocation);
        xDelta = startX - screenLocation[0];
        yDelta = startY - screenLocation[1];
        mWidthScale = (float) startWidth / curPhotoView.getWidth();
        mHeightScale = (float) startHeight / curPhotoView.getHeight();

        exitAnimation(() -> {
            //结束动画要做的操作
            finish();
            overridePendingTransition(0, 0);
        });
    }

    private void exitAnimation(final Runnable endAction) {
        //缩小动画
        curPhotoView.setPivotX(0);
        curPhotoView.setPivotY(0);
        curPhotoView.setScaleX(1);
        curPhotoView.setScaleY(1);
        curPhotoView.setTranslationX(0);
        curPhotoView.setTranslationY(0);

        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        curPhotoView.animate().setDuration(DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(xDelta).translationY(yDelta).setInterpolator(sInterpolator).withEndAction(endAction);
        //设置背景渐透明
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(getResources().getColor(R.color.black), "alpha", 0);
        bgAnim.setDuration(DURATION);
        bgAnim.start();
    }

    private void addItemPhotoView(String imgUrlStr) {
        PhotoView photoView = new PhotoView(this);
        photoView.setAdjustViewBounds(true);
        photoView.setMaximumScale(4.0f);
        Glide.with(this)
                .load(imgUrlStr)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(photoView);

//        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
//            @Override
//            public void onPhotoTap(ImageView view, float x, float y) {
//                onBackPressed();
//            }
//        });
        photoView.setOnClickListener(v -> onBackPressed());
        photoViewList.add(photoView);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //当ViewPager滚动时重置成position对应外面的ImageView的位置信息
        curPhotoView = photoViewList.get(position);
        if (mOptionEntities != null && !mOptionEntities.isEmpty()) {
            ImgOptionEntity entity = mOptionEntities.get(position);
            startY = entity.getTop();
            startX = entity.getLeft();
            startWidth = entity.getWidth();
            startHeight = entity.getHeight();

            mIndexTv.setText(position + 1 + "/" + mOptionEntities.size());
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public class ImageListAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return photoViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        public Object instantiateItem(android.view.ViewGroup container, int position) {
            container.addView(photoViewList.get(position));
            return photoViewList.get(position);

        }

        public void destroyItem(android.view.ViewGroup container, int position, Object object) {
            container.removeView(photoViewList.get(position));
        }

    }
}
