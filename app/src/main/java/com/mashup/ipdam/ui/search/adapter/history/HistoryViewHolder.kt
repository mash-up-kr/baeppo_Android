package com.mashup.ipdam.ui.search.adapter.history

import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.databinding.ItemHistoryBinding
import com.mashup.ipdam.ui.search.SearchViewModel


class HistoryViewHolder(
    binding: ItemHistoryBinding,
    bindingVariableId: Int?,
    private val searchViewModel: SearchViewModel
) : BaseRecyclerView.ViewHolder<ItemHistoryBinding>(
    binding,
    bindingVariableId
) {
    init {
        binding.root.setOnClickListener {
            searchViewModel.getPlaceByHistoryWithPosition(bindingAdapterPosition)
        }
        binding.historyDelete.setOnClickListener {
            searchViewModel.deleteHistoryWithPosition(bindingAdapterPosition)
        }
    }
}