package com.mashup.ipdam.entity.kakao

import com.google.gson.annotations.SerializedName

data class Meta (
    @SerializedName("same_name") val sameName : SameName,
    @SerializedName("pageable_count") val pageableCount : Int,
    @SerializedName("total_count") val totalCount : Int,
    @SerializedName("is_end") val isEnd : Boolean
)
