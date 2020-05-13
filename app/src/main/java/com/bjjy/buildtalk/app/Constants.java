package com.bjjy.buildtalk.app;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description: app常数
 */
public class Constants {

    //Test base_url
    public static final String BASE_URL = "https://www.51jiantan.com/";
    public static final String DEBUG_URL = "https://jt.chinabim.com/";
    //分享公众号的url
    public static final String END_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx94063ed8ba6128d5";

    public static final String PASS_ID = "passId";

    public static final String HEADER_PASSID = "passid:z7gcawmtu4g1blzgnzftns5435638tdl";

    //db_name
    public static final String DB_NAME = "build_talk.db";

    //shared_preference
    public static final String MY_SHARED_PREFERENCE = "build_talk_shared_preference";

    //BaseLoadState
    public static final int NORMAL_STATE = 0;
    public static final int LOADING_STATE = 1;
    public static final int EMPTY_STATE = 2;
    public static final int ERROR_STATE = 3;
    public static final int NONET_STATE = 4;

    public static final long DOUBLE_INTERVAL_TIME = 2000;

    //fragment
    public static final String CURRENT_FRAGMENT_KEY = "current_fragment";
    public static final int TYPE_DISCOVER = 0;
    public static final int TYPE_CIRCLE = 1;
    public static final int TYPE_TALK = 2;
    public static final int TYPE_MINE = 3;
    public static final int TYPE_FIND = 4;

    //TimeUtils
    public static final int MSEC = 1;
    public static final int SEC = 1000;
    public static final int MIN = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY = 86400000;
    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }

    /**
     * Shared Preference key
     */
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String LOGIN_STATUS = "login_status";
    public static final String ISGUIDE = "isGuide";
    public static final String IS_SHOW_PLAYER = "isShowPlayer";

    /**
     * EventBus Tag
     */
    public static final String TOPTIC_REFRESH = "toptic_refresh";
    public static final String TOPTIC_REFRESH_ALL = "toptic_refresh_all";
    public static final String INFO_REFRESH = "info_refresh";
    public static final String CARD_REFRESH = "card_refresh";
    public static final String PAY_SUCCESS = "pay_success";
    public static final String QUIT_CIRCLE = "quit_circle";
    public static final String FANS_REFRESH = "fans_refresh";
    public static final String PUBLISH_ACTIVITY = "publish_activity";
    public static final String NONET_REFRESH = "nonet_refresh";
    public static final String EVENT_BUS_TAG = "event_bus_tag";
    public static final String PROJECT_PAGER = "project_pager";
    public static final String KNOWLEDGE_PAGER = "knowledge_pager";
    public static final String WX_PAGER = "wx_pager";
    public static final String TAG_DEFAULT = "tag_default";

    /**
     * constants
     */
    public static final String TIMESTAMP = "timestamp";
    public static final String SIGN = "sign";
    public static final String SOURCE = "source";
    public static final String ANDROID = "android";
    public static final String USER_ID = "user_id";
    public static final String PAGE = "page";
    public static final String PAGE_SIZE = "page_size";
    public static final String MOBILE = "mobile";

    /**
     * player
     */
    public static final String EXTRA_NOTIFICATION = "com.bjjy.buildtalk.notification";
    public static final int SONG_PLAY = 0;//正在播放
    public static final int SONG_PAUSE = 1;//暂停
    public static final int SONG_RESUME = 2;//继续播放
    public static final int SONG_CHANGE = 3;//歌曲改变（上一首，下一首）
    public static final int SONG_CLOSE = 4;//播放器关闭
    public static final int SONG_PROGRESS = 5;//播放进度
    public static final int SONG_COMPLETION = 6;//播放完成
    public static final int SONG_ISTALK = 7;//判断是否是首页每日一谈的音频,用来改变图标


}
