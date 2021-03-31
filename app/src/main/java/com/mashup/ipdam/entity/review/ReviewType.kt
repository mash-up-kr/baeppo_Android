package com.mashup.ipdam.entity.review

import androidx.annotation.ArrayRes
import com.mashup.ipdam.R

enum class ReviewType(
    @ArrayRes val arrayResId: Int
) {
    DISTANCE(R.array.create_point_distance),
    HOST(R.array.create_point_host),
    SAFETY(R.array.create_point_safety),
    CLEANNESS(R.array.create_point_cleanness)
}