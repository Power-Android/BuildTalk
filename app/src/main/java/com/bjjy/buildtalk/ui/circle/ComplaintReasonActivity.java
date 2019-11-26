package com.bjjy.buildtalk.ui.circle;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.ReasonEntity;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ComplaintReasonActivity extends BaseActivity<ComplaintReasonPresenter> implements ComplaintReasonContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.content_et)
    EditText mContentEt;

    private List<ReasonEntity> mList = new ArrayList<>();
    private String mData_id;
    private ReasonAdapter mReasonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complaint_reason;
    }

    @Override
    protected void initView() {
        mData_id = getIntent().getStringExtra("data_id");

        mToolbarTitle.setText("投诉理由");
        mToolbarRightTitle.setText("提交");
        mToolbarRightTitle.setTextColor(getResources().getColor(R.color.blue_mid));

        mList.add(new ReasonEntity("垃圾广告", true));
        mList.add(new ReasonEntity("色情内容"));
        mList.add(new ReasonEntity("网络欺诈"));
        mList.add(new ReasonEntity("违法犯罪"));
        mList.add(new ReasonEntity("抄袭"));
        mList.add(new ReasonEntity("不实信息"));
        mList.add(new ReasonEntity("其他"));
    }

    @Override
    protected void initEventAndData() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mReasonAdapter = new ReasonAdapter(R.layout.adapter_complain_reason, mList);
        mRecyclerView.setAdapter(mReasonAdapter);
        mReasonAdapter.setOnItemClickListener(this);
    }

    @OnClick({R.id.toolbar_left_back, R.id.toolbar_right_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_back:
                finish();
                break;
            case R.id.toolbar_right_title:
                List<String> checkList = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).isChecked()) {
                        checkList.add(mList.get(i).getContent());
                    }
                }
                if (!TextUtils.isEmpty(mContentEt.getText().toString().trim())) {
                    checkList.add(mContentEt.getText().toString().trim());
                }
                String complain_content = StringUtils.listToString(checkList);

                mPresenter.complain(mData_id, complain_content);
                break;
        }
    }

    @Override
    public void handlerComplain(IEntity iEntity) {
        ToastUtils.showShort("投诉成功");
        finish();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<ReasonEntity> data = adapter.getData();
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setChecked(false);
        }
        data.get(position).setChecked(true);
        mReasonAdapter.notifyDataSetChanged();
    }

    private class ReasonAdapter extends BaseQuickAdapter<ReasonEntity, BaseViewHolder> {

        public ReasonAdapter(int layoutResId, @Nullable List<ReasonEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ReasonEntity item) {
            RelativeLayout bgRl = helper.getView(R.id.item_bg_rl);
            TextView contentTv = helper.getView(R.id.item_content_tv);
            contentTv.setText(item.getContent());
            if (item.isChecked()) {
                bgRl.setBackground(getDrawable(R.drawable.shape_reason_sel));
                contentTv.setTextColor(getResources().getColor(R.color.blue_mid));
            } else {
                bgRl.setBackground(getDrawable(R.drawable.shape_reason_def));
                contentTv.setTextColor(getResources().getColor(R.color.text_light));
            }
        }
    }
}
