package com.mashup.ipdam.data.review

import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

data class ReviewMarker (
    val count: Int? = 0,
    val id: Int,
    val latitude: Double,
    val longitude: Double
): TedClusterItem {

    override fun getTedLatLng(): TedLatLng =
        TedLatLng(latitude, longitude)
}