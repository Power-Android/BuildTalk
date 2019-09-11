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
 * @date 2019/7/1 4:33 PM
 * @project BuildTalk
 * @description:
 */
public class CourseDetailContarct {

    interface View extends IView{

        void handlerCourseList(CourseListEntity courseListEntity);

        void handlerThemeInfo(ThemeInfoEntity themeInfoEntity, boolean isRefresh);

        void handlerThemeInfoEmpty(List<ThemeInfoEntity.ThemeInfoBean> themeInfo);

        void handlerCommentSuccess(int i, List<ThemeInfoEntity.ThemeInfoBean> data, List<CommentContentBean> commentInfo);

        void handlerDeleteSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list);

        void handlerCollectSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data, int i);

        void handlerPraiseSuccess(List<ThemeInfoEntity.ThemeInfoBean> data, int i, PraiseEntity praiseEntity);

        void handlerWxOrder(PayOrderEntity payOrderEntity);

        void handlerCircleInfo(CircleInfoEntity circleInfoEntity);

        void handlerThumbSuccess(String thumb_url, List<ThemeInfoEntity.ThemeInfoBean> data, int i);
    }

    interface Presenter extends IPresenter<View>{

    }
}
