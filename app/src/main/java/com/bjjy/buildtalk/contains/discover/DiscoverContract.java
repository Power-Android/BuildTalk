package com.bjjy.buildtalk.contains.discover;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.DiscoverEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/4/26 4:53 PM
 * @project BuildTalk
 * @description:
 */
public class DiscoverContract {
    interface View extends IView{
        void handlerDiscoverType(List<DiscoverEntity> discoverEntityList);
    }

    interface Presenter extends IPresenter<View>{

    }
}
