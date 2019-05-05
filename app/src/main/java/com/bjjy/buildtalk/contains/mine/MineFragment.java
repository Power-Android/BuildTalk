package com.bjjy.buildtalk.contains.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author power
 * @date 2019/4/26 4:34 PM
 * @project BuildTalk
 * @description:
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    @BindView(R.id.face_iv)
    CircleImageView mFaceIv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.phone_tv)
    TextView mPhoneTv;
    @BindView(R.id.info_iv)
    ImageView mInfoIv;
    @BindView(R.id.info_rl)
    RelativeLayout mInfoRl;
    @BindView(R.id.icon1)
    ImageView mIcon1;
    @BindView(R.id.wallet_tv)
    TextView mWalletTv;
    @BindView(R.id.wallet_rl)
    RelativeLayout mWalletRl;
    @BindView(R.id.set_rl)
    RelativeLayout mSetRl;
    @BindView(R.id.help_rl)
    RelativeLayout mHelpRl;
    @BindView(R.id.service_rl)
    RelativeLayout mServiceRl;
    Unbinder unbinder;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        changeStatusBar(true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            changeStatusBar(true);
        } else {
            changeStatusBar(false);
        }
    }

    @Override
    protected void initEventAndData() {

    }

    private void changeStatusBar(boolean isTransparent) {
        if (isTransparent) {
            ImmersionBar.with(this)
                    .fitsSystemWindows(false)
                    .transparentStatusBar()
                    .statusBarDarkFont(false, 0.2f)
                    .keyboardEnable(true)
                    .init();
        } else {
            ImmersionBar.with(this)
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true, 0.2f)
                    .keyboardEnable(true)
                    .init();
        }
    }

    @OnClick({R.id.info_iv, R.id.wallet_rl, R.id.set_rl, R.id.help_rl, R.id.service_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.info_iv:
                break;
            case R.id.wallet_rl:
                break;
            case R.id.set_rl:
                break;
            case R.id.help_rl:
                break;
            case R.id.service_rl:
                break;
        }
    }
}
