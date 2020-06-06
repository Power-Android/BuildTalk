package com.bjjy.buildtalk.ui.video;

import android.text.TextUtils;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.PraiseEntity;
import com.bjjy.buildtalk.entity.ShortVideoEntity;
import com.bjjy.buildtalk.entity.ThemeVideoBean;
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
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
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
}
