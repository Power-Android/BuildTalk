package com.bjjy.buildtalk.ui.circle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.MyCardEntity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyCardActivity extends BaseActivity<MyCardPresenter> implements MyCardContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bg_iv)
    ImageView mBgIv;
    @BindView(R.id.face_iv)
    CircleImageView mFaceIv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.date_tv)
    TextView mDateTv;
    @BindView(R.id.theme_tv)
    TextView mThemeTv;
    @BindView(R.id.praise_tv)
    TextView mPraiseTv;
    @BindView(R.id.qrcode_iv)
    ImageView mQrcodeIv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.wechat_tv)
    TextView mWechatTv;
    @BindView(R.id.wechat_circle_tv)
    TextView mWechatCircleTv;
    private String mCircle_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_card;
    }

    @Override
    protected void initView() {
        mCircle_id = getIntent().getStringExtra("circle_id");
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText("我的名片");
    }

    @Override
    protected void initEventAndData() {
        mPresenter.myCard(mCircle_id);
    }

    @Override
    public void handlerMyCard(MyCardEntity myCardEntity) {
        Glide.with(this).load(myCardEntity.getCircle_image().getPic_url()).into(mBgIv);
        Glide.with(this).load(myCardEntity.getHeadImage()).into(mFaceIv);
        Glide.with(this).load(myCardEntity.getCircle_code()).into(mQrcodeIv);
        mNameTv.setText(myCardEntity.getName());
        mDateTv.setText(myCardEntity.getJoin_day());
        mThemeTv.setText(myCardEntity.getCountTheme());
        mPraiseTv.setText(myCardEntity.getCountpraise());
        mTitleTv.setText(myCardEntity.getCircle_name());
    }

    @OnClick({R.id.wechat_tv, R.id.wechat_circle_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wechat_tv:
                break;
            case R.id.wechat_circle_tv:
                break;
        }
    }
}
