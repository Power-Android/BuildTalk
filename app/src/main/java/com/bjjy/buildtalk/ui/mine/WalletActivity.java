package com.bjjy.buildtalk.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity<WalletPresenter> implements WalletContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.money_tv)
    TextView mMoneyTv;
    @BindView(R.id.with_draw_tv)
    TextView mWithDrawTv;
    @BindView(R.id.wallet_rl)
    RelativeLayout mWalletRl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initView() {
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarTitle.setText("我的钱包");
    }

    @Override
    protected void initEventAndData() {
        mPresenter.myWallet();
    }

    @Override
    public void handlerWallet(String s) {
        mMoneyTv.setText(s);
    }

    @OnClick({R.id.with_draw_tv, R.id.wallet_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.with_draw_tv:
                ToastUtils.showShort("功能暂未开通");
                break;
            case R.id.wallet_rl:
                startActivity(new Intent(this, TransactionActivity.class));
                break;
        }
    }
}
