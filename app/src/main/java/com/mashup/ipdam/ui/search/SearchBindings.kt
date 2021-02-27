package com.mashup.ipdam.ui.search

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.ext.setHtmlText
import com.mashup.ipdam.R
import com.mashup.ipdam.ui.search.adapter.PlaceAdapter
import com.mashup.ipdam.ui.search.data.Documents
import com.mashup.ipdam.utils.ColorUtils

@BindingAdapter("keyword")
fun showPlaceResultHeader(view: TextView, keyword: String) {
    view.text = view.context.getString(
            R.string.place_search_header,
            keyword)
}

@BindingAdapter("placeList")
fun showPlaceResultHeader(view: RecyclerView, placeList: List<Documents>) {
    view.apply {
        adapter = PlaceAdapter(placeList)
        layoutManager = LinearLayoutManager(view.context)
    }
}