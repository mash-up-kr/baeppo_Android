package com.mashup.ipdam.data.api

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.data.review.ReviewImage
import com.mashup.ipdam.network.service.ReviewService
import com.naver.maps.geometry.LatLng
import io.reactivex.*
import io.reactivex.Observable
import javax.inject.Inject

class ReviewServiceImpl @Inject constructor() : ReviewService {

    override fun getReviewInBoundary(
        userPrimaryId: String,
        topLeftLatLng: LatLng,
        bottomRightLatLng: LatLng
    ): Single<List<Review>> =
        getReviewDocuments(topLeftLatLng, bottomRightLatLng)
            .flatMap { document ->
                getReviewImagesWithReviewId(document, document.id)
            }.flatMap { (document, images, reviewId) ->
                getIsBookmarkWithImages(
                    userPrimaryId,
                    document,
                    images,
                    reviewId
                )
            }.flatMap { (document, images, isBookmark) ->
                getUserId(userPrimaryId, document, images, isBookmark)
            }.map { (document, triple) ->
                Review(
                    id = document.id,
                    title = document.getString("title"),
                    description = document.getString("description"),
                    amenities = document.getString("amenities"),
                    safety = document.getLong("safety")?.toInt(),
                    owner = document.getLong("owner")?.toInt(),
                    clean = document.getLong("clean")?.toInt(),
                    distance = document.getLong("distance")?.toInt(),
                    userPrimaryId = userPrimaryId,
                    address = document.getString("address"),
                    buildingName = document.getString("buildingName"),
                    latitude = document.getDouble("latitude"),
                    longitude = document.getDouble("longitude"),
                    rating = document.getDouble("rating"),
                    createdAt = document.getTimestamp("createdAt"),
                    updatedAt = document.getTimestamp("updatedAt"),
                    images = triple.first,
                    isBookmark = triple.second,
                    userId = triple.third
                )
            }.toList()

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
                    isBookmark = review.isBookmark,
                    title = review.title,
                    description = review.description,
                    amenities = review.amenities,
                    safety = review.safety,
                    owner = review.owner,
                    clean = review.clean,
                    distance = review.distance,
                    userPrimaryId = review.userId,
                    address = review.address,
                    buildingName = review.buildingName,
                    latitude = review.latitude,
                    longitude = review.longitude,
                    rating = review.rating,
                    userId = review.userId,
                    images = review.images,
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

    override fun getReviewsInMyBookmark(userPrimaryId: String): Single<List<Review>> =
        getIsBookmarkSameUserId(userPrimaryId)
            .flatMap {
                getReviewsDocumentWithReviewId(it)
            }.flatMap { document ->
                getReviewImagesWithReviewId(document, document.id)
            }.flatMap { (document, images, _) ->
                getUserId(userPrimaryId, document, images, true)
            }.map { (document, triple) ->
                Review(
                    id = document.id,
                    title = document.getString("title"),
                    description = document.getString("description"),
                    amenities = document.getString("amenities"),
                    safety = document.getLong("safety")?.toInt(),
                    owner = document.getLong("owner")?.toInt(),
                    clean = document.getLong("clean")?.toInt(),
                    distance = document.getLong("distance")?.toInt(),
                    userPrimaryId = document.getString("userPrimaryId"),
                    address = document.getString("address"),
                    buildingName = document.getString("buildingName"),
                    latitude = document.getDouble("latitude"),
                    longitude = document.getDouble("longitude"),
                    rating = document.getDouble("rating"),
                    createdAt = document.getTimestamp("createdAt"),
                    updatedAt = document.getTimestamp("updatedAt"),
                    images = triple.first,
                    isBookmark = triple.second,
                    userId = triple.third
                )
            }.toList()

    override fun getMyReviews(userPrimaryId: String): Single<List<Review>> =
        getMyReviewsDocument(userPrimaryId)
            .flatMap { document ->
                getReviewImagesWithReviewId(document, document.id)
            }.flatMap { (document, images, reviewId) ->
                getIsBookmarkWithImages(
                    userPrimaryId,
                    document,
                    images,
                    reviewId
                )
            }.flatMap { (document, images, isBookmark) ->
                getUserId(userPrimaryId, document, images, isBookmark)
            }.map { (document, triple) ->
                Review(
                    id = document.id,
                    title = document.getString("title"),
                    description = document.getString("description"),
                    amenities = document.getString("amenities"),
                    safety = document.getLong("safety")?.toInt(),
                    owner = document.getLong("owner")?.toInt(),
                    clean = document.getLong("clean")?.toInt(),
                    distance = document.getLong("distance")?.toInt(),
                    userPrimaryId = userPrimaryId,
                    userId = triple.third,
                    address = document.getString("address"),
                    buildingName = document.getString("buildingName"),
                    latitude = document.getDouble("latitude"),
                    longitude = document.getDouble("longitude"),
                    rating = document.getDouble("rating"),
                    createdAt = document.getTimestamp("createdAt"),
                    updatedAt = document.getTimestamp("updatedAt"),
                    images = triple.first,
                    isBookmark = triple.second
                )
            }.toList()

