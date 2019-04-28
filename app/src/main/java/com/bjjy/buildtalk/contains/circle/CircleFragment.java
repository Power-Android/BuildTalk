package com.bjjy.buildtalk.contains.circle;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.fragment.BaseFragment;

/**
 * @author power
 * @date 2019/4/26 4:33 PM
 * @project BuildTalk
 * @description:
 */
public class CircleFragment extends BaseFragment<CirclePresenter> implements CircleContract.View{

    public static CircleFragment newInstance() {
        return new CircleFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEventAndData() {

    }
}
