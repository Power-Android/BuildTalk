package com.bjjy.buildtalk.ui.talk;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.MasterArticleAdapter;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.MasterDetailEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.ui.discover.EveryTalkDetailActivity;
import com.bjjy.buildtalk.utils.SizeUtils;
import com.bjjy.buildtalk.utils.StatusBarUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MyViewPagerAdapter;
import com.bjjy.buildtalk.weight.tablayout.TabLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MasterDetailActivity extends BaseActivity<MasterDetailPresenter> implements MasterDetailContract.View, AppBarLayout.OnOffsetChangedListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.master_bg)
    ImageView mMasterBg;
    @BindView(R.id.face_iv)
    CircleImageView mFaceIv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.tag_iv)
    ImageView mTagIv;
    @BindView(R.id.job_tv)
    TextView mJobTv;
    @BindView(R.id.focus_iv)
    ImageView mFocusIv;
    @BindView(R.id.focus_tv)
    TextView mFocusTv;
    @BindView(R.id.focus_ll)
    LinearLayout mFocusLl;
    @BindView(R.id.circle_num_tv)
    TextView mCircleNumTv;
    @BindView(R.id.fans_num_tv)
    TextView mFansNumTv;
    @BindView(R.id.collect_num_tv)
    TextView mCollectNumTv;
    @BindView(R.id.focus_num_tv)
    TextView mFocusNumTv;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.top_title_rl)
    RelativeLayout mTopTitleRl;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.min_rl)
    RelativeLayout mMinRl;
    private String mUser_id;//被查看用户的ID
    private List<MasterDetailEntity.ArticleInfoBean> mArticleInfo = new ArrayList<>();
    private MasterDetailEntity mMasterDetailEntity;
    private MasterArticleAdapter mMasterArticleAdapter;
    private TextView mIntroductionNameTv;
    private TextView mIntroductionEduTv;
    private TextView mIntroductionIntroTv;
    private TextView mIntroductionDescTv;
    private TextView mIntroductionReceivedTv;
    private Intent mIntent;
    private BaseDialog mDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_master_detail;
    }

    @Override
    protected void initView() {
        mUser_id = getIntent().getStringExtra("user_id");
        StatusBarUtils.changeStatusBar(this, true, true);
        mTopTitleRl.setPadding(0, StatusBarUtils.getStatusBarHeight(), 0, 0);
        //设置appbar滚动布局的最小高度 因为getHeight可能为0，所以直接加上导航栏和tablayout的高度
        mMinRl.setMinimumHeight(StatusBarUtils.getStatusBarHeight() + (int) getResources().getDimension(R.dimen.dp_44));
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.tabData();
    }

    @Override
    public void handlerTab(List<String> list, List<View> views) {
        mViewpager.setAdapter(new MyViewPagerAdapter(list, views));
        mTablayout.setupWithViewPager(mViewpager);
        RecyclerView articleRecyclerView = views.get(0).findViewById(R.id.recycler_view);
        articleRecyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        mMasterArticleAdapter = new MasterArticleAdapter(R.layout.adapter_master_article, mArticleInfo);
        mMasterArticleAdapter.setOnItemClickListener(this);
        articleRecyclerView.setAdapter(mMasterArticleAdapter);
        mIntroductionNameTv = views.get(1).findViewById(R.id.name_tv);
        mIntroductionEduTv = views.get(1).findViewById(R.id.edu_tv);
        mIntroductionIntroTv = views.get(1).findViewById(R.id.intro_tv);
        mIntroductionDescTv = views.get(1).findViewById(R.id.desc_tv);
        mIntroductionReceivedTv = views.get(1).findViewById(R.id.received_tv);
        mPresenter.userDetail(mUser_id);
    }

    @Override
    public void handlerUserDetail(MasterDetailEntity detailEntity) {
        this.mMasterDetailEntity = detailEntity;
        mTitleTv.setText(detailEntity.getName());
        Glide.with(this).load(detailEntity.getBg_pic()).into(mMasterBg);
        Glide.with(this).load(detailEntity.getHeadImage()).into(mFaceIv);
        mNameTv.setText(detailEntity.getName());
        if (1 == detailEntity.getIs_author()){
            mTagIv.setVisibility(View.VISIBLE);
        }else {
            mTagIv.setVisibility(View.GONE);
        }
        mJobTv.setText(detailEntity.getAuthor_desc());
        if (1 == detailEntity.getIs_attention()){
            mFocusIv.setVisibility(View.VISIBLE);
            mFocusTv.setText("已关注");
        }else {
            mFocusIv.setVisibility(View.GONE);
            mFocusTv.setText("+关注");
        }
        mCircleNumTv.setText(detailEntity.getCountMyCircle() + "\n圈子");
        mFansNumTv.setText(detailEntity.getCountMyFans() + "\n粉丝");
        mCollectNumTv.setText(detailEntity.getCountMyCollect() + "\n收藏");
        mFocusNumTv.setText(detailEntity.getCountMyAttention() + "\n关注");

        mArticleInfo = detailEntity.getArticleInfo();
        mMasterArticleAdapter.setNewData(mArticleInfo);

        mIntroductionNameTv.setText(detailEntity.getName());
        if (detailEntity.getEducation() != null){
            mIntroductionEduTv.setText(detailEntity.getEducation());
        }else {
            mIntroductionEduTv.setText("");
        }
        mIntroductionDescTv.setText(detailEntity.getAuthor_desc());
        mIntroductionIntroTv.setText(detailEntity.getAuthor_intro());
        mIntroductionReceivedTv.setText(detailEntity.getReceived());

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();

        if (offset > SizeUtils.dp2px(getResources().getDimension(R.dimen.dp_44))) {
            mTitleTv.setText(mNameTv.getText());
            mBackIv.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_black_icon));
            mTitleTv.setVisibility(View.VISIBLE);
        } else {
            mBackIv.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_white_icon));
            mTitleTv.setVisibility(View.GONE);
        }

        float scale = (float) offset / scrollRange;
        int alpha = (int) (255 * scale);
        mTopTitleRl.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
    }

    @OnClick({R.id.face_iv, R.id.focus_ll, R.id.circle_num_tv, R.id.fans_num_tv, R.id.collect_num_tv, R.id.focus_num_tv, R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.face_iv:
                break;
            case R.id.focus_ll:
                if (1 == mMasterDetailEntity.getIs_attention()){
                    showDialog();
                }else {
                    mPresenter.attention(mUser_id);
                }
                break;
            case R.id.circle_num_tv:
                mIntent = new Intent(this,MasterCircleActivity.class);
                mIntent.putExtra("user_id", mUser_id);
                mIntent.putExtra("name", mMasterDetailEntity.getName());
                startActivity(mIntent);
                break;
            case R.id.fans_num_tv:
                mIntent = new Intent(this, FansFocusActivity.class);
                mIntent.putExtra("user_id", mUser_id);
                mIntent.putExtra("name", mMasterDetailEntity.getName());
                mIntent.putExtra("type", "fans");
                startActivity(mIntent);
                break;
            case R.id.collect_num_tv:
                mIntent = new Intent(this,MasterCollectActivity.class);
                mIntent.putExtra("user_id", mUser_id);
                mIntent.putExtra("name", mMasterDetailEntity.getName());
                startActivity(mIntent);
                break;
            case R.id.focus_num_tv:
                mIntent = new Intent(this, FansFocusActivity.class);
                mIntent.putExtra("user_id", mUser_id);
                mIntent.putExtra("name", mMasterDetailEntity.getName());
                mIntent.putExtra("type", "focus");
                startActivity(mIntent);
                break;
            case R.id.back_iv:
                finish();
                break;
        }
    }

    private void showDialog() {
        mDialog = new BaseDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    mPresenter.attention(mUser_id);
                    mDialog.dismiss();
                })
                .builder();
        TextView textView = mDialog.getView(R.id.text);
        textView.setText("确定取消关注？");
        mDialog.show();
    }

    @Override
    public void handlerAttrntion(BaseResponse<IEntity> baseResponse) {
        if (TextUtils.equals("关注成功", baseResponse.getErrorMsg())){
            mMasterDetailEntity.setIs_attention(1);
            mFocusIv.setVisibility(View.VISIBLE);
            mFocusTv.setText("已关注");
            ToastUtils.showCollect("关注成功", getResources().getDrawable(R.drawable.collect_success_icon));
        }else {
            mMasterDetailEntity.setIs_attention(0);
            mFocusIv.setVisibility(View.GONE);
            mFocusTv.setText("+关注");
            ToastUtils.showCollect("已取消关注", getResources().getDrawable(R.drawable.collect_cancle_icon));
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List<MasterDetailEntity.ArticleInfoBean> mArticleInfo = baseQuickAdapter.getData();
        Intent intent = new Intent(this, EveryTalkDetailActivity.class);
        intent.putExtra("article_id", mArticleInfo.get(i).getArticle_id()+"");
        startActivity(intent);
    }
}
