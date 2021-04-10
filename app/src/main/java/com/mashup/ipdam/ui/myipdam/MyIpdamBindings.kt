package com.mashup.ipdam.ui.myipdam

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.mashup.base.ext.setHtmlText
import com.mashup.ipdam.R
import com.mashup.ipdam.utils.ColorUtils

@BindingAdapter("myIpdamReviewCount")
fun setMyIpdamCount(view: TextView, count: Int) {
    val mainColor = ResourcesCompat.getColor(view.resources, R.color.primary_color, null)
    val mainHexColor = ColorUtils.getHexColor(mainColor)
    view.setHtmlText(
        view.context.getString(
            R.string.my_ipdam_review_count,
            count,
            mainHexColor
        )
    )
}