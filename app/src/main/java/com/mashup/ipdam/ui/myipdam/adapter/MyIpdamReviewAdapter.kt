package com.mashup.ipdam.ui.myipdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.databinding.ItemReviewBinding
import com.mashup.ipdam.ui.myipdam.MyIpdamViewModel

class MyIpdamReviewAdapter(private val myIpdamViewModel: MyIpdamViewModel) :
    BaseRecyclerView.Adapter<Review, ItemReviewBinding>(
        R.layout.item_review,
        BR.review,
        MyIpdamReviewDiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyIpdamReviewViewHolder =
        MyIpdamReviewViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), BR.review, myIpdamViewModel
        )
}

object MyIpdamReviewDiffCallback : DiffUtil.ItemCallback<Review>() {

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}