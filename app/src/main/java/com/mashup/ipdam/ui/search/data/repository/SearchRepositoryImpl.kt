package com.mashup.ipdam.ui.search.data.repository

import com.mashup.ipdam.network.service.KakaoService

class SearchRepositoryImpl(
    private val kakaoService: KakaoService
) : SearchRepository {
    override fun getPlaceByKeyword(keyword: String) = kakaoService.requestAddressByKeyword(keyword)
}