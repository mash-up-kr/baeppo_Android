package com.mashup.ipdam.data.review

data class Reviews(
    val count: String,
    val filter: String,
    val reviews: List<Review>
)