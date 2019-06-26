package com.bjjy.buildtalk.ui.talk;

import android.view.View;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.MasterDetailEntity;
import com.bjjy.buildtalk.entity.SearchResultEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/6/24 5:39 PM
 * @project BuildTalk
 * @description:
 */
public class CircleManDetailContract {

    interface View extends IView{

        void handlerTab(List<String> titleList, List<android.view.View> views);

        void handlerUserDetail(MasterDetailEntity detailEntity);

        void handlerCreate(SearchResultEntity resultEntity, boolean isRefresh);

        void handlerJoin(SearchResultEntity resultEntity, boolean isRefresh);

        void handlerAttrntion(BaseResponse<IEntity> baseResponse);
    }

    interface Presenter extends IPresenter<View>{

    }
}
