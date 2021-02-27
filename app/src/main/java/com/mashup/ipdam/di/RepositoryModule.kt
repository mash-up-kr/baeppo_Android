package com.mashup.ipdam.di

import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.network.service.KakaoService
import com.mashup.ipdam.ui.search.data.repository.SearchRepository
import com.mashup.ipdam.ui.search.data.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object RepositoryModule {

    @Provides
    fun provideSearchRepository(kakaoService: KakaoService): SearchRepository =
        SearchRepositoryImpl(kakaoService)
}
