package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.CircleTagEntity;
import com.bjjy.buildtalk.utils.KeyboardUtils;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CreateCircleActivity extends BaseActivity<CreateCirclePresenter> implements CreateCircleContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_circle;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText("免费创建圈子");
        mToolbarRightTitle.setText("完成");
        mToolbarRightTitle.setOnClickListener(v -> {
            finish();
        });
        mCircleIv.setOnClickListener(v -> {
            requestPhoto();
        });
    }

    private void requestPhoto() {
        // 进入相册 以下是例子：用不到的api可以不写
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
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void initEventAndData() {
        List<CircleTagEntity> list = new ArrayList<>();
        list.add(new CircleTagEntity("BIM", false));
        list.add(new CircleTagEntity("装配式", false));
        list.add(new CircleTagEntity("钢结构", false));
        list.add(new CircleTagEntity("建筑产业工业互联网", true));
        TagAdapter<CircleTagEntity> tagAdapter = new TagAdapter<CircleTagEntity>(list) {
            @Override
            public View getView(FlowLayout parent, int position, CircleTagEntity circleTagEntity) {
                View view = LayoutInflater.from(CreateCircleActivity.this)
                        .inflate(R.layout.flow_create_circle, parent, false);
                TextView tv = view.findViewById(R.id.tag_tv);
                ImageView delete = view.findViewById(R.id.tag_delete);
                tv.setText(circleTagEntity.getContent());
                if (list.get(position).isSelected()){
                    tv.setBackground(getResources().getDrawable(R.drawable.shape_create_circle_sel));
                    tv.setTextColor(getResources().getColor(R.color.white));
                }else {
                    tv.setBackground(getResources().getDrawable(R.drawable.shape_create_circle_def));
                    tv.setTextColor(getResources().getColor(R.color.text_mid));
                }
                if (list.get(position).isCustom()){
                    delete.setVisibility(View.VISIBLE);
                }else {
                    delete.setVisibility(View.GONE);
                }
                delete.setOnClickListener(v -> {
                    list.remove(position);
                    if (list.size() < 5){
                        mAddEt.setVisibility(View.VISIBLE);
                    }
                    notifyDataChanged();
                });
                return view;
            }

            @Override
            public void onSelected(int position, View view) {
                TextView tv = view.findViewById(R.id.tag_tv);
                ImageView delete = view.findViewById(R.id.tag_delete);
                tv.setBackground(getResources().getDrawable(R.drawable.shape_create_circle_sel));
                tv.setTextColor(getResources().getColor(R.color.white));
                if (list.get(position).isCustom()){
                    delete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void unSelected(int position, View view) {
                TextView tv = view.findViewById(R.id.tag_tv);
                ImageView delete = view.findViewById(R.id.tag_delete);
                tv.setBackground(getResources().getDrawable(R.drawable.shape_create_circle_def));
                tv.setTextColor(getResources().getColor(R.color.text_mid));
                delete.setVisibility(View.GONE);
            }
        };
        mFlowLayout.setAdapter(tagAdapter);

        mFlowLayout.setOnTagClickListener((view, position, parent) -> {
            TextView tv = view.findViewById(R.id.tag_tv);
            if (tv.isSelected()) {
                list.get(position).setSelected(false);
                tv.setSelected(false);
            } else {
                list.get(position).setSelected(true);
                tv.setSelected(true);
            }
            return true;
        });

        mAddEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                if (TextUtils.isEmpty(v.getText().toString().trim())){
                    ToastUtils.showShort("请输入标签内容");
                    return false;
                }
                list.add(new CircleTagEntity(v.getText().toString().trim(), true, true));
                tagAdapter.notifyDataChanged();
                mAddEt.getText().clear();
                KeyboardUtils.hideSoftInput(CreateCircleActivity.this);
                if (list.size() == 5){
                    mAddEt.setVisibility(View.GONE);
                }
            }
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                String image = localMedia.get(0).getPath();
                Glide.with(this).load(image).into(mCircleIv);
            }
        }
    }
}
