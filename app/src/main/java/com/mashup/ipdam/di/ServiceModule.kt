package com.mashup.ipdam.di

import com.mashup.ipdam.network.IpdamRetrofit
import com.mashup.ipdam.network.service.MapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideSearchService(okHttpClient: OkHttpClient): MapService =
        IpdamRetrofit.create(okHttpClient)
}