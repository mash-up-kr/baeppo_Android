package com.mashup.ipdam.ui.home.data

import android.util.Log
import com.mashup.ipdam.data.datastore.UserDataStore
import com.mashup.ipdam.data.map.MapBoundary
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.entity.kakao.address.AddressResult
import com.mashup.ipdam.network.service.KakaoService
import com.mashup.ipdam.network.service.ReviewService
import io.reactivex.Single
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService,
    private val userDataStore: UserDataStore,
    private val kakaoService: KakaoService
) : HomeRepository {
    override fun getReviewsByMapBoundary(
        mapBoundary: MapBoundary
    ): Single<List<Review>> {
        return userDataStore.getId()
            .flatMap { userPrimaryId ->
                reviewService.getReviewInBoundary(
                    userPrimaryId,
                    mapBoundary.topLeftLatLng,
                    mapBoundary.bottomRightLatLng
                )
            }
    }

    override fun getAddressByLocation(latitude: Double, longitude: Double): Single<AddressResult> =
        kakaoService.requestAddressByLocation(
            latitude = latitude.toString(),
            longitude = longitude.toString()
        )
}