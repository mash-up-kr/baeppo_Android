package com.mashup.ipdam.ui.search.data.repository

import com.mashup.ipdam.entity.history.History
import com.mashup.ipdam.entity.kakao.keyword.KeywordResult
import io.reactivex.Completable
import io.reactivex.Single

interface SearchRepository {
    fun getPlaceByKeyword(keyword: String): Single<KeywordResult>

    fun getHistoryAll(): Single<List<History>>

    fun insertHistory(history: History): Completable

    fun deleteHistoryAll(): Completable

    fun deleteHistory(history: History): Completable

    fun deleteHistoryWithAddress(address: String): Completable
}