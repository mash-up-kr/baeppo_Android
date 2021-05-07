package com.mashup.ipdam.ui.detail.adapter

import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.databinding.ItemReviewPointBinding

class DetailReviewPointViewHolder(
    binding: ItemReviewPointBinding,
    bindingVariableId: Int?
) : BaseRecyclerView.ViewHolder<ItemReviewPointBinding>(
    binding,
    bindingVariableId
) {
    init {
        binding.reviewPoint
    }
}