package com.bjjy.buildtalk.ui.home;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.ui.circle.PublishActivity;
import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.UGCKitVideoPublish;
import com.tencent.qcloud.ugckit.module.upload.TCVideoPublishKit;

public class VideoPublishActivity extends BaseActivity<VideoPublishPresenter> implements VideoPublishContract.View {
    private String mVideoPath = null;
    private String mCoverPath = null;
    private boolean mDisableCache;
    private UGCKitVideoPublish mUGCKitVideoPublish;

    @Override
    protected int getLayoutId() {
        initWindowParam();
        return R.layout.activity_video_publish;
    }

    @Override
    protected void initView() {
        mVideoPath = getIntent().getStringExtra(UGCKitConstants.VIDEO_PATH);
        mCoverPath = getIntent().getStringExtra(UGCKitConstants.VIDEO_COVERPATH);
        mDisableCache = getIntent().getBooleanExtra(UGCKitConstants.VIDEO_RECORD_NO_CACHE, false);
        mUGCKitVideoPublish = (UGCKitVideoPublish) findViewById(R.id.video_publish_layout);
    }

    @Override
    protected void initEventAndData() {
        /**
         * 设置发布视频的路径和封面
         */
        mUGCKitVideoPublish.setPublishPath(mVideoPath, mCoverPath);
        /**
         * 设置是否开启本地缓存，若关闭本地缓存，则发布完成后删除"已发布"的视频和封面
         */
        mUGCKitVideoPublish.setCacheEnable(mDisableCache);

        /**
         * 设置发布视频的监听器
         */
        mUGCKitVideoPublish.setOnPublishListener(new TCVideoPublishKit.OnPublishListener() {

            @Override
            public void onPublishCompleted(String videoId, String videoURL, String coverURL) {
                /**
                 * 发布完成，返回发布页面
                 */
                Intent intent = new Intent(VideoPublishActivity.this, PublishActivity.class);
                intent.putExtra("videoId", videoId);
                intent.putExtra("videoURL", videoURL);
                intent.putExtra("coverURL", coverURL);
                startActivity(intent);
                finish();
            }

            @Override
            public void onPublishCanceled() {
                /**
                 * 发布取消，退出发布页面
                 */
                finish();
            }
        });

        mPresenter.getSign();
    }

    @Override
    public void handlerSignSuccess(String sign) {
        mUGCKitVideoPublish.startPublish(sign);
    }

    private void initWindowParam() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUGCKitVideoPublish.release();
    }
}
