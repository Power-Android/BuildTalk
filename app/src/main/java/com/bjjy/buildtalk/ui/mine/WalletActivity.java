package com.bjjy.buildtalk.ui.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;

public class WalletActivity extends BaseActivity<WalletPresenter> implements WalletContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEventAndData() {

    }
}
