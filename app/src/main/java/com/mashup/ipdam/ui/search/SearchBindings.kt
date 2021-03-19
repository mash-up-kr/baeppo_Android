package com.mashup.ipdam.ui.search

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.ui.search.adapter.PlaceAdapter
import com.mashup.ipdam.ui.search.data.entity.kakao.Places

@BindingAdapter("keyword")
fun showPlaceHeader(view: TextView, keyword: String) {
    view.text = view.context.getString(
            R.string.search_place_header,
            keyword)
}

@BindingAdapter("placeList")
fun showPlacesItem(view: RecyclerView, placeList: List<Places>) {
    val adapter = view.adapter
    if (adapter is PlaceAdapter) {
        adapter.submitList(placeList)
    }
}

@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}