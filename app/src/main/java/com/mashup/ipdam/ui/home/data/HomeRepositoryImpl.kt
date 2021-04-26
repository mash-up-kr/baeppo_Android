package com.mashup.ipdam.ui.home.data

import com.mashup.ipdam.data.ReviewMarkersByMap
import com.mashup.ipdam.data.map.MapBoundary
import com.mashup.ipdam.network.service.ReviewService
import io.reactivex.Single
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService
) : HomeRepository {

    override fun getReviewsInBoundary(mapBoundary: MapBoundary): Single<ReviewMarkersByMap> =
        reviewService.requestReviewMarkersByMap(
            leftLatitude = mapBoundary.topLeftLatLng.latitude,
            topLongitude = mapBoundary.topLeftLatLng.longitude,
            rightLatitude = mapBoundary.bottomRightLatLng.latitude,
            bottomLongitude = mapBoundary.bottomRightLatLng.longitude,
        )
}