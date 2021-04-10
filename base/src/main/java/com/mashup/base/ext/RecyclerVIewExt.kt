package com.mashup.base.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.BaseRecyclerView

@BindingAdapter("replaceList")
fun RecyclerView.replaceList(list: List<Any>?) {
    @Suppress("UNCHECKED_CAST")
    (this.adapter as BaseRecyclerView.Adapter<Any, *>).run {
        this.submitList(list)
    }
}
