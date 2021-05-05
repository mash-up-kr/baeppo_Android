package com.mashup.ipdam.ui.myipdam

import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.data.review.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyIpdamViewModel @Inject constructor() : BaseViewModel() {
    override var logTag: String = "MyIpdamViewModel"
    val reviews = MutableLiveData<List<Review>>()
    fun getMyIpdamReviews() {

    }
}