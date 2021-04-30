package com.mashup.ipdam.ui.home.adapter.roomimage

import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ItemRoomImageBinding

class RoomImageViewPagerAdapter : BaseRecyclerView.Adapter<String, ItemRoomImageBinding>(
    R.layout.item_room_image,
    BR.reviewUrl,
    RoomImageDiffCallback
) {

    object RoomImageDiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}