package com.mashup.ipdam.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.SingleLiveEvent
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
    private val _showSearchResultEvent = SingleLiveEvent<Unit>()
    val showSearchResultEvent: SingleLiveEvent<Unit> = _showSearchResultEvent
    private val _isSearchingPlace = SingleLiveEvent<Unit>()
    val isSearchingPlace: SingleLiveEvent<Unit> = _isSearchingPlace

    private lateinit var nowMapBoundary: MapBoundary

    fun getIpdamBySymbol(symbolPosition: LatLng) {

    }

    fun getReviewByMarker() {
        //TODO: 파라미터로 symbolPosition: LatLng를 받을 것. MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        if (_bottomSheetState.value != BottomSheetState.MARKER_CLICKED) _bottomSheetState.value =
            BottomSheetState.MARKER_CLICKED
        _name.value = "서울 빌딩"
        _address.value = "서울시 광진구 광진로 55"
        reviews.value = MockReview.getMockReviewList()
    }

    fun getReviewInBoundary(mapBoundary: MapBoundary) {
        //TODO: 이후 MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        nowMapBoundary = mapBoundary
        if (_bottomSheetState.value != BottomSheetState.MAP_MOVED) _bottomSheetState.value =
            BottomSheetState.MAP_MOVED
        _name.value = "안암동"
        _address.value = "서울시 광진구 광진로 55"
        reviews.value = MockReview.getMockReviewList()
    }

    fun getResultBySearchAddress() {
        _isSearchingPlace.value = Unit
        if (searchAddress.value.isNullOrEmpty()) {
        } else {
            _showSearchResultEvent.value = Unit
        }
        searchAddress.value = ""
    }

    fun setMapCameraPosition(position: LatLng) {
        _mapCameraPosition.value = position
    }

    private fun updateReview(review: Review) {
        //TODO: 서버와의 통신으로 review update
    }

    fun toggleBookmark(review: Review) {
        updateReview(review.copy(bookmark = !review.bookmark))
        if (bottomSheetState.value == BottomSheetState.MARKER_CLICKED) getReviewByMarker()
        else if (this::nowMapBoundary.isInitialized) {
            getReviewInBoundary(nowMapBoundary)
        }
    }
}

enum class BottomSheetState {
    MARKER_CLICKED, MAP_MOVED
}


