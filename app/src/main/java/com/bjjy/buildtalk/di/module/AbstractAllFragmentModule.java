
package com.bjjy.buildtalk.di.module;

import com.bjjy.buildtalk.contains.circle.CircleFragment;
import com.bjjy.buildtalk.contains.discover.DiscoverFragment;
import com.bjjy.buildtalk.contains.mine.MineFragment;
import com.bjjy.buildtalk.contains.talk.TalkFragment;
import com.bjjy.buildtalk.di.component.BaseFragmentComponent;
import com.bjjy.buildtalk.di.module.fragment.CircleFragmentModule;
import com.bjjy.buildtalk.di.module.fragment.DiscoverFragmentmodule;
import com.bjjy.buildtalk.di.module.fragment.MineFragmentModule;
import com.bjjy.buildtalk.di.module.fragment.TalkFagmentModule;

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
}
