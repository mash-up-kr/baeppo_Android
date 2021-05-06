package com.mashup.ipdam

import android.app.Application
import com.mashup.ipdam.data.datastore.AuthorizationDataStoreImpl
import com.mashup.ipdam.data.datastore.UserDataStoreImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IpdamApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeDataStore()
    }

    private fun initializeDataStore() {
        AuthorizationDataStoreImpl.initAuthorizationDataStore(this)
        UserDataStoreImpl.initUserDataStore(this)
    }
}

