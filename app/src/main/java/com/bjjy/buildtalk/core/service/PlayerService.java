package com.bjjy.buildtalk.core.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.core.event.PlayerEvent;
import com.bjjy.buildtalk.core.receiver.Notifier;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * @author power
 * @date 2019-12-03 14:22
 * @project BuildTalk
 * @description:
 */
public class PlayerService extends Service {

    private PlayStatusBinder mPlayStatusBinder = new PlayStatusBinder();
    private MediaPlayer mMediaPlayer = new MediaPlayer();       //媒体播放器对象
    private boolean isPlaying = false;
    private boolean isClose = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Notifier.get().init(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private MediaPlayer.OnCompletionListener mOnCompletionListener = mp -> {
        //TODO 歌曲播放完成后，继续下一首，如果没有下一首了，请求数据后播放
        LogUtils.e("onCompletion");
        EventBus.getDefault().post(new PlayerEvent(Constants.SONG_COMPLETION));
    };

    private MediaPlayer.OnErrorListener mOnErrorListener = (mp, what, extra) -> {
        LogUtils.e("MediaPlayer:onError: " + what);
        return true;
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mMediaPlayer.setOnErrorListener(mOnErrorListener);
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        return mPlayStatusBinder;
    }

    public class PlayStatusBinder extends Binder{
        // 获取MediaService.this（方便在ServiceConnection中）
        public PlayerService getInstance() {
            return PlayerService.this;
        }


        /**
         * 点击开始播放
         */
        public void play(String url){
            mMediaPlayer.reset();
            try {
                mMediaPlayer.setDataSource(url);
                startPlay();
            } catch (IOException e) {
                e.printStackTrace();
                ToastUtils.showShort("当前歌曲无法播放");
            }
        }

        private void startPlay(){
            mMediaPlayer.prepareAsync();    //进行缓冲
            isPlaying = true;
            isClose = false;
            mMediaPlayer.setOnPreparedListener(mp -> {
                getDuration();
                EventBus.getDefault().post(new PlayerEvent(Constants.SONG_PROGRESS));
                mMediaPlayer.start();
            });
            EventBus.getDefault().post(new PlayerEvent(Constants.SONG_PLAY));
            EventBus.getDefault().post(new PlayerEvent(Constants.SONG_ISTALK));
        }

        /**
         * 暂停播放音乐
         */
        public void pause(){
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                isPlaying = false;
                isClose = false;
                mMediaPlayer.pause();
                EventBus.getDefault().post(new PlayerEvent(Constants.SONG_PAUSE));
            }
        }

        /**
         * 继续播放音乐
         */
        public void resume() {
            if (mMediaPlayer != null) {
                mMediaPlayer.start();
                isPlaying = true;
                isClose = false;
                EventBus.getDefault().post(new PlayerEvent(Constants.SONG_RESUME));
            }
        }

        /**
         * 下一首
         */
        public void next(){
            if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
                isPlaying = true;
                isClose = false;
                EventBus.getDefault().post(new PlayerEvent(Constants.SONG_CHANGE));
            }
        }

        /**
         * 上一首
         */
        public void last(){
            if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
                isPlaying = true;
                isClose = false;
                EventBus.getDefault().post(new PlayerEvent(Constants.SONG_CHANGE));
            }
        }

        public void close(){
            if (mMediaPlayer != null) {
                isClose = true;
                Notifier.get().cancelAll();
                EventBus.getDefault().post(new PlayerEvent(Constants.SONG_CLOSE));
            }
        }

        /**
         * 停止播放
         */
        public void stop(){
            if (mMediaPlayer != null) {
                isPlaying = false;
                mMediaPlayer.stop();
                Notifier.get().cancelAll();
                EventBus.getDefault().post(new PlayerEvent(Constants.SONG_CLOSE));
                try {
                    mMediaPlayer.prepareAsync(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * @return 返回是否正在播放
         */
        public boolean isPlaying() {
            return isPlaying;
        }

        /**
         * @return 返回播放器是否关闭
         */
        public boolean isClose(){
            return isClose;
        }

        /**
         * @return 返回MediaPlayer对象
         */
        public MediaPlayer getMediaPlayer() {
            return mMediaPlayer;
        }

        /**
         * @return 返回当前播放时长
         */
        public long getCurrentTime() {
            return mMediaPlayer.getCurrentPosition() / 1000;
        }

        public long getDuration(){
            return  mMediaPlayer.getDuration();
        }
    }
}
