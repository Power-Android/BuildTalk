package com.bjjy.buildtalk.ui.circle;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MyGridAdapter;
import com.bjjy.buildtalk.weight.NoScrollGridView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PublishActivity extends BaseActivity<PublishPresenter> implements PublishContarct.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.publish_tv)
    EditText mPublishTv;
    @BindView(R.id.grid_view)
    NoScrollGridView mGridView;
    @BindView(R.id.sel_pic_iv)
    ImageView mSelPicIv;
    @BindView(R.id.pic_rl)
    RelativeLayout mPicRl;

    private List<ThemeImageBean> list = new ArrayList<>();
    private MyGridAdapter mMyGridAdapter;
    private List<MultipartBody.Part> mParts = new ArrayList<>();
    private String mCircle_id;
    private ThemeInfoEntity.ThemeInfoBean mData;
    private boolean isEdit;
    private int mTheme_id = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    protected void initView() {
        mCircle_id = getIntent().getStringExtra("circle_id");
        mData = (ThemeInfoEntity.ThemeInfoBean) getIntent().getSerializableExtra("themeInfo");
        String circle_name = getIntent().getStringExtra("circle_name");
        if (mData != null) {
            isEdit = true;
            mTheme_id = mData.getTheme_id();
            mPublishTv.setText(mData.getTheme_content());
            if (mData.getTheme_image() != null) {
                list = mData.getTheme_image();
            }
        }
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            finish();
        });
        if (TextUtils.isEmpty(circle_name)) {
            mToolbarTitle.setText("发表");
        } else {
            mToolbarTitle.setText(circle_name);
        }
        mToolbarRightTitle.setText("完成");
        KeyboardUtils.registerSoftInputChangedListener(this, height -> {
            if (height == 0) {
                mPicRl.setVisibility(View.GONE);
            } else {
                mPicRl.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void initEventAndData() {
        mMyGridAdapter = new MyGridAdapter(list, true);
        mGridView.setAdapter(mMyGridAdapter);
        mMyGridAdapter.setDeleteCallBack(position -> {
            list.remove(position);
            mMyGridAdapter.notifyDataSetChanged();
        });
    }

    private void requestPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(9)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
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
                List<LocalMedia> mLocalMedia = PictureSelector.obtainMultipleResult(data);
                for (int i = 0; i < mLocalMedia.size(); i++) {
                    ThemeImageBean imageBean = new ThemeImageBean();
                    imageBean.setPic_url(mLocalMedia.get(i).getPath());
                    list.add(imageBean);
                    File file = new File(mLocalMedia.get(i).getPath());
                    RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);   //说明该文件为图片类型
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file[]", file.getName(), body);
                    mParts.add(part);
                }
                mMyGridAdapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick({R.id.toolbar_right_title, R.id.pic_rl})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_title:
                if (TextUtils.isEmpty(mPublishTv.getText().toString().trim()) & mParts == null) {
                    ToastUtils.showShort("请输入内容或图片");
                    return;
                }
                mPresenter.publishTheme(mCircle_id, mTheme_id , mPublishTv.getText().toString().trim(), mParts, isEdit, list);
                break;
            case R.id.pic_rl:
                requestPhoto();
                RxPermissions permissions = new RxPermissions(this);
                permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            PictureFileUtils.deleteCacheDirFile(PublishActivity.this);
                        } else {
                            Toast.makeText(PublishActivity.this,
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
                break;
        }

    }

    @Override
    public void handlerPublishSuccess(IEntity iEntity) {
        KeyboardUtils.hideSoftInput(this);
        EventBus.getDefault().post(new RefreshEvent(Constants.TOPTIC_REFRESH));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.unregisterSoftInputChangedListener(this);
    }
}
