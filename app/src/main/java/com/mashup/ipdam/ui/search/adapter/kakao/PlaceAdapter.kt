package com.mashup.ipdam.ui.search.adapter.kakao

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mashup.ipdam.databinding.ItemPlaceBinding
import com.mashup.ipdam.ui.search.data.entity.kakao.Places

class PlaceAdapter(
    private val placeClickListener: PlaceClickListener
) : ListAdapter<Places, PlaceViewHolder>(PlacesDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceViewHolder(
            ItemPlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), placeClickListener
        )

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface PlaceClickListener {
        fun onPlaceClick(position: Int)
    }
}

class PlacesDiffCallback : DiffUtil.ItemCallback<Places>() {
    override fun areItemsTheSame(oldItem: Places, newItem: Places): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Places, newItem: Places): Boolean {
        return oldItem.id == newItem.id
    }
}