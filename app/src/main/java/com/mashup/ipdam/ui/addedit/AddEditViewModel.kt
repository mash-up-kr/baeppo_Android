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
import javax.inject.Inject

class AddEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    override var logTag: String = "add_editViewModel"

    private val _reviewType = MutableLiveData(AddEditType.ADD)
    val reviewType: LiveData<AddEditType> = _reviewType
    val reviewAddress = MutableLiveData("")
    val reviewDetailAddress = MutableLiveData("")
    private val _showSearchResultEvent = SingleLiveEvent<Unit>()
    val showSearchResultEvent: SingleLiveEvent<Unit> = _showSearchResultEvent
    private val _reviewPointList = MutableLiveData<List<ReviewPoint>>(emptyList())
    val reviewPointList: LiveData<List<ReviewPoint>> = _reviewPointList
    private val _reviewAmenitiesList = MutableLiveData<List<ReviewAmenities>>(emptyList())
    val reviewAmenitiesList: LiveData<List<ReviewAmenities>> = _reviewAmenitiesList
    private val _roomImageList = MutableLiveData<List<String>>(emptyList())
    val roomImageList: LiveData<List<String>> = _roomImageList
    private val _addReviewImageEvent = SingleLiveEvent<Unit>()
    val addReviewImageEvent: SingleLiveEvent<Unit> = _addReviewImageEvent
    private val _addReviewAreaEvent = SingleLiveEvent<Unit>()
    val addReviewAreaEvent: SingleLiveEvent<Unit> = _addReviewAreaEvent
    val rating = MutableLiveData(0.0)


    init {
        val reviewId = savedStateHandle.get<Int>("reviewId")
        reviewId?.let {
            initReviewInfo(reviewId)
        } ?: initDefaultValue()
    }

    private fun initReviewInfo(reviewId: Int) {
        _reviewType.value = AddEditType.EDIT
        //TODO: review id API 통신
    }

    private fun initDefaultValue() {
        _reviewPointList.value = ReviewMockData.geReviewPointMockData()
        _reviewAmenitiesList.value = ReviewMockData.getReviewAreaMockData()
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

    fun changeSelectedAmenities(position: Int) {
        val amenitiesList = _reviewAmenitiesList.value ?: emptyList()
        val newAreaList = mutableListOf<ReviewAmenities>().apply {
            addAll(amenitiesList)

            val oldItem = removeAt(position)
            add(position, ReviewAmenities(oldItem.name, !oldItem.isExist))
        }
        _reviewAmenitiesList.postValue(newAreaList)
    }

    fun addSelectedAmenities(amenities: String) {
        val amenitiesList = _reviewAmenitiesList.value ?: emptyList()
        val newAreaList = mutableListOf<ReviewAmenities>().apply {
            addAll(amenitiesList)

            add(ReviewAmenities(amenities, true))
        }
        _reviewAmenitiesList.postValue(newAreaList)
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

    fun setReviewAddress(address: String) {
        reviewAddress.value = address
    }

    fun deleteReviewImage(position: Int) {
        val roomImageList = _roomImageList.value ?: emptyList()
        val newImageList = mutableListOf<String>().apply {
            addAll(roomImageList)

            removeAt(position)
        }
        _roomImageList.postValue(newImageList)
    }

    fun getReviewAddressBySearch() {
        _showSearchResultEvent.value = Unit
    }
}