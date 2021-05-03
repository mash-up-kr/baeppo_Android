package com.mashup.ipdam.ui.reviewDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.ui.reviewDetail.data.LevelType

class ReviewDetailViewModel : BaseViewModel() {
    override var logTag: String = "ReviewDetailViewModel"

    private val _reviewItem = MutableLiveData<Review>()
    val reviewItem: LiveData<Review>
        get() = _reviewItem

    private val _levelTypeList = MutableLiveData<List<LevelType>>()
    val levelTypeList: LiveData<List<LevelType>>
        get() = _levelTypeList

    private val _amenitiesList = MutableLiveData<List<String>>()
    val amenitiesList: LiveData<List<String>>
        get() = _amenitiesList

    private val _reviewDetailDescription = MutableLiveData<String>()
    val reviewDetailDescription: LiveData<String>
        get() = _reviewDetailDescription

    private val _reviewerId = MutableLiveData<String>()
    val reviewerId: LiveData<String>
        get() = _reviewerId

}