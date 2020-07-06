package com.bjjy.buildtalk.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.AttentionHAdapter;
import com.bjjy.buildtalk.adapter.DiscoverHAdapter;
import com.bjjy.buildtalk.adapter.EditDialogAdapter;
import com.bjjy.buildtalk.adapter.PublishCircleAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.fragment.BaseFragment;
import com.bjjy.buildtalk.core.event.RefreshEvent;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.ui.circle.ComplaintReasonActivity;
import com.bjjy.buildtalk.ui.circle.PublishActivity;
import com.bjjy.buildtalk.ui.circle.TopticDetailActivity;
import com.bjjy.buildtalk.ui.talk.CircleManDetailActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.ui.video.ShortVideoActivity;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.LoginHelper;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bjjy.buildtalk.weight.MyViewPagerAdapter;
import com.bjjy.buildtalk.weight.ScaleTransitionPagerTitleView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author power
 * @date 2020/5/9 2:16 PM
 * @project BuildTalk
 * @description: 发现
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View,
        BaseQuickAdapter.OnItemChildClickListener, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    Unbinder unbinder;

    private List<String> mTitleList = new ArrayList<>();
    private List<View> mViewList = new ArrayList<>();
    View attentionView, discoverView;
    private RecyclerView mAttentionRv;
    private RecyclerView mDiscoverRv;
    private List<ThemeInfoEntity.ThemeInfoBean> mDisList = new ArrayList<>();
    private List<ThemeInfoEntity.ThemeInfoBean> mAtenList = new ArrayList<>();
    private DiscoverHAdapter mDiscoverHAdapter;
    private AttentionHAdapter mAttentionHAdapter;
    private int disPage = 1;
    private int attenPage = 1;
    private String mPath = "pages/sub_circle/pages/subjectDetails/subjectDetails?";
    private String themePath;//主题拼接完成url
    BottomSheetDialog mBottomSheetDialog;
    BottomSheetBehavior mBehavior;
    private View mView;
    BottomSheetDialog mEditDialog;
    BottomSheetBehavior mEditBehavior;
    private View mEditView;
    private List<String> mItemList;
    private EditDialogAdapter mAdapter;
    private TextView mDSearch_tv;
    private TextView mASearch_tv;
    private BaseDialog mDeleteDialog;
    private BaseDialog mCircleDialog;
    private List<IEntity> mList;
    private PublishCircleAdapter mCircleAdapter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(RefreshEvent refreshEvent){
        if (TextUtils.equals(Constants.VIDEO_REFRESH, refreshEvent.getMsg())){
            if (refreshEvent.getPosition() >= 0){
                if (mViewpager != null){
                    if (mViewpager.getCurrentItem() == 1){
                        mDiscoverHAdapter.notifyItemChanged(refreshEvent.getPosition()+1);
                    }else {
                        mAttentionHAdapter.notifyItemChanged(refreshEvent.getPosition()+1);
                    }
                }
            }else {
                onRefresh(mRefreshLayout);
            }
        }
        if (TextUtils.equals(refreshEvent.getMsg(), Constants.TOPTIC_REFRESH)) {
            onRefresh(mRefreshLayout);
        }
    }

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frament_home;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initIndicator();
    }

    private void initIndicator() {
        mTitleList.add("关注");
        mTitleList.add("发现");
        attentionView = LayoutInflater.from(mContext).inflate(R.layout.view_attention, null, false);
        discoverView = LayoutInflater.from(mContext).inflate(R.layout.view_discover, null, false);
        mViewList.add(attentionView);
        mViewList.add(discoverView);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(mTitleList, mViewList);
        mViewpager.setAdapter(myViewPagerAdapter);

        mAttentionRv = attentionView.findViewById(R.id.recycler_view);
        mDiscoverRv = discoverView.findViewById(R.id.recycler_view);

        CommonNavigator commonNavigator = new CommonNavigator(mContext);
//        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mTitleList.get(index));
                simplePagerTitleView.setTextSize(20);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.text_color6));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.text_color7));
                simplePagerTitleView.setOnClickListener(v -> mViewpager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.parseColor("#FFA738"));
                indicator.setRoundRadius(10f);
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewpager);
        mViewpager.setCurrentItem(1);

        mDiscoverRv.setLayoutManager(new LinearLayoutManager(mContext));
        mDiscoverHAdapter = new DiscoverHAdapter(R.layout.adapter_discover_layout, mDisList,
                mPresenter.mDataManager.getUser().getUser_id());
        View d_headerView = LayoutInflater.from(mContext).inflate(R.layout.adapter_header_layout,
                null, false);
        mDiscoverHAdapter.addHeaderView(d_headerView);
        mDiscoverRv.setAdapter(mDiscoverHAdapter);
        ((SimpleItemAnimator) mDiscoverRv.getItemAnimator()).setSupportsChangeAnimations(false);
        mDiscoverHAdapter.setOnItemClickListener(this);
        mDiscoverHAdapter.setOnItemChildClickListener(this);
        mDSearch_tv = d_headerView.findViewById(R.id.search_tv);
        mAttentionRv.setLayoutManager(new LinearLayoutManager(mContext));
        mAttentionHAdapter = new AttentionHAdapter(R.layout.adapter_attention_layout, mAtenList,
                mPresenter.mDataManager.getUser().getUser_id());
        View a_headerView = LayoutInflater.from(mContext).inflate(R.layout.adapter_header_layout,
                null, false);
        mAttentionHAdapter.addHeaderView(a_headerView);
        mAttentionRv.setAdapter(mAttentionHAdapter);
        mASearch_tv = a_headerView.findViewById(R.id.search_tv);
        ((SimpleItemAnimator) mAttentionRv.getItemAnimator()).setSupportsChangeAnimations(false);
        mAttentionHAdapter.setOnItemChildClickListener(this);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);

        d_headerView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DisSearchActivity.class);
            intent.putExtra("type_id", "1");
            startActivity(intent);
        });
        a_headerView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DisSearchActivity.class);
            intent.putExtra("type_id", "2");
            startActivity(intent);
        });
    }

    @Override
    protected void initEventAndData() {
        mPresenter.discover(disPage);
        mPresenter.attention(attenPage);

    }

    @Override
    public void handlerDiscover(ThemeInfoEntity disrOrAttenEntity) {
        mDisList = disrOrAttenEntity.getThemeInfo();
        if (disPage == 1) {
            mDSearch_tv.setText("大家都在搜“" + disrOrAttenEntity.getTop_keyword() + "”");
            mDiscoverHAdapter.setNewData(mDisList);
        } else {
            mDiscoverHAdapter.addData(mDisList);
        }
    }

    @Override
    public void handlerAttention(ThemeInfoEntity disrOrAttenEntity) {
        mAtenList = disrOrAttenEntity.getThemeInfo();
        if (attenPage == 1) {
            mASearch_tv.setText("大家都在搜“" + disrOrAttenEntity.getTop_keyword() + "”");
            mAttentionHAdapter.setNewData(mAtenList);
        } else {
            mAttentionHAdapter.addData(mAtenList);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (0 == mViewpager.getCurrentItem()) {
            attenPage = 1;
            mPresenter.attention(attenPage);
        } else {
            disPage = 1;
            mPresenter.discover(disPage);
        }
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (0 == mViewpager.getCurrentItem()) {
            mPresenter.attention(++attenPage);
        } else {
            mPresenter.discover(++disPage);
        }
        refreshLayout.finishLoadMore();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<ThemeInfoEntity.ThemeInfoBean> mList = adapter.getData();
        LoginHelper.getInstance().login(mContext, mPresenter.mDataManager, () -> {
            Intent intent = new Intent(mContext, TopticDetailActivity.class);
            intent.putExtra("title", mList.get(position).getName());
            intent.putExtra("theme_id", mList.get(position).getTheme_id() + "");
            intent.putExtra("circle_id", mList.get(position).getCircle_id());
            startActivity(intent);
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<ThemeInfoEntity.ThemeInfoBean> mList = adapter.getData();
        switch (view.getId()) {
            case R.id.item_face_iv://个人主页
                if (mList.get(position).getIs_author().equals("1")) {
                    Intent intent = new Intent(mContext, MasterDetailActivity.class);
                    intent.putExtra("user_id", mList.get(position).getUser_id() + "");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, CircleManDetailActivity.class);
                    intent.putExtra("user_id", mList.get(position).getUser_id() + "");
                    startActivity(intent);
                }
                break;
            case R.id.item_from_tv:
                if (mList.get(position).getParent_themeInfo().getIs_author() == 1) {
                    Intent intent = new Intent(mContext, MasterDetailActivity.class);
                    intent.putExtra("user_id", mList.get(position).getParent_themeInfo().getUser_id() + "");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, CircleManDetailActivity.class);
                    intent.putExtra("user_id", mList.get(position).getParent_themeInfo().getUser_id() + "");
                    startActivity(intent);
                }
                break;
            case R.id.item_atten_cl://关注
                LoginHelper.getInstance().login(mContext, mPresenter.mDataManager, () -> mPresenter.attenUser(mList, position));
                break;
            case R.id.item_more_iv://更多操作
                LoginHelper.getInstance().login(mContext, mPresenter.mDataManager, () -> {
                    if (1 == mList.get(position).getParent_isDelete()) {
                        ToastUtils.showShort("原主题已被删除");
                        return;
                    }
                    if (mList.get(position).getTheme_image().size() > 0) {
                        mPresenter.getThumb(mList.get(position).getTheme_image().get(0).getPic_url(), mList,
                                position, true);
                    } else {
                        mPresenter.getThumb(mList.get(position).getHeadImage(), mList, position, true);
                    }
                });
                break;
            case R.id.item_praise_ll://点赞
                LoginHelper.getInstance().login(mContext, mPresenter.mDataManager, () -> mPresenter.praise(mList, position));
                break;
            case R.id.item_record_ll://进入详情
                LoginHelper.getInstance().login(mContext, mPresenter.mDataManager, () -> {
                    Intent intent = new Intent(mContext, TopticDetailActivity.class);
                    intent.putExtra("title", mList.get(position).getName());
                    intent.putExtra("theme_id", mList.get(position).getTheme_id() + "");
                    intent.putExtra("circle_id", mList.get(position).getCircle_id());
                    intent.putExtra("showEdit", true);
                    startActivity(intent);
                });
                break;
            case R.id.item_share_ll://分享
                LoginHelper.getInstance().login(mContext, mPresenter.mDataManager, () -> {
                    if (1 == mList.get(position).getParent_isDelete()) {
                        ToastUtils.showShort("原主题已被删除");
                        return;
                    }
                    if (mList.get(position).getTheme_image().size() > 0) {
                        mPresenter.getThumb(mList.get(position).getTheme_image().get(0).getPic_url(), mList,
                                position, false);
                    } else {
                        mPresenter.getThumb(mList.get(position).getHeadImage(), mList, position, false);
                    }
                });
                break;
            case R.id.item_vertical_video_view:
            case R.id.item_horizontal_video_view:
                Intent intent1 = new Intent(mContext, ShortVideoActivity.class);
                intent1.putExtra("type_id", "1");
                intent1.putExtra("theme_id", String.valueOf(mList.get(position).getTheme_id()));
                intent1.putExtra("user_id", String.valueOf(mList.get(position).getUser_id()));
                intent1.putExtra("position", position);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void handlerAttentUser(BaseResponse<IEntity> baseResponse, List<ThemeInfoEntity.ThemeInfoBean> data,
                                  int position) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(position).getUser_id().equals(data.get(i).getUser_id())){
                if (TextUtils.equals("关注成功", baseResponse.getErrorMsg())){
                    data.get(i).setIs_attention(1);
                }else {
                    data.get(i).setIs_attention(0);
                }
            }
        }
        if (mViewpager.getCurrentItem() == 1){
            mDiscoverHAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void handlerPraiseSuccess(List<ThemeInfoEntity.ThemeInfoBean> data, int i, PraiseEntity praiseEntity) {
        if (data.get(i).getIs_parise() == 0) {
            data.get(i).setIs_parise(1);
        } else {
            data.get(i).setIs_parise(0);
        }
        data.get(i).setParise_nickName(praiseEntity.getNickName());
        data.get(i).setCountParise(praiseEntity.getCountpraise());
        if (mViewpager.getCurrentItem() == 1) {//发现列表
            mDiscoverHAdapter.notifyItemChanged(i + 1);
            //如果点赞的这条主题是关注的人，那么刷新关注列表数据
            if (data.get(i).getIs_attention() == 1) {
                refreshChoiceness();
            }
        } else {//关注列表,局部刷新发现数据
            mAttentionHAdapter.notifyItemChanged(i + 1);
            int theme_id = data.get(i).getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mDiscoverHAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()) {
                    if (topticAdapterData.get(j).getIs_parise() == 0) {
                        topticAdapterData.get(j).setIs_parise(1);
                    } else {
                        topticAdapterData.get(j).setIs_parise(0);
                    }
                    topticAdapterData.get(j).setParise_nickName(praiseEntity.getNickName());
                    topticAdapterData.get(j).setCountParise(praiseEntity.getCountpraise());
                    mDiscoverHAdapter.notifyItemChanged(j + 1);
                }
            }
        }
    }

    /**
     * 刷新关注列表
     */
    private void refreshChoiceness() {
        attenPage = 1;
        mPresenter.attention(attenPage);
    }

    @Override
    public void handlerThumbSuccess(String thumb_url, List<ThemeInfoEntity.ThemeInfoBean> data,
                                    int i, boolean isEdit) {
        String mUrl = Constants.BASE_URL + "jtfwhgetopenid" + "?user_id=" +
                mPresenter.mDataManager.getUser().getUser_id() + "&theme_id=" + data.get(i).getTheme_id();
        String mEndUrl = Constants.END_URL + "&redirect_uri=" + URLEncoder.encode(mUrl) +
                "&response_type=code&scope=snsapi_userinfo&state=theme#wechat_redirect";
        themePath = mPath + "theme_id=" + data.get(i).getTheme_id() + "&circle_id=" + "这个Path不对呢，记得改" + "&num=1";
        if (isEdit) {
            showEditDialog(data.get(i), i, data, data.get(i).getParent_themeInfo(), themePath, mEndUrl,
                    TextUtils.isEmpty(data.get(i).getTheme_content()) ?
                            data.get(i).getParent_themeInfo().getTheme_content() : data.get(i).getTheme_content(),
                    thumb_url, data.get(i).getTheme_content(), true, false, data.get(i).getTheme_id(),
                    data.get(i).getCircle_id());
        } else {
            showShareDialog(themePath, mEndUrl, TextUtils.isEmpty(data.get(i).getTheme_content()) ?
                            data.get(i).getParent_themeInfo().getName() : data.get(i).getTheme_content(),
                    thumb_url, data.get(i).getTheme_content(), true, false, data.get(i).getTheme_id(),
                    data.get(i).getCircle_id());
        }
    }

    private void showShareDialog(String url, String weburl, String title, String imgUrl,
                                 String desc, boolean isSmall, boolean isVisible, int theme_id, int circle_id) {
        if (mBottomSheetDialog == null) {
            mBottomSheetDialog = new BottomSheetDialog(mContext, R.style.bottom_sheet_dialog);
            mBottomSheetDialog.getWindow().getAttributes().windowAnimations =
                    R.style.bottom_sheet_dialog;
            mBottomSheetDialog.setCancelable(true);
            mBottomSheetDialog.setCanceledOnTouchOutside(true);
            mView = getLayoutInflater().inflate(R.layout.dialog_share_layout, null);
            mBottomSheetDialog.setContentView(mView);
            mBehavior = BottomSheetBehavior.from((View) mView.getParent());
            mBehavior.setSkipCollapsed(true);
//            int peekHeight = getResources().getDisplayMetrics().heightPixels;
            //设置默认弹出高度为屏幕的0.4倍
//            mBehavior.setPeekHeight((int)(0.4 * peekHeight));
        }
        mView.findViewById(R.id.discover_tv).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mView.findViewById(R.id.circle_tv).setVisibility(isVisible ? View.GONE : View.VISIBLE);
        mBottomSheetDialog.show();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mView.findViewById(R.id.wechat_tv).setOnClickListener(v -> {
            if (isSmall) {
                DialogUtils.shareSmallProgram(url, imgUrl, title, desc, getActivity(), SHARE_MEDIA.WEIXIN);
            } else {
                DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, getActivity(), SHARE_MEDIA.WEIXIN);
            }
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.wechat_circle_tv).setOnClickListener(v -> {
            DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, getActivity(), SHARE_MEDIA.WEIXIN_CIRCLE);
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.discover_tv).setOnClickListener(v -> {
            mPresenter.shareTheme(theme_id, circle_id+"");
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.circle_tv).setOnClickListener(v -> {
            showCircleDialog(theme_id);
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.cancle_tv).setOnClickListener(v -> mBottomSheetDialog.dismiss());
    }

    private void showCircleDialog(int theme_id) {
        if (mCircleDialog == null){
            mCircleDialog = new BaseDialog.Builder(mContext)
                    .setViewId(R.layout.dialog_publish_circle_layout)
                    .setStyle(R.style.AppTheme)
                    //设置显示位置
                    .setGravity(Gravity.CENTER)
                    //设置动画
                    .setAnimation(R.style.Bottom_Top_aniamtion)
                    //设置dialog的宽高
                    .setWidthHeightpx(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    //设置触摸dialog外围是否关闭
                    .isOnTouchCanceled(true)
                    .addViewOnClickListener(R.id.cancle_tv, v -> mCircleDialog.dismiss())
                    .builder();
            List<IEntity> mList = new ArrayList<>();
            RecyclerView recyclerView = mCircleDialog.getView(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mCircleAdapter = new PublishCircleAdapter(R.layout.adapter_publish_circle, mList);
            recyclerView.setAdapter(mCircleAdapter);
            mPresenter.circleList(mCircleAdapter);
        }

        mCircleAdapter.setOnItemClickListener((adapter1, view, position) -> {
            List<IEntity> data = adapter1.getData();
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setChecked(false);
            }
            String circle_id = data.get(position).getCircle_id();
            data.get(position).setChecked(true);
            adapter1.notifyDataSetChanged();
            mPresenter.shareTheme(theme_id, circle_id);

            mCircleDialog.dismiss();
        });

        mCircleDialog.show();
    }

    @Override
    public void handlerCircleListSuccess(List<IEntity> iEntities, PublishCircleAdapter circleAdapter) {
        circleAdapter.setNewData(iEntities);
    }

    public void showEditDialog(ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list,
                               ThemeInfoEntity.ThemeInfoBean.ParentThemeInfoBean circleInfoEntity, String url,
                               String weburl, String title, String imgUrl,
                               String desc, boolean isSmall, boolean isVisible, int theme_id, int circle_id) {
        if (mEditDialog == null) {
            mEditDialog = new BottomSheetDialog(mContext, R.style.bottom_sheet_dialog);
            mEditDialog.getWindow().getAttributes().windowAnimations =
                    R.style.bottom_sheet_dialog;
            mEditDialog.setCancelable(true);
            mEditDialog.setCanceledOnTouchOutside(true);
            mEditView = getLayoutInflater().inflate(R.layout.dialog_theme_edit, null);
            mEditDialog.setContentView(mEditView);
            mEditBehavior = BottomSheetBehavior.from((View) mEditView.getParent());
            mEditBehavior.setSkipCollapsed(true);
//            int peekHeight = getResources().getDisplayMetrics().heightPixels;
            //设置默认弹出高度为屏幕的0.4倍
//            mBehavior.setPeekHeight((int)(0.4 * peekHeight));
            mItemList = new ArrayList<>();
            RecyclerView recyclerView = mEditView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,
                    false));
            mAdapter = new EditDialogAdapter(R.layout.adapter_edit_dialog, mItemList, data);
            recyclerView.setAdapter(mAdapter);
        }
        mEditView.findViewById(R.id.discover_tv).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mEditView.findViewById(R.id.circle_tv).setVisibility(isVisible ? View.GONE : View.VISIBLE);

        if (mPresenter.mDataManager.getUser().getUser_id().equals(circleInfoEntity.getUser_id() + "")) {//如果是圈主
            LogUtils.e(mPresenter.mDataManager.getUser().getUser_id() + "");
            if (mPresenter.mDataManager.getUser().getUser_id().equals(String.valueOf(data.getUser_id()))) {
                //如果是自己的主题----收藏、修改、置顶、加精、删除
                mItemList.clear();
                mItemList.add("收藏");
                mItemList.add("修改");
                mItemList.add("删除");
                mAdapter.setNewData(mItemList);
            } else {
                //不是自己的主题----收藏、置顶、加精、不喜欢、投诉
                mItemList.clear();
                mItemList.add("收藏");
                mItemList.add("不喜欢");
                mItemList.add("投诉");
                mAdapter.setNewData(mItemList);
            }
        } else {//如果是成员
            if (mPresenter.mDataManager.getUser().getUser_id().equals(String.valueOf(data.getUser_id()))) {
                //如果是自己的主题----收藏、修改、删除
                mItemList.clear();
                mItemList.add("收藏");
                mItemList.add("删除");
                mItemList.add("修改");
                mAdapter.setNewData(mItemList);
            } else {
                //不是自己的主题----收藏、不喜欢、投诉
                mItemList.clear();
                mItemList.add("收藏");
                mItemList.add("不喜欢");
                mItemList.add("投诉");
                mAdapter.setNewData(mItemList);
            }
        }
        mAdapter.setOnItemClickListener((adapter1, view, position) -> {
            List<String> item = adapter1.getData();
            switch (item.get(position)) {
                case "收藏":
                    mPresenter.collectTheme(data, i);
                    mEditDialog.dismiss();
                    break;
                case "修改":
                    if (mPresenter.mDataManager.getUser().getUser_id().equals(String.valueOf(data.getUser_id()))) {
                        Intent intent = new Intent(mContext, PublishActivity.class);
                        intent.putExtra("themeInfo", data);
                        intent.putExtra("publish_type", "2");
                        intent.putExtra("circle_name", data.getName());
                        intent.putExtra("circle_id", data.getCircle_id());
                        startActivity(intent);
                    }
                    mEditDialog.dismiss();
                    break;
                case "删除":
                    if (mPresenter.mDataManager.getUser().getUser_id().equals(String.valueOf(data.getUser_id()))) {
                        showDeleteDialog(data, i, list);
                    }
                    mEditDialog.dismiss();
                    break;
                case "不喜欢":
                    mPresenter.userShieldRecord(data, i, list);
                    mEditDialog.dismiss();
                    break;
                case "投诉":
                    Intent intent = new Intent(mContext, ComplaintReasonActivity.class);
                    intent.putExtra("data_id", data.getTheme_id() + "");
                    startActivity(intent);
                    mEditDialog.dismiss();
                    break;
            }
        });
        mEditDialog.show();
        mEditBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mEditView.findViewById(R.id.wechat_tv).setOnClickListener(v -> {
            if (isSmall) {
                DialogUtils.shareSmallProgram(url, imgUrl, title, desc, getActivity(), SHARE_MEDIA.WEIXIN);
            } else {
                DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, getActivity(), SHARE_MEDIA.WEIXIN);
            }
            mEditDialog.dismiss();
        });
        mEditView.findViewById(R.id.wechat_circle_tv).setOnClickListener(v -> {
            DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, getActivity(), SHARE_MEDIA.WEIXIN_CIRCLE);
            mEditDialog.dismiss();
        });
        mEditView.findViewById(R.id.discover_tv).setOnClickListener(v -> mEditDialog.dismiss());
        mEditView.findViewById(R.id.circle_tv).setOnClickListener(v -> {
            showCircleDialog(theme_id);
            mEditDialog.dismiss();
        });
        mEditView.findViewById(R.id.cancle_tv).setOnClickListener(v -> mEditDialog.dismiss());
    }

    @Override
    public void handleruserShieldRecordSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        list.remove(i);
        if (mViewpager.getCurrentItem() == 1){
            mDiscoverHAdapter.notifyItemChanged(i + 1);
            //如果点赞的这条主题是关注的人，那么刷新关注列表数据
            if (data.getIs_attention() == 1) {
                refreshChoiceness();
            }
        }else {
            mAttentionHAdapter.notifyItemChanged(i + 1);
            int theme_id = data.getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mDiscoverHAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()) {
                    topticAdapterData.remove(j);
                    mDiscoverHAdapter.notifyItemChanged(j + 1);
                }
            }
        }
    }

    private void showDeleteDialog(ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        mDeleteDialog = new BaseDialog.Builder(mContext)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) getResources().getDimension(R.dimen.dp_275), (int) getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mDeleteDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    mPresenter.deleteTheme(data, i, list);
                    mDeleteDialog.dismiss();
                })
                .builder();
        TextView textView = mDeleteDialog.getView(R.id.text);
        textView.setText("确定删除该主题？");
        mDeleteDialog.show();
    }

    @Override
    public void handlerDeleteSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        list.remove(i);
        if (mViewpager.getCurrentItem() == 1){
            mDiscoverHAdapter.notifyItemChanged(i + 1);
            //如果点赞的这条主题是关注的人，那么刷新关注列表数据
            if (data.getIs_attention() == 1) {
                refreshChoiceness();
            }
        }else {
            mAttentionHAdapter.notifyItemChanged(i + 1);
            int theme_id = data.getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mDiscoverHAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()) {
                    topticAdapterData.remove(j);
                    mDiscoverHAdapter.notifyItemChanged(j + 1);
                }
            }
        }
    }

    @Override
    public void handlerCollectSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i) {
        if (0 == data.getIs_collect()) {
            data.setIs_collect(1);
            ToastUtils.showCollect("收藏成功", getResources().getDrawable(R.drawable.collect_success_icon));
        } else {
            data.setIs_collect(0);
            ToastUtils.showCollect("取消收藏", getResources().getDrawable(R.drawable.collect_cancle_icon));
        }
        if (mViewpager.getCurrentItem() == 1) {//发现列表
            mDiscoverHAdapter.notifyItemChanged(i);
            //如果点赞的这条主题是关注的人，那么刷新关注列表数据
            if (data.getIs_attention() == 1) {
                refreshChoiceness();
            }
        } else {//精华列表,局部刷新主题数据
            mAttentionHAdapter.notifyItemChanged(i);
            int theme_id = data.getTheme_id();
            List<ThemeInfoEntity.ThemeInfoBean> topticAdapterData = mDiscoverHAdapter.getData();
            for (int j = 0; j < topticAdapterData.size(); j++) {
                if (theme_id == topticAdapterData.get(j).getTheme_id()) {
                    if (0 == topticAdapterData.get(j).getIs_collect()) {
                        topticAdapterData.get(j).setIs_collect(1);
                    } else {
                        topticAdapterData.get(j).setIs_collect(0);
                    }
                    mDiscoverHAdapter.notifyItemChanged(j);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(mContext);
    }
}
