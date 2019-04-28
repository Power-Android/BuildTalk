package com.bjjy.buildtalk.contains.discover;

import com.bjjy.buildtalk.adapter.DiscoverAdapter;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.entity.DiscoverEntity;

import java.util.List;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/4/26 4:54 PM
 * @project BuildTalk
 * @description:
 */
public class DiscoverPresenter extends BasePresenter<DiscoverContract.View> implements DiscoverContract.Presenter {

    @Inject
    public DiscoverPresenter() {
    }

    public void discoverType(List<DiscoverEntity> discoverEntityList) {
        discoverEntityList.add(new DiscoverEntity(DiscoverAdapter.BODY_BANNER));
        discoverEntityList.add(new DiscoverEntity(DiscoverAdapter.BODY_EVERYDAY_TALK));
        discoverEntityList.add(new DiscoverEntity(DiscoverAdapter.BODY_HOT_TOPTIC));
        discoverEntityList.add(new DiscoverEntity(DiscoverAdapter.BODY_COURSE));
        discoverEntityList.add(new DiscoverEntity(DiscoverAdapter.BODY_PROJECT));

        mView.handlerDiscoverType(discoverEntityList);
    }
}
