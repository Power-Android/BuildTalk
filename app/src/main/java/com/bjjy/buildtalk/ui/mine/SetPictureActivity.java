package com.bjjy.buildtalk.ui.mine;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SetPictureActivity extends BaseActivity<SetPicturePresenter> implements SetPictureContract.View {

    public static String TAG = "TAG";
    public static String FACE = "FACE";
    public static String BACKGROUND = "BACKGROUND";

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.image_view)
    ImageView mImageView;
    @BindView(R.id.photo_tv)
    TextView mPhotoTv;
    @BindView(R.id.camera_tv)
    TextView mCameraTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MultipartBody.Part mFile;
    private String type;
    private String mTag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_picture;
    }

    @Override
    protected void initView() {
        mTag = getIntent().getStringExtra(TAG);
        String pic = getIntent().getStringExtra("pic");
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        if (TextUtils.equals(mTag, FACE)) {
            mToolbarTitle.setText("设置个人头像");
            type = "2";
        } else {
            mToolbarTitle.setText("设置背景");
            type = "3";
        }
        Glide.with(this).load(pic).into(mImageView);
    }

    @Override
    protected void initEventAndData() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(SetPictureActivity.this);
                } else {
                    Toast.makeText(SetPictureActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.photo_tv, R.id.camera_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.photo_tv:
                requestPhoto();
                break;
            case R.id.camera_tv:
                requestCamera();
                break;
        }
    }

    private void requestPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .withAspectRatio(1,1)
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void requestCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .withAspectRatio(1,1)
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                String image = "";
                if (localMedia.get(0).isCompressed()){
                    image = localMedia.get(0).getCompressPath();
                }else {
                    image = localMedia.get(0).getPath();
                }
                if (!TextUtils.isEmpty(image)) {
                    File file = new File(image);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    mFile = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    mPresenter.upload(mFile, type);
                }
            }
        }
    }

    @Override
    public void handlerUpData(String picUrl) {
        if (TextUtils.equals(mTag, FACE)) {
            ToastUtils.showCollect("头像更新成功", getResources().getDrawable(R.drawable.collect_success_icon));
        } else {
            ToastUtils.showCollect("背景更新成功", getResources().getDrawable(R.drawable.collect_success_icon));
        }
        Glide.with(this).load(picUrl).into(mImageView);
        EventBus.getDefault().post(new RefreshEvent(Constants.INFO_REFRESH));
    }

}
