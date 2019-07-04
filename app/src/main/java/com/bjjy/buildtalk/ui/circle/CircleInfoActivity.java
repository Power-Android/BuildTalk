package com.bjjy.buildtalk.ui.circle;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.entity.CircleMasterInfoEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CircleInfoActivity extends BaseActivity<CircleInfoPresenter> implements CircleInfoContract.View {

    @BindView(R.id.bg_iv)
    ImageView mBgIv;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.share_iv)
    ImageView mShareIv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.view1)
    View mView1;
    @BindView(R.id.create_date_tv)
    TextView mCreateDateTv;
    @BindView(R.id.theme_num_tv)
    TextView mThemeNumTv;
    @BindView(R.id.member_num_tv)
    TextView mMemberNumTv;
    @BindView(R.id.info_rl)
    RelativeLayout mInfoRl;
    @BindView(R.id.face_iv)
    CircleImageView mFaceIv;
    @BindView(R.id.image1)
    CircleImageView mImage1;
    @BindView(R.id.image2)
    CircleImageView mImage2;
    @BindView(R.id.image3)
    CircleImageView mImage3;
    @BindView(R.id.member_rl)
    RelativeLayout mMemberRl;
    @BindView(R.id.card_rl)
    RelativeLayout mCardRl;
    @BindView(R.id.data_rl)
    RelativeLayout mDataRl;
    @BindView(R.id.quit_tv)
    TextView mQuitTv;
    private String mCircle_id;
    private String mOperate_user;
    private CircleMasterInfoEntity mMasterInfoEntity;
    private BaseDialog mQuitDialog;
    private Intent mIntent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_info;
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeStatusBar(this, true, false);
        mCircle_id = getIntent().getStringExtra("circle_id");
        mOperate_user = getIntent().getStringExtra("operate_user");
    }

    @Override
    protected void initEventAndData() {
        mPresenter.masterInfo(mCircle_id, mOperate_user);
    }

    @Override
    public void handlerMasterInfo(CircleMasterInfoEntity masterInfoEntity) {
        this.mMasterInfoEntity = masterInfoEntity;
        Glide.with(this).load(masterInfoEntity.getCircle_image().getPic_url()).into(mBgIv);
        Glide.with(this).load(masterInfoEntity.getMaster_pic()).into(mFaceIv);
        if (masterInfoEntity.getIs_circleMaster().equals("1") && Integer.parseInt(masterInfoEntity.getCountUser()) > 1) {
            mQuitTv.setVisibility(View.GONE);
        }
        mTitleTv.setText(masterInfoEntity.getCircle_name());
        mNameTv.setText("圈主：" + masterInfoEntity.getName());
        mCreateDateTv.setText(masterInfoEntity.getCreate_day());
        mThemeNumTv.setText(masterInfoEntity.getCountTheme());
        mMemberNumTv.setText(masterInfoEntity.getCountUser());
        List<String> user_image = masterInfoEntity.getUser_image();
        for (int i = 0; i < user_image.size(); i++) {
            if (user_image.size() == 1) {
                Glide.with(this).load(user_image.get(0)).into(mImage3);
            } else if (user_image.size() == 2) {
                Glide.with(this).load(user_image.get(0)).into(mImage3);
                Glide.with(this).load(user_image.get(1)).into(mImage2);
            } else {
                Glide.with(this).load(user_image.get(0)).into(mImage3);
                Glide.with(this).load(user_image.get(1)).into(mImage2);
                Glide.with(this).load(user_image.get(2)).into(mImage1);
            }
        }
    }

    @OnClick({R.id.back_iv, R.id.share_iv, R.id.member_rl, R.id.card_rl, R.id.data_rl, R.id.quit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.share_iv:
                break;
            case R.id.member_rl:
                mIntent = new Intent(this, CircleMemberActivity.class);
                mIntent.putExtra("circle_id", mMasterInfoEntity.getCircle_id());
                startActivity(mIntent);
                break;
            case R.id.card_rl:
                mIntent = new Intent(this, MyCardActivity.class);
                mIntent.putExtra("circle_id", mMasterInfoEntity.getCircle_id());
                startActivity(mIntent);
                break;
            case R.id.data_rl:
                mIntent = new Intent(this, CircleDataActivity.class);
                mIntent.putExtra("circle_id", mMasterInfoEntity.getCircle_id());
                startActivity(mIntent);
                break;
            case R.id.quit_tv:
                showQuitDialog();
                break;
        }
    }

    private void showQuitDialog() {
        mQuitDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mQuitDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    mPresenter.quitCircle(mMasterInfoEntity.getCircle_id());
                    mQuitDialog.dismiss();
                })
                .builder();
        mQuitDialog.show();
    }

    @Override
    public void handlerQuitCircle(IEntity iEntity) {
        EventBus.getDefault().post(new RefreshEvent(Constants.TOPTIC_REFRESH_ALL));
        finish();
    }
}
