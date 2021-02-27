package com.mashup.ipdam.di

import com.mashup.ipdam.data.datastore.AuthorizationDataStore
import com.mashup.ipdam.data.datastore.AuthorizationDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideAuthorizationDataStore(): AuthorizationDataStore = AuthorizationDataStoreImpl
}