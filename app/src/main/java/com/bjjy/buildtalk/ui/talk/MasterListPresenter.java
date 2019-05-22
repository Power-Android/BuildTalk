package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/10 1:59 PM
 * @project BuildTalk
 * @description:
 */
public class MasterListPresenter extends BasePresenter<MasterListContract.View> implements MasterListContract.Presenter {

    @Inject
    public MasterListPresenter() {
    }

    public void masterList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        mView.handlerMasterList(list);
    }
}
