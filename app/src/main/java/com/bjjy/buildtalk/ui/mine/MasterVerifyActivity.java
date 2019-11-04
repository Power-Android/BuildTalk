package com.bjjy.buildtalk.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.weight.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MasterVerifyActivity extends BaseActivity<MasterVerifyPresenter> implements MasterVerifyContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.face_iv)
    CircleImageView mFaceIv;
    @BindView(R.id.face_rl)
    RelativeLayout mFaceRl;
    @BindView(R.id.pic_tv)
    TextView mPicTv;
    @BindView(R.id.card_pic_rl)
    RelativeLayout mCardPicRl;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.name_rl)
    RelativeLayout mNameRl;
    @BindView(R.id.card_tv)
    TextView mCardTv;
    @BindView(R.id.card_num_rl)
    RelativeLayout mCardNumRl;
    @BindView(R.id.date_tv)
    TextView mDateTv;
    @BindView(R.id.card_date_rl)
    RelativeLayout mCardDateRl;
    @BindView(R.id.zhicheng_tv)
    TextView mZhichengTv;
    @BindView(R.id.zhicheng_rl)
    RelativeLayout mZhichengRl;
    @BindView(R.id.jieshao_tv)
    TextView mJieshaoTv;
    @BindView(R.id.jieshao_rl)
    RelativeLayout mJieshaoRl;
    private Intent mIntent;
    private BaseDialog mVerifyDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_master_verify;
    }

    @Override
    protected void initView() {
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarTitle.setText("大咖认证");
        mToolbarRightTitle.setText("完成");
    }

    @Override
    protected void initEventAndData() {
        showVerifyDialog();
    }

    private void showVerifyDialog() {
        mVerifyDialog = new BaseDialog.Builder(this)
                .setViewId(R.layout.dialog_verify_fail)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setWidthHeightpx((int)getResources().getDimension(R.dimen.dp_300), (int)getResources().getDimension(R.dimen.dp_345))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.close_iv, v -> mVerifyDialog.dismiss())
                .builder();
        mVerifyDialog.show();
    }

    @OnClick({R.id.toolbar_right_title, R.id.face_rl, R.id.card_pic_rl, R.id.name_rl, R.id.card_num_rl, R.id.card_date_rl, R.id.zhicheng_rl, R.id.jieshao_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_title:
                finish();
                break;
            case R.id.face_rl:
                startActivity(new Intent(this, SetPictureActivity.class));
                break;
            case R.id.card_pic_rl:
                startActivity(new Intent(this, IDCardActivity.class));
                break;
            case R.id.name_rl:
                break;
            case R.id.card_num_rl:
                break;
            case R.id.card_date_rl:
                break;
            case R.id.zhicheng_rl:
                mIntent = new Intent(this, EditNameActivity.class);
                mIntent.putExtra("type", "zhicheng");
                startActivity(mIntent);
                break;
            case R.id.jieshao_rl:
                mIntent = new Intent(this, EditNameActivity.class);
                mIntent.putExtra("type", "jieshao");
                startActivity(mIntent);
                break;
        }
    }
}
