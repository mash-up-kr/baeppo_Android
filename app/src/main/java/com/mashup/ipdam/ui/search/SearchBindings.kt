package com.mashup.ipdam.ui.search

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.ui.search.adapter.kakao.PlaceAdapter
import com.mashup.ipdam.ui.search.adapter.history.HistoryAdapter
import com.mashup.ipdam.ui.search.data.entity.history.History
import com.mashup.ipdam.ui.search.data.entity.kakao.Places

@BindingAdapter("keyword")
fun showPlaceHeader(view: TextView, keyword: String) {
    view.text = view.context.getString(
        R.string.search_place_header,
        keyword
    )
}

@BindingAdapter("placeList")
fun showPlacesItem(view: RecyclerView, placeList: List<Places>) {
    val adapter = view.adapter
    if (adapter is PlaceAdapter) {
        adapter.submitList(placeList)
    }
}

@BindingAdapter("historyList")
fun showHistoryItem(view: RecyclerView, historyList: List<History>) {
    val adapter = view.adapter
    if (adapter is HistoryAdapter) {
        adapter.submitList(historyList)
    }
}