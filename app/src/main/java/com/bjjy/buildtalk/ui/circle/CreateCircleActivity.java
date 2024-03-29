package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.entity.CircleTagEntity;
import com.bjjy.buildtalk.entity.SearchCircleInfoEntity;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateCircleActivity extends BaseActivity<CreateCirclePresenter> implements CreateCircleContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;
    @BindView(R.id.circle_iv)
    RoundedImageView mCircleIv;
    @BindView(R.id.circle_title_et)
    EditText mCircleTitleEt;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.edit_text)
    EditText mEditText;
    @BindView(R.id.add_et)
    EditText mAddEt;
    private MultipartBody.Part mFile;
    private List<CircleTagEntity> mTagList = new ArrayList<>();
    private List<CircleTagEntity> mSelList = new ArrayList<>();
    private TagAdapter mTagAdapter;
    private String mType;
    private String mCircle_id;
    private List<String> mCircle_tags = new ArrayList<>();
    private String mPic_url;
    private boolean isMax = false;
    private long lastTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_circle;
    }

    @Override
    protected void initView() {
        mType = getIntent().getStringExtra("type");
        mCircle_id = getIntent().getStringExtra("circle_id");
        mToolbarBack.setOnClickListener(v -> finish());
        if (TextUtils.isEmpty(mType)) {
            mToolbarTitle.setText("免费创建圈子");
        } else {
            mToolbarTitle.setText("圈子资料");
        }
        mToolbarRightTitle.setText("完成");
        mToolbarRightTitle.setOnClickListener(v -> {
            //常规方式
            long currTime = System.currentTimeMillis();
            if (currTime - lastTime > 2000) {
                createCircle();
            }
            lastTime = currTime;
        });
        mCircleIv.setOnClickListener(v -> {
            requestPhoto();
        });
    }

    @Override
    protected void initEventAndData() {
        mPresenter.circleTags();
    }

    @Override
    public void handlerTagList(List<CircleTagEntity> circleTagEntityList) {
        this.mTagList = circleTagEntityList;
        initFlowLayout();
    }

    private void initFlowLayout() {
        mTagAdapter = new TagAdapter<CircleTagEntity>(mTagList) {
            @Override
            public View getView(FlowLayout parent, int position, CircleTagEntity circleTagEntity) {
                View view = LayoutInflater.from(CreateCircleActivity.this)
                        .inflate(R.layout.flow_create_circle, parent, false);
                TextView tv = view.findViewById(R.id.tag_tv);
                ImageView delete = view.findViewById(R.id.tag_delete);
                tv.setText(circleTagEntity.getTag_name());
                if (circleTagEntity.isSelected()) {
                    tv.setBackground(getResources().getDrawable(R.drawable.shape_create_circle_sel));
                    tv.setTextColor(getResources().getColor(R.color.white));
                } else {
                    tv.setBackground(getResources().getDrawable(R.drawable.shape_create_circle_def));
                    tv.setTextColor(getResources().getColor(R.color.text_mid));
                }
                if (circleTagEntity.isCustom()) {
                    delete.setVisibility(View.VISIBLE);
                } else {
                    delete.setVisibility(View.GONE);
                }
                delete.setOnClickListener(v -> {
                    mTagList.remove(position);
                    vervify();
                    notifyDataChanged();
                });
                return view;
            }
        };
        mFlowLayout.setAdapter(mTagAdapter);

        mFlowLayout.setOnTagClickListener((view, position, parent) -> {
            if (mTagList.get(position).isSelected()) {
                mTagList.get(position).setSelected(false);
            } else {
                if (isMax) {
                    ToastUtils.showShort("最多选中5个标签");
                    return true;
                }
                mTagList.get(position).setSelected(true);
            }
            mTagAdapter.notifyDataChanged();
            vervify();
            return true;
        });

        mAddEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (TextUtils.isEmpty(v.getText().toString().trim())) {
                    ToastUtils.showShort("请输入标签内容");
                    return false;
                }
                for (int i = 0; i < mTagList.size(); i++) {
                    if (TextUtils.equals(v.getText().toString(), mTagList.get(i).getTag_name())) {
                        ToastUtils.showShort("不能重复添加标签");
                        return true;
                    }
                }
                mTagList.add(new CircleTagEntity(v.getText().toString().trim(), true, true));
                mTagAdapter.notifyDataChanged();
                mAddEt.getText().clear();
                KeyboardUtils.hideSoftInput(CreateCircleActivity.this);
            }
            vervify();
            return true;
        });
        if (!TextUtils.isEmpty(mType)) {
            mPresenter.searchCircleInfo(mCircle_id);
        } else {
            mTagAdapter.notifyDataChanged();
        }
    }

    private void vervify() {
        mSelList.clear();
        int a = 0;
        for (int i = 0; i < mTagList.size(); i++) {
            if (mTagList.get(i).isSelected()) {
                mSelList.add(mTagList.get(i));
            }
            if (mTagList.get(i).isCustom()) {
                a++;
            }
        }
        if (mSelList.size() >= 5) {
            isMax = true;
            mAddEt.setVisibility(View.GONE);
        } else {
            isMax = false;
            mAddEt.setVisibility(View.VISIBLE);
        }
        if (a >= 5) {
            mAddEt.setVisibility(View.GONE);
        }
    }

    private void createCircle() {

        if (TextUtils.isEmpty(mCircleTitleEt.getText().toString())) {
            ToastUtils.showShort("请填写圈子名称");
            return;
        }
        if (mSelList.size() == 0) {
            ToastUtils.showShort("请选择行业标签");
            return;
        }
        if (mFile == null) {
            if (!TextUtils.isEmpty(mPic_url)) {
                mPresenter.updateCircleInfo(mCircle_id, mPic_url, mCircleTitleEt.getText().toString(),
                        StringUtils.listToString(mSelList, ','), mEditText.getText().toString().trim());
            } else {
                ToastUtils.showShort("请添加圈子封面");
            }
            return;
        }
        if (TextUtils.isEmpty(mType)) {
            mPresenter.upload(false, mCircle_id, mFile, mCircleTitleEt.getText().toString(),
                    StringUtils.listToString(mSelList, ','), mEditText.getText().toString().trim());
        } else {
            if (mFile != null) {
                mPresenter.upload(true, mCircle_id, mFile, mCircleTitleEt.getText().toString(),
                        StringUtils.listToString(mSelList, ','), mEditText.getText().toString().trim());
            } else {
                mPresenter.updateCircleInfo(mCircle_id, mPic_url, mCircleTitleEt.getText().toString(),
                        StringUtils.listToString(mSelList, ','), mEditText.getText().toString().trim());
            }
        }
    }

    @Override
    public void handlerCreateSuccess(String iEntity) {
        EventBus.getDefault().post(new RefreshEvent(Constants.TOPTIC_REFRESH_ALL));
        Intent intent = new Intent(this, TopticCircleActivity.class);
        intent.putExtra("circle_id", iEntity);
        startActivity(intent);
        finish();
    }

    @Override
    public void handlerUpdateCircleInfo(String iEntity) {
        EventBus.getDefault().post(new RefreshEvent(Constants.TOPTIC_REFRESH_ALL));
        finish();
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
                .withAspectRatio(1, 1)
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
                if (localMedia.get(0).isCompressed()) {
                    image = localMedia.get(0).getCompressPath();
                } else {
                    image = localMedia.get(0).getPath();
                }
                Glide.with(this).load(image).into(mCircleIv);
                if (!TextUtils.isEmpty(image)) {
                    File file = new File(image);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    mFile = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                }
            }
        }
    }

    @Override
    public void handlerSearchCircleInfo(SearchCircleInfoEntity infoEntity) {
        mPic_url = infoEntity.getCircle_image().getPic_url();
        Glide.with(this).load(infoEntity.getCircle_image().getPic_url()).into(mCircleIv);
        mCircleTitleEt.setText(infoEntity.getCircle_name());
        mCircleTitleEt.setSelection(infoEntity.getCircle_name().length());//将光标追踪到内容的最后
        mEditText.setText(infoEntity.getCircle_desc());
        mEditText.setSelection(infoEntity.getCircle_desc().length());//将光标追踪到内容的最后

        String circle_tags = infoEntity.getCircle_tags();
        mCircle_tags = Arrays.asList(circle_tags.split(","));
        for (int i = 0; i < mTagList.size(); i++) {
            for (int j = 0; j < mCircle_tags.size(); j++) {
                if (mTagList.size() > 0 && mCircle_tags.size() > 0 && mTagList.get(i).getTag_name().equals(mCircle_tags.get(j))) {
                    mTagList.get(i).setSelected(true);
                }
            }
        }
        for (int i = 0; i < mCircle_tags.size(); i++) {
            mTagList.add(new CircleTagEntity(mCircle_tags.get(i), true, true));
        }
        removeDuplicate(mTagList);
        vervify();
        mTagAdapter.notifyDataChanged();
    }

    public static List removeDuplicate(List<CircleTagEntity> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getTag_name().equals(list.get(i).getTag_name())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
