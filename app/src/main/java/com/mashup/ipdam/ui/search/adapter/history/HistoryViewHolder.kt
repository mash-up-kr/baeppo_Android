package com.mashup.ipdam.ui.search.adapter.history

import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.databinding.ItemHistoryBinding
import com.mashup.ipdam.ui.search.data.entity.history.History


class HistoryViewHolder(
    val binding: ItemHistoryBinding,
    private val historyClickListener: HistoryAdapter.HistoryClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            historyClickListener.onHistoryClick(adapterPosition)
        }
        binding.historyDelete.setOnClickListener {
            historyClickListener.onHistoryDeleteClick(adapterPosition)
        }
    }

    fun bind(history: History) {
        binding.history = history
    }
}