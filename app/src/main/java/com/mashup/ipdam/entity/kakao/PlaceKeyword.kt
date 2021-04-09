package com.mashup.ipdam.entity.kakao

import com.google.gson.annotations.SerializedName

data class PlaceKeyword(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("documents") val places: List<Places>
)
