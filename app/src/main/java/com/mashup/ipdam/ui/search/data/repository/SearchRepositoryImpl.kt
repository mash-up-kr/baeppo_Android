package com.mashup.ipdam.ui.search.data.repository

import com.mashup.ipdam.network.service.KakaoService
import com.mashup.ipdam.entity.history.History
import com.mashup.ipdam.ui.search.data.source.HistoryDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val kakaoService: KakaoService,
    private val historyDao: HistoryDao
) : SearchRepository {
    override fun getPlaceByKeyword(keyword: String) = kakaoService.requestAddressByKeyword(keyword)

    override fun getHistoryAll(): Single<List<History>> =
        historyDao.getAll()

    override fun insertHistory(history: History): Completable =
        historyDao.insertHistory(history)

    override fun deleteHistoryAll(): Completable =
        historyDao.deleteAll()

    override fun deleteHistory(history: History): Completable =
        historyDao.deleteHistory(history)

    override fun deleteHistoryWithAddress(address: String): Completable =
        historyDao.deleteHistoryWithAddress(address)
}