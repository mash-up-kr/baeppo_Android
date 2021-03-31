package com.mashup.ipdam.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.entity.review.PointType
import com.mashup.ipdam.entity.review.ReviewConstant
import com.mashup.ipdam.entity.review.ReviewPoint
import javax.inject.Inject

class CreateViewModel @Inject constructor() : BaseViewModel() {
    override var logTag: String = "CreateViewModel"

    private val _reviewPointList = MutableLiveData(ReviewConstant.getReviewList())
    val reviewPointList: LiveData<List<ReviewPoint>> = _reviewPointList

    fun setReviewType(position: Int, pointType: PointType) {
        val reviewPointList = _reviewPointList.value ?: ReviewConstant.getReviewList()
        val newReviewPoint = mutableListOf<ReviewPoint>().apply {
            addAll(reviewPointList)
            get(position).pointType = pointType
        }
        _reviewPointList.value = newReviewPoint
    }
}