package com.mashup.ipdam.data

data class ReviewsByMap(
    val count: String,
    val filter: String,
    val reviews: List<Review>
)