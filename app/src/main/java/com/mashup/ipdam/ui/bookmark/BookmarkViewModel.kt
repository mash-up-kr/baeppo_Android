package com.mashup.ipdam.ui.bookmark

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.ui.home.MockReview
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor() : BaseViewModel() {
    override var logTag: String = "BookmarkViewModel"
    val reviews = MutableLiveData<List<Review>>()
    fun getBookmarkReviews() {
        reviews.value = MockReview.getMockBookmarkReviews()
        Log.d("!",reviews.value.toString())
    }
}