package com.bjjy.buildtalk.core.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.core.service.PlayerService;
import com.bjjy.buildtalk.entity.SongsEntity;
import com.bjjy.buildtalk.ui.main.MainActivity;
import com.bjjy.buildtalk.utils.ImageUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * @author power
 * @date 2019-12-13 12:17
 * @project BuildTalk
 * @description:
 */
public class Notifier {
    private static final int NOTIFICATION_ID = 0x111;
    private PlayerService playService;
    private NotificationManager notificationManager;
    NotificationChannel mNotificationChannel = null;
    private NotificationCompat.Builder mBuilder;
    private Bitmap mBitmap;

    public static Notifier get() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static Notifier instance = new Notifier();
    }

    private Notifier() {
    }

    public void init(PlayerService playService) {
        this.playService = playService;
        notificationManager = (NotificationManager) playService.getSystemService(NOTIFICATION_SERVICE);
    }

    public void showPlay(SongsEntity music) {
        if (music == null) {
            return;
        }
        Glide.with(playService).asBitmap().load(music.getAudio_picUrl()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                mBitmap = resource;
                playService.startForeground(NOTIFICATION_ID, buildNotification(playService, music, true));
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                playService.startForeground(NOTIFICATION_ID, buildNotification(playService, music, true));
            }
        });
    }

    public void showPause(SongsEntity music) {
        if (music == null) {
            return;
        }
        playService.stopForeground(false);
        notificationManager.notify(NOTIFICATION_ID, buildNotification(playService, music, false));
    }

    public void cancelAll() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.deleteNotificationChannel("com.bjjy.buildtalk.player.notification");
            mNotificationChannel = null;
        }
        notificationManager.cancelAll();
    }

    private Notification buildNotification(Context context, SongsEntity music, boolean isPlaying) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.EXTRA_NOTIFICATION, true);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setPackage(getPackageName());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (null == mNotificationChannel) {//8.0以上适配，增加渠道，否则不显示
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel = new NotificationChannel("com.bjjy.buildtalk.player.notification",
                        "default", NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationChannel.enableVibration(false);
                mNotificationChannel.setSound(null, null);
                mNotificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//设置在锁屏界面上显示这条通知
                notificationManager.createNotificationChannel(mNotificationChannel);
            }
        }
        mBuilder = new NotificationCompat.Builder(context,"com.bjjy.buildtalk.player.notification")
                .setSmallIcon(R.mipmap.app_icon)
                .setCustomContentView(getRemoteViews(context, music, isPlaying))
                .setContentIntent(pendingIntent);
        return mBuilder.build();
    }

    private RemoteViews getRemoteViews(Context context, SongsEntity music, boolean isPlaying) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification);

        String title = music.getArticle_title();
        if (mBitmap != null){
            remoteViews.setImageViewBitmap(R.id.iv_icon, mBitmap);
        }else {
            remoteViews.setImageViewResource(R.id.iv_icon, R.mipmap.app_icon);
        }

        remoteViews.setTextViewText(R.id.tv_title, title);

        Intent playIntent = new Intent(StatusBarReceiver.ACTION_STATUS_BAR);
        playIntent.putExtra(StatusBarReceiver.EXTRA, StatusBarReceiver.EXTRA_PLAY_PAUSE);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context, 0,
                playIntent.setPackage(getPackageName()), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.iv_play_pause, getPlayIconStatus(isPlaying));
        remoteViews.setOnClickPendingIntent(R.id.iv_play_pause, playPendingIntent);

        Intent nextIntent = new Intent(StatusBarReceiver.ACTION_STATUS_BAR);
        nextIntent.putExtra(StatusBarReceiver.EXTRA, StatusBarReceiver.EXTRA_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, 1,
                nextIntent.setPackage(getPackageName()), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.iv_next, R.drawable.next_icon);
        remoteViews.setOnClickPendingIntent(R.id.iv_next, nextPendingIntent);

        Intent lastIntent = new Intent(StatusBarReceiver.ACTION_STATUS_BAR);
        lastIntent.putExtra(StatusBarReceiver.EXTRA, StatusBarReceiver.EXTRA_LAST);
        PendingIntent lastPendingIntent = PendingIntent.getBroadcast(context, 2,
                lastIntent.setPackage(getPackageName()), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.iv_last, R.drawable.last_icon);
        remoteViews.setOnClickPendingIntent(R.id.iv_last, lastPendingIntent);
        return remoteViews;
    }

    private int getPlayIconStatus(boolean isPlaying) {
        if (isPlaying) {
            return R.drawable.media_stop_icon;
        } else {
            return R.drawable.media_play_icon;
        }
    }
}
