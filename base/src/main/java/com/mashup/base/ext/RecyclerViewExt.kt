package com.mashup.base.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mashup.base.BaseRecyclerView

@BindingAdapter("replaceList")
fun RecyclerView.replaceList(list: List<Any>?) {
    @Suppress("UNCHECKED_CAST")
    (this.adapter as BaseRecyclerView.Adapter<Any, *>).run {
        this.submitList(list)
    }
}

@BindingAdapter("viewPagerReplaceList")
fun ViewPager2.replaceList(list: List<Any>?) {
    @Suppress("UNCHECKED_CAST")
    (this.adapter as BaseRecyclerView.Adapter<Any, *>).run {
        this.submitList(list)
    }
}