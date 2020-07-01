package com.bjjy.buildtalk.ui.video;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ShortVideoEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

import java.util.List;

/**
 * @author power
 * @date 2020/5/12 2:25 PM
 * @project BuildTalk
 * @description:
 */
public class ShortVideoContract {
    interface View extends IView{

        void handlerVideoSuccess(ShortVideoEntity shortVideoEntity);

        void handlerAttentUser(BaseResponse<IEntity> response, List<ShortVideoEntity.ThemeInfoBean> data, int position);

        void handlerPraiseSuccess(List<ShortVideoEntity.ThemeInfoBean> data, int position, PraiseEntity praiseEntity);

        void handlerCommentList(ThemeInfoEntity.ThemeInfoBean themeInfoBean, int adapterPosition);

        void handlerPraiseSuccess1(PraiseEntity praiseEntity, String type_id, List<CommentContentBean> mComment_content, int position);

        void handlerDeleteComment(List<CommentContentBean> mComment_content, int i, int adapterPosition);

        void handlerCommentSuccess(List<CommentContentBean> commentInfo, int adapterPosition);

        void handlerCollectSuccess(IEntity iEntity, List<ShortVideoEntity.ThemeInfoBean> data, int position);
    }

    interface Presenter extends IPresenter<View>{

    }

}
