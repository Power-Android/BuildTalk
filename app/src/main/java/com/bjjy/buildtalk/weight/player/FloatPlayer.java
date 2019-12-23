package com.bjjy.buildtalk.weight.player;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.SizeUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.CircleProgressView;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * @author power
 * @date 2019-12-04 10:13
 * @project BuildTalk
 * @description: 悬浮播放器view
 */
public class FloatPlayer extends FrameLayout {
    private Context mContext;
    private boolean isMargin = false;   //距离底部margin
    private RelativeLayout mContentWrap, mPlayIcon, mDetailRl;
    private ImageView mCloseIv, mNextIv, mStautsIv, mLastIv;
    private RoundedImageView mCoverIv;
    private CircleProgressView mProgressView;
    private TextView mTitleTv, mTimeTv;
    private FloatPlayerListener listener;
    private View mPlayerView;
    private CountDownTimer mCountDownTimer;
    private long curTime = 0;

    public FloatPlayer(@NonNull Context context, boolean isMargin) {
        super(context);
        this.mContext = context;
        this.isMargin = isMargin;
        init();
    }

    private void init() {
        mPlayerView = LayoutInflater.from(mContext).inflate(R.layout.float_player_view, null);
        mContentWrap = mPlayerView.findViewById(R.id.content);
        mDetailRl = mPlayerView.findViewById(R.id.detail_rl);
        mCloseIv = mPlayerView.findViewById(R.id.player_close_iv);
        mCoverIv = mPlayerView.findViewById(R.id.player_img_iv);
        mNextIv = mPlayerView.findViewById(R.id.player_next_iv);
        mPlayIcon = mPlayerView.findViewById(R.id.play_rl);
        mProgressView = mPlayerView.findViewById(R.id.progress_bar);
        mStautsIv = mPlayerView.findViewById(R.id.stauts_iv);
        mLastIv = mPlayerView.findViewById(R.id.player_last_iv);
        mTitleTv = mPlayerView.findViewById(R.id.player_title_tv);
        mTimeTv = mPlayerView.findViewById(R.id.player_time_tv);

        mDetailRl.setOnClickListener(v -> listener.jump());
        mCloseIv.setOnClickListener(v -> listener.close());
        mNextIv.setOnClickListener(v -> listener.next());
        mPlayIcon.setOnClickListener(v -> listener.play());
        mLastIv.setOnClickListener(v -> listener.last());
        updateViewLayoutParams(isMargin);
        addView(mPlayerView);
    }

    public void updateViewLayoutParams(boolean isMargin) {
        if (mContentWrap != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mContentWrap.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            if (isMargin) {
                layoutParams.bottomMargin = (int) getResources().getDimension(R.dimen.dp_55);
            }else {
                layoutParams.bottomMargin = 0;
            }
            mContentWrap.setLayoutParams(layoutParams);
        }
    }

    public void setInfo(String coverUrl, String title) {
        Glide.with(mContext).load(coverUrl).into(mCoverIv);
        mTitleTv.setText(title);
    }

    public void setFloatViewListener(FloatPlayerListener listener) {
        this.listener = listener;
    }

    public void setPlayStatusIcon(int drawable, boolean isVisibleClose) {
        Glide.with(mContext).load(drawable).into(mStautsIv);
        if (isVisibleClose) {
            mCloseIv.setVisibility(VISIBLE);
        } else {
            mCloseIv.setVisibility(GONE);
        }
    }

    /**
     * @param current 时长
     * @param status  1是开始 2是暂停 3是继续
     */
    public void setSeekBar(int current, int status) {
        if (1 == status) {
            mProgressView.startAnimProgress(100, current);
        } else if (2 == status) {
            mProgressView.pause();
        } else {
            mProgressView.resume();
        }
    }

    /**
     * @param millisInFuture 时长
     * @param status         1是开始 2是暂停 3是继续
     */
    public void setCountDownTimer(long millisInFuture, int status) {
        if (1 == status) {
            initTimer(millisInFuture);
            mCountDownTimer.start();
        } else if (2 == status) {
            mCountDownTimer.cancel();
        } else {
            initTimer(curTime);
            mCountDownTimer.start();
        }
    }

    private void initTimer(long millisInFuture) {
        //倒计时显示操作
        mCountDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                curTime = millisUntilFinished;
                mTimeTv.setText(TimeUtils.stringForTime((int) (millisUntilFinished)));
            }

            @Override
            public void onFinish() {
                curTime = 0;
            }
        };
    }

    /**
     * 关闭播放器，添加动画
     */
    public void colsePlayer() {

    }

    public void clearSeekAndTimer() {
        if (mProgressView != null && mCountDownTimer != null){
            mProgressView.destroy();
            mCountDownTimer.cancel();
            mTimeTv.setText("00:00");
        }
    }
}
