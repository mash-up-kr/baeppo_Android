package com.mashup.ipdam.di

import com.mashup.ipdam.BuildConfig
import com.mashup.ipdam.network.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val TIMEOUT_CONNECT = 10L

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IpdamOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class KakaoOkHttpClient

    @IpdamOkHttpClient
    @Provides
    fun provideIpdamOkHttpClient(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .addInterceptor(createLoggingInterceptor())
            .addInterceptor(authorizationInterceptor)
            .build()

    @KakaoOkHttpClient
    @Provides
    fun provideKaKaoOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .addInterceptor(createLoggingInterceptor())
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("KakaoAK", "KakaoAK cebbe69291069180ea7e957572c9998f")
                    .build()

                chain.proceed(request)
            }
            .build()


    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}
