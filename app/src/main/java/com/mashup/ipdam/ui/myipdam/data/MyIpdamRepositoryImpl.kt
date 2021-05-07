package com.mashup.ipdam.ui.myipdam.data

import com.mashup.ipdam.data.datastore.UserDataStore
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.network.service.ReviewService
import io.reactivex.Single
import javax.inject.Inject

class MyIpdamRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService,
    private val userDataStore: UserDataStore
): MyIpdamRepository {
    override fun getMyIpdamReview(): Single<List<Review>> =
        userDataStore.getId()
            .flatMap { userPrimaryId ->
                reviewService.getMyReviews(userPrimaryId)
            }
}