package com.mashup.ipdam.di

import com.mashup.ipdam.data.api.ReviewServiceImpl
import com.mashup.ipdam.data.api.UserServiceImpl
import com.mashup.ipdam.network.IpdamRetrofit
import com.mashup.ipdam.network.service.KakaoService
import com.mashup.ipdam.network.service.ReviewService
import com.mashup.ipdam.network.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

//    @Provides
//    fun provideReviewService(
//        @NetworkModule.IpdamOkHttpClient okHttpClient: OkHttpClient
//    ): ReviewService =
//        IpdamRetrofit.create(
//            ReviewService::class.java,
//            okHttpClient,
//            IpdamRetrofit.IPDAM_API_END_POINT
//        )
    @Provides
    fun provideReviewService(): ReviewService =
        ReviewServiceImpl()

    @Provides
    fun provideKaKaoService(
        @NetworkModule.KakaoOkHttpClient okHttpClient: OkHttpClient
    ): KakaoService =
        IpdamRetrofit.create(
            KakaoService::class.java,
            okHttpClient,
            IpdamRetrofit.KAKAO_API_END_POINT
        )

    @Provides
    fun provideUserService(): UserService =
        UserServiceImpl()
}