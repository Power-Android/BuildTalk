package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.IEntity;

import java.io.File;

/**
 * @author power
 * @date 2019-11-21 09:22
 * @project BuildTalk
 * @description:
 */
public class PDFViewerContract {

    interface View extends IView{

        void handlerFile(File file);

        void handlerCollectSuccess(IEntity iEntity);
    }

    interface Presenter extends IPresenter<View>{

    }
}
