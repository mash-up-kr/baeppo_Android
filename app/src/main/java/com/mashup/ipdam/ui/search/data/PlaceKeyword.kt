package com.mashup.ipdam.ui.search.data

import com.google.gson.annotations.SerializedName

data class PlaceKeyword(
    @SerializedName("meta") var meta: Meta,
    @SerializedName("documents") var places: List<Places>
)
