package com.mashup.ipdam.ui.search.adapter.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ItemHistoryBinding
import com.mashup.ipdam.entity.history.History
import com.mashup.ipdam.ui.search.SearchViewModel

class HistoryAdapter(
    private val searchViewModel: SearchViewModel
) : BaseRecyclerView.Adapter<History, ItemHistoryBinding>(
    R.layout.item_history,
    BR.history, HistoryDiffCallback
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder =
        HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), BR.history,
            searchViewModel
        )
}

object HistoryDiffCallback : DiffUtil.ItemCallback<History>() {
    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem == newItem
    }
}