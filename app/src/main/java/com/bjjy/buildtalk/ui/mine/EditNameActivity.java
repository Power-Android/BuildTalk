package com.bjjy.buildtalk.ui.mine;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.ClearEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class EditNameActivity extends BaseActivity<EditNamePresenter> implements EditNameContract.View, View.OnClickListener {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.name_et)
    ClearEditText mNameEt;
    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    @BindView(R.id.content_et)
    EditText mContentEt;
    @BindView(R.id.edit_num_tv)
    TextView mEditNumTv;
    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;
    @BindView(R.id.relativelayout)
    RelativeLayout mRelativelayout;
    private String mType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_name;
    }

    @Override
    protected void initView() {
        mType = getIntent().getStringExtra("type");
        if ("name".equals(mType)) {
            mToolbarTitle.setText("修改名字");
        } else if ("zhicheng".equals(mType)) {
            mToolbarTitle.setText("职称");
        } else {
            mRelativelayout.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);
            mToolbarTitle.setText("个人介绍");
        }
        String name = getIntent().getStringExtra("name");
        mToolbarBack.setVisibility(View.GONE);
        mToolbarLeftTitle.setText("取消");
        mToolbarRightTitle.setText("完成");
        mNameEt.setText(name);
//        mNameEt.setOnEditorActionListener(this);
        mToolbarRightTitle.setOnClickListener(this);

    }

    @Override
    protected void initEventAndData() {
        mContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 2000) {
                    s.delete(2000, s.length());
                }
                int num = s.length();
                mEditNumTv.setText(String.valueOf(num) + "/2000个字");
            }
        });
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (actionId == EditorInfo.IME_ACTION_DONE) {
//            if (TextUtils.isEmpty(mNameEt.getText().toString().trim())) {
//                if (TextUtils.equals("name", mType)) {
//                    ToastUtils.showShort("请输入昵称");
//                } else if (TextUtils.equals("zhicheng", mType)) {
//                    ToastUtils.showShort("请输入职称");
//                }else {
//                    ToastUtils.showShort("请输入个人介绍");
//                }
//                return true;
//            }
//
//            KeyboardUtils.hideSoftInput(this);
//        }
//        return false;
//    }

    @Override
    public void handlerUpData() {
        EventBus.getDefault().post(new RefreshEvent(Constants.INFO_REFRESH));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.equals("name", mType)) {
            if (TextUtils.isEmpty(mNameEt.getText().toString().trim())){
                ToastUtils.showShort("请输入昵称");
                return;
            }
            mPresenter.editName(mNameEt.getText().toString().trim());
        }
        if (TextUtils.equals("zhicheng", mType)) {
            if (TextUtils.isEmpty(mNameEt.getText().toString().trim())){
                ToastUtils.showShort("请输入职称");
                return;
            }
        }
        if (TextUtils.equals("jieshao", mType)) {
            if (TextUtils.isEmpty(mContentEt.getText().toString().trim())){
                ToastUtils.showShort("请输入个人介绍");
                return;
            }
        }
        if (TextUtils.equals("name", mType)) {
            mPresenter.editName(mNameEt.getText().toString().trim());
        } else if (TextUtils.equals("zhicheng", mType)) {
            mPresenter.editZhicheng(mNameEt.getText().toString().trim());
        }else {
            mPresenter.editJieshao(mContentEt.getText().toString().trim());
        }
    }
}
