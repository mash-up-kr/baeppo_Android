package com.mashup.ipdam.ui.search

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mashup.ipdam.R

@BindingAdapter("keyword")
fun showPlaceHeader(view: TextView, keyword: String) {
    view.text = view.context.getString(
        R.string.search_place_header,
        keyword
    )
}

@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}