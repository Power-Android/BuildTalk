package com.bjjy.buildtalk.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonInfoActivity extends BaseActivity<PersonInfoPresenter> implements PersonInfoContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.face_iv)
    CircleImageView mFaceIv;
    @BindView(R.id.person_page)
    RelativeLayout mPersonPage;
    @BindView(R.id.verify_tv)
    TextView mVerifyTv;
    @BindView(R.id.master_verify)
    RelativeLayout mMasterVerify;
    @BindView(R.id.bg_rl)
    RelativeLayout mBgRl;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.name_rl)
    RelativeLayout mNameRl;
    @BindView(R.id.phone_tv)
    TextView mPhoneTv;
    @BindView(R.id.phone_rl)
    RelativeLayout mPhoneRl;
    private User mUser;
    private Intent mIntent;
    private BaseDialog mVerifyDialog;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(RefreshEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.INFO_REFRESH)) {
            initEventAndData();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_info;
    }

    @Override
    protected void initView() {
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarTitle.setText(R.string.person_info);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initEventAndData() {
        mUser = mPresenter.mDataManager.getUser();
        Glide.with(this).load(mUser.getHeadImage()).into(mFaceIv);
        mNameTv.setText(mUser.getNickName());
        mPhoneTv.setText(mUser.getMobile());
        if (TextUtils.equals("1", mUser.getUser_type())){
            mVerifyTv.setText("未认证");
        }else {
            mVerifyTv.setText("已认证");
        }
//        showVerifyDialog();
    }

    private void showVerifyDialog() {
        mVerifyDialog = new BaseDialog.Builder(this)
                .setViewId(R.layout.dialog_master_verify_layout)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.nomal_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx((int)getResources().getDimension(R.dimen.dp_300), (int)getResources().getDimension(R.dimen.dp_345))
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.close_iv, v -> mVerifyDialog.dismiss())
                .addViewOnClickListener(R.id.angin_tv, v -> {
                    startActivity(new Intent(PersonInfoActivity.this, MasterVerifyActivity.class));
                    mVerifyDialog.dismiss();
                })
                .builder();
        mVerifyDialog.show();
    }

    @OnClick({R.id.person_page, R.id.master_verify, R.id.bg_rl, R.id.name_rl, R.id.phone_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.person_page:
                mIntent = new Intent(this, SetPictureActivity.class);
                mIntent.putExtra("pic", mUser.getHeadImage());
                mIntent.putExtra(SetPictureActivity.TAG,SetPictureActivity.FACE);
                startActivity(mIntent);
                break;
            case R.id.master_verify:
//                mIntent = new Intent(this, MasterVerifyActivity.class);
//                startActivity(mIntent);
                break;
            case R.id.bg_rl:
                mIntent = new Intent(this, SetPictureActivity.class);
                mIntent.putExtra("pic", mUser.getBg_pic());
                mIntent.putExtra(SetPictureActivity.TAG,SetPictureActivity.BACKGROUND);
                startActivity(mIntent);
                break;
            case R.id.name_rl:
                mIntent = new Intent(this, EditNameActivity.class);
                mIntent.putExtra("type", "name");
                mIntent.putExtra("name", mUser.getNickName());
                startActivity(mIntent);
                break;
            case R.id.phone_rl:
                mIntent = new Intent(this, BindPhoneActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
