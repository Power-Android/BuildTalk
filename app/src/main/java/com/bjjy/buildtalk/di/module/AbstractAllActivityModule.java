
package com.bjjy.buildtalk.di.module;


import com.bjjy.buildtalk.di.module.activity.MainActivityModule;
import com.bjjy.buildtalk.contains.MainActivity;
import com.bjjy.buildtalk.di.component.BaseActivityComponent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AbstractAllActivityModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributesMainActivityInjector();

}
