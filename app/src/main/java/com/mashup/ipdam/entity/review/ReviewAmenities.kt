package com.mashup.ipdam.entity.review

data class ReviewAmenities(val name: String, val isExist: Boolean) {
    override fun toString(): String {
        return if (isExist) name else ""
    }
}
