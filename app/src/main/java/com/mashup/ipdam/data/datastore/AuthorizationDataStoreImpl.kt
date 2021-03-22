package com.mashup.ipdam.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

object AuthorizationDataStoreImpl : AuthorizationDataStore {

    private const val TAG: String = "AuthorizationDataStore"
    private const val AUTHORIZATION_DATA_STORE_NAME = "authorization_data_store_name"

    private object PreferencesKeys {
        val KEY_ACCESS_TOKEN = stringPreferencesKey("authorization")
    }

    private lateinit var authorizationDataStore: DataStore<Preferences>

    override suspend fun saveAccessToken(accessToken: String) {
        authorizationDataStore.edit { preferences ->
            preferences[PreferencesKeys.KEY_ACCESS_TOKEN] = accessToken
        }
    }

    override fun getAccessToken(): Flow<String> {
        return authorizationDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Log.e(TAG, "Error reading preferences.", exception)
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKeys.KEY_ACCESS_TOKEN] ?: ""
            }
    }

    fun authorizationDataStoreInit(context: Context) {
        authorizationDataStore =
            context.createDataStore(name = AUTHORIZATION_DATA_STORE_NAME)
    }
}