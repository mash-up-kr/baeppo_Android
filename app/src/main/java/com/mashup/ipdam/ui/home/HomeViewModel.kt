package com.mashup.ipdam.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.data.map.MapBoundary
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    override var logTag: String = "HomeViewModel"

    private val _bottomSheetState = MutableLiveData(BottomSheetState.MAP_MOVED)
    val bottomSheetState: LiveData<BottomSheetState> = _bottomSheetState

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

     val reviews = MutableLiveData<List<Review>>()

    private val _mapCameraPosition = MutableLiveData<LatLng>()
    val mapCameraPosition = _mapCameraPosition
    private val _address = MutableLiveData("")
    val address: LiveData<String> = _address

    val searchAddress = MutableLiveData("")
    private val _isSearchAddressEmpty = MutableLiveData(false)
    val isSearchAddressEmpty = _isSearchAddressEmpty
    private val _showSearchResultEvent = MutableLiveData(false)
    val showSearchResultEvent = _showSearchResultEvent
    private val _isSearchingPlace = MutableLiveData(false)
    val isSearchingPlace = _isSearchingPlace

    fun getIpdamBySymbol(symbolPosition: LatLng) {

    }

    fun getReviewByMarker() {
        //TODO: 파라미터로 symbolPosition: LatLng를 받을 것. MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        _bottomSheetState.value = BottomSheetState.MARKER_CLICKED
        _name.value = "서울 빌딩"
        _address.value = "서울시 광진구 광진로 55"
        reviews.value = MockReview.getMockReviewList()
    }

    fun getReviewInBoundary(mapBoundary: MapBoundary) {
        //TODO: 이후 MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        _bottomSheetState.value = BottomSheetState.MAP_MOVED
        _name.value = "안암동"
        _address.value = "서울시 광진구 광진로 55"
        reviews.value = MockReview.getMockReviewList()
    }

    fun getResultBySearchAddress() {
        _isSearchingPlace.value = true
        if (searchAddress.value.isNullOrEmpty()) {
            _isSearchAddressEmpty.value = true
        } else {
            _isSearchAddressEmpty.value = false
            _showSearchResultEvent.value = true
        }
    }

    fun setMapCameraPosition(position: LatLng) {
        _mapCameraPosition.value = position
    }
}

enum class BottomSheetState {
    MARKER_CLICKED, MAP_MOVED
}


