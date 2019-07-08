package com.bjjy.buildtalk.ui.main;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.User;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneLoginActivity extends BaseActivity<PhoneLoginPresenter> implements PhoneLoginContract.View {

    @BindView(R.id.phone_et)
    EditText mPhoneEt;
    @BindView(R.id.verify_et)
    EditText mVerifyEt;
    @BindView(R.id.code_btn)
    Button mCodeBtn;
    @BindView(R.id.login_btn)
    Button mLoginBtn;

    private CountDownTimer mTimer;
    private boolean isPhone;
    private boolean isCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_login;
    }

    @Override
    protected void initView() {
        StatusBarUtils.changeFontColor(this, true);
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

    @OnClick({R.id.code_btn, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                mPresenter.mobileRegister(mPhoneEt.getText().toString().trim(), mVerifyEt.getText().toString().trim());
                break;
        }
    }

    @Override
    public void handlerLoginSuccess(User user) {
        LogUtils.e(user.toString());
        KeyboardUtils.hideSoftInput(this);
        EventBus.getDefault().post(new RefreshEvent(Constants.INFO_REFRESH));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
            mTimer.cancel();
    }
}
