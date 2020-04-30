package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.MemberEntity;

/**
 * @author power
 * @date 2019/6/12 10:18 AM
 * @project BuildTalk
 * @description:
 */
public class CircleMemberContract {

    interface View extends IView{

        void handlerCircleUser1(MemberEntity iEntity, boolean isRefresh);
        void handlerCircleUser2(MemberEntity iEntity, boolean isRefresh);
        void handlerCircleUser3(MemberEntity iEntity, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View>{

    }
}
