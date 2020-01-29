package com.example.helloworld.di.component

import android.app.Application
import com.example.helloworld.core.BaseApplication
import com.example.helloworld.di.annotations.AppScope
import com.example.helloworld.di.module.AppModule
import com.example.helloworld.di.module.MainModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(modules = arrayOf(
    AndroidSupportInjectionModule::class,
    AppModule::class,
    MainModule::class))
interface ApplicationComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}