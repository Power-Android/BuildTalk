package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.CircleListEntity;
import com.bjjy.buildtalk.entity.IEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/10 2:50 PM
 * @project BuildTalk
 * @description:
 */
public class CircleListContract {

    interface View extends IView{

        void handlerCircleList(CircleListEntity circleListEntity, boolean isRefresh);

        void handlerAttrntion(BaseResponse<IEntity> baseResponse, List<CircleListEntity.CircleInfoBean> mList, int i);
    }

    interface Presenter extends IPresenter<View>{

    }
}
