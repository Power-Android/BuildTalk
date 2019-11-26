package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CardInfoEntity;
import com.bjjy.buildtalk.entity.IEntity;

/**
 * @author power
 * @date 2019-10-09 10:10
 * @project BuildTalk
 * @description:
 */
public class IDCardContract {

    interface View extends IView{

        void updateUserInfo(String picUrl);

        void handlerQuery(IEntity iEntity, String picUrl);

        void handlerCommit(CardInfoEntity cardInfoEntity);

        void handlerCommitFailuer(String message);

    }

    interface Presenter extends IPresenter<View>{

    }
}
