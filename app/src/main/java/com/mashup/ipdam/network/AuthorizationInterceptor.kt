package com.mashup.ipdam.network

import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.data.datastore.AuthorizationDataStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val authorizationDataStore: AuthorizationDataStore
) : Interceptor {

    companion object {
        private const val KEY_ACCESS_TOKEN = "authorization"
        private const val DEFAULT_TOKEN = "none"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .apply {
                    val token = authorizationDataStore.getAccessToken()
                        .subscribeOn(SchedulerProvider.io())
                        .blockingGet()
                    header(KEY_ACCESS_TOKEN, token ?: DEFAULT_TOKEN)
                }
            .build()
    )
}