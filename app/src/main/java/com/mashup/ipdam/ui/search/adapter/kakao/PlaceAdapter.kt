package com.mashup.ipdam.ui.search.adapter.kakao

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mashup.ipdam.databinding.ItemPlaceBinding
import com.mashup.ipdam.entity.kakao.Places

class PlaceAdapter(
    private val placeClickListener: PlaceClickListener
) : ListAdapter<Places, PlaceViewHolder>(Places.DiffCallback) {

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