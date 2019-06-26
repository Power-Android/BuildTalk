package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.MasterDetailEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/14 9:42 AM
 * @project BuildTalk
 * @description:
 */
public class MasterDetailContract {

    interface View extends IView{

        void handlerTab(List<String> list, List<android.view.View> views);

        void handlerUserDetail(MasterDetailEntity detailEntity);

        void handlerAttrntion(BaseResponse<IEntity> baseResponse);
    }

    interface Presenter extends IPresenter<View>{

    }
}
