package com.bjjy.buildtalk.di.component;

import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.di.module.AbstractAllActivityModule;
import com.bjjy.buildtalk.di.module.AbstractAllFragmentModule;
import com.bjjy.buildtalk.di.module.AppModule;
import com.bjjy.buildtalk.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AbstractAllActivityModule.class,
        AbstractAllFragmentModule.class,
        AppModule.class,
        HttpModule.class})

public interface AppComponent {

    void inject(App app);
}
