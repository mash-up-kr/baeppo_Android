package com.mashup.ipdam.entity.user

data class User(
    val id: String,
    val userId: String?,
    val userPassword: String?,
    val imageUrl: String? = null
    )
