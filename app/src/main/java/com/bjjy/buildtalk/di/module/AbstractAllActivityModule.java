
package com.bjjy.buildtalk.di.module;


import com.bjjy.buildtalk.di.component.BaseActivityComponent;
import com.bjjy.buildtalk.di.module.activity.AboutUsActivityModule;
import com.bjjy.buildtalk.di.module.activity.BindPhoneActivityModule;
import com.bjjy.buildtalk.di.module.activity.CircleAgreementActivityModule;
import com.bjjy.buildtalk.di.module.activity.CircleDataActivityModule;
import com.bjjy.buildtalk.di.module.activity.CircleInfoActivityModule;
import com.bjjy.buildtalk.di.module.activity.CircleListActivityModule;
import com.bjjy.buildtalk.di.module.activity.CircleManDetailActivityModule;
import com.bjjy.buildtalk.di.module.activity.CircleMemberActivityModule;
import com.bjjy.buildtalk.di.module.activity.CircleSearchActivityModule;
import com.bjjy.buildtalk.di.module.activity.ComplaintReasonActivityModule;
import com.bjjy.buildtalk.di.module.activity.CourseCircleActivityModule;
import com.bjjy.buildtalk.di.module.activity.CourseDetailActivityModule;
import com.bjjy.buildtalk.di.module.activity.CourseListActivityModule;
import com.bjjy.buildtalk.di.module.activity.CreateCircleActivityModule;
import com.bjjy.buildtalk.di.module.activity.DissertationActivityModule;
import com.bjjy.buildtalk.di.module.activity.DissertationListActivityModule;
import com.bjjy.buildtalk.di.module.activity.EditBindPhoneActivityModule;
import com.bjjy.buildtalk.di.module.activity.EditNameActivityModule;
import com.bjjy.buildtalk.di.module.activity.EverTalkListActivityModule;
import com.bjjy.buildtalk.di.module.activity.EveryTalkDetailModule;
import com.bjjy.buildtalk.di.module.activity.FansFocusActivityModule;
import com.bjjy.buildtalk.di.module.activity.FeedBackActivityModule;
import com.bjjy.buildtalk.di.module.activity.GuideActivityModule;
import com.bjjy.buildtalk.di.module.activity.IDCardActivityModule;
import com.bjjy.buildtalk.di.module.activity.LoginActivityModule;
import com.bjjy.buildtalk.di.module.activity.MainActivityModule;
import com.bjjy.buildtalk.di.module.activity.MasterCircleActivityModule;
import com.bjjy.buildtalk.di.module.activity.MasterCollectActivityModule;
import com.bjjy.buildtalk.di.module.activity.MasterDetailActivityModule;
import com.bjjy.buildtalk.di.module.activity.MasterListActivityModule;
import com.bjjy.buildtalk.di.module.activity.MasterVerifyActivityModule;
import com.bjjy.buildtalk.di.module.activity.MyCardActivityModule;
import com.bjjy.buildtalk.di.module.activity.PDFViewerActivityModule;
import com.bjjy.buildtalk.di.module.activity.PersonInfoActivityModule;
import com.bjjy.buildtalk.di.module.activity.PhoneLoginActivityModule;
import com.bjjy.buildtalk.di.module.activity.PublishActivityModule;
import com.bjjy.buildtalk.di.module.activity.ServiceActivityModule;
import com.bjjy.buildtalk.di.module.activity.SetPictureActivityModule;
import com.bjjy.buildtalk.di.module.activity.SettingActivityModule;
import com.bjjy.buildtalk.di.module.activity.ShortVideoActivityModule;
import com.bjjy.buildtalk.di.module.activity.SplashActivtyModule;
import com.bjjy.buildtalk.di.module.activity.TalkSearchActivityModule;
import com.bjjy.buildtalk.di.module.activity.TopticCircleActivityModule;
import com.bjjy.buildtalk.di.module.activity.TopticDetailActivityMudule;
import com.bjjy.buildtalk.di.module.activity.TopticListActivityModule;
import com.bjjy.buildtalk.di.module.activity.TransactionActivityModule;
import com.bjjy.buildtalk.di.module.activity.VersionRecordActivityModule;
import com.bjjy.buildtalk.di.module.activity.ViewpagerActivityModule;
import com.bjjy.buildtalk.di.module.activity.WalletActivityModule;
import com.bjjy.buildtalk.ui.circle.CircleAgreementActvity;
import com.bjjy.buildtalk.ui.circle.CircleDataActivity;
import com.bjjy.buildtalk.ui.circle.CircleInfoActivity;
import com.bjjy.buildtalk.ui.circle.CircleMemberActivity;
import com.bjjy.buildtalk.ui.circle.CircleSearchActivity;
import com.bjjy.buildtalk.ui.circle.ComplaintReasonActivity;
import com.bjjy.buildtalk.ui.circle.CourseCircleActivity;
import com.bjjy.buildtalk.ui.circle.CourseDetailActivity;
import com.bjjy.buildtalk.ui.circle.CreateCircleActivity;
import com.bjjy.buildtalk.ui.circle.MyCardActivity;
import com.bjjy.buildtalk.ui.circle.PDFViewerActivity;
import com.bjjy.buildtalk.ui.circle.PublishActivity;
import com.bjjy.buildtalk.ui.circle.TopticCircleActivity;
import com.bjjy.buildtalk.ui.circle.TopticDetailActivity;
import com.bjjy.buildtalk.ui.discover.CourseListActivity;
import com.bjjy.buildtalk.ui.discover.DissertationActivity;
import com.bjjy.buildtalk.ui.discover.DissertationListActivity;
import com.bjjy.buildtalk.ui.discover.EveryTalkDetailActivity;
import com.bjjy.buildtalk.ui.discover.EveryTalkListActivity;
import com.bjjy.buildtalk.ui.discover.TopticListActivity;
import com.bjjy.buildtalk.ui.main.GuideActivity;
import com.bjjy.buildtalk.ui.main.LoginActivity;
import com.bjjy.buildtalk.ui.main.MainActivity;
import com.bjjy.buildtalk.ui.main.PhoneLoginActivity;
import com.bjjy.buildtalk.ui.main.SplashActivity;
import com.bjjy.buildtalk.ui.main.ViewPagerActivity;
import com.bjjy.buildtalk.ui.mine.AboutUsActivity;
import com.bjjy.buildtalk.ui.mine.BindPhoneActivity;
import com.bjjy.buildtalk.ui.mine.EditBindPhoneActivity;
import com.bjjy.buildtalk.ui.mine.EditNameActivity;
import com.bjjy.buildtalk.ui.mine.FeedBackActivity;
import com.bjjy.buildtalk.ui.mine.IDCardActivity;
import com.bjjy.buildtalk.ui.mine.MasterVerifyActivity;
import com.bjjy.buildtalk.ui.mine.PersonInfoActivity;
import com.bjjy.buildtalk.ui.mine.ServiceActivity;
import com.bjjy.buildtalk.ui.mine.SetPictureActivity;
import com.bjjy.buildtalk.ui.mine.SettingActivity;
import com.bjjy.buildtalk.ui.mine.TransactionActivity;
import com.bjjy.buildtalk.ui.mine.VersionRecordActivity;
import com.bjjy.buildtalk.ui.mine.WalletActivity;
import com.bjjy.buildtalk.ui.talk.CircleListActivity;
import com.bjjy.buildtalk.ui.talk.CircleManDetailActivity;
import com.bjjy.buildtalk.ui.talk.FansFocusActivity;
import com.bjjy.buildtalk.ui.talk.MasterCircleActivity;
import com.bjjy.buildtalk.ui.talk.MasterCollectActivity;
import com.bjjy.buildtalk.ui.talk.MasterDetailActivity;
import com.bjjy.buildtalk.ui.talk.MasterListActivity;
import com.bjjy.buildtalk.ui.talk.TalkSearchActivity;
import com.bjjy.buildtalk.ui.video.ShortVideoActivity;

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

    @ContributesAndroidInjector(modules = AboutUsActivityModule.class)
    abstract AboutUsActivity mAboutUsActivity();

    @ContributesAndroidInjector(modules = TransactionActivityModule.class)
    abstract TransactionActivity mTransactionActivity();

    @ContributesAndroidInjector(modules = ViewpagerActivityModule.class)
    abstract ViewPagerActivity mViewPagerActivity();

    @ContributesAndroidInjector(modules = SetPictureActivityModule.class)
    abstract SetPictureActivity mSetPictureActivity();

    @ContributesAndroidInjector(modules = CircleInfoActivityModule.class)
    abstract CircleInfoActivity mCircleInfoActivity();

    @ContributesAndroidInjector(modules = CircleMemberActivityModule.class)
    abstract CircleMemberActivity mCircleMemberActivity();

    @ContributesAndroidInjector(modules = MyCardActivityModule.class)
    abstract MyCardActivity mMyCardActivity();

    @ContributesAndroidInjector(modules = PublishActivityModule.class)
    abstract PublishActivity mPublishActivity();

    @ContributesAndroidInjector(modules = CircleDataActivityModule.class)
    abstract CircleDataActivity mCircleDataActivity();

    @ContributesAndroidInjector(modules = TopticDetailActivityMudule.class)
    abstract TopticDetailActivity mTopticDetailActivity();

    @ContributesAndroidInjector(modules = CircleManDetailActivityModule.class)
    abstract CircleManDetailActivity mCircleManDetailActivity();

    @ContributesAndroidInjector(modules = EditNameActivityModule.class)
    abstract EditNameActivity mEditNameActivity();

    @ContributesAndroidInjector(modules = BindPhoneActivityModule.class)
    abstract BindPhoneActivity mBindPhoneActivity();

    @ContributesAndroidInjector(modules = EditBindPhoneActivityModule.class)
    abstract EditBindPhoneActivity mEditBindPhoneActivity();

    @ContributesAndroidInjector(modules = CourseDetailActivityModule.class)
    abstract CourseDetailActivity mCourseDetailActivity();

    @ContributesAndroidInjector(modules = ServiceActivityModule.class)
    abstract ServiceActivity mServiceActivity();

    @ContributesAndroidInjector(modules = MasterVerifyActivityModule.class)
    abstract MasterVerifyActivity mMasterVerifyActivity();

    @ContributesAndroidInjector(modules = VersionRecordActivityModule.class)
    abstract VersionRecordActivity mVersionRecordActivity();

    @ContributesAndroidInjector(modules = DissertationActivityModule.class)
    abstract DissertationActivity mDissertationActivity();

    @ContributesAndroidInjector(modules = IDCardActivityModule.class)
    abstract IDCardActivity mIDCardActivity();

    @ContributesAndroidInjector(modules = DissertationListActivityModule.class)
    abstract DissertationListActivity mDissertationListActivity();

    @ContributesAndroidInjector(modules = ComplaintReasonActivityModule.class)
    abstract ComplaintReasonActivity mComplaintReasonActivity();

    @ContributesAndroidInjector(modules = PDFViewerActivityModule.class)
    abstract PDFViewerActivity mPDFViewerActivity();

    @ContributesAndroidInjector(modules = ShortVideoActivityModule.class)
    abstract ShortVideoActivity mShortVideoActivity();
}
