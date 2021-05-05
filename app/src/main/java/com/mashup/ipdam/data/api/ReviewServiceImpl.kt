package com.mashup.ipdam.data.api

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.network.service.ReviewService
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class ReviewServiceImpl @Inject constructor(): ReviewService{

//    override fun getReviewInBoundary(
//        leftLatitude: Double,
//        topLongitude: Double,
//        rightLatitude: Double,
//        bottomLongitude: Double
//    ): Single<List<ReviewMarker>> {
//
//    }

    override fun createAndUpdateReview(reviewId: String?, review: Review): Single<Review> =
        Single.create { emitter ->
            val db = Firebase.firestore
            val document = if (reviewId != null) {
                db.collection("reviews").document(reviewId)
            } else {
                db.collection("reviews").document()
            }

            db.runBatch { batch ->
                batch.set(document, review)
            }.addOnCompleteListener {
                val newReview = Review(
                    id = document.id,
                    title = review.title,
                    description = review.description,
                    amenities = review.amenities,
                    safety = review.safety,
                    owner = review.owner,
                    clean = review.clean,
                    distance = review.distance,
                    userId = review.userId,
                    address = review.address,
                    buildingName = review.buildingName,
                    latitude = review.latitude,
                    longitude = review.longitude,
                    rating = review.rating,
                    createdAt = review.createdAt,
                    updatedAt = review.updatedAt
                )
                emitter.onSuccess(newReview)
            }.addOnFailureListener { exception ->
                emitter.onError(exception)
            }
        }

    override fun saveReviewImage(reviewId: String, imageUri: Uri): Completable =
        Completable.create { emitter ->
            val imageRef = Firebase.storage.reference.child(
                "reviewImages/${reviewId}${imageUri.lastPathSegment}"
            )
            imageRef.putFile(imageUri)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            emitter.onError(it)
                        }
                    }
                    imageRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        saveImageInFireStore(reviewId, downloadUri, emitter)
                    } else {
                        task.exception?.let {
                            emitter.onError(it)
                        }
                    }
                }
        }

    private fun saveImageInFireStore(
        reviewId: String,
        imageUrl: Uri,
        emitter: CompletableEmitter) {
        val reviewImageData = hashMapOf(
            "reviewId" to reviewId,
            "imageUrl" to imageUrl.toString()
        )
        val db = Firebase.firestore
        db.collection("reviewImages")
            .add(reviewImageData)
            .addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener { e ->
                emitter.onError(e)
            }
    }

//    override fun getReviewsInMyBookmark(primaryId: String): Single<List<ReviewMarker>> {
//
//    }
//
//    override fun getMyReviews(primaryId: String): Single<List<ReviewMarker>> {
//
//    }
//
//    override fun getReview(reviewId: String): Single<Review> {
//       return Singl
//    }
}