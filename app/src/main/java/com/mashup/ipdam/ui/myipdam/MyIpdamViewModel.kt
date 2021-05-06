package com.mashup.ipdam.ui.myipdam

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.SingleLiveEvent
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.ui.myipdam.data.MyIpdamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyIpdamViewModel @Inject constructor(
    private val myIpdamRepository: MyIpdamRepository
) : BaseViewModel() {
    override var logTag: String = "MyIpdamViewModel"
    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews
    private val _isLoading = SingleLiveEvent<Boolean>()
    val isLoading: SingleLiveEvent<Boolean> = _isLoading

    fun getMyIpdamReviews() {
        _isLoading.value = true
        myIpdamRepository.getMyIpdamReview()
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe({
                _isLoading.value = false
                _reviews.value = it
            }, {
                _isLoading.value = false
                Log.e(logTag, it.stackTraceToString())
            }).addToDisposable()
    }
}