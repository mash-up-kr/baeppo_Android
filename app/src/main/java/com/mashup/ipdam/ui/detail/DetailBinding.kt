package com.mashup.ipdam.ui.detail

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.mashup.ipdam.R
import com.mashup.ipdam.entity.review.ReviewPoint


@BindingAdapter("setSpeechBubble")
fun setSpeechBubble(view: AppCompatButton, reviewPoint: ReviewPoint) {
    view.text =
        view.resources.getStringArray(reviewPoint.reviewType.arrayResId)[reviewPoint.pointType.pointValue]
}

@BindingAdapter("setFeeling")
fun setFeeling(view: ImageView, point: Int) {
    when (point) {
        0 -> view.setImageResource(R.drawable.ic_active_bad)
        1 -> view.setImageResource(R.drawable.ic_active_normal)
        2 -> view.setImageResource(R.drawable.ic_active_good)
    }
}

@BindingAdapter("setTitle")
fun setTitle(view: TextView, reviewPoint: ReviewPoint) {
    view.text = view.resources.getStringArray(reviewPoint.reviewType.arrayResId)[0]
}