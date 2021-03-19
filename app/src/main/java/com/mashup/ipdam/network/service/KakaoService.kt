package com.mashup.ipdam.network.service

import com.mashup.ipdam.ui.search.data.entity.kakao.PlaceKeyword
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoService {
    @Headers("Authorization: KakaoAK cebbe69291069180ea7e957572c9998f")
    @GET("v2/local/search/keyword.json")
    fun requestAddressByKeyword(
        @Query("query") keyword: String,
    ): Single<PlaceKeyword>
}