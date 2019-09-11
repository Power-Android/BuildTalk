package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.CourseListEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/5/24 10:29 AM
 * @project BuildTalk
 * @description:
 */
public class CourseCircleContract {

    interface View extends IView{

        void handlerTab(List<String> titleList, List<android.view.View> views, List<Integer> badgeCountList);

        void handlerCircleInfo(CircleInfoEntity circleInfoEntity);

        void handlerThemeInfo(ThemeInfoEntity themeInfoEntity, boolean isRefresh);

        void handlerCourseList(CourseListEntity courseListEntity);

        void handlerWxOrder(PayOrderEntity payOrderEntity);

        void handlerPraiseSuccess(List<ThemeInfoEntity.ThemeInfoBean> data, int i, PraiseEntity praiseEntity);

        void handlerCollectSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i);

        void handlerDeleteSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list);

        void handlerCommentSuccess(int i, List<ThemeInfoEntity.ThemeInfoBean> data, List<CommentContentBean> commentInfo);

        void handlerEssenceInfo(ThemeInfoEntity themeInfoEntity, boolean isRefresh);

        void handlerChoicenessSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i);

        void handlerThumbSuccess(String thumb_url, List<ThemeInfoEntity.ThemeInfoBean> data, int i);

        void handleruserShieldRecordSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list);
    }

    interface Presenyer extends IPresenter<View>{

    }
}
