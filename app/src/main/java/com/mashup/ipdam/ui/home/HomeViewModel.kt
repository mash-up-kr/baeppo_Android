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
    val searchAddress = MutableLiveData("")
    private val _isSearchAddressEmpty = MutableLiveData(false)
    val isSearchAddressEmpty = _isSearchAddressEmpty
    private val _showSearchResultEvent = MutableLiveData(false)
    val showSearchResultEvent = _showSearchResultEvent
    private val _isSearchingPlace = MutableLiveData(false)
    val isSearchingPlace = _isSearchingPlace

    fun getIpdamBySymbol(symbolPosition: LatLng) {

    }

    fun getIpdamInBoundary(mapBoundary: MapBoundary) {
        //TODO: 이후 MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        _bottomSheetVisible.value = false
        _address.value = "서울 빌딩"
        _ipdamCount.value = 72
        _bottomSheetVisible.value = true
    }

    fun getResultBySearchAddress() {
        _isSearchingPlace.value = true
        if (searchAddress.value.isNullOrEmpty()) {
            _isSearchAddressEmpty.value = true
            _showSearchResultEvent.value = true
        } else {
            _isSearchAddressEmpty.value = false
        }
    }

}