package com.mashup.ipdam.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.SingleLiveEvent
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.data.map.MapBoundary
import com.mashup.ipdam.ui.home.data.HomeRepository
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    override var logTag: String = "HomeViewModel"

    private val _bottomSheetState = MutableLiveData(BottomSheetState.MAP_MOVED)
    val bottomSheetState: LiveData<BottomSheetState> = _bottomSheetState

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    val reviews = MutableLiveData<List<Review>>()
    private val _reviewMarkersOnMap = MutableLiveData<List<Marker>>()
    val reviewMarkersOnMap: LiveData<List<Marker>> = _reviewMarkersOnMap
    private val _deleteReviewMarkers = SingleLiveEvent<List<Marker>>()
    val deleteReviewMarkers: SingleLiveEvent<List<Marker>> = _deleteReviewMarkers

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

    fun getReviewByMarker(reviewId: Int) {
        //TODO: 파라미터로 symbolPosition: LatLng를 받을 것. MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        savedStateHandle["marker"] = reviewId
        if (_bottomSheetState.value != BottomSheetState.MARKER_CLICKED) _bottomSheetState.value =
            BottomSheetState.MARKER_CLICKED
        _name.value = "서울 빌딩"
        _address.value = "서울시 광진구 광진로 55"
        reviews.value = MockReview.getMockReviewList()
    }

    fun getReviewInBoundary(mapBoundary: MapBoundary) {
        savedStateHandle["boundary"] = mapBoundary
        homeRepository.getReviewsInBoundary(mapBoundary)
            .subscribeOn(SchedulerProvider.io())
            .map { it.toMarkerList() }
            .observeOn(SchedulerProvider.ui())
            .subscribe(
                { data ->
                    //TODO: 화면 중앙의 주소 가져오기
                    if (_bottomSheetState.value != BottomSheetState.MAP_MOVED) _bottomSheetState.value =
                        BottomSheetState.MAP_MOVED
                    _name.value = "안암동"
                    _address.value = "서울시 광진구 광진로 55"
                    _reviewMarkersOnMap.value = data
                },
                {
                    Log.e(logTag, it.toString())
                }
            ).addToDisposable()
    }

    fun getResultBySearchAddress() {
        _isSearchingPlace.value = true
        if (searchAddress.value.isNullOrEmpty()) {
            _isSearchAddressEmpty.value = true
        } else {
            _isSearchAddressEmpty.value = false
            _showSearchResultEvent.value = true
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
        if (bottomSheetState.value == BottomSheetState.MARKER_CLICKED){
            val reviewId = savedStateHandle.get<Int>("marker")
            reviewId?.let { getReviewByMarker(it) }
        } else {
            val boundary = savedStateHandle.get<MapBoundary>("boundary")
            boundary?.let {
                getReviewInBoundary(boundary)
            }
        }
    }
}

enum class BottomSheetState {
    MARKER_CLICKED, MAP_MOVED
}


