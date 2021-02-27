package com.mashup.ipdam.network.service

import com.mashup.ipdam.ui.search.data.PlaceKeyword
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.GET

interface KakaoService {
    @GET("v2/local/search/keyword.json")
    fun requestAddressByKeyword(
        @Field("query") keyword: String,
    ): Single<PlaceKeyword>
}