package com.mashup.ipdam.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.data.map.MapBoundary
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    override var logTag: String = "HomeViewModel"

    private val _bottomSheetState = MutableLiveData(BottomSheetState.MAP_MOVED)
    val bottomSheetState: LiveData<BottomSheetState> = _bottomSheetState

    private val _ipdamCount = MutableLiveData(0)
    val ipdamCount: LiveData<Int> = _ipdamCount
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name
    private val _address = MutableLiveData("")
    val address: LiveData<String> = _address

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    fun getIpdamBySymbol(symbolPosition: LatLng) {

    }

    fun getReviewByMarker() {
        //TODO: 파라미터로 symbolPosition: LatLng를 받을 것. MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        _bottomSheetState.value = BottomSheetState.MARKER_CLICKED
        _reviews.value = listOf(
            Review("개금빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
            Review("개금빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
            Review("개금빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
            Review("개금빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
            Review("개금빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
        )
    }

    fun getReviewInBoundary(mapBoundary: MapBoundary) {
        //TODO: 이후 MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        _bottomSheetState.value = BottomSheetState.MAP_MOVED
        _address.value = "서울 빌딩"
        _ipdamCount.value = 3
        _reviews.value =
            listOf(
                Review("우와빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
                Review("우와2빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
                Review("우와3빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
                Review("우와4빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
                Review("우와2빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
                Review("우와3빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
                Review("우와4빌딩", "20202020", "dahyun", listOf("S", "dd"), "ss", "ss", "ss", "sss"),
            )
    }
}

enum class BottomSheetState {
    MARKER_CLICKED, MAP_MOVED
}