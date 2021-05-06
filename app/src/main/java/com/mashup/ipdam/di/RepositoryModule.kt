package com.mashup.ipdam.di

import com.mashup.ipdam.ui.home.data.HomeRepository
import com.mashup.ipdam.ui.home.data.HomeRepositoryImpl
import com.mashup.ipdam.ui.myipdam.data.MyIpdamRepository
import com.mashup.ipdam.ui.myipdam.data.MyIpdamRepositoryImpl
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

    @Binds
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    abstract fun bindMyIpdamRepository(
        myIpdamRepositoryImpl: MyIpdamRepositoryImpl
    ): MyIpdamRepository
}
