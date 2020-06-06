package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/6/19 9:33 AM
 * @project BuildTalk
 * @description:
 */
public class TopticDetailContract {

    interface View extends IView{

        void handlerThemeInfo(ThemeInfoEntity.ThemeInfoBean themeInfoEntity);

        void handlerCommentList(ThemeInfoEntity.ThemeInfoBean themeInfoBean, boolean isRefresh);

        void handlerCollectSuccess(IEntity iEntity);

        void handlerDeleteSuccess(IEntity iEntity);

        void handlerPraiseSuccess(PraiseEntity praiseEntity, String type, List<CommentContentBean> mComment_content);

        void handlerDeleteComment(List<CommentContentBean> mComment_content);

        void handlerCommentSuccess(List<CommentContentBean> commentInfo);

        void handlerThumbSuccess(String thumb_url, ThemeInfoEntity.ThemeInfoBean themeInfoEntity);

        void handlerAttentUser(BaseResponse<IEntity> baseResponse, ThemeInfoEntity.ThemeInfoBean themeInfoEntity);

        void handlerChoicenessSuccess(IEntity iEntity, ThemeInfoEntity.ThemeInfoBean data);
    }

    interface Presenter extends IPresenter<View>{

    }

}
