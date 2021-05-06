package com.mashup.ipdam.ui.bookmark.data

import com.mashup.ipdam.data.review.Review
import io.reactivex.Single

interface BookmarkRepository {
    fun getMyBookmarkReview(): Single<List<Review>>
}