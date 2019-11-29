package com.bjjy.buildtalk.ui.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    @BindView(R.id.view3)
    ImageView mImageView;
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
        if (mPresenter != null) {
            mPresenter.userInfo();
        }
    }

    @Override
    public void handlerUser(User user) {
        mUser = mPresenter.mDataManager.getUser();
        Glide.with(this).load(user.getHeadImage()).into(mFaceIv);
        mNameTv.setText(user.getNickName());
        mPhoneTv.setText(user.getMobile());
        if (TextUtils.equals("1", user.getUser_type())){
            mVerifyTv.setText("未认证");
            mVerifyTv.setBackground(getResources().getDrawable(R.drawable.shape_wrz_2radius));
            Drawable drawable = getResources().getDrawable(R.drawable.def_verify);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mVerifyTv.setCompoundDrawables(drawable, null, null, null);
        }else {
            mImageView.setVisibility(View.INVISIBLE);
            mNameRl.setClickable(false);
            mVerifyTv.setText("已认证");
            mVerifyTv.setTextColor(getResources().getColor(R.color.oranger2));
            mVerifyTv.setBackground(getResources().getDrawable(R.drawable.shape_yrz_2radius));
            Drawable drawable = getResources().getDrawable(R.drawable.sel_verify);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mVerifyTv.setCompoundDrawables(drawable, null, null, null);
        }
    }

    private void showVerifyDialog() {
        mVerifyDialog = new BaseDialog.Builder(this)
                .setViewId(R.layout.dialog_master_verify_layout)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.nomal_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx((int)getResources().getDimension(R.dimen.dp_300), (int)getResources().getDimension(R.dimen.dp_472))
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.close_iv, v -> mVerifyDialog.dismiss())
                .addViewOnClickListener(R.id.angin_tv, v -> {
                    startActivity(new Intent(PersonInfoActivity.this, IDCardActivity.class));
                    mVerifyDialog.dismiss();
                })
                .builder();
        WebView webView = mVerifyDialog.getView(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        String url = "https://www.51jiantan.com/dkagreement";
        webView.loadUrl(url);
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
                if (TextUtils.equals("1",mUser.getUser_type())){
                    if (mPresenter.mDataManager.getVerifyRecordCount() > 0 ){
                        showVerifyDialog();
                    }else {
                        ToastUtils.showCollect("今日认证次数已上限", getResources().getDrawable(R.drawable.collect_cancle_icon));
                    }
                }else {
                    startActivity(new Intent(this, MasterVerifyActivity.class));
                }
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
        EventBus.getDefault().post(new RefreshEvent(Constants.INFO_REFRESH));
        EventBus.getDefault().unregister(this);
    }
}
