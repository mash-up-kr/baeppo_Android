package com.mashup.ipdam.data.review

data class ReviewsByMap(
    val count: String,
    val filter: String,
    val reviews: List<Review>
)