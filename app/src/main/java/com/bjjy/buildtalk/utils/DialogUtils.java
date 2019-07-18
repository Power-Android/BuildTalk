package com.bjjy.buildtalk.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author power
 * @date 2019/7/17 10:30 AM
 * @project BuildTalk
 * @description:
 */
public class DialogUtils {

    private static BaseDialog shareDialog;

    public static void showShareDialog(final Activity activity, String url, String title, String imgUrl, String desc) {

        shareDialog = new BaseDialog.Builder(activity).setViewId(R.layout.dialog_share_layout)
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(Gravity.BOTTOM)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.wechat_tv, v -> {
                    shareWebUrl(url, title, imgUrl, desc, activity, SHARE_MEDIA.WEIXIN);
                    shareDialog.dismiss();
                })
                .addViewOnClickListener(R.id.circle_tv, v -> {
                    shareWebUrl(url, title, imgUrl, desc, activity, SHARE_MEDIA.WEIXIN_CIRCLE);
                    shareDialog.dismiss();
                })
                .addViewOnClickListener(R.id.qq_tv, v -> {
                    shareWebUrl(url, title, imgUrl, desc, activity, SHARE_MEDIA.QQ);
                    shareDialog.dismiss();
                })
                .addViewOnClickListener(R.id.link_tv, v -> {
                    ClipboardManager cmb = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(url);
                    ToastUtils.showShort("已复制到剪切板");
                    shareDialog.dismiss();
                })
                .addViewOnClickListener(R.id.cancle_tv, v -> shareDialog.dismiss())
                //设置监听事件
                .builder();
        shareDialog.show();
    }

    public static void shareWebUrl(String url, String title, String imgUrl, String des,
                                   Activity context, SHARE_MEDIA pingtai) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage thumb = new UMImage(context, imgUrl);
        web.setThumb(thumb);  //缩略图
        web.setDescription(des);//描述
        //注意在新浪平台，缩略图属于必传参数，否则会报错
        ShareAction shareAction = new ShareAction(context).withMedia(web);
        shareAction.setPlatform(pingtai);//传入平台
        shareAction.setCallback(umShareListener);
        shareAction.share();
    }

    public static void shareImage(Activity context, String imgUrl, SHARE_MEDIA pingtai) {
        UMImage image = new UMImage(context, imgUrl);//网络图片
        //        UMImage image = new UMImage(UMShareActivity.this, file);//本地文件
        //        UMImage image = new UMImage(UMShareActivity.this, R.drawable.xxx);//资源文件
        //        UMImage image = new UMImage(UMShareActivity.this, bitmap);//bitmap文件
        //        UMImage image = new UMImage(UMShareActivity.this, byte[]);//字节流
        // 对于微信QQ的那个平台，分享的图片需要设置缩略图，缩略图的设置规则为：
        UMImage thumb = new UMImage(context, imgUrl);
        image.setThumb(thumb);
        // 用户可以设置压缩的方式：
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
        //        压缩格式设置：
        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
        ShareAction shareAction = new ShareAction(context).withMedia(image);
        shareAction.setPlatform(pingtai);//传入平台
        shareAction.setCallback(umShareListener);
        shareAction.share();
    }

    private static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e("plat", "platform" + platform);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t != null) {
                Log.e("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };
}
