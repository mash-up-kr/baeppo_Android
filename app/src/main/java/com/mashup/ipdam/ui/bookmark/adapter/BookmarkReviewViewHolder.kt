package com.mashup.ipdam.ui.bookmark.adapter

import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.databinding.ItemReviewBinding
import com.mashup.ipdam.ui.bookmark.BookmarkViewModel

class BookmarkReviewViewHolder(
    binding: ItemReviewBinding,
    bindingVariableId: Int?,
    private val bookmarkViewModel: BookmarkViewModel
) : BaseRecyclerView.ViewHolder<ItemReviewBinding>(
    binding,
    bindingVariableId
) {
    init {
        binding.apply {
            isMyReview = false
            ivBookmark.setOnClickListener {
                review?.let {
                    //bookmarkViewModel.toggleBookmark(it)
                }
            }
        }
    }
}