package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.entity.TransactionTabEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/29 9:41 AM
 * @project BuildTalk
 * @description:
 */
public class TransactionPresenter extends BasePresenter<TransactionContract.View> implements TransactionContract.Presenter {

    @Inject
    public TransactionPresenter() {

    }

    public void setTab() {
        List<TransactionTabEntity> list = new ArrayList<>();
        list.add(new TransactionTabEntity("全部", true));
        list.add(new TransactionTabEntity("课程", false));
        list.add(new TransactionTabEntity("文章", false));

        mView.handlerTab(list);
    }

    public void setRecord() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        mView.handlerRecord(list);
    }
}
