package com.mashup.ipdam.ui.search.data.entity.kakao

import com.google.gson.annotations.SerializedName

data class PlaceKeyword(
    @SerializedName("meta") var meta: Meta,
    @SerializedName("documents") var places: List<Places>
)
