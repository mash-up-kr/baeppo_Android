package com.mashup.ipdam.ui.myipdam.data

import com.mashup.ipdam.data.review.Review
import io.reactivex.Single

interface MyIpdamRepository {
    fun getMyIpdamReview(): Single<List<Review>>
}