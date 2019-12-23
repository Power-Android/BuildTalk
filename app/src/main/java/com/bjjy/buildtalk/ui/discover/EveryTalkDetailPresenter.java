package com.bjjy.buildtalk.ui.discover;

import android.text.TextUtils;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.EveryTalkDetailEntity;
import com.bjjy.buildtalk.entity.GuestBookEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PayOrderEntity;
import com.bjjy.buildtalk.entity.SaveRecordEntity;
import com.bjjy.buildtalk.entity.SongsEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.PlayerHelper;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

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

    public void everyTalkDetail(String article_id, String type_zhuanti) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (mDataManager.getLoginStatus()) {
            paramas.put("user_id", mDataManager.getUser().getUser_id());
        } else {
            paramas.put("user_id", "");
        }
        if (!TextUtils.isEmpty(type_zhuanti)) {
            paramas.put("type", type_zhuanti);
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
                .subscribeWith(new BaseObserver<EveryTalkDetailEntity>(mView, true) {
                    @Override
                    public void onSuccess(EveryTalkDetailEntity everyTalkDetailEntity) {
                        mView.handlerTalkDetail(everyTalkDetailEntity);
                    }
                }));
    }

    public void guestbook(String article_id, int page, boolean isRefresh) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("article_id", article_id);
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
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
                .subscribeWith(new BaseObserver<GuestBookEntity>(mView, true) {
                    @Override
                    public void onSuccess(GuestBookEntity guestBookEntity) {
                        mView.handlerGuestBookList(guestBookEntity, isRefresh);
                    }
                }));
    }

    public void saveRecord(int article_id, String content, String type_zhuanti) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("user_id", mDataManager.getUser().getUser_id());
        paramas.put("article_id", article_id + "");
        if (!TextUtils.isEmpty(type_zhuanti)) {
            paramas.put("type", type_zhuanti);
        }
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

    public void praiseRecord(String type_zhuanti, int guestbook_id, int position, boolean isPraise) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("user_id", mDataManager.getUser().getUser_id());
        paramas.put("guestbook_id", guestbook_id + "");
        if (!TextUtils.isEmpty(type_zhuanti)) {
            paramas.put("type", type_zhuanti);
        }
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

    public void collectArticle(String article_id, boolean isCollect, String type_zhuanti) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("user_id", mDataManager.getUser().getUser_id());
        paramas.put("article_id", article_id + "");
        if (!TextUtils.isEmpty(type_zhuanti)) {
            paramas.put("type", type_zhuanti);
        }
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

    public void deleteComment(String type_zhuanti, String comment_id, List<GuestBookEntity.GuestbookInfoBean> mComment_content, int i) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("guestbook_id", comment_id);
        if (TextUtils.isEmpty(type_zhuanti)) {
            paramas.put("type", "1");
        } else if (TextUtils.equals("2", type_zhuanti)) {
            paramas.put("type", "3");
        }
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
                        mView.handlerDeleteComment(mComment_content, i);
                    }
                }));
    }

    public void payOrder(String type_id, int data_id, String circle_name, String course_money) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("type_id", type_id);
        paramas.put("data_id", data_id + "");
        paramas.put("order_name", circle_name);
        paramas.put("order_price", course_money);
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.payOrder(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<PayOrderEntity>(mView, true) {
                    @Override
                    public void onSuccess(PayOrderEntity payOrderEntity) {
                        mView.handlerWxOrder(payOrderEntity);
                    }
                }));
    }

    public void requestSongs(String article_id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("article_id", article_id);
        paramas.put("sort", "2");
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);
        addSubscribe(mDataManager.searchAudioList(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .subscribeWith(new BaseObserver<List<SongsEntity>>() {
                    @Override
                    public void onSuccess(List<SongsEntity> songsEntities) {
                            mDataManager.addSongsData(songsEntities);
                            mView.handlerSongs();
                    }
                }));
    }
}
