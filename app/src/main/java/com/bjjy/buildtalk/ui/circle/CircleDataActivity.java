package com.bjjy.buildtalk.ui.circle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.SearchCircleInfoEntity;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class CircleDataActivity extends BaseActivity<CircleDataPresenter> implements CircleDataContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarBack;
    @BindView(R.id.circle_iv)
    RoundedImageView mCircleIv;
    @BindView(R.id.circle_title_tv)
    TextView mCircleTitleTv;
    @BindView(R.id.view2)
    RelativeLayout mView2;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.edit_text)
    TextView mEditText;
    private String mCircle_id;
    private List<String> mCircle_tags = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_data;
    }

    @Override
    protected void initView() {
        mCircle_id = getIntent().getStringExtra("circle_id");
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarTitle.setText("圈子资料");
    }

    @Override
    protected void initEventAndData() {
        mPresenter.searchCircleInfo(mCircle_id);
    }

    @Override
    public void handlerSearchCircleInfo(SearchCircleInfoEntity infoEntity) {
        Glide.with(this).load(infoEntity.getCircle_image().getPic_url()).into(mCircleIv);
        mCircleTitleTv.setText(infoEntity.getCircle_name());
        mEditText.setText(infoEntity.getCircle_desc());
        String circle_tags = infoEntity.getCircle_tags();
        mCircle_tags = Arrays.asList(circle_tags.split(","));
        mFlowLayout.setAdapter(new TagAdapter<String>(mCircle_tags) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = LayoutInflater.from(CircleDataActivity.this)
                        .inflate(R.layout.flow_create_circle, parent, false);
                TextView tv = view.findViewById(R.id.tag_tv);
                tv.setText(s);
                return view;
            }
        });
    }
}
