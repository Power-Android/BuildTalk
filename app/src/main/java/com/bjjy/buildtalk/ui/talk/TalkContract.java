package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CircleMasterEntity;
import com.bjjy.buildtalk.entity.IndustryMasterEntity;
import com.bjjy.buildtalk.entity.TalkEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/4/26 4:36 PM
 * @project BuildTalk
 * @description:
 */
public class TalkContract {
    interface View extends IView {
        void handlerTalkType(List<TalkEntity> talkEntityList);

        void handlerTalkMaster(IndustryMasterEntity industryMasterEntity);

        void handlerCircleMaster(List<CircleMasterEntity> list);
    }

    interface Presenter extends IPresenter<View> {

    }
}
