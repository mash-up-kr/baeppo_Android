package com.mashup.ipdam.ui.search.adapter.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mashup.ipdam.databinding.ItemHistoryBinding
import com.mashup.ipdam.ui.search.SearchViewModel
import com.mashup.ipdam.entity.history.History

class HistoryAdapter(
    private val searchViewModel: SearchViewModel
) : ListAdapter<History, HistoryViewHolder>(HistoryDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), searchViewModel
        )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object HistoryDiffCallback : DiffUtil.ItemCallback<History>() {
    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem == newItem
    }

}