package com.mashup.ipdam.ui.addedit

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.firebase.Timestamp
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.SingleLiveEvent
import com.mashup.ipdam.data.datastore.UserDataStore
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.entity.review.*
import com.mashup.ipdam.network.service.ReviewService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val reviewService: ReviewService,
    private val userDataStore: UserDataStore,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    override var logTag: String = "add_editViewModel"

    private val _reviewType = MutableLiveData(AddEditType.ADD)
    val reviewType: LiveData<AddEditType> = _reviewType
    private val review = MutableLiveData<Review>()

    private val _showSearchResultEvent = SingleLiveEvent<Unit>()
    val showSearchResultEvent: SingleLiveEvent<Unit> = _showSearchResultEvent
    private val _isReviewSaveSuccess = SingleLiveEvent<Unit>()
    val isReviewSaveSuccess: SingleLiveEvent<Unit> = _isReviewSaveSuccess
    private val _isReviewSaveFailed = SingleLiveEvent<Unit>()
    val isReviewSaveFailed: SingleLiveEvent<Unit> = _isReviewSaveFailed
    private val _isReviewInfoEmpty = SingleLiveEvent<Unit>()
    val isReviewInfoEmpty: SingleLiveEvent<Unit> = _isReviewInfoEmpty

    val reviewAddress = MutableLiveData("")
    val reviewDetailAddress = MutableLiveData("")
    private val _reviewPointList = MutableLiveData<List<ReviewPoint>>(emptyList())
    val reviewPointList: LiveData<List<ReviewPoint>> = _reviewPointList
    private val _reviewAmenitiesList = MutableLiveData<List<ReviewAmenities>>(emptyList())
    val reviewAmenitiesList: LiveData<List<ReviewAmenities>> = _reviewAmenitiesList
    private val _roomImageList = MutableLiveData<List<Uri>>(emptyList())
    val roomImageList: LiveData<List<Uri>> = _roomImageList
    private val _addReviewImageEvent = SingleLiveEvent<Unit>()
    val addReviewImageEvent: SingleLiveEvent<Unit> = _addReviewImageEvent
    private val _addReviewAreaEvent = SingleLiveEvent<Unit>()
    val addReviewAreaEvent: SingleLiveEvent<Unit> = _addReviewAreaEvent
    private val safetyValue = MutableLiveData<Int>()
    private val cleanValue = MutableLiveData<Int>()
    private val ownerValue = MutableLiveData<Int>()
    private val distanceValue = MutableLiveData<Int>()
    val rating = MutableLiveData(0.0)
    val title = MutableLiveData("")
    val description = MutableLiveData("")

    init {
        val reviewId = savedStateHandle.get<String>("reviewId")
        reviewId?.let {
            initReviewInfo(reviewId)
        } ?: initDefaultValue()
    }

    private fun initReviewInfo(reviewId: String) {
        _reviewType.value = AddEditType.EDIT
        //TODO: review id API 통신
    }

    private fun initDefaultValue() {
        _reviewPointList.value = ReviewMockData.geReviewPointMockData()
        _reviewAmenitiesList.value = ReviewMockData.getReviewAreaMockData()
    }

    fun requestSaveReview() {
        getPrimaryIdWithAction {
            saveReview(it)
        }
    }

    fun setReviewType(position: Int, pointType: PointType) {
        val reviewPointList = _reviewPointList.value ?: ReviewMockData.geReviewPointMockData()
        val newReviewPoint = mutableListOf<ReviewPoint>().apply {
            addAll(reviewPointList)

            val oldItem = removeAt(position)
            setReviewPointValueWhenChanged(oldItem.reviewType, pointType)
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

    fun setRoomImage(newRoomImageList: List<Uri>) {
        _roomImageList.postValue(newRoomImageList)
    }

    fun setReviewAddress(address: String) {
        reviewAddress.value = address
    }

    fun deleteReviewImage(position: Int) {
        val roomImageList = _roomImageList.value ?: emptyList()
        val newImageList = mutableListOf<Uri>().apply {
            addAll(roomImageList)

            removeAt(position)
        }
        _roomImageList.postValue(newImageList)
    }

    fun getReviewAddressBySearch() {
        _showSearchResultEvent.value = Unit
    }

    fun setReviewLocation(latitude: Double, longitude: Double) {
        savedStateHandle["latitude"] = latitude
        savedStateHandle["longitude"] = longitude
    }

    private fun setReviewPointValueWhenChanged(
        newReviewType: ReviewType,
        newPointValue: PointType
    ) {
        val pointValue = newPointValue.pointValue
        when (newReviewType) {
            ReviewType.HOST -> ownerValue.value = pointValue
            ReviewType.CLEANNESS -> cleanValue.value = pointValue
            ReviewType.SAFETY -> safetyValue.value = pointValue
            else -> distanceValue.value = pointValue
        }
    }

    private fun saveReview(primaryId: String) {
        val review = getReview(primaryId)
        if (review == null || review.isEmpty()) {
            _isReviewInfoEmpty.value = Unit
            return
        }
        val reviewId = savedStateHandle.get<String>("reviewId")
        reviewService.createAndUpdateReview(reviewId, review)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe({ reviewResult ->
                saveImageList(reviewResult.id)
            }, { exception ->
                _isReviewSaveFailed.value = Unit
                Log.e(logTag, exception.stackTraceToString())
            }).addToDisposable()
    }

    private fun saveImageList(reviewId: String?) {
        if (reviewId == null) {
            _isReviewSaveFailed.value = Unit
            return
        }

        var rootCompletable = Completable.fromSingle(Single.just(1))
        roomImageList.value?.forEach { imageUri ->
            rootCompletable = rootCompletable.andThen(
                reviewService.saveReviewImage(reviewId, imageUri)
            )
        }
        rootCompletable
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe({
                _isReviewSaveSuccess.value = Unit
            }, { exception ->
                _isReviewSaveFailed.value = Unit
                Log.e(logTag, exception.stackTraceToString())
            }).addToDisposable()
    }

    private fun getReview(primaryId: String): Review? {
        return Review(
            address = reviewAddress.value ?: return null,
            buildingName = reviewDetailAddress.value ?: return null,
            title = title.value ?: return null,
            description = description.value ?: return null,
            rating = rating.value ?: return null,
            latitude = savedStateHandle.get<Double>("latitude") ?: return null,
            longitude = savedStateHandle.get<Double>("longitude") ?: return null,
            amenities = reviewAmenitiesList.value?.joinToString(separator = ",") ?: return null,
            owner = ownerValue.value ?: return null,
            safety = safetyValue.value ?: return null,
            distance = distanceValue.value ?: return null,
            clean = cleanValue.value ?: return null,
            userId = primaryId,
            createdAt = review.value?.createdAt ?: Timestamp(Date()),
            updatedAt = Timestamp(Date())
        )
    }

    private fun getPrimaryIdWithAction(action: (String) -> Unit) {
        userDataStore.getId()
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe({
                action(it)
            }, { exception ->
                Log.e(logTag, exception.stackTraceToString())
            }).addToDisposable()
    }
}