package com.bjjy.buildtalk.ui.discover;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.GuestBookEntity;
import com.bjjy.buildtalk.entity.SaveRecordEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/5/7 1:35 PM
 * @project BuildTalk
 * @description:
 */
public class EveryTalkDetailPresenter extends BasePresenter<EveryTalkDetailContract.View> implements EveryTalkDetailContract.Presenter {

    @Inject
    public EveryTalkDetailPresenter() {
    }

    public void everyTalkDetail(String article_id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (mDataManager.getLoginStatus()){
            paramas.put("user_id", mDataManager.getUser().getUser_id());
        }else {
            paramas.put("user_id", "");
        }
        paramas.put("article_id", article_id);
        paramas.put("source", "android");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.everyTalkDetail(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(everyTalkDetailEntity -> mView != null)
                .subscribeWith(new BaseObserver<EveryTalkDetailEntity>(mView, false) {
                    @Override
                    public void onSuccess(EveryTalkDetailEntity everyTalkDetailEntity) {
                        mView.handlerTalkDetail(everyTalkDetailEntity);
                    }
                }));
    }

    public void guestbook(String article_id, int page) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("article_id", article_id);
        paramas.put(Constants.PAGE, String.valueOf(page));
        paramas.put(Constants.PAGE_SIZE, "20");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.guestBookList(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(guestBookEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<GuestBookEntity>(mView, false) {
                    @Override
                    public void onSuccess(GuestBookEntity guestBookEntity) {
                        mView.handlerGuestBookList(guestBookEntity);
                    }
                }));
    }

    public void saveRecord(int article_id, String content) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("user_id", mDataManager.getUser().getUser_id());
        paramas.put("article_id", article_id + "");
        paramas.put("source", "android");
        paramas.put("content", content);
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.saveRecord(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(saveRecordEntity -> mView != null)
                .subscribeWith(new BaseObserver<SaveRecordEntity>(mView) {
                    @Override
                    public void onSuccess(SaveRecordEntity saveRecordEntities) {
                        mView.handlerSaveRecord();
                    }
                }));
    }

    public void praiseRecord(int guestbook_id, int position, boolean isPraise) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("user_id", mDataManager.getUser().getUser_id());
        paramas.put("guestbook_id", guestbook_id+"");
        paramas.put("source", "android");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.praiseRecord(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(testEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView) {
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.praiseSuccess(true, position, isPraise);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        if (code == 0) {
                            mView.praiseSuccess(false, position, isPraise);
                        }
                        super.onFailure(code, message);
                    }
                }));
    }

    public void collectArticle(String article_id, boolean isCollect) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("user_id", mDataManager.getUser().getUser_id());
        paramas.put("article_id", article_id+"");
        paramas.put("source", "android");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.collectArticle(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(testEntityBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView) {
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.collectSuccess(true, isCollect);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        if (code == 0) {
                            mView.collectSuccess(false, isCollect);
                        }
                        super.onFailure(code, message);
                    }
                }));
    }

}
