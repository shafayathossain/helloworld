package com.shafayat.helloworld.di.module

import com.shafayat.helloworld.core.data.preference.AppPreference
import com.shafayat.helloworld.core.data.preference.AppPreferenceImpl
import com.shafayat.helloworld.data.datasources.networksource.MessageNetworkSource
import com.shafayat.helloworld.data.datasources.networksource.MessageNetworkSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    abstract fun provideAppPreference(preference: AppPreferenceImpl): AppPreference

    @Binds
    fun bindNetworkSource(source: MessageNetworkSourceImpl): MessageNetworkSource
}