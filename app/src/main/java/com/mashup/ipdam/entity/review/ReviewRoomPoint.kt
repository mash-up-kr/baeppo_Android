package com.mashup.ipdam.entity.review

import androidx.annotation.ArrayRes
import com.mashup.ipdam.R

data class ReviewPoint(
    val reviewType: ReviewType,
    val pointType: PointType = PointType.NONE
)

enum class PointType(val pointValue: Int) {
    NONE(-1), BAD(1), NORMAL(2), GOOD(3)
}

enum class ReviewType(
    @ArrayRes val arrayResId: Int
) {
    DISTANCE(R.array.add_edit_point_distance),
    HOST(R.array.add_edit_point_host),
    SAFETY(R.array.add_edit_point_safety),
    CLEANNESS(R.array.add_edit_point_cleanness)
}

