package com.mashup.ipdam.network

import com.mashup.base.schedulers.SchedulerProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object IpdamRetrofit {
    // TODO Replace end-point
    const val API_END_POINT = "https://example.com/api"

    fun <T> create(
        service: Class<T>,
        client: OkHttpClient,
        httpUrl: String = API_END_POINT
    ): T = Retrofit.Builder()
        .baseUrl(httpUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(SchedulerProvider.io()))
        .build()
        .create(service)

    inline fun <reified T : Any> create(
        client: OkHttpClient,
        httpUrl: String = API_END_POINT
    ): T {
        require(httpUrl.isNotBlank()) { "Parameter httpUrl cannot be blank." }
        return create(service = T::class.java, httpUrl = httpUrl, client = client)
    }
} 