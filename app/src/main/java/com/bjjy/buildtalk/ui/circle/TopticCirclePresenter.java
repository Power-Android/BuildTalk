package com.bjjy.buildtalk.ui.circle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.CircleTopticAdapter;
import com.bjjy.buildtalk.adapter.MasterArticleAdapter;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.base.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/22 11:13 AM
 * @project BuildTalk
 * @description:
 */
public class TopticCirclePresenter extends BasePresenter<TopticCircleContract.View> implements TopticCircleContract.Presenter {

    private List<String> mTitleList = new ArrayList<>();
    private List<View> mViews = new ArrayList<>();
    private List<Integer> mBadgeCountList = new ArrayList<>();
    private View mThemeView, mEssenceView;

    @Inject
    public TopticCirclePresenter() {

    }

    public void tabData() {
        mTitleList.add(App.getContext().getString(R.string.theme));
        mTitleList.add(App.getContext().getString(R.string.essence));

        mBadgeCountList.add(0);
        mBadgeCountList.add(2);

        mThemeView = LayoutInflater.from(App.getContext()).inflate(R.layout.circle_toptic_theme, null, false);
        mEssenceView = LayoutInflater.from(App.getContext()).inflate(R.layout.circle_toptic_essence, null, false);
        mViews.add(mThemeView);
        mViews.add(mEssenceView);

        mView.handlerTab(mTitleList, mViews, mBadgeCountList);

        setAdapter();
    }

    private void setAdapter() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        RecyclerView recyclerView = mThemeView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        CircleTopticAdapter topticAdapter = new CircleTopticAdapter(R.layout.adapter_article_toptic, list);
        View footerView = LayoutInflater.from(App.getContext()).inflate(R.layout.footer_circle_toptic,null,false);
        topticAdapter.addFooterView(footerView);
        recyclerView.setAdapter(topticAdapter);

    }
}
