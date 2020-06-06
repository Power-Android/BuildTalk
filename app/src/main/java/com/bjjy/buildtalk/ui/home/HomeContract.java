package com.bjjy.buildtalk.ui.home;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.DisrOrAttenEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;

import java.util.List;

/**
 * @author power
 * @date 2020/5/9 2:18 PM
 * @project BuildTalk
 * @description:
 */
public class HomeContract {
    interface View extends IView{

        void handlerDiscover(DisrOrAttenEntity disrOrAttenEntity);

        void handlerAttention(DisrOrAttenEntity disrOrAttenEntity);

        void handlerThumbSuccess(String thumb_url, List<DisrOrAttenEntity.ThemeInfoBean> data, int position, boolean isEdit);

        void handlerPraiseSuccess(List<DisrOrAttenEntity.ThemeInfoBean> mList, int position, PraiseEntity praiseEntity);

        void handlerAttentUser(BaseResponse<IEntity> baseResponse, List<DisrOrAttenEntity.ThemeInfoBean> data, int position);
    }

    interface Presenter extends IPresenter<View>{

    }
}
