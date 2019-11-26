package com.bjjy.buildtalk.ui.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.permissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ServiceActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;
    @BindView(R.id.phone_tv)
    TextView mPhoneTv;
    @BindView(R.id.time_tv)
    TextView mTimeTv;
    @BindView(R.id.dial_tv)
    TextView mDialTv;
    private BaseDialog mPhoneDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();
    }

    private void initView() {
        mToolbarTitle.setText("在线客服");
        mToolbarBack.setOnClickListener(v -> finish());
    }

    @OnClick(R.id.dial_tv)
    public void onViewClicked() {
        checkPerssion();
    }

    @SuppressLint("MissingPermission")
    private void showPhoneDialog() {
        mPhoneDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mPhoneDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "13810696570"));
                    startActivity(intent);
                    mPhoneDialog.dismiss();
                })
                .builder();
        TextView textView = mPhoneDialog.getView(R.id.text);
        textView.setText("13810696570");
        TextView dialTv = mPhoneDialog.getView(R.id.query_tv);
        dialTv.setText("呼叫");
        mPhoneDialog.show();

    }

    private void checkPerssion() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.CALL_PHONE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean){
                    showPhoneDialog();
                }else {
                    ToastUtils.showShort("拨打电话权限被拒绝");
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
