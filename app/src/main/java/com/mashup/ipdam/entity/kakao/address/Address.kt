package com.mashup.ipdam.entity.kakao.address

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("region_type") val regionType: String,
    @SerializedName("address_name") val addressName: String,
    @SerializedName("region_1depth_name") val region1DepthName: String,
    @SerializedName("region_2depth_name") val region2DepthName: String,
    @SerializedName("region_3depth_name") val region3DepthName: String,
    @SerializedName("region_4depth_name") val region4DepthName: String,
    @SerializedName("code") val code: String,
    @SerializedName("x") val longitude: Double,
    @SerializedName("y") val latitude: Double
)
