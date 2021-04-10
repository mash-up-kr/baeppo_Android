package com.mashup.ipdam.ui.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.SingleLiveEvent
import com.mashup.ipdam.entity.review.PointType
import com.mashup.ipdam.entity.review.ReviewAmenities
import com.mashup.ipdam.entity.review.ReviewMockData
import com.mashup.ipdam.entity.review.ReviewPoint
import com.mashup.ipdam.error.Error
import javax.inject.Inject

class AddEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    override var logTag: String = "add_editViewModel"

    private val _reviewPointList = MutableLiveData<List<ReviewPoint>>(emptyList())
    val reviewPointList: LiveData<List<ReviewPoint>> = _reviewPointList
    private val _reviewAreaList = MutableLiveData<List<ReviewAmenities>>(emptyList())
    val reviewAmenitiesList: LiveData<List<ReviewAmenities>> = _reviewAreaList
    private val _roomImageList = MutableLiveData<List<String>>(emptyList())
    val roomImageList: LiveData<List<String>> = _roomImageList
    private val _addReviewImageEvent = SingleLiveEvent<Unit>()
    val addReviewImageEvent: SingleLiveEvent<Unit> = _addReviewImageEvent
    private val _addReviewAreaEvent = SingleLiveEvent<Unit>()
    val addReviewAreaEvent: SingleLiveEvent<Unit> = _addReviewAreaEvent
    val rating = MutableLiveData<Double>()
    private val _eventError = SingleLiveEvent<Error>()
    val eventError: SingleLiveEvent<Error> = _eventError


    init {
        val reviewId = savedStateHandle.get<Int>("reviewId")
        reviewId?.let {
            //TODO: 리뷰 정보를 불러와 뷰모델 초기화
        } ?: initDefaultValue()
    }

    private fun initDefaultValue() {
        _reviewPointList.value = ReviewMockData.geReviewPointMockData()
        _reviewAreaList.value = ReviewMockData.getReviewAreaMockData()
        _roomImageList.value = emptyList()
        rating.value = 0.0
    }

    fun setReviewType(position: Int, pointType: PointType) {
        val reviewPointList = _reviewPointList.value ?: ReviewMockData.geReviewPointMockData()
        val newReviewPoint = mutableListOf<ReviewPoint>().apply {
            addAll(reviewPointList)

            val oldItem = removeAt(position)
            add(position, ReviewPoint(oldItem.reviewType, pointType))
        }
        _reviewPointList.postValue(newReviewPoint)
    }

    fun changeSelectedArea(position: Int) {
        val areaList = _reviewAreaList.value ?: emptyList()
        val newAreaList = mutableListOf<ReviewAmenities>().apply {
            addAll(areaList)

            val oldItem = removeAt(position)
            add(position, ReviewAmenities(oldItem.name, !oldItem.isExist))
        }
        _reviewAreaList.postValue(newAreaList)
    }

    fun eventAddReviewImage() {
        _addReviewImageEvent.value = Unit
    }

    fun eventAddReviewArea() {
        _addReviewAreaEvent.value = Unit
    }

    fun setRoomImage(newRoomImageList: List<String>) {
        _roomImageList.postValue(newRoomImageList)
    }

    fun deleteReviewImage(position: Int) {
        val roomImageList = _roomImageList.value ?: emptyList()
        val newImageList = mutableListOf<String>().apply {
            addAll(roomImageList)

            removeAt(position)
        }
        _roomImageList.postValue(newImageList)
    }
}