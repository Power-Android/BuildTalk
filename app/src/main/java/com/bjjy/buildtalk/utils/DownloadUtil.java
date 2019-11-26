package com.bjjy.buildtalk.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by WangYi
 *
 * @Date : 2018/3/10
 * @Desc : 下载文件工具
 */
public class DownloadUtil {

    public static void download(final String url, final String fileName, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onDownloadFailed(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                try {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "jt");
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(appDir,fileName);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    listener.onDownloadSuccess(file.getAbsolutePath(),file);
                } catch (Exception e) {
                    listener.onDownloadFailed(e.getMessage());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface OnDownloadListener {
        void onDownloadSuccess(String path, File file);

        void onDownloading(int progress);

        void onDownloadFailed(String msg);
    }
}
