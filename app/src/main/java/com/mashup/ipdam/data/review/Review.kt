package com.mashup.ipdam.data.review

import com.google.firebase.Timestamp

data class Review(
    val id: String? = null,
    val title: String,
    val description: String,
    val buildingName: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val owner: Int,
    val safety: Int,
    val clean: Int,
    val distance: Int,
    val amenities: String,
    val userId: String,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null,
    val images: List<String>? = null,
    val bookmark: Boolean = false,
) {
    fun isEmpty(): Boolean {
        return title.isEmpty() or description.isEmpty() or address.isEmpty() or
                buildingName.isEmpty()
    }
}
