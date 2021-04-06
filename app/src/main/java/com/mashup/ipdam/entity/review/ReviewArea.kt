package com.mashup.ipdam.entity.review

import androidx.recyclerview.widget.DiffUtil

data class ReviewArea(val name: String, val isExist: Boolean) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<ReviewArea>() {
            override fun areItemsTheSame(oldItem: ReviewArea, newItem: ReviewArea): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: ReviewArea, newItem: ReviewArea): Boolean {
                return oldItem == newItem
            }
        }
    }
}
