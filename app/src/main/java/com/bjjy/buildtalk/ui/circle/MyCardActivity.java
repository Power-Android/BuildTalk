package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.MyCardEntity;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

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
    @BindView(R.id.text)
    TextView mTextView;

    private String mCircle_id;
    private MyCardEntity mMyCardEntity;
    private String mUrl;

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
        this.mMyCardEntity = myCardEntity;
        mUrl = "https://jt.chinabim.com/share/#/card/" + myCardEntity.getUser_id() + "/" + myCardEntity.getCircle_id()+".jpg";
        Glide.with(this).load(myCardEntity.getCircle_image().getPic_url()).into(mBgIv);
        Glide.with(this).load(myCardEntity.getHeadImage()).into(mFaceIv);
        Glide.with(this).load(myCardEntity.getCircle_code()).into(mQrcodeIv);
        if (TextUtils.equals("1",myCardEntity.getIs_circleMaster())){
            mDateTv.setText(myCardEntity.getCreate_day());
            mTextView.setText("创建天数");
        }else {
            mDateTv.setText(myCardEntity.getJoin_day());
            mTextView.setText("加入天数");
        }
        mNameTv.setText(myCardEntity.getName());
        mThemeTv.setText(myCardEntity.getCountTheme());
        mPraiseTv.setText(myCardEntity.getCountpraise());
        mTitleTv.setText(myCardEntity.getCircle_name());
    }

    @OnClick({R.id.wechat_tv, R.id.wechat_circle_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wechat_tv:
                DialogUtils.shareWebUrl(mUrl, mMyCardEntity.getName()+"的名片", mMyCardEntity.getCircle_image().getPic_url(), mMyCardEntity.getCircle_name(), this, SHARE_MEDIA.WEIXIN);
                break;
            case R.id.wechat_circle_tv:
                DialogUtils.shareWebUrl(mUrl, mMyCardEntity.getName()+"的名片", mMyCardEntity.getCircle_image().getPic_url(), mMyCardEntity.getCircle_name(), this, SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
        }
    }
}
