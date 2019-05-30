package com.bjjy.buildtalk.ui.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;

public class PersonPageActivity extends BaseActivity<PersonPagePresenter> implements PersonPageContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_page;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEventAndData() {

    }
}
