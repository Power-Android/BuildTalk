package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.SearchResultEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/14 4:27 PM
 * @project BuildTalk
 * @description:
 */
public class MasterCircleContract {

    interface View extends IView{

        void handlerTab(List<String> titleList, List<android.view.View> views);

        void handlerCreate(SearchResultEntity resultEntity, boolean isRefresh);

        void handlerJoin(SearchResultEntity resultEntity, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View>{

    }
}
