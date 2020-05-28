package com.bjjy.buildtalk.app;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.utils.EncryptUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.tencent.liteav.network.TXCStreamDownloader.TAG;

/**
 * @author power
 * @date 2020/5/21 9:54 AM
 * @project BuildTalk
 * @description:
 */
public class AliOSS {
    // 访问的endpoint地址
    public static final String OSS_ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
    public static final String BUCKET_NAME = "ciip-dev";
    public static final String OSS_ACCESS_KEY_ID = "LTAISs15xFKycMuC";
    public static final String OSS_ACCESS_KEY_SECRET = "lR5RlLswa3ASsrZs4hUt0lRuTyCiyW";
    public static OSSClient ossClient;

    public static void init(final Context ctx) {
//        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(
//                OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET, TOKEN);
//        ossClient = new OSSClient(ctx, OSS_ENDPOINT, credentialProvider);
        OSSCredentialProvider provider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                return EncryptUtils.sign(OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET, content);
            }
        };
        ossClient = new OSSClient(ctx, OSS_ENDPOINT, provider);
    }

    public static void uploadImage(String path, String name, OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCallBack) {
        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest("<bucketName>", "<objectKey>", "<uploadFilePath>");
        PutObjectRequest put = new PutObjectRequest(BUCKET_NAME, name, path);

        // 异步上传时可以设置进度回调
        put.setProgressCallback((request, currentSize, totalSize) -> Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize));

        if (ossClient == null) {
            init(App.getContext());
            ossCallBack.onFailure(null, null, null);
        } else {
            OSSAsyncTask task = ossClient.asyncPutObject(put, ossCallBack);
            // task.cancel(); // 可以取消任务
            task.waitUntilFinished(); // 可以等待直到任务完成
        }
    }
}
