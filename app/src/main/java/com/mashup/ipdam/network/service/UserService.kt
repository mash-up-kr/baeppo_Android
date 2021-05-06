package com.mashup.ipdam.network.service

import android.net.Uri
import com.mashup.ipdam.entity.user.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserService {
    fun login(id: String, password: String): Single<User>

    fun register(id: String, password: String): Single<User>

    fun getUser(primaryId: String): Single<User>

    fun setImageUrlWithUri(primaryId: String, newImageUri: Uri): Completable
}