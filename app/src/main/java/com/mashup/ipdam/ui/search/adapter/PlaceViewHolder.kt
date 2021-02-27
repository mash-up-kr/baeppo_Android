package com.mashup.ipdam.ui.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.databinding.ItemPlaceBinding
import com.mashup.ipdam.ui.search.data.Documents

class PlaceViewHolder(
    val binding: ItemPlaceBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(document: Documents) {
        binding.document = document
    }
}