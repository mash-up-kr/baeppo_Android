package com.mashup.ipdam.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.data.map.MapBoundary
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    override var logTag: String = "HomeViewModel"

    private val _ipdamCount = MutableLiveData(0)
    val ipdamCount: LiveData<Int> = _ipdamCount
    private val _address = MutableLiveData("")
    val address: LiveData<String> = _address

    private val _bottomSheetVisible = MutableLiveData<Boolean>()
    val bottomSheetVisible = _bottomSheetVisible

    fun getIpdamBySymbol(symbolPosition: LatLng) {

    }

    fun getIpdamInBoundary(mapBoundary: MapBoundary) {
        //TODO: 이후 MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        _bottomSheetVisible.value = false
        _address.value = "서울 빌딩"
        _ipdamCount.value = 72
        _bottomSheetVisible.value = true
    }
}