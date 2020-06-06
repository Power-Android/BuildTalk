package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.adapter.PublishCircleAdapter;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.IEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/6/13 9:39 AM
 * @project BuildTalk
 * @description:
 */
public class PublishContarct {

    interface View extends IView{

        void handlerPublishSuccess(String iEntity);

        void handlerSignSuccess(String sign);

        void handlerCircleListSuccess(List<IEntity> iEntities, PublishCircleAdapter adapter);
    }

    interface Presenter extends IPresenter<View>{

    }
}
