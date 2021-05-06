package com.mashup.ipdam.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava2.RxDataStore
import io.reactivex.Flowable
import io.reactivex.Single

object UserDataStoreImpl : UserDataStore {

    private const val TAG: String = "UserDataStore"
    private const val USER_DATA_STORE_NAME = "user_data_store_name"

    private object PreferencesKeys {
        val ID_ACCESS_TOKEN = stringPreferencesKey("id")
    }

    private lateinit var authorizationDataStore: RxDataStore<Preferences>

    override fun saveId(id: String): Single<Preferences> =
        authorizationDataStore.updateDataAsync { preferences ->
            val mutablePreferences = preferences.toMutablePreferences().apply {
                set(PreferencesKeys.ID_ACCESS_TOKEN, id)
            }
            Single.just(mutablePreferences)
        }

    override fun getId(): Single<String?> =
        authorizationDataStore
            .data()
            .map { preferences ->
                preferences[PreferencesKeys.ID_ACCESS_TOKEN]
            }.firstOrError()


    fun initUserDataStore(context: Context) {
        authorizationDataStore =
            RxPreferenceDataStoreBuilder(context, USER_DATA_STORE_NAME).build()
    }
}