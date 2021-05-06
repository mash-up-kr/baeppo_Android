package com.mashup.ipdam.data.api

import android.net.Uri
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mashup.ipdam.entity.user.User
import com.mashup.ipdam.error.DuplicatedUserException
import com.mashup.ipdam.error.NotFoundUserException
import com.mashup.ipdam.network.service.UserService
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Single
import io.reactivex.SingleEmitter
import java.util.*
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {
    override fun login(id: String, password: String): Single<User> =
        Single.create { emitter ->
            Firebase.firestore.collection("users")
                .whereEqualTo("userId", id)
                .whereEqualTo("userPassword", password)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.documents.isEmpty()) {
                        emitter.onError(NotFoundUserException())
                    } else {
                        val userDocument = snapshot.documents[0]
                        val user = User(
                            id = userDocument.id,
                            userId = userDocument.getString("userId"),
                            userPassword = userDocument.getString("userPassword"),
                            imageUrl = userDocument.getString("user")
                        )
                        emitter.onSuccess(user)
                    }
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun register(id: String, password: String): Single<User> =
        Single.create { emitter ->
            val db = Firebase.firestore
            db.collection("users")
                .whereEqualTo("userId", id)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.documents.isEmpty()) {
                        createUserInFirebase(db, id, password, emitter)
                    } else {
                        emitter.onError(DuplicatedUserException())
                    }
                }.addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }

    override fun getUser(userPrimaryId: String): Single<User> = Single.create { emit ->
        Firebase.firestore.collection("users").document(userPrimaryId)
            .get()
            .addOnSuccessListener { document ->
                val user = User(
                    id = userPrimaryId,
                    userId = document.getString("userId"),
                    userPassword = document.getString("userPassword"),
                    imageUrl = document.getString("imageUrl")
                )
                emit.onSuccess(user)
            }.addOnFailureListener {
                emit.onError(it)
            }
    }

    override fun setImageUrlWithUri(userPrimaryId: String, newImageUri: Uri): Completable =
        Completable.create { emitter ->
            val imageRef = Firebase.storage.reference.child(
                "userImages/${userPrimaryId}"
            )
            Log.d("imageUrl","userImages/${userPrimaryId}")
            imageRef.putFile(newImageUri)
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
                        setImageUrlInUserInfo(userPrimaryId, downloadUri, emitter)
                    } else {
                        task.exception?.let {
                            emitter.onError(it)
                        }
                    }
                }
        }

    private fun setImageUrlInUserInfo(
        primaryId: String,
        imageUrl: Uri,
        emitter: CompletableEmitter
    ) {
        val db = Firebase.firestore
        val document = db.collection("users").document(primaryId)

        db.runBatch { batch ->
            batch.update(document, "imageUrl", imageUrl.toString())
            batch.update(document, "updatedAt", Timestamp(Date()))
        }.addOnCompleteListener {
            emitter.onComplete()
        }.addOnFailureListener { exception ->
            emitter.onError(exception)
        }
    }

    private fun createUserInFirebase(
        db: FirebaseFirestore,
        id: String,
        password: String,
        emitter: SingleEmitter<User>
    ) {
        val userData = hashMapOf(
            "userId" to id,
            "userPassword" to password,
            "createdAt" to Timestamp(Date()),
            "updatedAt" to Timestamp(Date())
        )
        db.collection("users")
            .add(userData)
            .addOnSuccessListener { documentReference ->
                val user = User(
                    id = documentReference.id,
                    userId = id,
                    userPassword = password
                )
                emitter.onSuccess(user)
            }.addOnFailureListener { e ->
                emitter.onError(e)
            }
    }
}