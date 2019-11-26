package com.bjjy.buildtalk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

import java.util.HashMap;

/**
 * @author power
 * @date 2019/4/28 11:42 AM
 * @project BuildTalk
 * @description:
 */
public class GlideUtils extends ImageLoader {



    /**
     * @param context
     * @param path
     * @param imageView
     * banner轮播图
     */
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(new RequestOptions()
                .error(R.drawable.test_banner))
                .into(imageView);
    }

    /**
     *  context 上下文
     *  url 视频地址
     *  imageView 设置image
     */
    public static Bitmap loadVideoScreenshot(String url) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(url, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

}
