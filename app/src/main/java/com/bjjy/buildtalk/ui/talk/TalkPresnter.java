package com.bjjy.buildtalk.ui.talk;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.TalkAdapter;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.CircleMasterEntity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.IndustryMasterEntity;
import com.bjjy.buildtalk.entity.TalkEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.bjjy.buildtalk.ui.discover.DiscoverFragment.HOT_TOPTIC_PAGE;

/**
 * @author power
 * @date 2019/4/26 4:39 PM
 * @project BuildTalk
 * @description:
 */
public class TalkPresnter extends BasePresenter<TalkContract.View> implements TalkContract.Presenter {
    private int pageCount = 1;

    @Inject
    public TalkPresnter() {

    }

    public void talkType(List<TalkEntity> talkEntityList) {
        talkEntityList.add(new TalkEntity(TalkAdapter.BODY_MASTER));
        talkEntityList.add(new TalkEntity(TalkAdapter.BODY_CIRCLE_MAN));
        mView.handlerTalkType(talkEntityList);
    }

    public void talkMaster() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        if (TalkFragment.PAGE <= pageCount){
            paramas.put(App.getContext().getString(R.string.PAGE),HOT_TOPTIC_PAGE+"");
        }else {
            TalkFragment.PAGE = 1;
            paramas.put("page",TalkFragment.PAGE+"");
        }
        paramas.put(App.getContext().getString(R.string.PAGE_SIZE),"3");
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.searchIndustryMaster(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<IndustryMasterEntity>(mView,false){
                    @Override
                    public void onSuccess(IndustryMasterEntity industryMasterEntity) {
                        pageCount = industryMasterEntity.getPage_count();
                        mView.handlerTalkMaster(industryMasterEntity);
                    }
                }));
    }

    public void talkCircleMaster() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("show", "1");
        if (mDataManager.getLoginStatus()){
            paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        }else {
            paramas.put(Constants.USER_ID, "");
        }
        paramas.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);

        addSubscribe(mDataManager.searchCircleMaster(headers,paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<List<CircleMasterEntity>>(mView,false){
                    @Override
                    public void onSuccess(List<CircleMasterEntity> list) {
                        mView.handlerCircleMaster(list);
                    }
                }));
    }
}
