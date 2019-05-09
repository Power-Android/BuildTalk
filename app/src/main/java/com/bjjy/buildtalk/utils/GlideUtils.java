package com.bjjy.buildtalk.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bjjy.buildtalk.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

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

}
