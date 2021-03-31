package com.mashup.base.ext

import android.graphics.drawable.Drawable
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageButtonDrawable")
fun setImageDrawable(view: ImageButton, drawable: Drawable) {
    view.setImageDrawable(drawable)
}
