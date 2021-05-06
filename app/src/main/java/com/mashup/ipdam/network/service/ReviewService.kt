package com.mashup.ipdam.network.service

import android.net.Uri
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.data.review.ReviewMarker
import com.mashup.ipdam.data.review.Reviews
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ReviewService {
//    @GET("/v1/map")
//    fun getReviewInBoundary(
//        @Query("leftLatitude") leftLatitude: Double,
//        @Query("topLongitude") topLongitude: Double,
//        @Query("rightLatitude") rightLatitude: Double,
//        @Query("bottomLongitude") bottomLongitude: Double,
//    ): Single<List<ReviewMarker>>

    fun createAndUpdateReview(reviewId: String? = null, review: Review): Single<Review>

    fun saveReviewImage(reviewId: String, imageUri: Uri): Completable

//    fun getReviewsInMyBookmark(primaryId: String): Single<List<ReviewMarker>>
//
//    fun getMyReviews(primaryId: String): Single<List<ReviewMarker>>
//
//    fun getReview(reviewId: String): Single<Review>
}
