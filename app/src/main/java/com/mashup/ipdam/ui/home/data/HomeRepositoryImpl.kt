package com.mashup.ipdam.ui.home.data

import com.mashup.ipdam.data.review.ReviewMarkersByMap
import com.mashup.ipdam.data.map.MapBoundary
import com.mashup.ipdam.entity.kakao.address.AddressResult
import com.mashup.ipdam.network.service.KakaoService
import com.mashup.ipdam.network.service.ReviewService
import io.reactivex.Single
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService,
    private val kakaoService: KakaoService,
) : HomeRepository {

    override fun getReviewsInBoundary(mapBoundary: MapBoundary): Single<ReviewMarkersByMap> =
        reviewService.requestReviewMarkersByMap(
            leftLatitude = mapBoundary.topLeftLatLng.latitude,
            topLongitude = mapBoundary.topLeftLatLng.longitude,
            rightLatitude = mapBoundary.bottomRightLatLng.latitude,
            bottomLongitude = mapBoundary.bottomRightLatLng.longitude,
        )

    override fun getAddressByLocation(latitude: Double, longitude: Double): Single<AddressResult> =
        kakaoService.requestAddressByLocation(
            latitude = latitude.toString(),
            longitude = longitude.toString()
        )
}