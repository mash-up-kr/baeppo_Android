package com.mashup.ipdam.entity.review

data class ReviewPoint(
    val reviewType: ReviewType,
    var pointType: PointType = PointType.NONE
)

