package com.bjjy.buildtalk.contains.mine;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.fragment.BaseFragment;

/**
 * @author power
 * @date 2019/4/26 4:34 PM
 * @project BuildTalk
 * @description:
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View{

    public static MineFragment newInstance(){
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEventAndData() {

    }
}
