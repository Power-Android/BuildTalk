
package com.bjjy.buildtalk.di.module;


import com.bjjy.buildtalk.contains.main.GuideActivity;
import com.bjjy.buildtalk.contains.main.SplashActivity;
import com.bjjy.buildtalk.di.module.activity.GuideActivityModule;
import com.bjjy.buildtalk.di.module.activity.MainActivityModule;
import com.bjjy.buildtalk.contains.main.MainActivity;
import com.bjjy.buildtalk.di.component.BaseActivityComponent;
import com.bjjy.buildtalk.di.module.activity.SplashActivtyModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AbstractAllActivityModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributesMainActivityInjector();

    @ContributesAndroidInjector(modules = GuideActivityModule.class)
    abstract GuideActivity ContributesGuideActivityInjector();

    @ContributesAndroidInjector(modules = SplashActivtyModule.class)
    abstract SplashActivity ContributesSplashActivtyInjector();
}
