package com.bjjy.buildtalk.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonInfoActivity extends BaseActivity<PersonInfoPresenter> implements PersonInfoContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.face_iv)
    CircleImageView mFaceIv;
    @BindView(R.id.person_page)
    RelativeLayout mPersonPage;
    @BindView(R.id.verify_tv)
    TextView mVerifyTv;
    @BindView(R.id.master_verify)
    RelativeLayout mMasterVerify;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_info;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_left_black_icon);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbarTitle.setText(R.string.person_info);
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.person_page, R.id.master_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.person_page:
                startActivity(new Intent(this, PersonPageActivity.class));
                break;
            case R.id.master_verify:
                break;
        }
    }
}
