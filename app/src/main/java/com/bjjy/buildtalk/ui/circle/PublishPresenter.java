package com.bjjy.buildtalk.ui.circle;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bjjy.buildtalk.adapter.PublishCircleAdapter;
import com.bjjy.buildtalk.app.AliOSS;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.base.presenter.BasePresenter;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PdfInfoEntity;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.StringUtils;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.UriUtils;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author power
 * @date 2019/6/13 9:40 AM
 * @project BuildTalk
 * @description:
 */
public class PublishPresenter extends BasePresenter<PublishContarct.View> {
    private List<String> images = new ArrayList<>();
    private List<String> pdfUrls = new ArrayList<>();
    private List<String> pdfNames = new ArrayList<>();
    private URI mUri;

    @Inject
    public PublishPresenter() {

    }

    public void publishImages(String circle_id, int theme_id, String theme_content, boolean isEdit,
                              boolean isChecked, List<ThemeImageBean> list, String publish_type) {
        if (list.size() > 0){
            upLoadImages(circle_id, theme_id, theme_content, isEdit, list, isChecked, publish_type);
        }else {
            publish(circle_id, theme_id, theme_content, null, isEdit, isChecked, publish_type);
        }
    }

    private void publish(String circle_id, int theme_id, String theme_content, String picUrl,
                         boolean isEdit, boolean isChecked, String publish_type) {
        if (!isEdit){
            newTheme(circle_id, theme_content, picUrl, null, null, isChecked, publish_type,
                    null, null, null, null, null, null);
        }else {
            editTheme(circle_id, theme_id, theme_content, picUrl, null, null, isChecked, publish_type,
                    null, null, null, null, null, null);
        }
    }

    private void newTheme(String circle_id, String theme_content, String picUrl, String pdfUrl, String pdfName,
                          boolean isChecked, String publish_type, String tx_videoId, String theme_video,
                          String coverURL, String video_height, String video_width, String videoDuration) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.TIMESTAMP, timestamp);
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.SOURCE, Constants.ANDROID);
        paramas.put("circle_id", TextUtils.isEmpty(circle_id) ? "0" : circle_id);
        paramas.put("theme_content", theme_content);
        paramas.put("publish_type", !TextUtils.isEmpty(publish_type) ? "2" : "1");
        if (!TextUtils.isEmpty(publish_type)){
            paramas.put("is_find", "1");
        }else {
            paramas.put("is_find", isChecked ? "1" : "0");//是否同步到发现
        }
        if (!TextUtils.isEmpty(picUrl)){
            paramas.put("theme_image", picUrl);
        }
        if (!TextUtils.isEmpty(pdfUrl)){
            paramas.put("theme_pdf", pdfUrl);
            paramas.put("pdf_name", pdfName);
        }
        if (!TextUtils.isEmpty(tx_videoId)){
            paramas.put("tx_videoId", tx_videoId);
            paramas.put("theme_video", theme_video);
            paramas.put("coverURL", coverURL);
            paramas.put("video_height", video_height);
            paramas.put("video_width", video_width);
            paramas.put("video_duration", videoDuration);
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

    private void editTheme(String circle_id, int theme_id, String theme_content, String picUrl, String pdfUrl,
                           String pdfName, boolean isChecked, String publish_type, String tx_videoId,
                           String theme_video, String coverURL, String video_height, String video_width,
                           String videoDuration) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.TIMESTAMP, timestamp);
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put("theme_id", theme_id+"");
        paramas.put("theme_content", theme_content);
//        paramas.put("publish_type", !TextUtils.isEmpty(publish_type) ? "2" : "1");
        if (!TextUtils.isEmpty(picUrl)){
            paramas.put("theme_image", picUrl);
        }
        if (!TextUtils.isEmpty(pdfUrl)){
            paramas.put("theme_pdf", pdfUrl);
            paramas.put("pdf_name", pdfName);
        }
