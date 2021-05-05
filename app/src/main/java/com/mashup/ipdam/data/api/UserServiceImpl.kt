package com.mashup.ipdam.data.api

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mashup.ipdam.entity.user.User
import com.mashup.ipdam.error.DuplicatedUserException
import com.mashup.ipdam.error.NotFoundUserException
import com.mashup.ipdam.network.service.UserService
import io.reactivex.Single
import io.reactivex.SingleEmitter
import java.util.*
import javax.inject.Inject

class UserServiceImpl @Inject constructor(): UserService {
    override fun login(id: String, password: String): Single<User> =
        Single.create { emit ->
            Firebase.firestore.collection("users")
                .whereEqualTo("userId", id)
                .whereEqualTo("userPassword", password)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.documents.isEmpty()) {
                        emit.onError(NotFoundUserException())
                    } else {
                        val userDocument = snapshot.documents[0]
                        val user = User(
                            id = userDocument.id,
                            userId = userDocument.getString("userId"),
                            userPassword = userDocument.getString("userPassword"),
                            imageUrl = userDocument.getString("user")
                        )
                        emit.onSuccess(user)
                    }
                }.addOnFailureListener {
                    emit.onError(it)
                }
        }

    override fun register(id: String, password: String): Single<User> =
        Single.create { emitter ->
            Firebase.firestore.collection("users")
                .whereEqualTo("userId", id)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.documents.isEmpty()) {
                        createUserInFirebase(id, password, emitter)
                    } else {
                        emitter.onError(DuplicatedUserException())
                    }
                }.addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }

    private fun createUserInFirebase(id: String, password: String, emitter: SingleEmitter<User>) {
        val userData = hashMapOf(
            "userId" to id,
            "userPassword" to password,
            "createdAt" to Timestamp(Date()),
            "updatedAt" to Timestamp(Date())
        )
        Firebase.firestore.collection("users")
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