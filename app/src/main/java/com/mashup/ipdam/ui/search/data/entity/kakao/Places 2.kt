package com.mashup.ipdam.ui.search.data.entity.kakao

import com.google.gson.annotations.SerializedName

data class Places (
    @SerializedName("place_name") var placeName : String,
    @SerializedName("distance") var distance : String,
    @SerializedName("place_url") var placeUrl : String,
    @SerializedName("category_name") var categoryName : String,
    @SerializedName("address_name") var addressName : String,
    @SerializedName("road_address_name") var roadAddressName : String,
    @SerializedName("id") var id : String,
    @SerializedName("phone") var phone : String,
    @SerializedName("category_group_code") var categoryGroupCode : String,
    @SerializedName("category_group_name") var categoryGroupName : String,
    @SerializedName("x") var x : String,
    @SerializedName("y") var y : String
)
