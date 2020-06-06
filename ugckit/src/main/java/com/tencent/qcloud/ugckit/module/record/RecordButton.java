package com.tencent.qcloud.ugckit.module.record;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.tencent.qcloud.ugckit.R;
import com.tencent.qcloud.ugckit.module.record.interfaces.IRecordButton;

/**
 * 多种拍摄模式的按钮
 */
public class RecordButton extends RelativeLayout implements IRecordButton, View.OnTouchListener {
    private Activity mActivity;
    private ViewGroup mRootLayout;

    private View mViewPressModeOutter;
    private View mViewPressModeInner;
    private boolean mIsRecording;
    private OnRecordButtonListener mOnRecordButtonListener;

    public RecordButton(Context context) {
        super(context);
        initViews();
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        mActivity = (Activity) getContext();
        inflate(mActivity, R.layout.record_button, this);
        setOnTouchListener(this);

        mRootLayout = (ViewGroup) findViewById(R.id.layout_compose_record_btn);

        mViewPressModeOutter = findViewById(R.id.view_record_touch_shot_bkg);
        mViewPressModeInner = findViewById(R.id.view_record_touch_shot);
    }

    @Override
    public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startRecordAnim();
                mIsRecording = true;
                break;

            case MotionEvent.ACTION_UP:
                pauseRecordAnim();
                break;
        }
        return true;
    }

    /**
     * 开始录制操作执行的动画
     */
    public void startRecordAnim() {
        startRecordAnimByLongTouch();
    }

    /**
     * 暂停录制操作执行的动画
     */
    public void pauseRecordAnim() {
        pauseRecordAnimByLongTouch();
    }

    /**
     * 拍摄模式为"长按"录制下。开始录制操作执行的动画
     */
    private void startRecordAnimByLongTouch() {
        ObjectAnimator btnBkgZoomOutXAn = ObjectAnimator.ofFloat(mViewPressModeOutter, "scaleX", 1.1f);
        ObjectAnimator btnBkgZoomOutYAn = ObjectAnimator.ofFloat(mViewPressModeOutter, "scaleY", 1.1f);

        ObjectAnimator btnZoomInXAn = ObjectAnimator.ofFloat(mViewPressModeInner, "scaleX", 0.9f);
        ObjectAnimator btnZoomInYAn = ObjectAnimator.ofFloat(mViewPressModeInner, "scaleY", 0.9f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(80);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(btnBkgZoomOutXAn).with(btnBkgZoomOutYAn).with(btnZoomInXAn).with(btnZoomInYAn);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnRecordButtonListener != null) {
                    mOnRecordButtonListener.onRecordStart();
                    mIsRecording = true;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    /**
     * 拍摄模式为"长按"录制下。暂停录制操作执行的动画
     */
    private void pauseRecordAnimByLongTouch() {
        ObjectAnimator btnBkgZoomInXAn = ObjectAnimator.ofFloat(mViewPressModeOutter, "scaleX", 1f);
        ObjectAnimator btnBkgZoomIntYAn = ObjectAnimator.ofFloat(mViewPressModeOutter, "scaleY", 1f);

        ObjectAnimator btnZoomInXAn = ObjectAnimator.ofFloat(mViewPressModeInner, "scaleX", 1f);
        ObjectAnimator btnZoomInYAn = ObjectAnimator.ofFloat(mViewPressModeInner, "scaleY", 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(80);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(btnBkgZoomInXAn).with(btnBkgZoomIntYAn).with(btnZoomInXAn).with(btnZoomInYAn);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnRecordButtonListener != null) {
                    mOnRecordButtonListener.onRecordPause();
                    mIsRecording = false;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @Override
    public void setOnRecordButtonListener(OnRecordButtonListener listener) {
        mOnRecordButtonListener = listener;
    }

    @Override
    public void setTouchRecordOutterColor(int color) {
        mViewPressModeOutter.setBackgroundResource(color);
    }

    @Override
    public void setTouchRecordInnerColor(int color) {
        mViewPressModeInner.setBackgroundResource(color);
    }

}
