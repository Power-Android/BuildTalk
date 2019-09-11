package com.bjjy.buildtalk.ui.mine;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.VersionRecordEntity;

import java.util.List;

/**
 * @author power
 * @date 2019-09-02 14:31
 * @project BuildTalk
 * @description:
 */
public class VersionRecordContarct {

    interface View extends IView{

        void handlerVersionRecord(List<VersionRecordEntity> versionRecordEntities);

    }

    interface Presenter extends IPresenter<View>{

    }
}
