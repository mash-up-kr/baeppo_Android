package com.mashup.ipdam.ui.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mashup.base.ext.setHtmlText
import com.mashup.base.glide.GlideApp
import com.mashup.ipdam.R
import com.mashup.ipdam.utils.ColorUtils

@BindingAdapter(value = ["ipdamAddress", "ipdamCount"], requireAll = true)
fun showIpdamHeader(view: TextView, address: String, count: Int) {
    val mainColor = ResourcesCompat.getColor(view.resources, R.color.main_color, null)
    val mainHexColor = ColorUtils.getHexColor(mainColor)
    view.setHtmlText(
        view.context.getString(
            R.string.ipdam_header_text,
            address,
            mainHexColor,
            count
        )
    )
}

@BindingAdapter("bottomSheetVisible")
fun setVisibleBottomSheet(bottomSheetRootView: ConstraintLayout, visible: Boolean) {
    BottomSheetBehavior.from(bottomSheetRootView).run {
        state =
            if (visible) BottomSheetBehavior.STATE_COLLAPSED else BottomSheetBehavior.STATE_HIDDEN
    }
}

@BindingAdapter("reviewCount")
fun setReviewCount(view: TextView, count: Int) {
    view.text = view.context.getString(R.string.review_count, count)
}

@BindingAdapter("starCount")
fun setStarCount(view: TextView, count: Double) {
    view.text = view.context.getString(R.string.star_count, count)
}

@BindingAdapter(value = ["isBookmark", "bookmarkColor"], requireAll = true)
fun setBookmarkColor(view: ImageView, isBookmark: Boolean, @ColorInt color: Int) {
    if (isBookmark) {
        view.imageTintList =
            ColorStateList.valueOf(color)
    } else {
        view.imageTintList =
            null
    }
}

@BindingAdapter("imageUrl")
fun setImageByUri(view: ImageView, imgUri: String) {
    GlideApp.with(view.context).load(imgUri).centerCrop().into(view)
}
