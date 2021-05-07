package com.mashup.ipdam.ui.home.adapter.roomimage

import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.data.review.ReviewImage
import com.mashup.ipdam.databinding.ItemRoomImageBinding

class RoomImageViewPagerAdapter : BaseRecyclerView.Adapter<ReviewImage, ItemRoomImageBinding>(
    R.layout.item_room_image,
    BR.reviewImage,
    RoomImageDiffCallback
) {

    object RoomImageDiffCallback : DiffUtil.ItemCallback<ReviewImage>() {

        override fun areItemsTheSame(oldItem: ReviewImage, newItem: ReviewImage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ReviewImage, newItem: ReviewImage): Boolean {
            return oldItem == newItem
        }
    }
}