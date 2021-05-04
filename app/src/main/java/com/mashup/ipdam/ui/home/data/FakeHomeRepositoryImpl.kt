package com.mashup.ipdam.ui.home.data

import com.mashup.ipdam.data.review.ReviewMarker
import com.mashup.ipdam.data.review.ReviewMarkersByMap
import com.mashup.ipdam.data.map.MapBoundary
import com.mashup.ipdam.entity.kakao.address.AddressResult
import com.mashup.ipdam.network.service.KakaoService
import com.mashup.ipdam.network.service.ReviewService
import io.reactivex.Single
import javax.inject.Inject

class FakeHomeRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService,
    private val kakaoService: KakaoService
) : HomeRepository {

    override fun getReviewsInBoundary(mapBoundary: MapBoundary): Single<ReviewMarkersByMap> {
        val reviewMarkers = ReviewMarkersByMap().apply {
            addAll(
                getItems(mapBoundary)
            )
        }
        return Single.just(reviewMarkers)
    }

    private fun getItems(mapBoundary: MapBoundary): List<ReviewMarker> {
        return mutableListOf(
            ReviewMarker(
                id = 1,
                latitude = 37.480750227845085,
                longitude = 126.95086663819522,
                count = 0
            ),
            ReviewMarker(
                id = 5,
                latitude = 37.48105672947453,
                longitude = 126.95625251370473,
                count = 0
            ),
            ReviewMarker(
                id = 4,
                latitude = 37.4812388275091,
                longitude = 126.9529746579089,
                count = 0
            ),
            ReviewMarker(
                id = 3,
                latitude = 37.48400208054287,
                longitude = 126.95164176074785,
                count = 0
            ),
            ReviewMarker(
                id = 2,
                latitude = 37.480181095953505,
                longitude = 126.95047547573188,
                count = 0
            ),
            ReviewMarker(
                id = 11,
                latitude = 37.48130493523419,
                longitude = 126.95415760413938,
                count = 0
            ),
            ReviewMarker(
                id = 13,
                latitude = 37.481676,
                longitude = 126.951205,
                count = 0
            ),
            ReviewMarker(
                id = 20,
                latitude = 37.481306,
                longitude = 126.950561,
                count = 0
            ),
            ReviewMarker(
                id = 20,
                latitude =  37.481945,
                longitude = 126.951425,
                count = 0
            ),
            ReviewMarker(
                id = 114,
                latitude = 37.481787,
                longitude =  126.951613,
                count = 0
            ),
            ReviewMarker(
                id = 1231,
                latitude = 37.481327,
                longitude = 126.952772,
                count = 0,
            )
        )
    }

    override fun getAddressByLocation(latitude: Double, longitude: Double): Single<AddressResult> =
        kakaoService.requestAddressByLocation(
            latitude = latitude.toString(),
            longitude = longitude.toString()
        )
}