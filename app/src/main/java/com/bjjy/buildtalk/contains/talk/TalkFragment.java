package com.bjjy.buildtalk.contains.talk;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.fragment.BaseFragment;

/**
 * @author power
 * @date 2019/4/26 4:35 PM
 * @project BuildTalk
 * @description:
 */
public class TalkFragment extends BaseFragment<TalkPresnter> implements TalkContract.View{

    public static TalkFragment newInstance() {
        return new TalkFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_talk;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEventAndData() {

    }
}
