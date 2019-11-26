package com.bjjy.buildtalk.ui.mine;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.utils.KeyboardUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity<FeedBackPresenter> implements FeedBackContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.content_et)
    EditText mContentEt;
    @BindView(R.id.edit_num_tv)
    TextView mEditNumTv;
    @BindView(R.id.commit_tv)
    TextView mCommitTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView() {
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarTitle.setText(R.string.problem_feedback);
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

    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.commit_tv)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mContentEt.getText().toString().trim())){
            ToastUtils.showShort("请输入问题或建议");
            return;
        }
        mPresenter.questFeedBack(mContentEt.getText().toString());
    }

    @Override
    public void handlerquestFeedBack() {
        ToastUtils.showShort("问题反馈已提交");
        KeyboardUtils.hideSoftInput(this);
        finish();
    }
}
