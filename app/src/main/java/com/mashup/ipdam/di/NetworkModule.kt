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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val TIMEOUT_CONNECT = 10L

    @Provides
    fun provideOkHttpClient(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .addInterceptor(add_editLoggingInterceptor())
            .addInterceptor(authorizationInterceptor)
            .build()


    private fun add_editLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}
