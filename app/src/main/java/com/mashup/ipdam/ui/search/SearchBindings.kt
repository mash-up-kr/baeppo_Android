package com.mashup.ipdam.ui.search

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.ui.search.adapter.kakao.PlaceAdapter
import com.mashup.ipdam.ui.search.adapter.history.HistoryAdapter
import com.mashup.ipdam.entity.history.History
import com.mashup.ipdam.entity.kakao.Places

@BindingAdapter("keyword")
fun setPlaceHeader(view: TextView, keyword: String) {
    view.text = view.context.getString(
        R.string.search_place_header,
        keyword
    )
}

@BindingAdapter("placeList")
fun setPlacesItem(view: RecyclerView, placeList: List<Places>) {
    val adapter = view.adapter
    if (adapter is PlaceAdapter) {
        adapter.submitList(placeList)
    }
}

@BindingAdapter("historyList")
fun setHistoryItem(view: RecyclerView, historyList: List<History>) {
    val adapter = view.adapter
    if (adapter is HistoryAdapter) {
        adapter.submitList(historyList)
    }
}