package com.mashup.ipdam.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.databinding.ItemReviewBinding
import com.mashup.ipdam.ui.bookmark.BookmarkViewModel

class BookmarkReviewAdapter(private val BookmarkViewModel: BookmarkViewModel) :
    BaseRecyclerView.Adapter<Review, ItemReviewBinding>(
        R.layout.item_review,
        BR.review,
        BookmarkReviewDiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarkReviewViewHolder =
        BookmarkReviewViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), BR.review, BookmarkViewModel
        )
}

object BookmarkReviewDiffCallback : DiffUtil.ItemCallback<Review>() {

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}