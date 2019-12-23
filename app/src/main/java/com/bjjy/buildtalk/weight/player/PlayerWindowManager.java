package com.bjjy.buildtalk.weight.player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.app.DataManager;
import com.bjjy.buildtalk.core.event.PlayerEvent;
import com.bjjy.buildtalk.core.receiver.Notifier;
import com.bjjy.buildtalk.core.rx.BaseObserver;
import com.bjjy.buildtalk.core.rx.RxUtils;
import com.bjjy.buildtalk.core.service.PlayerService;
import com.bjjy.buildtalk.entity.SongsEntity;
import com.bjjy.buildtalk.utils.HeaderUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bjjy.buildtalk.utils.PlayerHelper;
import com.bjjy.buildtalk.utils.TimeUtils;
import com.bjjy.buildtalk.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author power
 * @date 2019-12-04 12:01
 * @project BuildTalk
 * @description:
 */
public class PlayerWindowManager {

    private boolean isMargin = false;
    private FloatPlayer mFloatPlayer;
    private String songId;
    private List<SongsEntity> mSongsData;
    private FrameLayout contentView;
    private Activity mLastActivity;//用来判定是不是mainactivity
    private static PlayerService.PlayStatusBinder mPlayStatusBinder;
    private static PlayerWindowManager instance = null;
    private DataManager mDataManager;
    private int position; //当前歌曲position
    private String mLastId;
    private View mRootView;
    private CompositeDisposable mCompositeDisposable;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PlayerEvent eventBean) {
        if (Constants.SONG_PLAY == eventBean.getInt_msg()) {
            LogUtils.e("Constants.SONG_PLAY ：开始播放");
            setPlayStatusIcon(R.drawable.media_stop_icon, false);
        } else if (Constants.SONG_PAUSE == eventBean.getInt_msg()) {
            LogUtils.e("Constants.SONG_PAUSE ：音乐暂停");
            setPlayStatusIcon(R.drawable.media_play_icon, true);
            setSeekBar((int) mPlayStatusBinder.getDuration(), 2);
            setTimer((int) mPlayStatusBinder.getDuration(), 2);
        } else if (Constants.SONG_RESUME == eventBean.getInt_msg()) {
            LogUtils.e("Constants.SONG_RESUME ：继续播放");
            setPlayStatusIcon(R.drawable.media_stop_icon, false);
            setSeekBar((int) mPlayStatusBinder.getDuration(), 3);
            setTimer((int) mPlayStatusBinder.getDuration(), 3);
        } else if (Constants.SONG_CHANGE == eventBean.getInt_msg()) {
            LogUtils.e("Constants.SONG_CHANGE ：歌曲改变");
        } else if (Constants.SONG_CLOSE == eventBean.getInt_msg()) {
            LogUtils.e("Constants.SONG_CLOSE ：播放器关闭");
            closePlayer();
        } else if (Constants.SONG_PROGRESS == eventBean.getInt_msg()) {
            LogUtils.e("Constants.SONG_PROGRESS ：播放进度");
            mSeekBarHandler.sendEmptyMessage(0);
        }else if (Constants.SONG_COMPLETION == eventBean.getInt_msg()){
            LogUtils.e("Constants.SONG_COMPLETION ：播放完成");
            mFloatPlayerListener.next();
        }
    }

    private void updateSongs(String status) {
        //请求到数据后先找到数据的position
        mSongsData = mDataManager.getSongsData();
        for (int i = 0; i < mSongsData.size(); i++) {
            if (TextUtils.equals(songId, mSongsData.get(i).getArticle_id())) {
                position = i ; //当前歌曲列表的position
                if (TextUtils.equals(status, "1")){
                    startPlay(songId);
                }else if (TextUtils.equals(status, "2")){
                    mFloatPlayerListener.next();
                }else {
                    mFloatPlayerListener.last();
                }
                return;
            }
        }
    }

    /**
     * 计时器和进度条的handler
     */
    @SuppressLint("HandlerLeak")
    private Handler mSeekBarHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setSeekBar((int) mPlayStatusBinder.getDuration(), 1);
            setTimer((int) mPlayStatusBinder.getDuration(), 1);
        }
    };

    public PlayerWindowManager() {
        EventBus.getDefault().register(this);
    }

    /**
     * @return 单例
     * 我们直接用application的context进行绑定服务，这样就可以用全局的context
     */
    public static PlayerWindowManager getInstance() {
        Intent playIntent = new Intent(App.getContext(), PlayerService.class);
        App.getContext().bindService(playIntent, connection, Context.BIND_AUTO_CREATE);

        if (instance == null) {
            synchronized (PlayerWindowManager.class) {
                if (instance == null) {
                    instance = new PlayerWindowManager();
                }
            }
        }
        return instance;
    }

    private static ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayStatusBinder = (PlayerService.PlayStatusBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            EventBus.getDefault().unregister(this);
        }
    };

    public ServiceConnection getServiceConenction() {
        return connection;
    }

    public PlayerService.PlayStatusBinder getBinder() {
        return mPlayStatusBinder;
    }

    public View getPlayerView(){
        return mFloatPlayer;
    }

    public synchronized void showFloatPlayer(Activity activity, DataManager dataManager, String id, boolean isMargin) {
        if (mLastActivity != null) {
            if (mFloatPlayer != null) {
                contentView.removeView(mFloatPlayer);
            }
        }
        this.mLastActivity = activity;
        this.mDataManager = dataManager;
        this.songId = id;
        this.isMargin = isMargin;
        mRootView = activity.getWindow().getDecorView().getRootView();
        contentView = mRootView.findViewById(android.R.id.content);
        startPlay(id);
    }

    private FloatPlayerListener mFloatPlayerListener = new FloatPlayerListener() {
        @Override
        public void play() {
            if (mPlayStatusBinder.isPlaying()) {
                Notifier.get().showPause(mSongsData.get(position));
                mPlayStatusBinder.pause();
            } else {
                Notifier.get().showPlay(mSongsData.get(position));
                mPlayStatusBinder.resume();
            }
        }

        @Override
        public void next() {
            /**
             * 1.判断position是不是最后一个，如果是最后一个，请求数据
             * 2.position++后播放下一首
             */
            if (position + 1 == mSongsData.size()){
                //请求数据
                requestSongs(songId, "2");
            }else {
                clearSeekAnTimer();//换歌的时候清一下进度
                position++;
                String article_id = mSongsData.get(position).getArticle_id();
                songId = article_id;
                startPlay(article_id);
            }
            mPlayStatusBinder.next();
        }

        @Override
        public void last() {
            /**
             * 1.判断position是不是第一个，如果是第一个，请求数据
             * 2.position--后播放上一首
             */
            if (position == 0){
                requestSongs(songId, "3");
            }else {
                clearSeekAnTimer();//换歌的时候清一下进度
                position--;
                String article_id = mSongsData.get(position).getArticle_id();
                songId = article_id;
                startPlay(article_id);
            }
            mPlayStatusBinder.last();
        }

        @Override
        public void close() {
            mPlayStatusBinder.close();
            mDataManager.setIsSHowPlayer(false);
        }

        @Override
        public void jump() {

        }
    };

    /**
     * @param id 点击开始播放时调用该方法 传入歌曲id
     *           每次点击前 先判断数据库
     */
    private void startPlay(String id) {
        if (mFloatPlayer == null) {
            mFloatPlayer = new FloatPlayer(App.getContext(), isMargin);
            mFloatPlayer.setFloatViewListener(mFloatPlayerListener);
        }
        if (mLastActivity != null) {
            if (mFloatPlayer != null) {
                contentView.removeView(mFloatPlayer);
            }
        }
        updateViewLayoutParams(isMargin);
        mFloatPlayer.setVisibility(View.VISIBLE);
        //更新播放器margin
        contentView.addView(mFloatPlayer);

        /**
         *   1. 播放器关闭后，点击同一个音频，先判断是否关闭，如果是关闭的，继续播放，再判断是否是第二种状态
         *   2. 点击返回的时候会走onresume方法，回调到判断里，改变下播放器信息和状态
         */
        if (TextUtils.equals(mLastId, id)) {
            if (mPlayStatusBinder.isClose()){//播放器关闭
                mFloatPlayer.setVisibility(View.VISIBLE);
                mPlayStatusBinder.resume();
            }else {
                if (mPlayStatusBinder.isPlaying()) {
                    EventBus.getDefault().post(new PlayerEvent(Constants.SONG_PLAY));
                } else {
                    EventBus.getDefault().post(new PlayerEvent(Constants.SONG_PAUSE));
                }
            }
            setPlayerInfo();
        }else {
            //如果id和上次播放的不同，先去数据库查询如果有，记录position，开始播放
            mSongsData = mDataManager.getSongsData();
            if (PlayerHelper.querySongs(id, mDataManager) == null){
                requestSongs(id, "1");
                return;
            }
            for (int i = 0; i < mSongsData.size(); i++) {
                if (TextUtils.equals(id, mSongsData.get(i).getArticle_id())) {
                    clearSeekAnTimer();//换歌的时候清一下进度
                    position = i; //当前歌曲列表的position
                    mPlayStatusBinder.play(mSongsData.get(i).getAudio_url());
                    Notifier.get().showPlay(mSongsData.get(position));
                    setPlayerInfo();
                    mLastId = id;
                    return;
                }
            }
        }
    }

    /**
     * @return 返回当前播放的歌曲id
     */
    public String getSongId(){
        if (TextUtils.isEmpty(songId)){
            return null;
        }
        return songId;
    }

    /**
     * 设置播放器信息
     */
    private void setPlayerInfo() {//设置播放器信息
        mFloatPlayer.setInfo(
                mSongsData.get(position).getAudio_picUrl(),
                mSongsData.get(position).getArticle_title());
    }

    //设置播放状态icon
    private void setPlayStatusIcon(int drawable, boolean isVisibleClose) {
        mFloatPlayer.setPlayStatusIcon(drawable, isVisibleClose);
    }

    /**
     * @param duration 时长
     * @param status  状态  1是开始 2是暂停 3是继续
     * 设置进度条
     */
    private void setSeekBar(int duration, int status) {
        mFloatPlayer.setSeekBar(duration, status);
    }

    /**
     * @param duration 时长
     * @param status  状态  1是开始 2是暂停 3是继续
     * 设置计时器
     */
    private void setTimer(int duration, int status){
        mFloatPlayer.setCountDownTimer(duration, status);
    }

    /**
     * 关闭播放器
     */
    private void closePlayer() {
        mFloatPlayer.setVisibility(View.GONE);
    }

    /**
     * 清除进度条和时长的进度
     */
    private void clearSeekAnTimer(){
        mFloatPlayer.clearSeekAndTimer();
    }

    public void updateViewLayoutParams(boolean isMargin){
        mFloatPlayer.updateViewLayoutParams(isMargin);
    }


    /**
     * @param songId
     * @param status 1是点击播放 2是下一首 3是上一首
     */
    public void requestSongs(String songId, String status){
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        String timestamp = String.valueOf(TimeUtils.getNowSeconds());
        Map<String, String> paramas = new HashMap<>();
        paramas.put("article_id", String.valueOf(songId));
        paramas.put(Constants.TIMESTAMP, timestamp);
        String sign = HeaderUtils.getSign(HeaderUtils.sortMapByKey(paramas, true));

        Map<String, String> headers = new HashMap<>();
        headers.put(App.getContext().getString(R.string.TIMESTAMP), timestamp);
        headers.put(App.getContext().getString(R.string.SIGN), sign);
        mCompositeDisposable.add(mDataManager.searchAudioList(headers, paramas)
                .compose(RxUtils.SchedulerTransformer())
                .subscribeWith(new BaseObserver<List<SongsEntity>>(){
                    @Override
                    public void onSuccess(List<SongsEntity> songsEntities) {
                        /**
                         * 1.判断还有没有新数据(返回集合的最后一个是不是和现在播放的id相同)
                         * 2.拿到数据后添加到数据库,更新歌曲
                         */
                        if (PlayerHelper.querySongs(songId, mDataManager) != null){//如果数据库里有
                            //判断是不是新数据的最后一个或者是第一个
                            if (TextUtils.equals(songId, songsEntities.get(songsEntities.size()-1).getArticle_id()) || TextUtils.equals(songId, songsEntities.get(0).getArticle_id())){
                                ToastUtils.showShort("没有更多数据了");
                            }else {
                                //如果数据库里有,判断他是不是数据库里的第一个或者最后一个
                                List<SongsEntity> songsData = mDataManager.getSongsData();
                                if (TextUtils.equals("2",status) && TextUtils.equals(songId,songsData.get(songsData.size()- 1).getArticle_id())){
                                    mDataManager.addSongsData(songsEntities);
                                    updateSongs(status);
                                }else if (TextUtils.equals("3",status) && TextUtils.equals(songId,songsData.get(0).getArticle_id())){
                                    mDataManager.addSongsData(songsEntities);
                                    updateSongs(status);
                                }
                            }
                        }else {//如果没有替换数据库，更新歌曲
                            mDataManager.addSongsData(songsEntities);
                            updateSongs(status);
                        }
                    }
                }));
    }

    public void receiverPlayClick() {
        mFloatPlayerListener.play();
    }

    public void receiverNextClick() {
        mFloatPlayerListener.next();
    }

    public void receiverLastClick() {
        mFloatPlayerListener.last();
    }
}
