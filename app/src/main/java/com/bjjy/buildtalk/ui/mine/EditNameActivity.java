package com.bjjy.buildtalk.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.ClearEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNameActivity extends BaseActivity<EditNamePresenter> implements EditNameContract.View, TextView.OnEditorActionListener {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.name_et)
    ClearEditText mNameEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_name;
    }

    @Override
    protected void initView() {
        String name = getIntent().getStringExtra("name");
        mToolbarLeftTitle.setText("取消");
        mToolbarTitle.setText("修改名字");
        mNameEt.setText(name);
        mNameEt.setOnEditorActionListener(this);
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (TextUtils.isEmpty(mNameEt.getText().toString().trim())) {
                ToastUtils.showShort("请输入昵称");
                return true;
            }
            mPresenter.editName(mNameEt.getText().toString().trim());
            KeyboardUtils.hideSoftInput(this);
        }
        return false;
    }

    @Override
    public void handlerUpData(String nickName) {
        EventBus.getDefault().post(new RefreshEvent(Constants.INFO_REFRESH));
        finish();
    }
}
