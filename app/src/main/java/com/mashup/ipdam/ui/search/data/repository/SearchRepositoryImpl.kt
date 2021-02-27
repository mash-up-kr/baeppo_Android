package com.mashup.ipdam.ui.search.data.repository

import com.mashup.ipdam.network.service.KakaoService
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val kakaoService: KakaoService
) : SearchRepository {
    override fun getPlaceByKeyword(keyword: String) = kakaoService.requestAddressByKeyword(keyword)
}