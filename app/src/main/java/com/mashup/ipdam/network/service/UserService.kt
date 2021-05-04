package com.mashup.ipdam.network.service

import com.mashup.ipdam.entity.user.User
import io.reactivex.Single

interface UserService {
    fun login(id: String, password: String): Single<User>

    fun register(id: String, password: String): Single<User>
}