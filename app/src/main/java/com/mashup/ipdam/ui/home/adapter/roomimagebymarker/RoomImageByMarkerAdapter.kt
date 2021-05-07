package com.mashup.ipdam.ui.home.adapter.roomimagebymarker

import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.data.review.ReviewImage
import com.mashup.ipdam.databinding.ItemRoomImageByMarkerBinding

class RoomImageByMarkerAdapter :
    BaseRecyclerView.Adapter<ReviewImage, ItemRoomImageByMarkerBinding>(
        R.layout.item_room_image_by_marker,
        BR.reviewImage,
        RoomImageByMarkerDiffCallback
    )

object RoomImageByMarkerDiffCallback : DiffUtil.ItemCallback<ReviewImage>() {

    override fun areItemsTheSame(oldItem: ReviewImage, newItem: ReviewImage): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ReviewImage, newItem: ReviewImage): Boolean {
        return oldItem == newItem
    }
}