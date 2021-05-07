package com.mashup.ipdam.ui.home

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.google.firebase.Timestamp
import com.mashup.base.ext.setHtmlText
import com.mashup.ipdam.R
import com.mashup.ipdam.utils.ColorUtils
import com.mashup.ipdam.utils.DateUtil
import java.text.DecimalFormat

@BindingAdapter(value = ["ipdamAddress", "ipdamCount"], requireAll = true)
fun showIpdamHeader(view: TextView, address: String, count: Int) {
    val mainColor = ResourcesCompat.getColor(view.resources, R.color.primary_color, null)
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

@BindingAdapter("reviewCount")
fun setReviewCount(view: TextView, count: Int) {
    view.text = view.context.getString(R.string.review_count, count)
}

@BindingAdapter("reviewDate")
fun setReviewDate(view: TextView, date: Timestamp) {
    view.text = DateUtil.getDateFormatter().format(date.toDate())
}

@BindingAdapter("ratingWhenLoad")
fun TextView.setTextWithRating(ratingValue: Double) {
    val decimalFormat = DecimalFormat("#.#")
    val htmlText = context.getString(R.string.home_review_rating, decimalFormat.format(ratingValue))
    setHtmlText(htmlText)
}

@BindingAdapter("bookmark")
fun setBookmarkColor(view: ImageView, bookmark: Boolean) {
    view.imageTintList = if (bookmark) ColorStateList.valueOf(
        ContextCompat.getColor(
            view.context,
            R.color.yellow
        )
    ) else null
}
