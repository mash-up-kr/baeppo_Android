package com.mashup.ipdam.network

import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.data.datastore.AuthorizationDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val authorizationDataStore: AuthorizationDataStore
) : Interceptor {

    companion object {
        private const val KEY_ACCESS_TOKEN = "authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .apply {
                    val token = authorizationDataStore.getAccessToken()
                        .subscribeOn(SchedulerProvider.io())
                        .blockingFirst()
                    header(KEY_ACCESS_TOKEN, token)
                }
            .build()
    )
}