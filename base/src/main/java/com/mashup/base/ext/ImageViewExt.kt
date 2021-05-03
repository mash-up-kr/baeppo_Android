package com.mashup.base.ext

import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mashup.base.R

@BindingAdapter("loadImage")
fun ImageView.loadImage(@DrawableRes resId: Int) {
    setImageResource(resId)
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(drawable: VectorDrawable) {
    setImageDrawable(drawable)
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(context)
            .load(it)
            .centerCrop()
            .into(this)
    }
}

@BindingAdapter("loadImageWithRadius")
fun ImageView.loadImageWithRadius(imageUrl: String?) {
    imageUrl?.let {
        val radius = context.resources.getDimensionPixelSize(R.dimen.radius_corner)
        Glide.with(context)
            .load(it)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(radius)))
            .into(this)
    }
}

@BindingAdapter(value = ["circleImageUrl", "errorImage"], requireAll = false)
fun ImageView.setCircleImageUrl(url: String?, errorImage: Drawable?) {
    url?.let {
        Glide.with(context)
            .load(url)
            .error(errorImage)
            .apply(RequestOptions().circleCrop())
            .into(this)
    }
}