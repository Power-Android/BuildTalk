package com.tencent.qcloud.ugckit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tencent.liteav.demo.beauty.BeautyParams;
import com.tencent.qcloud.ugckit.basic.ITitleBarLayout;
import com.tencent.qcloud.ugckit.basic.OnUpdateUIListener;
import com.tencent.qcloud.ugckit.basic.UGCKitResult;
import com.tencent.qcloud.ugckit.component.dialog.ProgressDialogUtil;
import com.tencent.qcloud.ugckit.component.dialogfragment.ProgressFragmentUtil;
import com.tencent.qcloud.ugckit.module.ProcessKit;
import com.tencent.qcloud.ugckit.module.effect.VideoEditerSDK;
import com.tencent.qcloud.ugckit.module.effect.bgm.view.SoundEffectsPannel;
import com.tencent.qcloud.ugckit.module.record.AbsVideoRecordUI;
import com.tencent.qcloud.ugckit.module.record.AudioFocusManager;
import com.tencent.qcloud.ugckit.module.record.MusicInfo;
import com.tencent.qcloud.ugckit.module.record.OnFilterScrollViewListener;
import com.tencent.qcloud.ugckit.module.record.PhotoSoundPlayer;
import com.tencent.qcloud.ugckit.module.record.RecordBottomLayout;
import com.tencent.qcloud.ugckit.module.record.RecordModeView;
import com.tencent.qcloud.ugckit.module.record.RecordMusicManager;
import com.tencent.qcloud.ugckit.module.record.ScrollFilterView;
import com.tencent.qcloud.ugckit.module.record.UGCKitRecordConfig;
import com.tencent.qcloud.ugckit.module.record.VideoRecordSDK;
import com.tencent.qcloud.ugckit.module.record.interfaces.IRecordButton;
import com.tencent.qcloud.ugckit.module.record.interfaces.IRecordMusicPannel;
import com.tencent.qcloud.ugckit.module.record.interfaces.IRecordRightLayout;
import com.tencent.qcloud.ugckit.module.record.interfaces.IVideoRecordKit;
import com.tencent.qcloud.ugckit.utils.DialogUtil;
import com.tencent.qcloud.ugckit.utils.LogReport;
import com.tencent.qcloud.ugckit.utils.TelephonyUtil;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXUGCRecord;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoInfoReader;

