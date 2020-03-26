package com.example.helloworld.di.module

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.helloworld.BuildConfig
import com.example.helloworld.core.data.localdb.AppDatabase
import com.example.helloworld.core.data.network.NetworkFactory
import com.example.helloworld.data.datasources.networksource.MessageNetworkService
import com.example.helloworld.data.datasources.networksource.MessageNetworkSource
import com.example.helloworld.data.datasources.networksource.MessageNetworkSourceImpl
import com.example.helloworld.data.main.MainRepository
import com.example.helloworld.data.main.MainRepositoryImpl
import com.example.helloworld.di.annotations.ActivityScope
import com.example.helloworld.di.annotations.ApplicationContext
import com.example.helloworld.di.annotations.FragmentScope
import com.example.helloworld.di.annotations.ViewModelKey
import com.example.helloworld.ui.features.main.MainActivity
import com.example.helloworld.ui.features.main.MainFragment
import com.example.helloworld.ui.features.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

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

    @Binds
    abstract fun bindMainRepository(repo: MainRepositoryImpl): MainRepository

    @Binds
    abstract fun bindNetworkSource(source: MessageNetworkSourceImpl): MessageNetworkSource

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideMessageNetworkService(@ApplicationContext context: Context): MessageNetworkService {
            return NetworkFactory.createService(context, MessageNetworkService::class.java)
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideLocalDB(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(context,
                AppDatabase::class.java, BuildConfig.DB_NAME)
                .build()
        }
    }
}