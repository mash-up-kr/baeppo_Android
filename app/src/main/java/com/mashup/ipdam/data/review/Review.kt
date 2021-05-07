package com.mashup.ipdam.data.review

import com.google.firebase.Timestamp
import com.mashup.ipdam.data.map.MapConstants.DEFAULT_LATITUDE
import com.mashup.ipdam.data.map.MapConstants.DEFAULT_LONGITUDE
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

data class Review(
    val id: String? = null,
    val title: String?,
    val description: String?,
    val buildingName: String?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val rating: Double?,
    val owner: Int?,
    val safety: Int?,
    val clean: Int?,
    val distance: Int?,
    val amenities: String?,
    val userPrimaryId: String?,
    val userId: String? = null,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null,
    val images: List<ReviewImage>? = null,
    val isBookmark: Boolean = false,
): TedClusterItem {
    fun isEmpty(): Boolean {
        return title.isNullOrEmpty() or description.isNullOrEmpty() or address.isNullOrEmpty() or
                buildingName.isNullOrEmpty()
    }

    override fun getTedLatLng(): TedLatLng =
        TedLatLng(latitude ?: DEFAULT_LATITUDE,
            longitude ?: DEFAULT_LONGITUDE)
}