public class UGCKitVideoRecord extends AbsVideoRecordUI implements
        IRecordButton.OnRecordButtonListener,
        ScrollFilterView.OnRecordFilterListener,
        VideoRecordSDK.OnVideoRecordListener {

    private static final String TAG = "UGCKitVideoRecord";
    private OnRecordListener mOnRecordListener;
    private FragmentActivity mActivity;
    private ProgressFragmentUtil mProgressFragmentUtil;
    private ProgressDialogUtil mProgressDialogUtil;
    private UGCBeautyKit mUGCBeautyKit;
    private boolean mEnable;

    public UGCKitVideoRecord(Context context) {
        super(context);
        initDefault(context);
    }

    public UGCKitVideoRecord(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefault(context);
    }

    public UGCKitVideoRecord(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefault(context);
    }

    private void initDefault(Context context) {
        mActivity = (FragmentActivity) getContext();
        // 初始化SDK:TXUGCRecord
        VideoRecordSDK.getInstance().initSDK();
        // 初始化视频草稿箱
        VideoRecordSDK.getInstance().initRecordDraft(context);
        VideoRecordSDK.getInstance().setOnRestoreDraftListener(new VideoRecordSDK.OnRestoreDraftListener() {
            @Override
            public void onDraftProgress(long duration) {
                getRecordBottomLayout().updateProgress((int) duration);
                getRecordBottomLayout().getRecordProgressView().clipComplete();
            }

            @Override
            public void onDraftTotal(long duration) {

            }
        });

        VideoRecordSDK.getInstance().setVideoRecordListener(this);
        // 点击"下一步"
//        getTitleBar().setVisible(false, ITitleBarLayout.POSITION.RIGHT);
//        getTitleBar().setOnRightClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mProgressDialogUtil.showProgressDialog();
//
//                VideoRecordSDK.getInstance().stopRecord();
//            }
//        });

        // 点击"录制按钮"（包括"拍照"，"单击拍"，"按住拍"）
        getRecordBottomLayout().setOnRecordButtonListener(this);

        getScrollFilterView().setOnRecordFilterListener(this);

        TelephonyUtil.getInstance().initPhoneListener();
        mProgressDialogUtil = new ProgressDialogUtil(mActivity);

        UGCKitRecordConfig config = UGCKitRecordConfig.getInstance();
        config.mBeautyParams = new BeautyParams();

        // 设置默认美颜
        config.mBeautyParams.mBeautyStyle = 0;
        config.mBeautyParams.mBeautyLevel = 4;
        config.mBeautyParams.mWhiteLevel = 1;
        // 初始化默认配置
        VideoRecordSDK.getInstance().initConfig(config);
        VideoRecordSDK.getInstance().updateBeautyParam(config.mBeautyParams);

        TXUGCRecord txugcRecord = VideoRecordSDK.getInstance().getRecorder();
        mUGCBeautyKit = new UGCBeautyKit(txugcRecord);
        mUGCBeautyKit.setOnFilterScrollViewListener(new OnFilterScrollViewListener() {

            @Override
            public void onFilerChange(Bitmap filterImage, int index) {
                getScrollFilterView().doTextAnimator(index);
            }
        });
        getBeautyPanel().setProxy(mUGCBeautyKit);
    }

    @Override
    public void setOnRecordListener(OnRecordListener listener) {
        mOnRecordListener = listener;
    }

    @Override
    public void setOnMusicChooseListener(OnMusicChooseListener listener) {

    }

    @Override
    public void start() {
        // 打开录制预览界面
        VideoRecordSDK.getInstance().startCameraPreview(getRecordVideoView());
    }

    @Override
    public void stop() {
        Log.d(TAG, "stop");
        TelephonyUtil.getInstance().uninitPhoneListener();

        getRecordBottomLayout().getRecordButton().pauseRecordAnim();
        //关闭闪光灯
        closeTorch();
        // 停止录制预览界面
        VideoRecordSDK.getInstance().stopCameraPreview();
        // 暂停录制
        VideoRecordSDK.getInstance().pauseRecord();
    }

    @Override
    public void release() {
        Log.d(TAG, "release");
        getRecordBottomLayout().getRecordProgressView().release();
        // 停止录制
        VideoRecordSDK.getInstance().releaseRecord();

        UGCKitRecordConfig.getInstance().clear();
        // 录制TXUGCRecord是单例，需要释放时还原配置
        getBeautyPanel().clear();

        VideoRecordSDK.getInstance().setVideoRecordListener(null);
        mUGCBeautyKit.setOnFilterScrollViewListener(null);
    }

    @Override
    public void screenOrientationChange() {
        Log.d(TAG, "screenOrientationChange");
        VideoRecordSDK.getInstance().stopCameraPreview();

        VideoRecordSDK.getInstance().pauseRecord();

        VideoRecordSDK.getInstance().startCameraPreview(getRecordVideoView());
    }

    @Override
    public void setRecordMusicInfo(@NonNull MusicInfo musicInfo) {
        if (musicInfo != null) {
            Log.d(TAG, "music name:" + musicInfo.name + ", path:" + musicInfo.path);
        }
    }

    @Override
    public void backPressed() {
        Log.d(TAG, "backPressed");
        // 录制已停止，则回调"录制被取消"
        if (VideoRecordSDK.getInstance().getRecordState() == VideoRecordSDK.STATE_STOP) {
            if (mOnRecordListener != null) {
                mOnRecordListener.onRecordCanceled();
            }
            return;
        }
        // 录制已开始，点击返回键，暂停录制
        if (VideoRecordSDK.getInstance().getRecordState() == VideoRecordSDK.STATE_START) {
            VideoRecordSDK.getInstance().pauseRecord();
        }

        int size = VideoRecordSDK.getInstance().getPartManager().getPartsPathList().size();
        if (size == 0) {
            if (mOnRecordListener != null) {
                mOnRecordListener.onRecordCanceled();
            }
            return;
        }

        showGiveupRecordDialog();
    }

    /**
     * 显示放弃录制对话框
     */
    private void showGiveupRecordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alertDialog = builder.setTitle(getResources().getString(R.string.cancel_record)).setCancelable(false).setMessage(R.string.confirm_cancel_record_content)
                .setPositiveButton(R.string.give_up, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        dialog.dismiss();

                        VideoRecordSDK.getInstance().deleteAllParts();

                        if (mOnRecordListener != null) {
                            mOnRecordListener.onRecordCanceled();
                        }
                        return;
                    }
                })
                .setNegativeButton(getResources().getString(R.string.wrong_click), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * 点击录制开始按钮
     */
    @Override
    public void onRecordStart() {
        getRecordBottomLayout().startRecord();
        // 开始/继续录制
        int retCode = VideoRecordSDK.getInstance().startRecord();
        if (retCode == VideoRecordSDK.START_RECORD_FAIL) { //点击开始录制失败，录制按钮状态变为暂停
            getRecordBottomLayout().getRecordButton().pauseRecordAnim();
            return;
        }

        AudioFocusManager.getInstance().setAudioFocusListener(new AudioFocusManager.OnAudioFocusListener() {
            @Override
            public void onAudioFocusChange() {
                VideoRecordSDK.getInstance().pauseRecord();
            }
        });
        AudioFocusManager.getInstance().requestAudioFocus();
    }

    /**
     * 点击录制暂停按钮
     */
    @Override
    public void onRecordPause() {
        Log.d(TAG, "onRecordPause");
        getRecordBottomLayout().pauseRecord();

        VideoRecordSDK.getInstance().pauseRecord();
        RecordMusicManager.getInstance().pauseMusic();
        AudioFocusManager.getInstance().abandonAudioFocus();
        if (mProgressDialogUtil != null && mEnable) {
            mProgressDialogUtil.showProgressDialog();
            VideoRecordSDK.getInstance().stopRecord();
        }
    }

    /**
     * 点击照相
     */
    @Override
    public void onTakePhoto() {

    }

    @Override
    public void onDeleteParts(int partsSize, long duration) {

    }

    @Override
    public void onSingleClick(float x, float y) {
        getBeautyPanel().setVisibility(View.GONE);
        getRecordBottomLayout().setVisibility(View.VISIBLE);
        TXUGCRecord record = VideoRecordSDK.getInstance().getRecorder();
        if (record != null) {
            record.setFocusPosition(x, y);
        }
    }

    @Override
    public void onRecordProgress(long milliSecond) {
        getRecordBottomLayout().updateProgress(milliSecond);

        float second = milliSecond / 1000f;
        mEnable = second >= UGCKitRecordConfig.getInstance().mMinDuration / 1000;
    }

    @Override
    public void onRecordEvent() {
        getRecordBottomLayout().getRecordProgressView().clipComplete();
    }

    @Override
    public void onRecordComplete(@NonNull TXRecordCommon.TXRecordResult result) {
        LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_VIDEO_RECORD, result.retCode, result.descMsg);

        if (result.retCode >= 0) {
            mProgressDialogUtil.dismissProgressDialog();
            mProgressDialogUtil = null;
            boolean editFlag = UGCKitRecordConfig.getInstance().mIsNeedEdit;
            if (editFlag) {
                // 录制后需要进行编辑，预处理产生视频缩略图
                startPreprocess(result.videoPath);
            } else {
                // 录制后不需要进行编辑视频，直接输出录制视频路径
                if (mOnRecordListener != null) {
                    UGCKitResult ugcKitResult = new UGCKitResult();
                    String outputPath = VideoRecordSDK.getInstance().getRecordVideoPath();
                    ugcKitResult.errorCode = result.retCode;
                    ugcKitResult.descMsg = result.descMsg;
                    ugcKitResult.outputPath = outputPath;
                    ugcKitResult.coverPath = result.coverPath;
                    mOnRecordListener.onRecordCompleted(ugcKitResult);
                }
            }
        }
    }

    private void startPreprocess(String videoPath) {
        mProgressFragmentUtil = new ProgressFragmentUtil(mActivity);
        mProgressFragmentUtil.showLoadingProgress(new ProgressFragmentUtil.IProgressListener() {
            @Override
            public void onStop() {
                mProgressFragmentUtil.dismissLoadingProgress();

                ProcessKit.getInstance().stopProcess();
            }
        });

        loadVideoInfo(videoPath);
    }

    /**
     * 加载视频信息
     *
     * @param videoPath
     */
    private void loadVideoInfo(String videoPath) {
        TXVideoEditConstants.TXVideoInfo info = TXVideoInfoReader.getInstance(UGCKit.getAppContext()).getVideoFileInfo(videoPath);
        if (info == null) {
            DialogUtil.showDialog(UGCKitImpl.getAppContext(), getResources().getString(R.string.tc_video_preprocess_activity_edit_failed), getResources().getString(R.string.ugckit_does_not_support_android_version_below_4_3), null);
        } else {
            // 设置视频基本信息
            VideoEditerSDK.getInstance().initSDK();
            VideoEditerSDK.getInstance().getEditer().setVideoPath(videoPath);
            VideoEditerSDK.getInstance().setTXVideoInfo(info);
            VideoEditerSDK.getInstance().setCutterStartTime(0, info.duration);

            ProcessKit.getInstance().setOnUpdateUIListener(new OnUpdateUIListener() {
                @Override
                public void onUIProgress(float progress) {
                    mProgressFragmentUtil.updateGenerateProgress((int) (progress * 100));
                }

                @Override
                public void onUIComplete(int retCode, String descMsg) {
                    // 更新UI控件
                    mProgressFragmentUtil.dismissLoadingProgress();
                    if (mOnRecordListener != null) {
                        UGCKitResult ugcKitResult = new UGCKitResult();
                        ugcKitResult.errorCode = retCode;
                        ugcKitResult.descMsg = descMsg;
                        mOnRecordListener.onRecordCompleted(ugcKitResult);
                    }
                }

                @Override
                public void onUICancel() {
                    // 更新Activity
                    if (mOnRecordListener != null) {
                        mOnRecordListener.onRecordCanceled();
                    }
                }
            });
            // 开始视频预处理
            ProcessKit.getInstance().startProcess();
        }
    }

    @Override
    public void setConfig(UGCKitRecordConfig config) {
        VideoRecordSDK.getInstance().setConfig(config);
        // 初始化最大/最小视频录制时长
        getRecordBottomLayout().initDuration();
        // 设置默认的录制模式
//        getRecordBottomLayout().getRecordButton().setCurrentRecordMode(UGCKitRecordConfig.getInstance().mRecordMode);
    }

    @Override
    public void setEditVideoFlag(boolean enable) {
        UGCKitRecordConfig.getInstance().mIsNeedEdit = enable;
    }

}
