
package com.bjjy.buildtalk.di.module;


import com.bjjy.buildtalk.di.component.BaseActivityComponent;
import com.bjjy.buildtalk.di.module.activity.CircleAgreementActivityModule;
import com.bjjy.buildtalk.di.module.activity.CircleListActivityModule;
import com.bjjy.buildtalk.di.module.activity.CircleSearchActivityModule;
import com.bjjy.buildtalk.di.module.activity.CourseCircleActivityModule;
import com.bjjy.buildtalk.di.module.activity.CourseListActivityModule;
import com.bjjy.buildtalk.di.module.activity.CreateCircleActivityModule;
import com.bjjy.buildtalk.di.module.activity.EverTalkListActivityModule;
import com.bjjy.buildtalk.di.module.activity.EveryTalkDetailModule;
import com.bjjy.buildtalk.di.module.activity.FansFocusActivityModule;
import com.bjjy.buildtalk.di.module.activity.FeedBackActivityModule;
import com.bjjy.buildtalk.di.module.activity.GuideActivityModule;
import com.bjjy.buildtalk.di.module.activity.LoginActivityModule;
import com.bjjy.buildtalk.di.module.activity.MainActivityModule;
import com.bjjy.buildtalk.di.module.activity.MasterCircleActivityModule;
import com.bjjy.buildtalk.di.module.activity.MasterCollectActivityModule;
import com.bjjy.buildtalk.di.module.activity.MasterDetailActivityModule;
import com.bjjy.buildtalk.di.module.activity.MasterListActivityModule;
import com.bjjy.buildtalk.di.module.activity.PersonInfoActivityModule;
import com.bjjy.buildtalk.di.module.activity.PhoneLoginActivityModule;
import com.bjjy.buildtalk.di.module.activity.SettingActivityModule;
import com.bjjy.buildtalk.di.module.activity.SplashActivtyModule;
import com.bjjy.buildtalk.di.module.activity.TalkSearchActivityModule;
import com.bjjy.buildtalk.di.module.activity.TopticCircleActivityModule;
import com.bjjy.buildtalk.di.module.activity.TopticListActivityModule;
import com.bjjy.buildtalk.di.module.activity.WalletActivityModule;
import com.bjjy.buildtalk.ui.circle.CircleAgreementActvity;
import com.bjjy.buildtalk.ui.circle.CircleSearchActivity;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.bjjy.buildtalk.ui.circle.CreateCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.ui.discover.CourseListActivity;
import com.bjjy.buildtalk.ui.discover.EveryTalkDetailActivity;
import com.bjjy.buildtalk.ui.discover.EveryTalkListActivity;
import com.bjjy.buildtalk.ui.discover.TopticListActivity;
import com.bjjy.buildtalk.ui.main.GuideActivity;
import com.bjjy.buildtalk.ui.main.LoginActivity;
import com.bjjy.buildtalk.ui.main.MainActivity;
import com.bjjy.buildtalk.ui.main.PhoneLoginActivity;
import com.bjjy.buildtalk.ui.main.SplashActivity;
import com.bjjy.buildtalk.ui.mine.FeedBackActivity;
import com.bjjy.buildtalk.ui.mine.PersonInfoActivity;
import com.bjjy.buildtalk.ui.mine.SettingActivity;
import com.bjjy.buildtalk.ui.mine.WalletActivity;
import com.bjjy.buildtalk.ui.talk.CircleListActivity;
import com.bjjy.buildtalk.ui.talk.FansFocusActivity;
import com.bjjy.buildtalk.ui.talk.MasterCircleActivity;
import com.bjjy.buildtalk.ui.talk.MasterCollectActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.ui.talk.MasterListActivity;
import com.bjjy.buildtalk.ui.talk.TalkSearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AbstractAllActivityModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mMainActivity();

    @ContributesAndroidInjector(modules = GuideActivityModule.class)
    abstract GuideActivity mGuideActivity();

    @ContributesAndroidInjector(modules = SplashActivtyModule.class)
    abstract SplashActivity mSplashActivity();

    @ContributesAndroidInjector(modules = EverTalkListActivityModule.class)
    abstract EveryTalkListActivity mEveryTalkListActivity();

    @ContributesAndroidInjector(modules = TopticListActivityModule.class)
    abstract TopticListActivity mTopticListActivity();

    @ContributesAndroidInjector(modules = CourseListActivityModule.class)
    abstract CourseListActivity mCourseListActivity();

    @ContributesAndroidInjector(modules = EveryTalkDetailModule.class)
    abstract EveryTalkDetailActivity mEveryTalkDetailActivity();

    @ContributesAndroidInjector(modules = TalkSearchActivityModule.class)
    abstract TalkSearchActivity mTalkSearchActivity();

    @ContributesAndroidInjector(modules = MasterListActivityModule.class)
    abstract MasterListActivity mMasterListActivity();

    @ContributesAndroidInjector(modules = CircleListActivityModule.class)
    abstract CircleListActivity mCircleListActivity();

    @ContributesAndroidInjector(modules = MasterDetailActivityModule.class)
    abstract MasterDetailActivity mMasterDetailActivity();

    @ContributesAndroidInjector(modules = MasterCircleActivityModule.class)
    abstract MasterCircleActivity mMasterCircleActivity();

    @ContributesAndroidInjector(modules = FansFocusActivityModule.class)
    abstract FansFocusActivity mFansFocusActivity();

    @ContributesAndroidInjector(modules = MasterCollectActivityModule.class)
    abstract MasterCollectActivity mMasterCollectActivity();

    @ContributesAndroidInjector(modules = CircleSearchActivityModule.class)
    abstract CircleSearchActivity mCircleSearchActivity();

    @ContributesAndroidInjector(modules = CircleAgreementActivityModule.class)
    abstract CircleAgreementActvity mCircleAgreementActvity();

    @ContributesAndroidInjector(modules = CreateCircleActivityModule.class)
    abstract CreateCircleActivity mCreateCircleActivity();

    @ContributesAndroidInjector(modules = TopticCircleActivityModule.class)
    abstract TopticCircleActivity mTopticCircleActivity();

    @ContributesAndroidInjector(modules = CourseCircleActivityModule.class)
    abstract CourseCircleActivity mCourseCircleActivity();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity mLoginActivity();

    @ContributesAndroidInjector(modules = PhoneLoginActivityModule.class)
    abstract PhoneLoginActivity mPhoneLoginActivity();

    @ContributesAndroidInjector(modules = WalletActivityModule.class)
    abstract WalletActivity mWalletActivity();

    @ContributesAndroidInjector(modules = SettingActivityModule.class)
    abstract SettingActivity mSettingActivity();

    @ContributesAndroidInjector(modules = PersonInfoActivityModule.class)
    abstract PersonInfoActivity mPersonInfoActivity();

    @ContributesAndroidInjector(modules = FeedBackActivityModule.class)
    abstract FeedBackActivity mFeedBackActivity();

}
