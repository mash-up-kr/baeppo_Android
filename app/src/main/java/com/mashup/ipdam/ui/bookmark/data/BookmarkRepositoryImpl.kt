package com.mashup.ipdam.ui.bookmark.data

import com.mashup.ipdam.data.datastore.UserDataStore
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.network.service.ReviewService
import com.mashup.ipdam.ui.myipdam.data.MyIpdamRepository
import io.reactivex.Single
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService,
    private val userDataStore: UserDataStore
): BookmarkRepository {
    override fun getMyBookmarkReview(): Single<List<Review>> =
        userDataStore.getId()
            .flatMap { userPrimaryId ->
                reviewService.getReviewsInMyBookmark(userPrimaryId)
            }
}