    override fun deleteMyReview(reviewId: String): Completable =
        Completable.create { emitter ->
            val db = Firebase.firestore
            db.collection("reviews")
                .document(reviewId)
                .delete()
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun deleteBookMarkReview(reviewId: String, userPrimaryId: String): Completable =
        Completable.create { emitter ->
            val db = Firebase.firestore
            db.collection("bookmarks")
                .document(reviewId)
                .delete()
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun createBookmarkReview(reviewId: String, userPrimaryId: String): Completable {
        val bookmarkData = hashMapOf(
            "reviewId" to reviewId,
            "userId" to userPrimaryId
        )

        return Completable.create { emitter ->
            val db = Firebase.firestore
            db.collection("bookmarks")
                .add(bookmarkData)
                .addOnSuccessListener {
                    emitter.onComplete()
                }.addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }
    }

    private fun saveImageInFireStore(
        reviewId: String,
        imageUrl: Uri,
        emitter: CompletableEmitter
    ) {
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

    private fun getReviewDocuments(
        topLeftLatLng: LatLng,
        bottomRightLatLng: LatLng
    ): Observable<DocumentSnapshot> =
        Observable.create { emitter ->
            val db = Firebase.firestore
            db.collection("reviews")
                .whereGreaterThan("latitude", bottomRightLatLng.latitude)
                .get()
                .addOnSuccessListener { documents ->
                    documents.filter {
                        // Firestore 에서 오직 한 필드에게만 쿼리 가능
                        val latitude = it.getDouble("latitude")
                        val longitude = it.getDouble("longitude")
                        if (longitude == null || latitude == null) {
                            false
                        } else {
                            Log.d("LatLng", "latitude=$latitude, longitude=$longitude")
                            longitude >= topLeftLatLng.longitude
                                    && longitude <= bottomRightLatLng.longitude &&
                                    latitude <= topLeftLatLng.latitude
                        }
                    }.forEach { document ->
                        emitter.onNext(document)
                    }
                    emitter.onComplete()
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    private fun getMyReviewsDocument(
        userPrimaryId: String
    ): Observable<DocumentSnapshot> =
        Observable.create { emitter ->
            val db = Firebase.firestore
            db.collection("reviews")
                .whereEqualTo("userPrimaryId", userPrimaryId)
                .get()
                .addOnSuccessListener { documents ->
                    documents.forEach { document ->
                        emitter.onNext(document)
                    }
                    emitter.onComplete()
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    private fun getReviewsDocumentWithReviewId(
        reviewId: String
    ): Observable<DocumentSnapshot> =
        Observable.create { emitter ->
            val db = Firebase.firestore
            db.collection("reviews").document(reviewId)
                .get()
                .addOnSuccessListener { document ->
                    emitter.onNext(document)
                    emitter.onComplete()
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    private fun getReviewImagesWithReviewId(
        reviewDocument: DocumentSnapshot,
        reviewId: String
    ): Observable<Triple<DocumentSnapshot, List<ReviewImage>, String>> =
        Observable.create { emitter ->
            val db = Firebase.firestore
            db.collection("reviewImages")
                .whereEqualTo("reviewId", reviewId)
                .get()
                .addOnSuccessListener { snapshot ->
                    val images = mutableListOf<ReviewImage>()
                    snapshot.forEach { document ->
                        val image = ReviewImage(
                            id = document.id,
                            imageUrl = document.getString("imageUrl"),
                            reviewId = reviewId
                        )
                        images.add(image)
                    }
                    emitter.onNext(Triple(reviewDocument, images, reviewId))
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }

    private fun getIsBookmarkSameUserId(
        userPrimaryId: String
    ): Observable<String> =
        Observable.create { emitter ->
            val db = Firebase.firestore
            db.collection("bookmarks")
                .whereEqualTo("userPk", userPrimaryId)
                .get()
                .addOnSuccessListener { documents ->
                    documents.forEach { document ->
                        emitter.onNext(document.id)
                    }
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }

    private fun getIsBookmarkWithImages(
        userPrimaryId: String,
        reviewDocument: DocumentSnapshot,
        images: List<ReviewImage>,
        reviewId: String
    ): Observable<Triple<DocumentSnapshot, List<ReviewImage>, Boolean>> =
        Observable.create { emitter ->
            val db = Firebase.firestore
            db.collection("bookmarks")
                .whereEqualTo("reviewId", reviewId)
                .whereEqualTo("userPk", userPrimaryId)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.documents.isEmpty()) {
                        emitter.onNext(Triple(reviewDocument, images, false))
                    } else {
                        emitter.onNext(Triple(reviewDocument, images, true))
                    }
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onNext(Triple(reviewDocument, images, false))
                    emitter.onComplete()
                }
        }

    private fun getUserId(
        userPrimaryId: String,
        reviewDocument: DocumentSnapshot,
        images: List<ReviewImage>,
        isBookmark: Boolean
    ): Observable<Pair<DocumentSnapshot, Triple<List<ReviewImage>, Boolean, String?>>> =
        Observable.create { emitter ->
            val db = Firebase.firestore
            db.collection("users")
                .document(userPrimaryId)
                .get()
                .addOnSuccessListener { document ->
                    emitter.onNext(
                        reviewDocument to
                                Triple(images, isBookmark, document.getString("userId"))
                    )
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onNext(
                        reviewDocument to
                                Triple(images, isBookmark, "unknown user")
                    )
                    emitter.onComplete()
                }
        }
}