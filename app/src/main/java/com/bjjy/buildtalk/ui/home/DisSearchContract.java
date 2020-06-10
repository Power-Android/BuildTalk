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
 * @date 2020/6/10 9:17 AM
 * @project BuildTalk
 * @description:
 */
public class DisSearchContract {

    interface View extends IView{

        void handlerSearch(DisrOrAttenEntity disrOrAttenEntity);

        void handlerThumbSuccess(String thumb_url, List<DisrOrAttenEntity.ThemeInfoBean> data, int i, boolean isEdit);

        void handlerPraiseSuccess(List<DisrOrAttenEntity.ThemeInfoBean> mList, int position, PraiseEntity praiseEntity);

        void handlerAttentUser(BaseResponse<IEntity> baseResponse, List<DisrOrAttenEntity.ThemeInfoBean> data, int position);

    }

    interface Presenter extends IPresenter<View>{

    }
}
