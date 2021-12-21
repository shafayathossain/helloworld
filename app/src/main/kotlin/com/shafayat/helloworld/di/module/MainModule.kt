package com.shafayat.helloworld.di.module

import android.content.Context
import androidx.room.Room
import com.shafayat.helloworld.BuildConfig
import com.shafayat.helloworld.core.data.localdb.AppDatabase
import com.shafayat.helloworld.core.data.network.NetworkFactory
import com.shafayat.helloworld.data.datasources.networksource.MessageNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideMessageNetworkService(@ApplicationContext context: Context): MessageNetworkService {
        return NetworkFactory.createService(context, MessageNetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalDB(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, com.shafayat.helloworld.BuildConfig.DB_NAME
        )
            .build()
    }
}