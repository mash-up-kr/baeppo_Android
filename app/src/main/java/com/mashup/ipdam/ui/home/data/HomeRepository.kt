package com.mashup.ipdam.ui.home.data

import com.mashup.ipdam.entity.kakao.address.AddressResult
import io.reactivex.Single

interface HomeRepository {
    fun getAddressByLocation(latitude: Double, longitude: Double): Single<AddressResult>
}