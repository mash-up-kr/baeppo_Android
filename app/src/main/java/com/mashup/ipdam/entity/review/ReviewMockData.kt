package com.mashup.ipdam.entity.review


object ReviewMockData {
    fun geReviewPointMockData(): List<ReviewPoint> = arrayListOf(
        ReviewPoint(ReviewType.DISTANCE),
        ReviewPoint(ReviewType.HOST),
        ReviewPoint(ReviewType.SAFETY),
        ReviewPoint(ReviewType.CLEANNESS)
    )

    fun getReviewAreaMockData(): List<ReviewAmenities> = arrayListOf(
        ReviewAmenities("편의점", false),
        ReviewAmenities("지하철", false),
        ReviewAmenities("슈퍼", false),
        ReviewAmenities("대형마트", false),
        ReviewAmenities("공원", false),
        ReviewAmenities("세탁소", false)
    )
}
