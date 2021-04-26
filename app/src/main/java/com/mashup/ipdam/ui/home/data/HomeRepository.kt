package com.mashup.ipdam.ui.home.data

import com.mashup.ipdam.data.ReviewMarkersByMap
import com.mashup.ipdam.data.map.MapBoundary
import io.reactivex.Single

interface HomeRepository {
    fun getReviewsInBoundary(mapBoundary: MapBoundary): Single<ReviewMarkersByMap>
}