package com.mashup.ipdam.data.datastore

import androidx.datastore.preferences.core.Preferences
import io.reactivex.Flowable
import io.reactivex.Single

interface UserDataStore {
    fun saveId(id: String): Single<Preferences>
    fun getId(): Flowable<String>
}