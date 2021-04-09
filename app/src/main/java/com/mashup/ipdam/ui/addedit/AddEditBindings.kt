package com.mashup.ipdam.ui.addedit

import android.graphics.Typeface
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.entity.review.ReviewAmenities
import com.mashup.ipdam.entity.review.ReviewPoint
import com.mashup.ipdam.ui.addedit.adapter.PointAdapter
import com.mashup.ipdam.ui.addedit.adapter.area.AmenitiesAdapter
import com.mashup.ipdam.ui.addedit.adapter.image.RoomImageAdapter
import com.willy.ratingbar.BaseRatingBar
import java.text.DecimalFormat


@BindingAdapter("pointList")
fun setPointItem(view: RecyclerView, pointList: List<ReviewPoint>) {
    val adapter = view.adapter
    if (adapter is PointAdapter) {
        adapter.submitList(pointList)
    }
}

@BindingAdapter("areaList")
fun setAreaItem(view: RecyclerView, amenitiesList: List<ReviewAmenities>) {
    val concatAdapter = view.adapter
    if (concatAdapter is ConcatAdapter) {
        val imageAdapter = concatAdapter.adapters[0]
        if (imageAdapter is AmenitiesAdapter) {
            imageAdapter.submitList(amenitiesList)
        }
    }
}

@BindingAdapter("imageList")
fun setImageItem(view: RecyclerView, imageList: List<String>) {
    val concatAdapter = view.adapter
    if (concatAdapter is ConcatAdapter) {
        val imageAdapter = concatAdapter.adapters[1]
        if (imageAdapter is RoomImageAdapter) {
            imageAdapter.submitList(imageList)
        }
    }
}

@BindingAdapter("ratingToString")
fun TextView.setTextWithRating(ratingValue: Double) {
    val decimalFormat = DecimalFormat("#.#")
    text = context.getString(R.string.add_edit_point_rating, decimalFormat.format(ratingValue))
}

@BindingAdapter("reviewRating")
fun BaseRatingBar.setRatingDouble(ratingValue: Double) {
    rating = ratingValue.toFloat()
}

@BindingAdapter("ratingChanged")
fun BaseRatingBar.setRatingChanged(ratingChanged: InverseBindingListener) {
    setOnRatingChangeListener { _, _, _ ->
        ratingChanged.onChange()
    }
}

@InverseBindingAdapter(attribute = "reviewRating", event = "ratingChanged")
fun BaseRatingBar.getRatingDouble(): Double {
    return rating.toDouble()
}

@BindingAdapter("android:textStyle")
fun setTypeface(textView: TextView, style: String) {
    when (style) {
        "bold" -> textView.setTypeface(null, Typeface.BOLD)
        else -> textView.setTypeface(null, Typeface.NORMAL)
    }
}