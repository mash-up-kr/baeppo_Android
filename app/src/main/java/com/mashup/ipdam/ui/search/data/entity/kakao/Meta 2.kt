package com.mashup.ipdam.ui.search.data.entity.kakao

import com.google.gson.annotations.SerializedName

data class Meta (
    @SerializedName("same_name") var sameName : SameName,
    @SerializedName("pageable_count") var pageableCount : Int,
    @SerializedName("total_count") var totalCount : Int,
    @SerializedName("is_end") var isEnd : Boolean
)
