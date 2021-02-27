package com.mashup.ipdam.data

data class Review(
    val buildingName: String,
    val createdDate: String,
    val id: String,
    val images: List<String>,
    val isBookmark: String,
    val nickName: String,
    val rating: String,
    val userId: String
)