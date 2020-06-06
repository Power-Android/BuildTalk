package com.tencent.qcloud.ugckit.module.record;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tencent.liteav.demo.beauty.BeautyPanel;
import com.tencent.qcloud.ugckit.module.effect.bgm.view.SoundEffectsPannel;
import com.tencent.qcloud.ugckit.R;
import com.tencent.qcloud.ugckit.component.TitleBarLayout;

import com.tencent.qcloud.ugckit.module.record.interfaces.IVideoRecordKit;
import com.tencent.qcloud.ugckit.utils.UIAttributeUtil;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXUGCRecord;

public abstract class AbsVideoRecordUI extends RelativeLayout implements IVideoRecordKit, View.OnClickListener {
    private Activity mActivity;
    private TXCloudVideoView mVideoView;
    private ScrollFilterView mScrollFilterView;
    private RecordBottomLayout mRecordBottomLayout;
    private BeautyPanel mBeautyPanel;
    private ImageView mIvClose;
    // 切换摄像头
    private ImageView mIv_switch_camera;
    // 闪光灯
    private ImageView mIv_torch;
    /**
     * 是否前置摄像头UI判断
     */
    private boolean mFrontCameraFlag = false;
    /**
     * 是否打开闪光灯UI判断
     */
    private boolean mIsTorchOpenFlag;
    private int mTorchOnImage;
    private int mTorchOffImage;
    private int mTorchDisableImage;

    public AbsVideoRecordUI(Context context) {
        super(context);
        initViews();
    }

    public AbsVideoRecordUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public AbsVideoRecordUI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.video_rec_layout, this);
        mActivity = (Activity) getContext();

        mVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
        mRecordBottomLayout = (RecordBottomLayout) findViewById(R.id.record_bottom_layout);
        mBeautyPanel = (BeautyPanel) findViewById(R.id.beauty_pannel);
        mScrollFilterView = (ScrollFilterView) findViewById(R.id.scrollFilterView);
        mScrollFilterView.setBeautyPannel(mBeautyPanel);
        mIvClose = findViewById(R.id.iv_close);
        mIv_switch_camera = findViewById(R.id.iv_switch_camera);
        mIv_torch = findViewById(R.id.iv_torch);

        mIv_switch_camera.setOnClickListener(this);
        mIv_torch.setOnClickListener(this);

        mTorchDisableImage = UIAttributeUtil.getResResources(mActivity, R.attr.recordTorchDisableIcon, R.drawable.ugc_torch_disable);
        mTorchOffImage = UIAttributeUtil.getResResources(mActivity, R.attr.recordTorchOffIcon, R.drawable.selector_torch_close);
        mTorchOnImage = UIAttributeUtil.getResResources(mActivity, R.attr.recordTorchOnIcon, R.drawable.selector_torch_open);

        if (mFrontCameraFlag) {
            mIv_torch.setVisibility(View.GONE);
            mIv_torch.setImageResource(mTorchDisableImage);
        } else {
            mIv_torch.setVisibility(View.VISIBLE);
            mIv_torch.setImageResource(mTorchOffImage);
        }
    }

    public ImageView getCloseIv() {
        return mIvClose;
    }

    public ImageView getSwitchCamera() {
        return mIv_switch_camera;
    }

    public ImageView getTorchIv() {
        return mIv_torch;
    }

    public TXCloudVideoView getRecordVideoView() {
        return mVideoView;
    }

    public ScrollFilterView getScrollFilterView() {
        return mScrollFilterView;
    }

    public RecordBottomLayout getRecordBottomLayout() {
        return mRecordBottomLayout;
    }

    public BeautyPanel getBeautyPanel() {
        return mBeautyPanel;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_torch) {
            toggleTorch();
        } else if (id == R.id.iv_switch_camera) {
            switchCamera();
        }
    }

    /**
     * 切换前后摄像头
     */
    private void switchCamera() {
        mFrontCameraFlag = !mFrontCameraFlag;
        mIsTorchOpenFlag = false;
        if (mFrontCameraFlag) {
            mIv_torch.setVisibility(View.GONE);
            mIv_torch.setImageResource(mTorchDisableImage);
        } else {
            mIv_torch.setVisibility(View.VISIBLE);
            mIv_torch.setImageResource(mTorchOffImage);
        }
        TXUGCRecord record = VideoRecordSDK.getInstance().getRecorder();
        if (record != null) {
            record.switchCamera(mFrontCameraFlag);
        }
    }

    /**
     * 切换闪光灯开/关
     */
    private void toggleTorch() {
        mIsTorchOpenFlag = !mIsTorchOpenFlag;
        if (mIsTorchOpenFlag) {
            mIv_torch.setImageResource(mTorchOnImage);

            TXUGCRecord record = VideoRecordSDK.getInstance().getRecorder();
            if (record != null) {
                record.toggleTorch(true);
            }
        } else {
            mIv_torch.setImageResource(mTorchOffImage);
            TXUGCRecord record = VideoRecordSDK.getInstance().getRecorder();
            if (record != null) {
                record.toggleTorch(false);
            }
        }
    }

    /**
     * 设置闪光灯的状态为关闭
     */
    public void closeTorch() {
        if (mIsTorchOpenFlag) {
            mIsTorchOpenFlag = false;
            if (mFrontCameraFlag) {
                mIv_torch.setVisibility(View.GONE);
                mIv_torch.setImageResource(mTorchDisableImage);
            } else {
                mIv_torch.setImageResource(mTorchOffImage);
                mIv_torch.setVisibility(View.VISIBLE);
            }
        }
    }

}
