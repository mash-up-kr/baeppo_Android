package com.mashup.ipdam.ui.bookmark

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.SingleLiveEvent
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.ui.bookmark.data.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel() {
    override var logTag: String = "BookmarkViewModel"
    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    private val _isLoading = SingleLiveEvent<Boolean>()
    val isLoading: SingleLiveEvent<Boolean> = _isLoading

    fun getBookmarkReviews() {
        _isLoading.value = true
        bookmarkRepository.getMyBookmarkReview()
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