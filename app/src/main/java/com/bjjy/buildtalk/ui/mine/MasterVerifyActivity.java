package com.bjjy.buildtalk.ui.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.entity.CardInfoEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MasterVerifyActivity extends BaseActivity<MasterVerifyPresenter> implements MasterVerifyContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.face_iv)
    CircleImageView mFaceIv;
    @BindView(R.id.face_rl)
    RelativeLayout mFaceRl;
    @BindView(R.id.pic_tv)
    TextView mPicTv;
    @BindView(R.id.card_pic_rl)
    RelativeLayout mCardPicRl;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.name_rl)
    RelativeLayout mNameRl;
    @BindView(R.id.card_tv)
    TextView mCardTv;
    @BindView(R.id.card_num_rl)
    RelativeLayout mCardNumRl;
    @BindView(R.id.date_tv)
    TextView mDateTv;
    @BindView(R.id.card_date_rl)
    RelativeLayout mCardDateRl;
    @BindView(R.id.zhicheng_tv)
    TextView mZhichengTv;
    @BindView(R.id.zhicheng_rl)
    RelativeLayout mZhichengRl;
    @BindView(R.id.jieshao_tv)
    TextView mJieshaoTv;
    @BindView(R.id.jieshao_rl)
    RelativeLayout mJieshaoRl;

    private Intent mIntent;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(RefreshEvent eventBean) {
        if (TextUtils.equals(eventBean.getMsg(), Constants.INFO_REFRESH)) {
            initEventAndData();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_master_verify;
    }

    @Override
    protected void initView() {
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarTitle.setText("大咖认证");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.cardInfo();
    }

    @Override
    public void handlerCardInfo(CardInfoEntity cardInfoEntity) {
        mNameTv.setText(cardInfoEntity.getAuthor_name());
        mCardTv.setText(cardInfoEntity.getCardNumber());
        mDateTv.setText(cardInfoEntity.getCardValidDate());
        if (!TextUtils.isEmpty(cardInfoEntity.getAuthor_desc())){
            mZhichengTv.setTextColor(getResources().getColor(R.color.text_mid));
            mZhichengTv.setText(cardInfoEntity.getAuthor_desc());
        }
        if (!TextUtils.isEmpty(cardInfoEntity.getAuthor_intro())){
            mJieshaoTv.setTextColor(getResources().getColor(R.color.text_mid));
            mJieshaoTv.setText(cardInfoEntity.getAuthor_intro());
        }
    }

    @OnClick({R.id.toolbar_right_title, R.id.face_rl, R.id.card_pic_rl, R.id.name_rl, R.id.card_num_rl, R.id.card_date_rl, R.id.zhicheng_rl, R.id.jieshao_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.face_rl:
//                startActivity(new Intent(this, SetPictureActivity.class));
                break;
            case R.id.card_pic_rl:
//                startActivity(new Intent(this, IDCardActivity.class));
                break;
            case R.id.zhicheng_rl:
                mIntent = new Intent(this, EditNameActivity.class);
                mIntent.putExtra("type", "zhicheng");
                startActivity(mIntent);
                break;
            case R.id.jieshao_rl:
                mIntent = new Intent(this, EditNameActivity.class);
                mIntent.putExtra("type", "jieshao");
                startActivity(mIntent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
