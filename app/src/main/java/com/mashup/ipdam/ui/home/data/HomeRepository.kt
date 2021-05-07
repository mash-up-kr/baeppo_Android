package com.mashup.ipdam.ui.home.data

import com.mashup.ipdam.data.map.MapBoundary
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.entity.kakao.address.AddressResult
import io.reactivex.Single

interface HomeRepository {

    fun getReviewsByMapBoundary(
        mapBoundary: MapBoundary
    ): Single<List<Review>>

    fun getAddressByLocation(latitude: Double, longitude: Double): Single<AddressResult>
}