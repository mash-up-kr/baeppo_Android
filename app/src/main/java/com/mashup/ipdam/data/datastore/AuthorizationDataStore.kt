package com.mashup.ipdam.data.datastore

import kotlinx.coroutines.flow.Flow

interface AuthorizationDataStore {
    suspend fun saveAccessToken(accessToken: String)
    fun getAccessToken(): Flow<String>
}