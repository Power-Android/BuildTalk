package com.bjjy.buildtalk.ui.home;

import com.bjjy.buildtalk.adapter.PublishCircleAdapter;
import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

import java.util.List;

/**
 * @author power
 * @date 2020/5/9 2:18 PM
 * @project BuildTalk
 * @description:
 */
public class HomeContract {
    interface View extends IView{

        void handlerDiscover(ThemeInfoEntity disrOrAttenEntity);

        void handlerAttention(ThemeInfoEntity disrOrAttenEntity);

        void handlerThumbSuccess(String thumb_url, List<ThemeInfoEntity.ThemeInfoBean> data, int position, boolean isEdit);

        void handlerPraiseSuccess(List<ThemeInfoEntity.ThemeInfoBean> mList, int position, PraiseEntity praiseEntity);

        void handlerAttentUser(BaseResponse<IEntity> baseResponse, List<ThemeInfoEntity.ThemeInfoBean> data, int position);

        void handlerCollectSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i);

        void handlerDeleteSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list);

        void handleruserShieldRecordSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list);

        void handlerCircleListSuccess(List<IEntity> iEntities, PublishCircleAdapter circleAdapter);
    }

    interface Presenter extends IPresenter<View>{

    }
}