//        if (!TextUtils.isEmpty(publish_type)){
//            paramas.put("is_find", "1");
//        }else {
//            paramas.put("is_find", isChecked ? "1" : "0");//是否同步到发现
//        }
        if (!TextUtils.isEmpty(picUrl)){
            paramas.put("theme_image", picUrl);
        }
        if (!TextUtils.isEmpty(pdfUrl)){
            paramas.put("theme_pdf", pdfUrl);
            paramas.put("pdf_name", pdfName);
        }
        if (!TextUtils.isEmpty(tx_videoId)){
            paramas.put("tx_videoId", tx_videoId);
            paramas.put("theme_video", theme_video);
            paramas.put("coverURL", TextUtils.isEmpty(coverURL) ? "" : coverURL);
            paramas.put("video_height", TextUtils.isEmpty(video_height) ? "" : video_height);
            paramas.put("video_width", TextUtils.isEmpty(video_width) ? "" : video_width);
            paramas.put("video_duration", TextUtils.isEmpty(videoDuration) ? "" : videoDuration);
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

    public void pdf(String circle_id, int theme_id, String theme_content, boolean isEdit, boolean isChecked,
                    List<PdfInfoEntity> pdfList, String publish_type) {
        //TODO OSS上传，上传完合成PDF的url和name字符串
        upLoadPDFs(circle_id, theme_id, theme_content, isEdit, isChecked, pdfList, publish_type);
    }

    private void upLoadPDFs(String circle_id, int theme_id, String theme_content, boolean isEdit,
                            boolean isChecked, List<PdfInfoEntity> pdfList, String publish_type) {
        if (AliOSS.ossClient == null) {
            AliOSS.init(App.getContext());
        }
        if (null == pdfList && pdfList.size() == 0){
            return;
        }
        ossUpLoadPDF(circle_id, theme_id, theme_content, isEdit, isChecked, pdfList, publish_type);
    }

    private void ossUpLoadPDF(String circle_id, int theme_id, String theme_content, boolean isEdit,
                              boolean isChecked, List<PdfInfoEntity> pdfList, String publish_type) {
        if (pdfList.size() <= 0) {
            // 文件全部上传完毕，这里编写上传结束的逻辑，如果要在主线程操作，最好用Handler或runOnUiThead做对应逻辑
            LogUtils.e("全部上传完毕");
            String pdf_url = StringUtils.listToString(pdfUrls);
            String pdf_name = StringUtils.listToString(pdfNames);
            mView.hideLoading();
            publishPdf(circle_id, theme_id, theme_content, isEdit, pdf_url, pdf_name, isChecked, publish_type);
            return;// 这个return必须有，否则下面报越界异常，原因自己思考下哈
        }
        String path = pdfList.get(0).getPath();
        File file = UriUtils.uri2File(pdfList.get(0).getUri());
        LogUtils.e(file.getAbsolutePath());
        String absPath = file.getAbsolutePath();
        if (TextUtils.isEmpty(path)) {
            pdfList.remove(0);
            // url为空就没必要上传了，这里做的是跳过它继续上传的逻辑。
            ossUpLoadPDF(circle_id, theme_id, theme_content, isEdit, isChecked, pdfList, publish_type);
            return;
        }
        final String name = pdfList.get(0).getName();
        final String upName = "build_talk_android" + "/" + name;
        // 下面3个参数依次为bucket名，ObjectKey名，上传文件路径
        PutObjectRequest put = new PutObjectRequest(AliOSS.BUCKET_NAME, upName, absPath);

        // 设置进度回调
        put.setProgressCallback((request, currentSize, totalSize) -> {
            // 进度逻辑
            Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
        });
        // 异步上传
        OSSAsyncTask task = AliOSS.ossClient.asyncPutObject(put,
                new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) { // 上传成功
                        String url = "http://"+AliOSS.BUCKET_NAME +".oss-cn-beijing.aliyuncs.com" + "/" + upName;
                        LogUtils.e(url);
                        images.add(url);
                        pdfUrls.add(url);
                        pdfNames.add(pdfList.get(0).getName());
                        pdfList.remove(0);
                        ossUpLoadPDF(circle_id, theme_id, theme_content, isEdit, isChecked, pdfList, publish_type);// 递归同步效果
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientExcepion,
                                          ServiceException serviceException) { // 上传失败
                        mView.hideLoading();
                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            clientExcepion.printStackTrace();
                        }
                        if (serviceException != null) {
                            // 服务异常
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                    }
                });
        // task.cancel(); // 可以取消任务
        task.waitUntilFinished(); // 可以等待直到任务完成
    }

    private void publishPdf(String circle_id, int theme_id, String theme_content, boolean isEdit, String pdfUrl,
                            String pdfName, boolean isChecked, String publish_type) {
        if (!isEdit){
            newTheme(circle_id, theme_content, null, pdfUrl, pdfName, isChecked, publish_type,
                    null, null, null, null, null, null);
        }else {
            editTheme(circle_id, theme_id, theme_content, null, pdfUrl, pdfName, isChecked, publish_type,
                    null, null, null, null, null, null);
        }
    }

    public void video(String circle_id, int theme_id, String theme_content, boolean isEdit, boolean checked,
                      String publish_type, String videoId, String videoURL, String coverURL, String videoWith,
                      String videoHeight, String videoDuration) {
        if (!isEdit){
            newTheme(circle_id, theme_content, null, null, null, checked, publish_type,
                    videoId, videoURL, coverURL, videoHeight, videoWith, videoDuration);
        }else {
            editTheme(circle_id, theme_id, theme_content, null, null, null, checked, publish_type,
                    videoId, videoURL, coverURL, videoHeight, videoWith, videoDuration);
        }
    }

    public void upLoadImages(String circle_id, int theme_id, String theme_content, boolean isEdit,
                             List<ThemeImageBean> list, boolean isChecked, String publish_type){
        if (AliOSS.ossClient == null) {
            AliOSS.init(App.getContext());
        }
        if (null == list && list.size() == 0){
            return;
        }
        ossUpload(circle_id, theme_id, theme_content, isEdit, list, isChecked, publish_type);
    }

    private void ossUpload(String circle_id, int theme_id, String theme_content, boolean isEdit,
                           List<ThemeImageBean> urls, boolean isChecked, String publish_type) {
        if (urls.size() <= 0) {
            // 文件全部上传完毕，这里编写上传结束的逻辑，如果要在主线程操作，最好用Handler或runOnUiThead做对应逻辑
            LogUtils.e("全部上传完毕");
            String url = StringUtils.listToString(images);
            mView.hideLoading();
            publish(circle_id, theme_id, theme_content, url, isEdit, isChecked, publish_type);
            return;// 这个return必须有，否则下面报越界异常，原因自己思考下哈
        }
        final String url = urls.get(0).getPic_url();
        if (TextUtils.isEmpty(url)) {
            urls.remove(0);
            // url为空就没必要上传了，这里做的是跳过它继续上传的逻辑。
            ossUpload(circle_id, theme_id, theme_content, isEdit, urls, isChecked, publish_type);
            return;
        }
        final String name = System.currentTimeMillis() + ".jpg";
        final String upName = "build_talk_android" + "/" + name;
        // 下面3个参数依次为bucket名，ObjectKey名，上传文件路径
        PutObjectRequest put = new PutObjectRequest(AliOSS.BUCKET_NAME, upName, url);

        // 设置进度回调
        put.setProgressCallback((request, currentSize, totalSize) -> {
            // 进度逻辑
            Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
        });
        // 异步上传
        OSSAsyncTask task = AliOSS.ossClient.asyncPutObject(put,
                new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) { // 上传成功
                        String url = "http://"+AliOSS.BUCKET_NAME +".oss-cn-beijing.aliyuncs.com" + "/" + upName;
                        LogUtils.e(url);
                        urls.remove(0);
                        images.add(url);
                        ossUpload(circle_id, theme_id, theme_content, isEdit, urls, isChecked, publish_type);// 递归同步效果
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientExcepion,
                                          ServiceException serviceException) { // 上传失败
                        mView.hideLoading();
                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            clientExcepion.printStackTrace();
                        }
                        if (serviceException != null) {
                            // 服务异常
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                    }
                });
        // task.cancel(); // 可以取消任务
         task.waitUntilFinished(); // 可以等待直到任务完成
    }

    public void getSign() {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);
        headers.put(Constants.PASS_ID, Constants.HEADER_PASSID);

        addSubscribe(mDataManager.getClientUploadSign(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<String>(mView, true, false) {
                    @Override
                    public void onSuccess(String sign) {
                        mView.handlerSignSuccess(sign);
                    }
                }));
    }

    public void circleList(PublishCircleAdapter adapter) {
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put(Constants.USER_ID, mDataManager.getUser().getUser_id());
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.TIMESTAMP, timestamp);
        headers.put(Constants.SIGN, sign);

        addSubscribe(mDataManager.chooseCircle(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .filter(response -> mView != null)
                .subscribeWith(new BaseObserver<List<IEntity>>(mView, true, false) {
                    @Override
                    public void onSuccess(List<IEntity> iEntities) {
                        mView.handlerCircleListSuccess(iEntities, adapter);
                    }
                }));
    }
}
