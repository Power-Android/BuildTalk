package com.bjjy.buildtalk.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.adapter.EditDialogAdapter;
import com.bjjy.buildtalk.entity.ActivityEntity;
import com.bjjy.buildtalk.entity.CircleInfoEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;
import com.bjjy.buildtalk.ui.circle.ComplaintReasonActivity;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.bjjy.buildtalk.ui.circle.CourseCirclePresenter;
import com.bjjy.buildtalk.ui.circle.PublishActivity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticCirclePresenter;
import com.bjjy.buildtalk.ui.discover.DissertationActivity;
import com.bjjy.buildtalk.ui.discover.EveryTalkDetailActivity;
import com.bjjy.buildtalk.ui.mine.AboutUsActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.weight.BaseDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.makeramen.roundedimageview.RoundedImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.MessageDigest;
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
    private static BaseDialog mEditDailog;
    private static BaseDialog mDeleteDialog;
    private static BaseDialog mMInputDialog;
    private static BaseDialog mActivityDialog;
    private static BaseDialog mSaveImageDialog;

    public static void showSaveImageDialog(Context context, String imgUrl) {
        mSaveImageDialog = new BaseDialog.Builder(context)
                .setViewId(R.layout.dialog_select_layout)
                .setGravity(Gravity.BOTTOM)
                .setAnimation(R.style.bottom_aniamtion)
                .isOnTouchCanceled(true)
                .setWidthHeightpx(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .builder();
        mSaveImageDialog.setCancelable(true);
        mSaveImageDialog.getView(R.id.cancle_tv).setOnClickListener(v -> mSaveImageDialog.dismiss());
        mSaveImageDialog.getView(R.id.save_tv).setOnClickListener(view -> {
            final Bitmap[] mBitmap = new Bitmap[1];
            Glide.with(context).load(imgUrl).apply(new RequestOptions()
                    .transform(new Transformation<Bitmap>() {
                        @NonNull
                        @Override
                        public Resource<Bitmap> transform(@NonNull Context context1, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
                            mBitmap[0] = resource.get();
                            saveImageToGallery(context1, mBitmap[0]);
                            return resource;
                        }

                        @Override
                        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                        }
                    })).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                }
            });
            mSaveImageDialog.dismiss();
        });
        mSaveImageDialog.setCanceledOnTouchOutside(true);
        mSaveImageDialog.show();
    }

    public static void showActivityDialog(Context context, ActivityEntity activityEntity) {
        mActivityDialog = new BaseDialog.Builder(context)
                .setViewId(R.layout.dialog_activity_layout)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.nomal_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx((int) context.getResources().getDimension(R.dimen.dp_280), (int) context.getResources().getDimension(R.dimen.dp_380))
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(false)
                .addViewOnClickListener(R.id.iv, v -> {
                    if (TextUtils.equals("发表", activityEntity.getType_name())) {
                        return;
                    }
                    if (!TextUtils.isEmpty(activityEntity.getActivity_url())) {
                        Intent intent = new Intent(context, AboutUsActivity.class);
                        intent.putExtra("url", activityEntity.getActivity_url());
                        intent.putExtra("title", activityEntity.getActivity_title());
                        context.startActivity(intent);
                    } else {
                        switch (activityEntity.getType_id()) {
                            case "1":
                                if ("1".equals(activityEntity.getCircle_type())) {
                                    Intent intent = new Intent(context, TopticCircleActivity.class);
                                    intent.putExtra("circle_id", activityEntity.getData_id() + "");
                                    context.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(context, CourseCircleActivity.class);
                                    intent.putExtra("circle_id", activityEntity.getData_id() + "");
                                    context.startActivity(intent);
                                }
                                break;
                            case "2":
                                Intent intent1 = new Intent(context, EveryTalkDetailActivity.class);
                                intent1.putExtra("article_id", activityEntity.getData_id() + "");
                                intent1.putExtra("type", "article");
                                context.startActivity(intent1);
                                break;
                            case "3":
                                Intent intent2 = new Intent(context, MasterDetailActivity.class);
                                intent2.putExtra("user_id", activityEntity.getData_id() + "");
                                context.startActivity(intent2);
                                break;
                            case "4":
                                Intent intent3 = new Intent(context, DissertationActivity.class);
                                intent3.putExtra("id", activityEntity.getData_id() + "");
                                context.startActivity(intent3);
                                break;
                        }
                    }

                    mActivityDialog.dismiss();
                })
                .addViewOnClickListener(R.id.close, v -> mActivityDialog.dismiss())
                //设置监听事件
                .builder();

        final Bitmap[] mBitmap = new Bitmap[1];
        Glide.with(context).load(activityEntity.getPic_url()).apply(new RequestOptions()
                .transform(new Transformation<Bitmap>() {
                    @NonNull
                    @Override
                    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
                        mBitmap[0] = resource.get();
                        return resource;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                })).into((RoundedImageView) mActivityDialog.getView(R.id.iv));
        if (TextUtils.equals("发表", activityEntity.getType_name())) {
            ToastUtils.showShort("长按可保存图片~");
            mActivityDialog.getView(R.id.iv).setOnLongClickListener(v1 -> {
                saveImageToGallery(context, mBitmap[0]);
                return true;
            });
        }
        mActivityDialog.show();
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片 创建文件夹
        File appDir = new File(Environment.getExternalStorageDirectory(), "jt");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //图片文件名称
        String fileName = "jt_" + System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.e("111", e.getMessage());
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        String path = file.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
        } catch (FileNotFoundException e) {
            Log.e("333", e.getMessage());
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        ToastUtils.showShort("图片保存成功");
    }

    /**
     * @param activity
     * @param data
     * @param i
     * @param list
     * @param topticPresenter
     * @param coursePresenter
     * @param circleInfoEntity 圈子主题/精华的编辑按钮弹出框
     */
    public static void showEditDialog(final Activity activity, ThemeInfoEntity.ThemeInfoBean data, int i,
                                      List<ThemeInfoEntity.ThemeInfoBean> list, TopticCirclePresenter topticPresenter,
                                      CourseCirclePresenter coursePresenter, CircleInfoEntity circleInfoEntity) {
        mEditDailog = new BaseDialog.Builder(activity)
                .setGravity(Gravity.BOTTOM)
                .setViewId(R.layout.dialog_theme_edit)
                .setWidthHeightpx(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimation(R.style.bottom_aniamtion)
                .isOnTouchCanceled(true)
                .builder();
        RecyclerView recyclerView = mEditDailog.getView(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 4));
        List<String> itemList = new ArrayList<>();
        EditDialogAdapter adapter = new EditDialogAdapter(R.layout.adapter_edit_dialog, itemList, data);
        recyclerView.setAdapter(adapter);

        if (coursePresenter == null) {
            if (topticPresenter.mDataManager.getUser().getUser_id().equals(circleInfoEntity.getCircleInfo().getUser_id() + "")) {//如果是圈主
                if (topticPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                    //如果是自己的主题----收藏、修改、置顶、加精、删除
                    itemList.clear();
                    itemList.add("收藏");
                    itemList.add("修改");
                    itemList.add("置顶");
                    itemList.add("加精");
                    itemList.add("删除");
                    adapter.setNewData(itemList);
                } else {
                    //不是自己的主题----收藏、置顶、加精、不喜欢、投诉
                    itemList.clear();
                    itemList.add("收藏");
                    itemList.add("置顶");
                    itemList.add("加精");
                    itemList.add("不喜欢");
                    itemList.add("投诉");
                    adapter.setNewData(itemList);
                }
            } else {//如果是成员
                if (topticPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                    //如果是自己的主题----收藏、修改、删除
                    itemList.clear();
                    itemList.add("收藏");
                    itemList.add("删除");
                    itemList.add("修改");
                    adapter.setNewData(itemList);
                } else {
                    //不是自己的主题----收藏、不喜欢、投诉
                    itemList.clear();
                    itemList.add("收藏");
                    itemList.add("不喜欢");
                    itemList.add("投诉");
                    adapter.setNewData(itemList);
                }
            }
        } else {
            if (coursePresenter.mDataManager.getUser().getUser_id().equals(circleInfoEntity.getCircleInfo().getUser_id() + "")) {//如果是圈主
                if (coursePresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                    //如果是自己的主题----收藏、修改、置顶、加精、删除
                    itemList.clear();
                    itemList.add("收藏");
                    itemList.add("修改");
                    itemList.add("置顶");
                    itemList.add("加精");
                    itemList.add("删除");
                    adapter.setNewData(itemList);
                } else {
                    //不是自己的主题----收藏、置顶、加精、不喜欢、投诉
                    itemList.clear();
                    itemList.add("收藏");
                    itemList.add("置顶");
                    itemList.add("加精");
                    itemList.add("不喜欢");
                    itemList.add("投诉");
                    adapter.setNewData(itemList);
                }
            } else {//如果是成员
                if (coursePresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                    //如果是自己的主题----收藏、修改、删除
                    //如果是自己的主题----收藏、修改、删除
                    itemList.clear();
                    itemList.add("收藏");
                    itemList.add("删除");
                    itemList.add("修改");
                    adapter.setNewData(itemList);
                } else {
                    //不是自己的主题----收藏、不喜欢、投诉
                    itemList.clear();
                    itemList.add("收藏");
                    itemList.add("不喜欢");
                    itemList.add("投诉");
                    adapter.setNewData(itemList);
                }
            }
        }

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            List<String> item = adapter1.getData();
            switch (item.get(position)) {
                case "收藏":
                    if (coursePresenter == null) {
                        topticPresenter.collectTheme(data, i);
                    } else {
                        coursePresenter.collectTheme(data, i);
                    }
                    mEditDailog.dismiss();
                    break;
                case "修改":
                    if (coursePresenter == null) {
                        if (topticPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                            Intent intent = new Intent(activity, PublishActivity.class);
                            intent.putExtra("themeInfo", data);
                            intent.putExtra("circle_name", circleInfoEntity.getCircleInfo().getCircle_name());
                            activity.startActivity(intent);
                        }
                    } else {
                        if (coursePresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                            Intent intent = new Intent(activity, PublishActivity.class);
                            intent.putExtra("themeInfo", data);
                            intent.putExtra("circle_name", circleInfoEntity.getCircleInfo().getCircle_name());
                            activity.startActivity(intent);
                        }
                    }
                    mEditDailog.dismiss();
                    break;
                case "置顶":
                    if (coursePresenter == null){
                        topticPresenter.themeTopOperate(data, i);
                    }else {
                        coursePresenter.themeTopOperate(data, i);
                    }
                    mEditDailog.dismiss();
                    break;
                case "加精":
                    if (coursePresenter == null) {
                        topticPresenter.addChoiceness(data, i);
                    } else {
                        coursePresenter.addChoiceness(data, i);
                    }
                    mEditDailog.dismiss();
                    break;
                case "删除":
                    if (coursePresenter == null) {
                        if (topticPresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                            showDeleteDialog(data, i, list, activity, topticPresenter, null);
                        }
                    } else {
                        if (coursePresenter.mDataManager.getUser().getUser_id().equals(data.getUser_id())) {
                            showDeleteDialog(data, i, list, activity, null, coursePresenter);
                        }
                    }
                    mEditDailog.dismiss();
                    break;
                case "不喜欢":
                    if (coursePresenter == null) {
                        topticPresenter.userShieldRecord(data, i, list);
                    } else {
                        coursePresenter.userShieldRecord(data, i, list);
                    }
                    mEditDailog.dismiss();
                    break;
                case "投诉":
                    Intent intent = new Intent(activity, ComplaintReasonActivity.class);
                    intent.putExtra("data_id", data.getTheme_id() + "");
                    activity.startActivity(intent);
                    mEditDailog.dismiss();
                    break;
            }
        });
        TextView cancle_tv = mEditDailog.getView(R.id.cancle_tv);
        cancle_tv.setOnClickListener(v -> mEditDailog.dismiss());
        mEditDailog.show();
    }

    /**
     * @param data
     * @param i
     * @param list
     * @param activity
     * @param topticPresenter
     * @param coursePresenter 评论弹出框
     */
    public static void showDeleteDialog(ThemeInfoEntity.ThemeInfoBean data, int i, List<ThemeInfoEntity.ThemeInfoBean> list,
                                        Activity activity, TopticCirclePresenter topticPresenter, CourseCirclePresenter coursePresenter) {
        mDeleteDialog = new BaseDialog.Builder(activity)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.nomal_aniamtion)
                .setViewId(R.layout.dialog_quit_layout)
                .setWidthHeightdp((int) activity.getResources().getDimension(R.dimen.dp_275), (int) activity.getResources().getDimension(R.dimen.dp_138))
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.cancle_tv, v -> mDeleteDialog.dismiss())
                .addViewOnClickListener(R.id.query_tv, v -> {
                    if (coursePresenter == null) {
                        topticPresenter.deleteTheme(data, i, list);
                    } else {
                        coursePresenter.deleteTheme(data, i, list);
                    }
                    mDeleteDialog.dismiss();
                })
                .builder();
        TextView textView = mDeleteDialog.getView(R.id.text);
        textView.setText("确定删除此主题？");
        mDeleteDialog.show();
    }

    public static void showCommentDialog(int adapterPosition, int theme_id, String pareantId,  int i, List<ThemeInfoEntity.ThemeInfoBean> data,
                                         String commentId, String replyName, Activity activity, TopticCirclePresenter topticPresenter,
                                         CourseCirclePresenter coursePresenter) {
        mMInputDialog = new BaseDialog.Builder(activity)
                .setGravity(Gravity.BOTTOM)
                .setViewId(R.layout.dialog_input_layout)
                .setWidthHeightdp(ViewGroup.LayoutParams.MATCH_PARENT, (int) activity.getResources().getDimension(R.dimen.dp_129))
                .isOnTouchCanceled(true)
                .builder();
        EditText mInputEt = mMInputDialog.getView(R.id.comment_et);
        TextView publishTv = mMInputDialog.getView(R.id.publish_tv);
        if (!TextUtils.isEmpty(replyName)){
            mInputEt.setHint("回复" + replyName + "：");
        }
        mMInputDialog.setOnShowListener(dialog -> mInputEt.postDelayed(() -> {
            mInputEt.requestFocus();
            KeyboardUtils.showSoftInput(mInputEt);
        }, 200));
        mMInputDialog.setOnDismissListener(dialog -> {
            mInputEt.getText().clear();
            KeyboardUtils.toggleSoftInput();
        });
        KeyboardUtils.registerSoftInputChangedListener(activity, height -> {
            if (height == 0 && mMInputDialog != null) {
                mMInputDialog.setOnDismissListener(null);
                mMInputDialog.dismiss();
            }
        });
        mMInputDialog.show();
        publishTv.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mInputEt.getText().toString().trim())) {
                ToastUtils.showShort("请输入评论内容");
                return;
            }
            if (coursePresenter == null) {
                topticPresenter.publishComment(adapterPosition,mInputEt.getText().toString().trim(), String.valueOf(theme_id), commentId, pareantId, i, data);
            } else {
                coursePresenter.publishComment(adapterPosition,mInputEt.getText().toString().trim(), String.valueOf(theme_id), commentId, pareantId, i, data);
            }
            if (mMInputDialog != null) {
                mMInputDialog.dismiss();
            }
        });
    }

    public static void showShareDialog(final Activity activity, String url, String weburl, String title, String imgUrl, String desc, boolean isSmall) {

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
                    if (isSmall) {
                        shareSmallProgram(url, imgUrl, title, desc, activity, SHARE_MEDIA.WEIXIN);
                    } else {
                        shareWebUrl(weburl, title, imgUrl, desc, activity, SHARE_MEDIA.WEIXIN);
                    }
                    shareDialog.dismiss();
                })
                .addViewOnClickListener(R.id.circle_tv, v -> {
                    shareWebUrl(weburl, title, imgUrl, desc, activity, SHARE_MEDIA.WEIXIN_CIRCLE);
                    shareDialog.dismiss();
                })
                .addViewOnClickListener(R.id.qq_tv, v -> {
                    shareWebUrl(weburl, title, imgUrl, desc, activity, SHARE_MEDIA.QQ);
                    shareDialog.dismiss();
                })
                .addViewOnClickListener(R.id.link_tv, v -> {
                    ClipboardManager cmb = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(weburl);
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

    public static void shareWebUrl1(String url, String title, Bitmap imgUrl, String des,
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

    public static void shareSmallProgram(String path, String imgUrl, String title, String des, Activity context, SHARE_MEDIA pingtai) {
        //兼容低版本的网页链接
        UMMin umMin = new UMMin(path);
        // 小程序消息封面图片
        umMin.setThumb(new UMImage(context, imgUrl));
        // 小程序消息title
        umMin.setTitle(title);
        // 小程序消息描述
        umMin.setDescription(des);
        //小程序页面路径
        umMin.setPath(path);
        // 小程序原始id,在微信平台查询
        umMin.setUserName("gh_e446f16a71e4");
        new ShareAction(context)
                .withMedia(umMin)
                .setPlatform(pingtai)
                .setCallback(umShareListener).share();
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
