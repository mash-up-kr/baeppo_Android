package com.mashup.ipdam.ui.detail.adapter

import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ItemReviewPointBinding
import com.mashup.ipdam.entity.review.ReviewPoint

class DetailReviewPointAdapter : BaseRecyclerView.Adapter<ReviewPoint, ItemReviewPointBinding>(
    R.layout.item_review_point,
    BR.reviewPoint,
    DetailReviewPointDiffCallback
)

object DetailReviewPointDiffCallback : DiffUtil.ItemCallback<ReviewPoint>() {
    override fun areItemsTheSame(oldItem: ReviewPoint, newItem: ReviewPoint): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ReviewPoint, newItem: ReviewPoint): Boolean {
        return oldItem == newItem
    }
}