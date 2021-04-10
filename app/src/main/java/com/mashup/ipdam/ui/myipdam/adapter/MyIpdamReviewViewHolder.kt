package com.mashup.ipdam.ui.myipdam.adapter

import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.databinding.ItemReviewBinding
import com.mashup.ipdam.ui.myipdam.MyIpdamViewModel

class MyIpdamReviewViewHolder(
    binding: ItemReviewBinding,
    bindingVariableId: Int?,
    private val MyIpdamViewModel: MyIpdamViewModel
) : BaseRecyclerView.ViewHolder<ItemReviewBinding>(
    binding,
    bindingVariableId
) {
    init {
        binding.apply {
            isMyReview = true
            ivBookmark.setOnClickListener {
                review?.let {
                    //MyIpdamViewModel.toggleMyIpdam(it)
                }
            }
        }
    }
}