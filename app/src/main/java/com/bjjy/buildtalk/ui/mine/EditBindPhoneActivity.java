package com.bjjy.buildtalk.ui.mine;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class EditBindPhoneActivity extends BaseActivity<EditBindPhonePresenter> implements EditBindPhoneContract.View {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.phone_et)
    EditText mPhoneEt;
    @BindView(R.id.verify_et)
    EditText mVerifyEt;
    @BindView(R.id.code_btn)
    Button mCodeBtn;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolBarBack;

    private CountDownTimer mTimer;
    private boolean isPhone;
    private boolean isCode;
    private String mType;
    private String mUnionid;
    private String mOpenid;
    private String mIconurl;
    private String mName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_bind_phone;
    }

    @Override
    protected void initView() {
        mType = getIntent().getStringExtra("type");
        mToolBarBack.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(mType)){
            mUnionid = getIntent().getStringExtra("unionid");
            mOpenid = getIntent().getStringExtra("openid");
            mIconurl = getIntent().getStringExtra("iconurl");
            mName = getIntent().getStringExtra("name");
            mLoginBtn.setText("绑定手机号");
            mToolbarLeftTitle.setText("");
        }else {
            mLoginBtn.setText("更改");
            mToolbarLeftTitle.setText(R.string.cancle);
        }
        mToolbarTitle.setText("绑定手机号");
        mPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    isPhone = true;
                } else {
                    isPhone = false;
                }
                linkLogin();
                linkCode(s.toString());
            }
        });
        mVerifyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && isPhone) {
                    isCode = true;
                } else {
                    isCode = false;
                }
                linkLogin();
            }
        });
    }

    private void linkLogin() {
        if (isPhone && isCode) {
            mLoginBtn.setAlpha(1.0f);
            mLoginBtn.setClickable(true);
        } else {
            mLoginBtn.setAlpha(0.4f);
            mLoginBtn.setClickable(false);
        }
    }

    private void linkCode(String s) {
        if (s.length() == 11) {
            mCodeBtn.setTextColor(getResources().getColor(R.color.blue_mid));
            mCodeBtn.setClickable(true);
        } else {
            mCodeBtn.setTextColor(getResources().getColor(R.color.border1));
            mCodeBtn.setClickable(false);
        }
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.toolbar_left_title, R.id.code_btn, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.code_btn:
                if (this.isDestroyed())
                    return;
                mTimer = new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mCodeBtn.setText(millisUntilFinished / 1000 + "s后重新获取");
                        mCodeBtn.setTextColor(getResources().getColor(R.color.border1));
                        mCodeBtn.setClickable(false);
                    }

                    @Override
                    public void onFinish() {
                        linkCode(mPhoneEt.getText().toString().trim());
                        mCodeBtn.setClickable(true);
                        mCodeBtn.setText("获取验证码");
                    }
                }.start();
                mPresenter.senSms(mPhoneEt.getText().toString().trim());
                break;
            case R.id.login_btn:
                if (TextUtils.isEmpty(mType)){
                    mPresenter.updatePhone(mPhoneEt.getText().toString(), mVerifyEt.getText().toString());
                }else {
                    mPresenter.bindPhone(mPhoneEt.getText().toString(), mVerifyEt.getText().toString(),mType, mUnionid, mOpenid, mIconurl, mName);
                }
                break;
        }
    }

    @Override
    public void handlerUpdate() {
        EventBus.getDefault().post(new RefreshEvent(Constants.INFO_REFRESH));
        ToastUtils.showCollect("手机号码修改成功", getResources().getDrawable(R.drawable.collect_success_icon));
        KeyboardUtils.hideSoftInput(this);
        finish();
    }

    @Override
    public void handlerBindPhone() {
        EventBus.getDefault().post(new RefreshEvent(Constants.INFO_REFRESH));
        ToastUtils.showCollect("绑定成功", getResources().getDrawable(R.drawable.collect_success_icon));
        if (LoginHelper.callBack != null) {
            LoginHelper.callBack.onLogin();
        }
        KeyboardUtils.hideSoftInput(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
            mTimer.cancel();
    }
}
