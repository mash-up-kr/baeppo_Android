package com.mashup.base.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(context)
            .load(it)
            .into(this)
    }
}

@BindingAdapter("circleImageUrl")
fun ImageView.setCircleImageUrl(url: String?) {
    url?.let {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions().circleCrop())
            .into(this)
    }
}