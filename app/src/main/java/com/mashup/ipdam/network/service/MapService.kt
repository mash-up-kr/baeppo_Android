package com.mashup.ipdam.network.service

import com.mashup.ipdam.data.ReviewMarkersByMap
import com.mashup.ipdam.data.ReviewsByMap
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {
    @GET("/v1/map")
    fun requestReviewMarkersByMap(
        @Query("leftLatitude") leftLatitude: Double,
        @Query("topLongitude") topLongitude: Double,
        @Query("rightLatitude") rightLatitude: Double,
        @Query("bottomLongitude") bottomLongitude: Double,
    ): Single<ReviewMarkersByMap>

    @GET("/v1/map/list")
    fun requestReviewsByMap(
        @Query("leftLatitude") leftLatitude: Double,
        @Query("topLongitude") topLongitude: Double,
        @Query("rightLatitude") rightLatitude: Double,
        @Query("bottomLongitude") bottomLongitude: Double,
    ): Single<ReviewsByMap>
}
