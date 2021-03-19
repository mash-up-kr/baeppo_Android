package com.mashup.ipdam.ui.search.data.entity.kakao

import com.google.gson.annotations.SerializedName

data class SameName(
    @SerializedName("region") val region: List<String>,
    @SerializedName("keyword") val keyword: String,
    @SerializedName("selected_region") val selectedRegion: String
)
