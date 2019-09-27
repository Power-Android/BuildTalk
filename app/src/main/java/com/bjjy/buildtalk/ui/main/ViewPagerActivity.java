package com.bjjy.buildtalk.ui.main;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.ImgOptionEntity;
import com.bjjy.buildtalk.utils.AllUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.ZOOM_FOCUS_CENTER_IMMEDIATE;

public class ViewPagerActivity extends BaseActivity<ViewpagerPresenter> implements ViewPagerContract.View, ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.index_tv)
    TextView mIndexTv;
    @BindView(R.id.bg_rl)
    RelativeLayout bgRl;
    private List<ImgOptionEntity> mOptionEntities;
    private int mPositon;
    private List<View> photoViewList;
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
    private View curPhotoView;
    private static final int DURATION = 250;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            LogUtils.e("handler");
            ImageListAdapter imageListAdapter = new ImageListAdapter();
            mViewPager.setAdapter(imageListAdapter);
            mViewPager.addOnPageChangeListener(ViewPagerActivity.this);
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
                        enterAnimation(() -> {
                            //开始动画之后要做的操作
                        });
                        //返回 true 继续绘制，返回false取消。
                        return true;
                    }
                });
            }
            return true;
        }
    });

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
            for (int i = 0; i < mOptionEntities.size(); i++) {
                addItemPhotoView(mOptionEntities.get(i).getImgUrl(), i);
            }

            //设置选中的位置来初始化动画
            ImgOptionEntity entity = mOptionEntities.get(mPositon);

            startY = entity.getTop();
            startX = entity.getLeft();
            startWidth = entity.getWidth();
            startHeight = entity.getHeight();

            mIndexTv.setText(mPositon + 1 + "/" + mOptionEntities.size());

            if (mOptionEntities.size() == 1) mIndexTv.setVisibility(View.GONE);
            else mIndexTv.setVisibility(View.VISIBLE);
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

    private void addItemPhotoView(String imgUrlStr, int i) {
        PhotoView photoView = new PhotoView(this);
        SubsamplingScaleImageView scaleImageView = new SubsamplingScaleImageView(this);
        photoView.setOnClickListener(v -> onBackPressed());
        scaleImageView.setOnClickListener(v -> onBackPressed());
        Glide.with(this)
                .load(imgUrlStr)
                .apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (resource.getIntrinsicWidth() < resource.getIntrinsicHeight() &&
                                resource.getIntrinsicHeight() > AllUtils.getScreenHeight(ViewPagerActivity.this)){
                            Glide.with(ViewPagerActivity.this)
                                    .load(imgUrlStr)
                                    .downloadOnly(new SimpleTarget<File>() {
                                        @Override
                                        public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                            // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                                            ImageSource imageSource = ImageSource.uri(Uri.fromFile(resource));
                                            int sWidth = BitmapFactory.decodeFile(resource.getAbsolutePath()).getWidth();
                                            int sHeight = BitmapFactory.decodeFile(resource.getAbsolutePath()).getHeight();
                                            Log.e("picLong",sWidth+"-----"+sHeight);
                                            int width = AllUtils.getScreenWidth(ViewPagerActivity.this);
                                            int height = AllUtils.getScreenHeight(ViewPagerActivity.this);
                                            float scaleW = width / (float) sWidth;
                                            //float scaleH = height / (float) sHeight;
                                            if (sHeight >= height
                                                    && sHeight / sWidth >= height / width) {
                                                scaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                                scaleImageView.setImage(imageSource, new ImageViewState(scaleW, new PointF(0, 0), 0));
                                            } else {
                                                scaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
                                                scaleImageView.setImage(imageSource);
                                                scaleImageView.setDoubleTapZoomStyle(ZOOM_FOCUS_CENTER_IMMEDIATE);
                                            }
                                        }
                                    });
                            photoViewList.add(scaleImageView);
                            LogUtils.e("长图");
                        }else {
                            Glide.with(ViewPagerActivity.this)
                                    .load(resource)
                                    .into(photoView);
                            photoViewList.add(photoView);
                            LogUtils.e("小图");
                        }
                        if (i == mOptionEntities.size() - 1){
                            mHandler.sendEmptyMessage(1);
                        }
                    }
                });
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
//            View view = getLayoutInflater().inflate(R.layout.view_pager_item, null);
//            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            SubsamplingScaleImageView scaleImageView = view.findViewById(R.id.scale_view);
//            PhotoView photoView = view.findViewById(R.id.photo_view);

//            scaleImageView.setOnClickListener(v -> onBackPressed());
//            photoView.setOnClickListener(v -> onBackPressed());


            container.addView(photoViewList.get(position));
            return photoViewList.get(position);
//            return view;
        }

        public void destroyItem(android.view.ViewGroup container, int position, Object object) {
            container.removeView(photoViewList.get(position));
//            container.removeView((View) object);
        }

    }
}
