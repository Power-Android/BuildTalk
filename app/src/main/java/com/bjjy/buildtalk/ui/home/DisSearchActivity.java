package com.bjjy.buildtalk.ui.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.DiscoverHAdapter;
import com.bjjy.buildtalk.adapter.EditDialogAdapter;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.ui.circle.ComplaintReasonActivity;
import com.bjjy.buildtalk.ui.circle.TopticDetailActivity;
import com.bjjy.buildtalk.ui.video.ShortVideoActivity;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DisSearchActivity extends BaseActivity<DisSearchPresenter> implements DisSearchContract.View, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarLeftBack;
    @BindView(R.id.search_et)
    EditText mSearchEt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;
    private String mType_id;
    private int page = 1;
    private DiscoverHAdapter mDiscoverHAdapter;
    private List<ThemeInfoEntity.ThemeInfoBean> mList;

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
    private BaseDialog mDeleteDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dis_search;
    }

    @Override
    protected void initView() {
        mType_id = getIntent().getStringExtra("type_id");
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDiscoverHAdapter = new DiscoverHAdapter(R.layout.adapter_discover_layout, mList, mPresenter.mDataManager.getUser().getUser_id());
        mRecyclerView.setAdapter(mDiscoverHAdapter);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mDiscoverHAdapter.setOnItemClickListener(this);
        mDiscoverHAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initEventAndData() {
        mSearchEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (TextUtils.isEmpty(mSearchEt.getText().toString())) {
                    ToastUtils.showShort("请输入搜索内容");
                    return false;
                }
                mPresenter.search(mSearchEt.getText().toString(), mType_id, page);
            }
            return false;
        });
    }

    @OnClick(R.id.toolbar_left_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void handlerSearch(ThemeInfoEntity disrOrAttenEntity) {
        List<ThemeInfoEntity.ThemeInfoBean> themeInfo = disrOrAttenEntity.getThemeInfo();
        if (page == 1) {
            mDiscoverHAdapter.setNewData(themeInfo);
        } else {
            mDiscoverHAdapter.addData(themeInfo);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.search(mSearchEt.getText().toString(), mType_id, ++page);
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.search(mSearchEt.getText().toString(), mType_id, page);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<ThemeInfoEntity.ThemeInfoBean> mList = adapter.getData();
        Intent intent = new Intent(this, TopticDetailActivity.class);
        intent.putExtra("title", mList.get(position).getName());
        intent.putExtra("theme_id", mList.get(position).getTheme_id() + "");
        intent.putExtra("circle_id", mList.get(position).getCircle_id());
        startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<ThemeInfoEntity.ThemeInfoBean> mList = adapter.getData();
        switch (view.getId()) {
            case R.id.item_face_iv://个人主页
            case R.id.item_from_tv:
                break;
            case R.id.item_atten_cl://关注
                mPresenter.attenUser(mList, position);
                break;
            case R.id.item_more_iv://更多操作
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
                break;
            case R.id.item_praise_ll://点赞
                mPresenter.praise(mList, position);
                break;
            case R.id.item_record_ll://进入详情
                Intent intent = new Intent(this, TopticDetailActivity.class);
                intent.putExtra("title", mList.get(position).getName());
                intent.putExtra("theme_id", mList.get(position).getTheme_id() + "");
                intent.putExtra("circle_id", mList.get(position).getCircle_id());
                startActivity(intent);
                break;
            case R.id.item_share_ll://分享
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
                break;
            case R.id.item_vertical_video_view:
            case R.id.item_horizontal_video_view:
                Intent intent1 = new Intent(this, ShortVideoActivity.class);
                intent1.putExtra("type_id", "1");
                intent1.putExtra("theme_id", String.valueOf(mList.get(position).getTheme_id()));
                intent1.putExtra("user_id", String.valueOf(mList.get(position).getUser_id()));
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void handlerAttentUser(BaseResponse<IEntity> baseResponse, List<ThemeInfoEntity.ThemeInfoBean> data,
                                  int position) {
        if (TextUtils.equals("关注成功", baseResponse.getErrorMsg())) {
            data.get(position).setIs_attention(1);
        } else {
            data.get(position).setIs_attention(0);
        }
        mDiscoverHAdapter.notifyItemChanged(position);
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
        mDiscoverHAdapter.notifyItemChanged(i + 1);
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
                    thumb_url, data.get(i).getTheme_content(), true, true);
        } else {
            showShareDialog(themePath, mEndUrl, TextUtils.isEmpty(data.get(i).getTheme_content()) ?
                            data.get(i).getParent_themeInfo().getName() : data.get(i).getTheme_content(),
                    thumb_url, data.get(i).getTheme_content(), true, true);
        }
    }

    private void showShareDialog(String url, String weburl, String title, String imgUrl,
                                 String desc, boolean isSmall, boolean isVisible) {
        if (mBottomSheetDialog == null) {
            mBottomSheetDialog = new BottomSheetDialog(this, R.style.bottom_sheet_dialog);
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
                DialogUtils.shareSmallProgram(url, imgUrl, title, desc, this, SHARE_MEDIA.WEIXIN);
            } else {
                DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, this, SHARE_MEDIA.WEIXIN);
            }
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.wechat_circle_tv).setOnClickListener(v -> {
            DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, this, SHARE_MEDIA.WEIXIN_CIRCLE);
            mBottomSheetDialog.dismiss();
        });
        mView.findViewById(R.id.discover_tv).setOnClickListener(v -> mBottomSheetDialog.dismiss());
        mView.findViewById(R.id.circle_tv).setOnClickListener(v -> mBottomSheetDialog.dismiss());
        mView.findViewById(R.id.cancle_tv).setOnClickListener(v -> mBottomSheetDialog.dismiss());
    }

    public void showEditDialog(ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list,
                               ThemeInfoEntity.ThemeInfoBean.ParentThemeInfoBean circleInfoEntity, String url,
                               String weburl, String title, String imgUrl,
                               String desc, boolean isSmall, boolean isVisible) {
        if (mEditDialog == null) {
            mEditDialog = new BottomSheetDialog(this, R.style.bottom_sheet_dialog);
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
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                    false));
            mAdapter = new EditDialogAdapter(R.layout.adapter_edit_dialog, mItemList, data);
            recyclerView.setAdapter(mAdapter);
        }
        mEditView.findViewById(R.id.discover_tv).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mEditView.findViewById(R.id.circle_tv).setVisibility(isVisible ? View.GONE : View.VISIBLE);

        if (mPresenter.mDataManager.getUser().getUser_id().equals(circleInfoEntity.getUser_id() + "")) {//如果是圈主
            if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
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
            if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
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
//                    if (mPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
//                        Intent intent = new Intent(mContext, PublishActivity.class);
//                        intent.putExtra("themeInfo", data);
//                        intent.putExtra("circle_name", circleInfoEntity.get());
//                        startActivity(intent);
//                    }
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
                    Intent intent = new Intent(this, ComplaintReasonActivity.class);
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
                DialogUtils.shareSmallProgram(url, imgUrl, title, desc, this, SHARE_MEDIA.WEIXIN);
            } else {
                DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, this, SHARE_MEDIA.WEIXIN);
            }
            mEditDialog.dismiss();
        });
        mEditView.findViewById(R.id.wechat_circle_tv).setOnClickListener(v -> {
            DialogUtils.shareWebUrl(weburl, title, imgUrl, desc, this, SHARE_MEDIA.WEIXIN_CIRCLE);
            mEditDialog.dismiss();
        });
        mEditView.findViewById(R.id.discover_tv).setOnClickListener(v -> mEditDialog.dismiss());
        mEditView.findViewById(R.id.circle_tv).setOnClickListener(v -> mEditDialog.dismiss());
        mEditView.findViewById(R.id.cancle_tv).setOnClickListener(v -> mEditDialog.dismiss());
    }

    @Override
    public void handleruserShieldRecordSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        list.remove(i);
        mDiscoverHAdapter.notifyItemChanged(i);
    }

    private void showDeleteDialog(ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        mDeleteDialog = new BaseDialog.Builder(this)
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
        textView.setText("确定删除此条评论？");
        mDeleteDialog.show();
    }

    @Override
    public void handlerDeleteSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list) {
        list.remove(i);
        mDiscoverHAdapter.notifyItemChanged(i);
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
        mDiscoverHAdapter.notifyItemChanged(i);
    }

}