package com.bjjy.buildtalk.ui.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.ui.main.LoginActivity;
import com.bjjy.buildtalk.ui.talk.CircleManDetailActivity;
import com.bjjy.buildtalk.ui.talk.FansFocusActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(RefreshEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.INFO_REFRESH)) {
            initEventAndData();
        }
    }

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeStatusBar(this, true, false);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtils.changeStatusBar(this, true, false);
        } else {
            StatusBarUtils.changeStatusBar(this, false, true);
        }
    }

    @Override
    protected void initEventAndData() {
        if (mPresenter.mDataManager.getLoginStatus()){
            User user = mPresenter.mDataManager.getUser();
            Glide.with(mContext).load(user.getHeadImage()).apply(new RequestOptions().error(R.drawable.moren_face)).into(mFaceIv);
            mNameTv.setText(user.getNickName());
            mPhoneTv.setText(user.getMobile());
        }else {
            Glide.with(mContext).load(R.drawable.moren_face).into(mFaceIv);
            mNameTv.setText(R.string.login);
            mPhoneTv.setText(R.string.login_more_look);
        }
//        mPresenter.userInfo(user.getUser_id());
    }

    @OnClick({R.id.info_iv, R.id.wallet_rl, R.id.set_rl, R.id.help_rl, R.id.service_rl, R.id.name_tv, R.id.face_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.info_iv:
                LoginHelper.login(mContext, mPresenter.mDataManager, () -> startActivity(new Intent(mContext, PersonInfoActivity.class)));
                break;
            case R.id.wallet_rl:
                LoginHelper.login(mContext, mPresenter.mDataManager, () -> startActivity(new Intent(mContext, WalletActivity.class)));
                break;
            case R.id.set_rl:
                LoginHelper.login(mContext, mPresenter.mDataManager, () -> startActivity(new Intent(mContext, SettingActivity.class)));
                break;
            case R.id.help_rl:
                LoginHelper.login(mContext, mPresenter.mDataManager, () -> startActivity(new Intent(mContext, FeedBackActivity.class)));
                break;
            case R.id.service_rl:
                startActivity(new Intent(mContext, ServiceActivity.class));
                break;
            case R.id.name_tv:
                if (!mPresenter.mDataManager.getLoginStatus())
                    startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.face_iv:
                LoginHelper.login(mContext, mPresenter.mDataManager, () -> {
                    if (TextUtils.equals("1", mPresenter.mDataManager.getUser().getUser_type())){
                        Intent intent = new Intent(mContext, CircleManDetailActivity.class);
                        intent.putExtra("user_id", mPresenter.mDataManager.getUser().getUser_id());
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(mContext, MasterDetailActivity.class);
                        intent.putExtra("user_id", mPresenter.mDataManager.getUser().getUser_id());
                        startActivity(intent);
                    }
                });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
