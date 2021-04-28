package com.mashup.ipdam.entity.kakao.address

import com.google.gson.annotations.SerializedName

data class AddressResult(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("documents") val address: List<Address>
)