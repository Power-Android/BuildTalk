package com.bjjy.buildtalk.contains.talk;

import com.bjjy.buildtalk.adapter.TalkAdapter;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.entity.TalkEntity;

import java.util.List;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/4/26 4:39 PM
 * @project BuildTalk
 * @description:
 */
public class TalkPresnter extends BasePresenter<TalkContract.View> implements TalkContract.Presenter {
    @Inject
    public TalkPresnter() {

    }

    public void talkType(List<TalkEntity> talkEntityList) {
        talkEntityList.add(new TalkEntity(TalkAdapter.BODY_MASTER));
        talkEntityList.add(new TalkEntity(TalkAdapter.BODY_CIRCLE_MAN));
        mView.handlerTalkType(talkEntityList);
    }
}
