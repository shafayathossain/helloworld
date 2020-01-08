package com.example.helloworld.di.module

import androidx.lifecycle.ViewModel
import com.example.helloworld.di.annotations.ActivityScope
import com.example.helloworld.di.annotations.FragmentScope
import com.example.helloworld.di.annotations.ViewModelKey
import com.example.helloworld.ui.MainActivity
import com.example.helloworld.ui.MainFragment
import com.example.helloworld.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindMainFragment(): MainFragment

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}