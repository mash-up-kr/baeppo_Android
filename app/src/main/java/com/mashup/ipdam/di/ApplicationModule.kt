package com.mashup.ipdam.di

import com.mashup.ipdam.data.datastore.AuthorizationDataStore
import com.mashup.ipdam.data.datastore.AuthorizationDataStoreImpl
import com.mashup.ipdam.data.datastore.UserDataStore
import com.mashup.ipdam.data.datastore.UserDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideAuthorizationDataStore(): AuthorizationDataStore = AuthorizationDataStoreImpl

    @Provides
    fun provideUserDataSource(): UserDataStore = UserDataStoreImpl
}