package com.mashup.ipdam.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.SingleLiveEvent
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.data.ReviewMarker
import com.mashup.ipdam.data.map.MapBoundary
import com.mashup.ipdam.ui.home.data.HomeRepository
import com.naver.maps.geometry.LatLng
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
    private val _reviewMarkersOnMap = MutableLiveData<List<ReviewMarker>>()
    val reviewMarkersOnMap: LiveData<List<ReviewMarker>> = _reviewMarkersOnMap

    private val _mapCameraPosition = MutableLiveData<LatLng>()
    val mapCameraPosition = _mapCameraPosition
    private val _address = MutableLiveData("")
    val address: LiveData<String> = _address

    val searchAddress = MutableLiveData("")
    private val _showSearchResultEvent = SingleLiveEvent<Unit>()
    val showSearchResultEvent: SingleLiveEvent<Unit> = _showSearchResultEvent
    private val _isSearchingPlace = SingleLiveEvent<Unit>()
    val isSearchingPlace: SingleLiveEvent<Unit> = _isSearchingPlace

    fun getReviewByMarker(reviewId: Int) {
        savedStateHandle["marker"] = reviewId
        if (_bottomSheetState.value != BottomSheetState.MARKER_CLICKED) _bottomSheetState.value =
            BottomSheetState.MARKER_CLICKED
        reviews.value = MockReview.getMockReviewList()
    }

    fun getReviewInBoundary(mapBoundary: MapBoundary) {
        savedStateHandle["boundary"] = mapBoundary
        homeRepository.getReviewsInBoundary(mapBoundary)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe(
                { data ->
                    if (_bottomSheetState.value != BottomSheetState.MAP_MOVED) _bottomSheetState.value =
                        BottomSheetState.MAP_MOVED
                    _reviewMarkersOnMap.value = data
                },
                {
                    Log.e(logTag, it.toString())
                }
            ).addToDisposable()
        reviews.value = MockReview.getMockReviewList()
    }

    fun getAddressByLatLng(position: LatLng) {
        homeRepository.getAddressByLocation(
            latitude = position.latitude,
            longitude = position.longitude
        ).subscribeOn(SchedulerProvider.io())
            .map { it.address }
            .observeOn(SchedulerProvider.ui())
            .subscribe(
                { data ->
                    if (!data.isEmpty()) {
                        _address.value = data[0].addressName
                        _name.value = data[0].region3DepthName
                    }
                },
                {
                    Log.e(logTag, it.toString())
                }
            ).addToDisposable()

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
        if (bottomSheetState.value == BottomSheetState.MARKER_CLICKED) {
            val reviewId = savedStateHandle.get<Int>("marker")
            reviewId?.let { getReviewByMarker(it) }
        } else {
            val boundary = savedStateHandle.get<MapBoundary>("boundary")
            boundary?.let {
                getReviewInBoundary(boundary)
            }
        }
    }

    fun sortReviewByTime() {
        reviews.apply {
            value = value?.sortedByDescending { it.createdDate }
        }
    }

    fun sortReviewByStar() {
        reviews.apply {
            value = value?.sortedByDescending { it.rating }
        }
    }
}

enum class BottomSheetState {
    MARKER_CLICKED, MAP_MOVED
}


