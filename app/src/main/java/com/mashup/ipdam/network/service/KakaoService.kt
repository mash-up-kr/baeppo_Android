package com.mashup.ipdam.network.service

import com.mashup.ipdam.entity.kakao.address.AddressResult
import com.mashup.ipdam.entity.kakao.keyword.KeywordResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {
    @GET("v2/local/search/keyword.json")
    fun requestAddressByKeyword(
        @Query("query") keyword: String,
    ): Single<KeywordResult>

    fun requestAddressByLocation(
        @Query("y") latitude: String,
        @Query("x") longitude: String
    ): Single<AddressResult>
}