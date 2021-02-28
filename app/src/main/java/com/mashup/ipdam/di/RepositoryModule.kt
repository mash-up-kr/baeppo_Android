package com.mashup.ipdam.di

import com.mashup.ipdam.ui.search.data.repository.SearchRepository
import com.mashup.ipdam.ui.search.data.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSearchRepository(
        repositoryImpl: SearchRepositoryImpl
    ): SearchRepository
}
