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

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name
    private val _address = MutableLiveData("")
    val address: LiveData<String> = _address

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: MutableLiveData<List<Review>> = _reviews

    fun getIpdamBySymbol(symbolPosition: LatLng) {

    }

    fun getReviewByMarker() {
        //TODO: 파라미터로 symbolPosition: LatLng를 받을 것. MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        _bottomSheetState.value = BottomSheetState.MARKER_CLICKED
        _name.value = "서울 빌딩"
        _address.value = "서울시 광진구 광진로 55"
        _reviews.value = listOf(
            Review(
                "서울빌딩",
                "2020.01.20",
                "dahyun",
                listOf(
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAyMTlfMTM4%2FMDAxNjEzNjYzMDgyMzUz.UmYCoopR8Q8rTZTztLy9dHtZ9ZPZCIhHc8F7aUeQn5og.KfjcQvyb-xKR1G_sb28Mv7E8qZ4yBDz-Rf65j55Qhw0g.JPEG.jskdms007%2FIMG_6114.jpg&type=a340",
                    "dd"
                ),
                false,
                "ss",
                1.5,
                "sss"
            ),
            Review(
                "서울빌딩",
                "2020.01.20",
                "dahyun",
                listOf(
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAxMDhfMTY2%2FMDAxNjEwMTE0ODU5OTA0.AQHbOYKQYzAqXjSEmCXjSlKSPe5D87aRv1fr_opYeOIg.AJbs2vOn5bhicqiHogYAF7MvY4rrwvK8FpOHEPJ7tgog.JPEG.psaa980%2F20210105%25A3%25DF114311.jpg&type=a340",
                    "dd"
                ),
                false,
                "ss",
                4.4,
                "sss"
            ),
            Review(
                "서울빌딩",
                "2020.01.20",
                "dahyun",
                listOf(
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDEyMDFfNTYg%2FMDAxNjA2Nzk5MDE2MDUw.U3qreKXfTgkNnzaPzcCMGjJXv59kacodEEn374dX8sgg.6SCh_iWN6Xiq4trm4A2A0TZraZGZP9lzA3N-oOXigbgg.PNG.monk0531%2Fimage.png&type=a340",
                    "dd"
                ),
                true,
                "ss",
                5.0,
                "sss"
            ),
            Review(
                "보빌딩",
                "2020.01.20",
                "dahyun",
                listOf(
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAyMDRfMTYx%2FMDAxNjEyNDQzMzkxNzg4.U_ov3cutDLr5SOKQCuqvIWnjhtY1xnyHb6gn2-D6rgEg.hGHrcNgvx9hRTHGpNqLg2j2KBIwufGfv0biR4XCXmsMg.JPEG.vit4566%2FIMG_0387.jpg&type=a340",
                    "dd"
                ),
                false,
                "ss",
                4.4,
                "sss"
            ),
        )
    }

    fun getReviewInBoundary(mapBoundary: MapBoundary) {
        //TODO: 이후 MOCK DATA 파싱이나 서버 통신 결과를 받아올 예정
        _bottomSheetState.value = BottomSheetState.MAP_MOVED
        _name.value = "안암동"
        _address.value = "서울시 광진구 광진로 55"
        _reviews.value =
            listOf(
                Review(
                    "서울빌딩",
                    "2020.01.20",
                    "dahyun",
                    listOf(
                        "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAyMTlfMTM4%2FMDAxNjEzNjYzMDgyMzUz.UmYCoopR8Q8rTZTztLy9dHtZ9ZPZCIhHc8F7aUeQn5og.KfjcQvyb-xKR1G_sb28Mv7E8qZ4yBDz-Rf65j55Qhw0g.JPEG.jskdms007%2FIMG_6114.jpg&type=a340",
                        "dd"
                    ),
                    false,
                    "ss",
                    1.5,
                    "sss"
                ),
                Review(
                    "안빌딩",
                    "2020.01.20",
                    "dahyun",
                    listOf(
                        "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAxMDhfMTY2%2FMDAxNjEwMTE0ODU5OTA0.AQHbOYKQYzAqXjSEmCXjSlKSPe5D87aRv1fr_opYeOIg.AJbs2vOn5bhicqiHogYAF7MvY4rrwvK8FpOHEPJ7tgog.JPEG.psaa980%2F20210105%25A3%25DF114311.jpg&type=a340",
                        "dd"
                    ),
                    false,
                    "ss",
                    4.4,
                    "sss"
                ),
                Review(
                    "보빌딩",
                    "2020.01.20",
                    "dahyun",
                    listOf(
                        "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDEyMDFfNTYg%2FMDAxNjA2Nzk5MDE2MDUw.U3qreKXfTgkNnzaPzcCMGjJXv59kacodEEn374dX8sgg.6SCh_iWN6Xiq4trm4A2A0TZraZGZP9lzA3N-oOXigbgg.PNG.monk0531%2Fimage.png&type=a340",
                        "dd"
                    ),
                    false,
                    "ss",
                    4.4,
                    "sss"
                ),
                Review(
                    "보빌딩",
                    "2020.01.20",
                    "dahyun",
                    listOf(
                        "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAyMDRfMTYx%2FMDAxNjEyNDQzMzkxNzg4.U_ov3cutDLr5SOKQCuqvIWnjhtY1xnyHb6gn2-D6rgEg.hGHrcNgvx9hRTHGpNqLg2j2KBIwufGfv0biR4XCXmsMg.JPEG.vit4566%2FIMG_0387.jpg&type=a340",
                        "dd"
                    ),
                    false,
                    "ss",
                    4.4,
                    "sss"
                ),

                )
    }
}

enum class BottomSheetState {
    MARKER_CLICKED, MAP_MOVED
}