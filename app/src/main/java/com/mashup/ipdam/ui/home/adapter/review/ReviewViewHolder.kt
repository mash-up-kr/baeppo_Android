package com.mashup.ipdam.ui.home.adapter.review

import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.databinding.ItemReviewBinding
import com.mashup.ipdam.ui.home.HomeViewModel

class ReviewViewHolder(
    binding: ItemReviewBinding,
    bindingVariableId: Int?,
    private val homeViewModel: HomeViewModel
) : BaseRecyclerView.ViewHolder<ItemReviewBinding>(
    binding,
    bindingVariableId
) {
    init {
        binding.apply {
            isMyReview = false
            ivBookmark.setOnClickListener {
                review?.let {
                    homeViewModel.toggleBookmark(it)
                }
            }
        }
    }
}