package com.mashup.ipdam.data

import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

data class ReviewMarker (
    val count: Int,
    val id: Int,
    val latitude: Double,
    val longitude: Double
): TedClusterItem {

    override fun getTedLatLng(): TedLatLng =
        TedLatLng(latitude, longitude)
}