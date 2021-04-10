package com.mashup.ipdam.ui.search.adapter.kakao

import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.databinding.ItemPlaceBinding

class PlaceViewHolder(
    binding: ItemPlaceBinding,
    bindingVariableId: Int?,
    private val placeClickListener: PlaceAdapter.PlaceClickListener
) : BaseRecyclerView.ViewHolder<ItemPlaceBinding>(
    binding,
    bindingVariableId
) {
    init {
        binding.root.setOnClickListener {
            placeClickListener.onPlaceClick(bindingAdapterPosition)
        }
    }
}