package com.mashup.ipdam.ui.search

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.ui.search.adapter.PlaceAdapter
import com.mashup.ipdam.ui.search.data.Places

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