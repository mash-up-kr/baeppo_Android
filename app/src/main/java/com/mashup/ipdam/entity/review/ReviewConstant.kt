package com.mashup.ipdam.entity.review

object ReviewConstant {
    fun getReviewList(): List<ReviewPoint> = arrayListOf(
        ReviewPoint(
            ReviewType.DISTANCE,
        ),
        ReviewPoint(
            ReviewType.HOST
        ),
        ReviewPoint(
            ReviewType.SAFETY
        ),
        ReviewPoint(
            ReviewType.CLEANNESS
        )
    )
}