package com.mashup.ipdam.data.review

data class Review(
    val buildingName: String,
    val createdDate: String,
    val id: Int,
    val images: List<String>,
    val bookmark: Boolean,
    val nickName: String,
    val rating: Double,
    val userId: String
)
