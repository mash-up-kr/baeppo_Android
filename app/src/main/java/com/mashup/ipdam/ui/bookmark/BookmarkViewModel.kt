package com.mashup.ipdam.ui.bookmark

import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.data.review.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor() : BaseViewModel() {
    override var logTag: String = "BookmarkViewModel"
    val reviews = MutableLiveData<List<Review>>()
    fun getBookmarkReviews() {

    }
}