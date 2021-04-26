package com.mashup.ipdam.data

import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker

class ReviewMarkersByMap : ArrayList<ReviewMarker>() {
    fun toMarkerList(): List<Marker> = this.map { reviewMarker ->
        val marker = Marker().apply {
            position = LatLng(reviewMarker.latitude, reviewMarker.longitude)
            tag = reviewMarker.id
        }
        marker
    }
}