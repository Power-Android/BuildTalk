package com.bjjy.buildtalk.ui.circle;

import android.support.v4.app.Fragment;
import android.view.View;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;

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
    }

    interface Presenter extends IPresenter<View>{

    }
}
