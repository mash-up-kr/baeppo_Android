package com.mashup.ipdam.ui.home.adapter

import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.databinding.ItemRoomImageByMarkerBinding


class RoomImageByMarkerAdapter() :
    BaseRecyclerView.Adapter<Review, ItemRoomImageByMarkerBinding>(
        R.layout.item_room_image_by_marker,
        BR.review,
        RoomImageByMarkerDiffCallback
    ) {}

object RoomImageByMarkerDiffCallback : DiffUtil.ItemCallback<Review>() {

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}