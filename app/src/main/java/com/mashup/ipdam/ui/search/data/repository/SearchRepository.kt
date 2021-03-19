package com.mashup.ipdam.ui.search.data.repository

import com.mashup.ipdam.ui.search.data.entity.kakao.PlaceKeyword
import io.reactivex.Single

interface SearchRepository {
    fun getPlaceByKeyword(keyword: String): Single<PlaceKeyword>
}