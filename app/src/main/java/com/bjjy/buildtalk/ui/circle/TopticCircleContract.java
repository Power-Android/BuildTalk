package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/22 11:13 AM
 * @project BuildTalk
 * @description:
 */
public class TopticCircleContract {

    interface View extends IView{

        void handlerTab(List<String> titleList, List<android.view.View> viewList, List<Integer> badgeCountList);

        void handlerCircleInfo(CircleInfoEntity circleInfoEntity);

        void handlerThemeInfo(ThemeInfoEntity themeInfoEntity, boolean isRefresh);

        void handlerJoinSuccess(IEntity iEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
