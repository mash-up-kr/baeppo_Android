package com.mashup.ipdam.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava2.RxDataStore
import io.reactivex.Flowable
import io.reactivex.Single

object AuthorizationDataStoreImpl : AuthorizationDataStore {

    private const val TAG: String = "AuthorizationDataStore"
    private const val AUTHORIZATION_DATA_STORE_NAME = "authorization_data_store_name"

    private object PreferencesKeys {
        val AUTHORIZATION_ACCESS_TOKEN = stringPreferencesKey("authorization")
    }

    private lateinit var authorizationDataStore: RxDataStore<Preferences>

    override fun saveAccessToken(accessToken: String): Single<Preferences> =
        authorizationDataStore.updateDataAsync { preferences ->
            val mutablePreferences = preferences.toMutablePreferences().apply {
                set(PreferencesKeys.AUTHORIZATION_ACCESS_TOKEN, accessToken)
            }
            Single.just(mutablePreferences)
        }

    override fun getAccessToken(): Single<String?> =
        authorizationDataStore
            .data()
            .map { preferences ->
                preferences[PreferencesKeys.AUTHORIZATION_ACCESS_TOKEN]
            }.firstOrError()

    fun initAuthorizationDataStore(context: Context) {
        authorizationDataStore =
            RxPreferenceDataStoreBuilder(context, AUTHORIZATION_DATA_STORE_NAME).build()
    }
}