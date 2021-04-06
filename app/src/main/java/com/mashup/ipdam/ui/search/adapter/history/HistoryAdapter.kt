package com.mashup.ipdam.ui.search.adapter.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mashup.ipdam.databinding.ItemHistoryBinding
import com.mashup.ipdam.ui.search.SearchViewModel
import com.mashup.ipdam.entity.history.History

class HistoryAdapter(
    private val searchViewModel: SearchViewModel
) : ListAdapter<History, HistoryViewHolder>(History.DiffCallback) {

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