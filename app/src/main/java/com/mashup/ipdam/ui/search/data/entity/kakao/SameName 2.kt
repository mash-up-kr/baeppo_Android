package com.mashup.ipdam.ui.search.data.entity.kakao

import com.google.gson.annotations.SerializedName

data class SameName(
    @SerializedName("region") var region: List<String>,
    @SerializedName("keyword") var keyword: String,
    @SerializedName("selected_region") var selectedRegion: String
)
