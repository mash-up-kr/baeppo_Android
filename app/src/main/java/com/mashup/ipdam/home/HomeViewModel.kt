package com.mashup.ipdam.home

import com.mashup.base.BaseViewModel
import com.mashup.ipdam.data.map.MapBoundary
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    override var logTag: String = "HomeViewModel"

    fun getIpdamBySymbol(symbolPosition: LatLng) {

    }

    fun getIpdamInBoundary(mapBoundary: MapBoundary) {

    }
}