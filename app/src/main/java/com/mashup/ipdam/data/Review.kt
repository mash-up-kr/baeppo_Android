package com.mashup.ipdam.data

data class Review(
    val buildingName: String,
    val createdDate: String,
    val id: String,
    val images: List<String>,
    val bookmark: Boolean,
    val nickName: String,
    val rating: Double,
    val userId: String
)
