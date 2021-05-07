package com.mashup.ipdam.network.service

import android.net.Uri
import com.mashup.ipdam.data.review.Review
import com.naver.maps.geometry.LatLng
import io.reactivex.Completable
import io.reactivex.Single

interface ReviewService {
    fun getReviewInBoundary(
        userPrimaryId: String,
        topLeftLatLng: LatLng,
        bottomRightLatLng: LatLng
    ): Single<List<Review>>

    fun createAndUpdateReview(reviewId: String? = null, review: Review): Single<Review>

    fun saveReviewImage(reviewId: String, imageUri: Uri): Completable

    fun getReviewsInMyBookmark(userPrimaryId: String): Single<List<Review>>

    fun getMyReviews(userPrimaryId: String): Single<List<Review>>

   // fun getReview(reviewId: String): Single<Review>
}
