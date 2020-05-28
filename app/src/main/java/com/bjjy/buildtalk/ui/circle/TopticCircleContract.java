package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

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

        void handlerCircleInfo(CircleInfoEntity circleInfoEntity);

        void handlerThemeInfo(ThemeInfoEntity themeInfoEntity, boolean isRefresh);

        void handlerJoinSuccess(IEntity iEntity);

        void handlerCommentSuccess(int adapterPosition, int position, List<ThemeInfoEntity.ThemeInfoBean> data, List<CommentContentBean> contentBeanList);

        void handlerPraiseSuccess(List<ThemeInfoEntity.ThemeInfoBean> data, int i, PraiseEntity praiseEntity);

        void handlerCollectSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i);

        void handlerDeleteSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list);

        void handlerChoicenessSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i);

        void handlerEssenceInfo(ThemeInfoEntity themeInfoEntity, boolean isRefresh);

        void handlerThumbSuccess(String thumb_url, List<ThemeInfoEntity.ThemeInfoBean> data, int i, boolean isEdit);

        void handleruserShieldRecordSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list);

        void handlerTopOperateSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i);

        void handlerRetractSuccess(int i);
    }

    interface Presenter extends IPresenter<View>{

    }
}
