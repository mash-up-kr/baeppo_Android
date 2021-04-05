package com.mashup.ipdam.entity.review


object ReviewMockData {
    fun geReviewPointMockData(): List<ReviewPoint> = arrayListOf(
        ReviewPoint(ReviewType.DISTANCE),
        ReviewPoint(ReviewType.HOST),
        ReviewPoint(ReviewType.SAFETY),
        ReviewPoint(ReviewType.CLEANNESS)
    )

    fun getReviewAreaMockData(): List<ReviewArea> = arrayListOf(
        ReviewArea("편의점", false),
        ReviewArea("지하철", false),
        ReviewArea("슈퍼", false),
        ReviewArea("대형마트", false),
        ReviewArea("공원", false),
        ReviewArea("세탁소", false)
    )
}
