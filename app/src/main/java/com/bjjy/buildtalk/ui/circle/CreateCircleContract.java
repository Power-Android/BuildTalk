package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CircleTagEntity;
import com.bjjy.buildtalk.entity.IEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/16 4:53 PM
 * @project BuildTalk
 * @description:
 */
public class CreateCircleContract {
    interface View extends IView{

        void handlerTagList(List<CircleTagEntity> circleTagEntityList);

        void handlerCreateSuccess(IEntity iEntity);
    }
    interface Presenter extends IPresenter<View>{

    }
}
