package com.mashup.ipdam.entity.review

import androidx.annotation.ArrayRes
import com.mashup.ipdam.R

data class ReviewPoint(
    val reviewType: ReviewType,
    val pointType: PointType = PointType.NONE
)

enum class PointType(val pointValue: Int) {
    NONE(-1), BAD(1), NORMAL(2), GOOD(3);

    companion object {
        fun from(value: Int): PointType = PointType.values().first { it.pointValue == value }
    }
}

enum class ReviewType(
    @ArrayRes val arrayResId: Int
) {
    DISTANCE(R.array.review_point_distance),
    HOST(R.array.review_point_host),
    SAFETY(R.array.review_point_safety),
    CLEANNESS(R.array.review_point_cleanness)
}

