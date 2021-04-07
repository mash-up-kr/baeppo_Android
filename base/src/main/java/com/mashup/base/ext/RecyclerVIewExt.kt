package com.mashup.base.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.BaseRecyclerView

@BindingAdapter("replaceAll")
fun RecyclerView.replaceAll(list: List<Any>?) {
    @Suppress("UNCHECKED_CAST")
    (this.adapter as BaseRecyclerView.Adapter<Any, *>).run {
        this.submitList(list)
    }
}
