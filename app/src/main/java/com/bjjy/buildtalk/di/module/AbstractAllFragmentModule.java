
package com.bjjy.buildtalk.di.module;

import com.bjjy.buildtalk.di.component.BaseFragmentComponent;
import com.bjjy.buildtalk.di.module.fragment.CircleFragmentModule;
import com.bjjy.buildtalk.di.module.fragment.DiscoverFragmentmodule;
import com.bjjy.buildtalk.di.module.fragment.HomeFragmentModule;
import com.bjjy.buildtalk.di.module.fragment.MineFragmentModule;
import com.bjjy.buildtalk.di.module.fragment.TalkFagmentModule;
import com.bjjy.buildtalk.ui.circle.CircleFragment;
import com.bjjy.buildtalk.ui.discover.DiscoverFragment;
import com.bjjy.buildtalk.ui.home.HomeFragment;
import com.bjjy.buildtalk.ui.mine.MineFragment;
import com.bjjy.buildtalk.ui.talk.TalkFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AbstractAllFragmentModule {

    @ContributesAndroidInjector(modules = DiscoverFragmentmodule.class)
    abstract DiscoverFragment contributesDiscoverFragmentInject();

    @ContributesAndroidInjector(modules = CircleFragmentModule.class)
    abstract CircleFragment contributesCircleFragmentInject();

    @ContributesAndroidInjector(modules = TalkFagmentModule.class)
    abstract TalkFragment contributesTalkFragmentInject();

    @ContributesAndroidInjector(modules = MineFragmentModule.class)
    abstract MineFragment contributesMineFragmentInject();

    @ContributesAndroidInjector(modules = HomeFragmentModule.class)
    abstract HomeFragment contributesHomeFragmentInject();
}
