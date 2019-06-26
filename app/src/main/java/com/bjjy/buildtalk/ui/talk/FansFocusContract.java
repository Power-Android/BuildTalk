package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.FansFocusEntity;
import com.bjjy.buildtalk.entity.IEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/14 4:41 PM
 * @project BuildTalk
 * @description:
 */
public class FansFocusContract {
    interface View extends IView{

        void handlerMyFans(FansFocusEntity fansFocusEntity, boolean isRefresh);

        void handlerMyAttention(FansFocusEntity fansFocusEntity, boolean isRefresh);

        void handlerAttrntion(BaseResponse<IEntity> baseResponse, List<FansFocusEntity.MyFansInfoBean> mMyFansInfo, int i);

        void handlerAttrntion1(BaseResponse<IEntity> baseResponse, List<FansFocusEntity.AttentionInfoBean> mAttentionInfoBeans, int i);
    }

    interface Presenter extends IPresenter<View>{

    }
}
