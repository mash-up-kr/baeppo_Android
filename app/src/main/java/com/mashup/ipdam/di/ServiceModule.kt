package com.mashup.ipdam.di

import com.mashup.ipdam.network.IpdamRetrofit
import com.mashup.ipdam.network.service.KakaoService
import com.mashup.ipdam.network.service.ReviewService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideReviewService(okHttpClient: OkHttpClient): ReviewService =
        IpdamRetrofit.create(
            ReviewService::class.java,
            okHttpClient, IpdamRetrofit.IPDAM_API_END_POINT
        )

    @Provides
    fun provideKaKaoService(okHttpClient: OkHttpClient): KakaoService =
        IpdamRetrofit.create(
            KakaoService::class.java,
            okHttpClient, IpdamRetrofit.KAKAO_API_END_POINT
        )
}