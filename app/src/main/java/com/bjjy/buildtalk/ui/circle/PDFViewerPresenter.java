package com.bjjy.buildtalk.ui.circle;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.utils.DownloadUtil;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019-11-21 09:23
 * @project BuildTalk
 * @description:
 */
public class PDFViewerPresenter extends BasePresenter<PDFViewerContract.View>{

    @Inject
    public PDFViewerPresenter() {

    }

    public void downloadPDF(String url, String name) {
        DownloadUtil.download(url, name, new DownloadUtil.OnDownloadListener() {

            @Override
            public void onDownloadSuccess(String path, File file) {
                mView.handlerFile(file);
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed(String msg) {
                mView.hideLoading();
            }
        });
    }

    public void collectTheme(String theme_id) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("theme_id", theme_id);
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
                        mView.handlerCollectSuccess(iEntity);
                    }
                }));
    }
}
