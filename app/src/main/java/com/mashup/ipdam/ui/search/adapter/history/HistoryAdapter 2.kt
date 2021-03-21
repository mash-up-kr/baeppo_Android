package com.mashup.ipdam.ui.search.adapter.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mashup.ipdam.databinding.ItemHistoryBinding
import com.mashup.ipdam.ui.search.data.entity.history.History

class HistoryAdapter(
    private val historyClickListener: HistoryClickListener
) : ListAdapter<History, HistoryViewHolder>(HistoryDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), historyClickListener
        )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface HistoryClickListener {
        fun onHistoryClick(position: Int)
        fun onHistoryDeleteClick(position: Int)
    }
}

class HistoryDiffCallback : DiffUtil.ItemCallback<History>() {
    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem == newItem
    }

}