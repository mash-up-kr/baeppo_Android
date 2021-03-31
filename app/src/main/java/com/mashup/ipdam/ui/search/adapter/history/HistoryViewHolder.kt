package com.mashup.ipdam.ui.search.adapter.history

import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.databinding.ItemHistoryBinding
import com.mashup.ipdam.entity.history.History
import com.mashup.ipdam.ui.search.SearchViewModel


class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    private val searchViewModel: SearchViewModel
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            searchViewModel.getPlaceByHistoryWithPosition(adapterPosition)
        }
        binding.historyDelete.setOnClickListener {
            searchViewModel.deleteHistoryWithPosition(adapterPosition)
        }
    }

    fun bind(history: History) {
        binding.history = history
    }
}