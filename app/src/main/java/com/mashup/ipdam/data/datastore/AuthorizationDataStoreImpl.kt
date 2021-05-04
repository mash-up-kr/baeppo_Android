package com.mashup.ipdam.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.datastore.rxjava2.RxDataStore
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object AuthorizationDataStoreImpl : AuthorizationDataStore {

    private const val TAG: String = "AuthorizationDataStore"
    private const val AUTHORIZATION_DATA_STORE_NAME = "user_data_store_name"

    private object PreferencesKeys {
        val AUTHORIZATION_ACCESS_TOKEN = stringPreferencesKey("authorization")
        val ID_ACCESS_TOKEN = stringPreferencesKey("id")
    }

    private lateinit var authorizationDataStore: RxDataStore<Preferences>

    override fun saveAccessToken(accessToken: String): Single<Preferences> =
        authorizationDataStore.updateDataAsync { preferences ->
            val mutablePreferences = preferences.toMutablePreferences().apply {
                set(PreferencesKeys.AUTHORIZATION_ACCESS_TOKEN, accessToken)
            }
            Single.just(mutablePreferences)
        }

    override fun getAccessToken(): Flowable<String> =
        authorizationDataStore
            .data()
            .map { preferences ->
                preferences[PreferencesKeys.AUTHORIZATION_ACCESS_TOKEN]
            }

    override fun saveId(id: String): Single<Preferences> =
        authorizationDataStore.updateDataAsync { preferences ->
            val mutablePreferences = preferences.toMutablePreferences().apply {
                set(PreferencesKeys.ID_ACCESS_TOKEN, id)
            }
            Single.just(mutablePreferences)
        }

    override fun getId(): Flowable<String> =
        authorizationDataStore
            .data()
            .map { preferences ->
                preferences[PreferencesKeys.ID_ACCESS_TOKEN]
            }


    fun authorizationDataStoreInit(context: Context) {
        authorizationDataStore =
            RxDataStore.create(
                context.createDataStore(name = AUTHORIZATION_DATA_STORE_NAME),
                CoroutineScope(Dispatchers.IO)
            )
    }
}