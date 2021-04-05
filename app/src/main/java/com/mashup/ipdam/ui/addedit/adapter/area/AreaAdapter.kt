package com.mashup.ipdam.ui.addedit.adapter.area

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.databinding.ItemAreaBinding
import com.mashup.ipdam.entity.review.ReviewArea
import com.mashup.ipdam.ui.addedit.AddEditViewModel

class AreaAdapter(
    private val viewModel: AddEditViewModel
) : ListAdapter<ReviewArea, AreaViewHolder>(ReviewAreaDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        return AreaViewHolder(
            ItemAreaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), viewModel
        )
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AreaViewHolder(
    private val binding: ItemAreaBinding,
    private val viewModel: AddEditViewModel
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.apply {
            root.setOnClickListener {
                viewModel.changeSelectedArea(bindingAdapterPosition)
            }
        }
    }

    fun bind(reviewArea: ReviewArea) {
        binding.apply {
            item = reviewArea
        }
    }
}

object ReviewAreaDiffCallback : DiffUtil.ItemCallback<ReviewArea>() {
    override fun areItemsTheSame(oldItem: ReviewArea, newItem: ReviewArea): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ReviewArea, newItem: ReviewArea): Boolean {
        return oldItem == newItem
    }

}