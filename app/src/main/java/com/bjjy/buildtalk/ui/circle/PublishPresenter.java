package com.bjjy.buildtalk.ui.circle;

import android.text.TextUtils;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PdfInfoEntity;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author power
 * @date 2019/6/13 9:40 AM
 * @project BuildTalk
 * @description:
 */
public class PublishPresenter extends BasePresenter<PublishContarct.View> {

    @Inject
    public PublishPresenter() {

    }

    public void publishTheme(String circle_id, int theme_id, String theme_content, boolean isEdit, List<ThemeImageBean> list) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPart() != null){
                parts.add(list.get(i).getPart());
            }
        }
        if (parts.size() > 0){
            addSubscribe(mDataManager.uploadFiles(parts)
                    .compose(RxUtils.SchedulerTransformer())
                    .filter(stringBaseResponse -> mView != null)
                    .subscribeWith(new BaseObserver<String>(mView, false) {
                        @Override
                        public void onSuccess(String picUrl) {
                            List<String> result = Arrays.asList(picUrl.split(","));
                            for (int i = 0; i < result.size(); i++) {
                                for (int j = 0; j < list.size(); j++) {
                                    if (!list.get(j).getPic_url().contains("https")){
                                        list.get(j).setPic_url(result.get(i));
                                        break;
                                    }
                                }
                            }
                            publish(circle_id, theme_id, theme_content, picUrl, isEdit, list);
                        }

                        @Override
                        public void onFailure(int code, String message) {
                            super.onFailure(code, message);
                            mView.hideLoading();
                        }
                    }));
        }else {
            publish(circle_id, theme_id, theme_content, null, isEdit, list);
        }
    }

    private void publish(String circle_id, int theme_id, String theme_content, String picUrl, boolean isEdit, List<ThemeImageBean> list) {
        if (!isEdit){
            newTheme(circle_id, theme_content, picUrl, null);
        }else {
            picUrl = StringUtils.listToString2(list, ',');
            editTheme(circle_id, theme_id, theme_content, picUrl, null);
        }
    }

    private void newTheme(String circle_id, String theme_content, String picUrl, String pdfUrl) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.TIMESTAMP, timestamp);
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("circle_id", circle_id);
        paramas.put("theme_content", theme_content);
        if (!TextUtils.isEmpty(picUrl)){
            paramas.put("theme_image", picUrl);
        }
        if (!TextUtils.isEmpty(pdfUrl)){
            paramas.put("theme_pdf", pdfUrl);
        }
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.publishTheme(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(baseResponse -> mView != null)
                .subscribeWith(new BaseObserver<String>(mView, false) {
                    @Override
                    public void onSuccess(String pic) {
                        mView.handlerPublishSuccess(pic);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        mView.hideLoading();
                    }
                }));
    }

    private void editTheme(String circle_id, int theme_id, String theme_content, String picUrl, String pdfUrl) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.TIMESTAMP, timestamp);
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("theme_id", theme_id+"");
        paramas.put("theme_content", theme_content);
        if (!TextUtils.isEmpty(picUrl)){
            paramas.put("theme_image", picUrl);
        }
        if (!TextUtils.isEmpty(pdfUrl)){
            paramas.put("theme_pdf", pdfUrl);
        }
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.updateTheme(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(baseResponse -> mView != null)
                .subscribeWith(new BaseObserver<IEntity>(mView, false) {
                    @Override
                    public void onSuccess(IEntity iEntity) {
                        mView.handlerPublishSuccess("");
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        mView.hideLoading();
                    }
                }));
    }

    public void pdf(String circle_id, int theme_id, String theme_content, boolean isEdit, List<PdfInfoEntity> pdfList) {

        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < pdfList.size(); i++) {
            if (!TextUtils.isEmpty(pdfList.get(i).getPath())){
                File file = new File(pdfList.get(i).getPath());
                RequestBody body = RequestBody.create(MediaType.parse("pdf/*"), file);   //说明该文件为pdf类型
                MultipartBody.Part part = MultipartBody.Part.createFormData("file[]", file.getName(), body);
                parts.add(part);
            }
        }
        if (parts.size() > 0){
            addSubscribe(mDataManager.pdfUploadHandle(parts)
                    .compose(RxUtils.SchedulerTransformer())
                    .filter(stringBaseResponse -> mView != null)
                    .subscribeWith(new BaseObserver<String>(mView, false) {
                        @Override
                        public void onSuccess(String picUrl) {
                            List<String> result = Arrays.asList(picUrl.split(","));
                            for (int i = 0; i < result.size(); i++) {
                                for (int j = 0; j < pdfList.size(); j++) {
                                    if (TextUtils.isEmpty(pdfList.get(j).getUrl())){
                                        pdfList.set(j,new PdfInfoEntity("", result.get(i)));
                                    }
                                }
                            }
                            String picUrls = StringUtils.listToString4(pdfList, ',');
                            publishPdf(circle_id,theme_id,theme_content,isEdit,picUrls);
                        }

                        @Override
                        public void onFailure(int code, String message) {
                            super.onFailure(code, message);
                            mView.hideLoading();
                        }
                    }));
        }else {

        }

    }

    private void publishPdf(String circle_id, int theme_id, String theme_content, boolean isEdit, String pdfUrl) {
        if (!isEdit){
            newTheme(circle_id, theme_content, null, pdfUrl);
        }else {
            editTheme(circle_id, theme_id, theme_content, null, pdfUrl);
        }
    }
}
