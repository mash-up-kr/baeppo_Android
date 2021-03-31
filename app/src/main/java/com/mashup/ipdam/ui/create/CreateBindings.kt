package com.mashup.ipdam.ui.create

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.entity.review.ReviewPoint
import com.mashup.ipdam.ui.create.adapter.PointAdapter

@BindingAdapter("pointList")
fun setPointItem(view: RecyclerView, pointList: List<ReviewPoint>) {
    val adapter = view.adapter
    if (adapter is PointAdapter) {
        adapter.submitList(pointList)
    }
}