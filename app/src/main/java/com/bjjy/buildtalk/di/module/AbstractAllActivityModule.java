
package com.bjjy.buildtalk.di.module;


import com.bjjy.buildtalk.contains.discover.CourseListActivity;
import com.bjjy.buildtalk.contains.discover.EveryTalkDetailActivity;
import com.bjjy.buildtalk.contains.discover.EveryTalkListActivity;
import com.bjjy.buildtalk.contains.discover.TopticListActivity;
import com.bjjy.buildtalk.contains.main.GuideActivity;
import com.bjjy.buildtalk.contains.main.MainActivity;
import com.bjjy.buildtalk.contains.main.SplashActivity;
import com.bjjy.buildtalk.di.component.BaseActivityComponent;
import com.bjjy.buildtalk.di.module.activity.CourseListActivityModule;
import com.bjjy.buildtalk.di.module.activity.EverTalkListActivityModule;
import com.bjjy.buildtalk.di.module.activity.EveryTalkDetailModule;
import com.bjjy.buildtalk.di.module.activity.GuideActivityModule;
import com.bjjy.buildtalk.di.module.activity.MainActivityModule;
import com.bjjy.buildtalk.di.module.activity.SplashActivtyModule;
import com.bjjy.buildtalk.di.module.activity.TopticListActivityModule;

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
}
