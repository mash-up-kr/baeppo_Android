package com.mashup.ipdam.entity.kakao.keyword

import com.google.gson.annotations.SerializedName

data class KeywordResult(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("documents") val places: List<Place>
)
