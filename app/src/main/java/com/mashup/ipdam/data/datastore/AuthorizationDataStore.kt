package com.mashup.ipdam.data.datastore

import androidx.datastore.preferences.core.Preferences
import io.reactivex.Flowable
import io.reactivex.Single

interface AuthorizationDataStore {
    fun saveAccessToken(accessToken: String): Single<Preferences>
    fun getAccessToken(): Flowable<String>
    fun saveId(id: String): Single<Preferences>
    fun getId(): Flowable<String>
}