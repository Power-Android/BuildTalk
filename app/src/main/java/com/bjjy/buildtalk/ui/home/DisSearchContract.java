package com.bjjy.buildtalk.ui.home;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

import java.util.List;

/**
 * @author power
 * @date 2020/6/10 9:17 AM
 * @project BuildTalk
 * @description:
 */
public class DisSearchContract {

    interface View extends IView{

        void handlerSearch(ThemeInfoEntity disrOrAttenEntity);

        void handlerThumbSuccess(String thumb_url, List<ThemeInfoEntity.ThemeInfoBean> data, int i, boolean isEdit);

        void handlerPraiseSuccess(List<ThemeInfoEntity.ThemeInfoBean> mList, int position, PraiseEntity praiseEntity);

        void handlerAttentUser(BaseResponse<IEntity> baseResponse, List<ThemeInfoEntity.ThemeInfoBean> data, int position);

        void handlerCollectSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i);

        void handlerDeleteSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list);

        void handleruserShieldRecordSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list);

    }

    interface Presenter extends IPresenter<View>{

    }
}
