package com.bjjy.buildtalk.ui.video;

import android.text.TextUtils;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CommentContentBean;
import com.bjjy.buildtalk.entity.CommentSuccessEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ShortVideoEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2020/5/12 2:25 PM
 * @project BuildTalk
 * @description:
 */
public class ShortVideoPresenter extends BasePresenter<ShortVideoContract.View> implements ShortVideoContract.Presenter {

    @Inject
    public ShortVideoPresenter() {

    }

    public void getVideoList(String type_id, String theme_id, String user_id, int page) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (mDataManager.getLoginStatus()){
            paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        }
        paramas.put("type_id", type_id);
        paramas.put("theme_id", theme_id);
        paramas.put(Constants.PAGE, String.valueOf(page));
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.searchVideoTheme(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<ShortVideoEntity>(mView, false) {
                    @Override
                    public void onSuccess(ShortVideoEntity shortVideoEntity) {
                        mView.handlerVideoSuccess(shortVideoEntity);
                    }
                }));
    }

    public void attenUser(List<ShortVideoEntity.ThemeInfoBean> data, int position) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("examine_user", mDataManager.getUser().getUser_id());
        paramas.put(Constants.USER_ID, String.valueOf(data.get(position).getUser_id()));
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.attention(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView,false){
                    @Override
                    public void onSuccess(IEntity iEntity) {
//                        mView.handlerUserDetail(detailEntity);
                    }

                    @Override
                    public void onNext(BaseResponse<IEntity> baseResponse) {
                        super.onNext(baseResponse);
                        if (baseResponse.getErrorCode() == 1){
                            mView.handlerAttentUser(baseResponse, data, position);
                        }
                    }
                }));
    }

    public void praise(List<ShortVideoEntity.ThemeInfoBean> data, String type_id, int position) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("data_id", data.get(position).getTheme_id()+"");
        paramas.put("type_id", type_id);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.themeParise(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<PraiseEntity>(mView, false) {
                    @Override
                    public void onSuccess(PraiseEntity praiseEntity) {
                        mView.handlerPraiseSuccess(data,position,praiseEntity);
                    }
                }));
    }

    public void commentList(int theme_id, int commentPage, int adapterPosition) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (mDataManager.getLoginStatus()){
            paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        }
        paramas.put("theme_id", String.valueOf(theme_id));
        paramas.put(Constants.PAGE, commentPage+"");
        paramas.put(Constants.PAGE_SIZE, "10");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.commentPageHandle(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<ThemeInfoEntity.ThemeInfoBean>(mView, false) {
                    @Override
                    public void onSuccess(ThemeInfoEntity.ThemeInfoBean themeInfoBean) {
                        mView.handlerCommentList(themeInfoBean, adapterPosition);
                    }
                }));
    }

    public void praise1(List<CommentContentBean> mComment_content, String type_id, int position) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("data_id", mComment_content.get(position).getComment_id()+"");
        paramas.put("type_id", type_id);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.themeParise(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<PraiseEntity>(mView, false) {
                    @Override
                    public void onSuccess(PraiseEntity praiseEntity) {
                        mView.handlerPraiseSuccess1(praiseEntity, type_id, mComment_content, position);
                    }
                }));
    }

    public void deleteComment(String comment_id, List<CommentContentBean> mComment_content, int i, int adapterPosition) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("guestbook_id", comment_id);
        paramas.put("type", "2");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.deleteGuestbook(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, false) {
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.handlerDeleteComment(mComment_content, i, adapterPosition);
                    }
                }));
    }

    public void publishComment(String content, String theme_id, String comment_id, String parentCommentId, int adapterPosition) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("theme_id", theme_id);
        paramas.put("content", content);
        paramas.put("parentCommentId", TextUtils.equals("0", parentCommentId) ? comment_id : parentCommentId);
        paramas.put("reply_commentId", TextUtils.isEmpty(comment_id) ? "" : comment_id);//如果是回复他人的评论，传他人的评论id,否则传空
        paramas.put("publish_type", "1");//1 返回主题详情外评论样式 2返回主题详情页评论样式
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.publishComment(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<CommentSuccessEntity>(mView, false) {
                    @Override
                    public void onSuccess(CommentSuccessEntity commentSuccessEntity) {
                        mView.handlerCommentSuccess(commentSuccessEntity.getCommentInfo(), adapterPosition);
                    }
                }));
    }

    public void videoBrowse(int theme_id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("data_id", String.valueOf(theme_id));
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.themeVideoBrowse(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, false) {
                    @Override
                    public void onSuccess(IEntity iEntity) {

                    }
                }));
    }

    public void collectTheme(List<ShortVideoEntity.ThemeInfoBean> data, int position) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("theme_id", data.get(position).getTheme_id()+"");
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.collectTheme(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, false) {
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.handlerCollectSuccess(iEntity,data, position);
                    }
                }));
    }
}
