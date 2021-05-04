package com.mashup.ipdam.data.api

import com.mashup.ipdam.entity.user.User
import com.mashup.ipdam.network.service.UserService
import io.reactivex.Single
import javax.inject.Inject

class UserServiceImpl @Inject constructor(): UserService {
    override fun login(id: String, password: String): Single<User> {
        //TODO: Firebase로 Login 구현
        return Single.just(User("", "", ""))
    }

    override fun register(id: String, password: String): Single<User> {
        //TODO: Firebase로 Register 구현
        return Single.just(User("", "", ""))
    }
}