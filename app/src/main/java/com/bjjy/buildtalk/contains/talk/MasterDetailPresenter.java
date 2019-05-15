package com.bjjy.buildtalk.contains.talk;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.MasterArticleAdapter;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/14 9:43 AM
 * @project BuildTalk
 * @description:
 */
public class MasterDetailPresenter extends BasePresenter<MasterDetailContract.View> implements MasterDetailContract.Presenter {

    private List<String> list = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private View mArticleView;
    private View mIntroductionView;

    @Inject
    public MasterDetailPresenter() {

    }

    public void tabData() {
        list.add(App.getContext().getString(R.string.exquisite_article));
        list.add(App.getContext().getString(R.string.personal_introduction));

        mArticleView = LayoutInflater.from(App.getContext()).inflate(R.layout.master_exquisite_article, null, false);
        mIntroductionView = LayoutInflater.from(App.getContext()).inflate(R.layout.master_personal_introduction, null, false);
        views.add(mArticleView);
        views.add(mIntroductionView);

        mView.handlerTab(list, views);

        setAdapter();
    }

    private void setAdapter() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        RecyclerView articleRecyclerView = mArticleView.findViewById(R.id.recycler_view);
        articleRecyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        MasterArticleAdapter masterArticleAdapter = new MasterArticleAdapter(R.layout.adapter_master_article, list);
        articleRecyclerView.setAdapter(masterArticleAdapter);
    }
}
