package com.mashup.ipdam.ui.search.data.repository

import com.mashup.ipdam.ui.search.data.entity.history.History
import com.mashup.ipdam.ui.search.data.entity.kakao.PlaceKeyword
import io.reactivex.Completable
import io.reactivex.Single

interface SearchRepository {
    fun getPlaceByKeyword(keyword: String): Single<PlaceKeyword>

    fun getHistoryAll(): Single<List<History>>

    fun insertHistory(history: History): Completable

    fun deleteHistoryAll(): Completable

    fun deleteHistory(history: History): Completable

    fun deleteHistoryWithAddress(address: String): Completable
}