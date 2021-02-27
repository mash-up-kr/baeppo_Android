package com.mashup.ipdam

import android.app.Application
import com.mashup.ipdam.data.datastore.AuthorizationDataStoreImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IpdamApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeAuthorizationDataStore()
    }

    private fun initializeAuthorizationDataStore() {
        AuthorizationDataStoreImpl.authorizationDataStoreInit(this)
    }
}

