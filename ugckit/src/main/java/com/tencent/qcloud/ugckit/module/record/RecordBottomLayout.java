package com.tencent.qcloud.ugckit.module.record;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.tencent.qcloud.ugckit.utils.UIAttributeUtil;
import com.tencent.qcloud.ugckit.R;
import com.tencent.ugc.TXUGCRecord;

import java.util.Locale;

public class RecordBottomLayout extends RelativeLayout {
    private Activity mActivity;
    private TextView mTvProgressTime;
    // 录制进度条
    private RecordProgressView mRecordProgressView;
    // 录制按钮
    private RecordButton mRecordButton;

    public RecordBottomLayout(Context context) {
        super(context);
        initViews();
    }

    public RecordBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public RecordBottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        mActivity = (Activity) getContext();
        inflate(mActivity, R.layout.record_bottom_layout, this);

        mTvProgressTime = (TextView) findViewById(R.id.record_progress_time);
        mTvProgressTime.setText(0.0f + getResources().getString(R.string.unit_second));
        mTvProgressTime.setVisibility(View.INVISIBLE);

        mRecordProgressView = (RecordProgressView) findViewById(R.id.record_progress_view);

        mRecordButton = (RecordButton) findViewById(R.id.record_button);


    }

    public void setOnRecordButtonListener(RecordButton.OnRecordButtonListener listener) {
        mRecordButton.setOnRecordButtonListener(listener);
    }

    public void startRecord() {
        mTvProgressTime.setVisibility(View.VISIBLE);
    }

    public void pauseRecord() {
        mTvProgressTime.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化最大/最小视频录制时长
     */
    public void initDuration() {
        mRecordProgressView.setMaxDuration(UGCKitRecordConfig.getInstance().mMaxDuration);
        mRecordProgressView.setMinDuration(UGCKitRecordConfig.getInstance().mMinDuration);
    }

    /**
     * 更新录制进度Progress
     *
     * @param milliSecond
     */
    public void updateProgress(long milliSecond) {
        mRecordProgressView.setProgress((int) milliSecond);
        float second = milliSecond / 1000f;
        mTvProgressTime.setText(String.format(Locale.CHINA, "%.1f", second) + getResources().getString(R.string.unit_second));
    }

    public RecordButton getRecordButton() {
        return mRecordButton;
    }

    public RecordProgressView getRecordProgressView() {
        return mRecordProgressView;
    }

}
