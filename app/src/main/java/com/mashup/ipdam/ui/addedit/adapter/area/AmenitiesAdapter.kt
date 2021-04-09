package com.mashup.ipdam.ui.addedit.adapter.area

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.databinding.ItemAreaBinding
import com.mashup.ipdam.entity.review.ReviewAmenities
import com.mashup.ipdam.ui.addedit.AddEditViewModel

class AmenitiesAdapter(
    private val viewModel: AddEditViewModel
) : ListAdapter<ReviewAmenities, AmenitiesViewHolder>(ReviewAmenitiesDiffUtilCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenitiesViewHolder {
        return AmenitiesViewHolder(
            ItemAreaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), viewModel
        )
    }

    override fun onBindViewHolder(holder: AmenitiesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AmenitiesViewHolder(
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

    fun bind(reviewAmenities: ReviewAmenities) {
        binding.apply {
            item = reviewAmenities
        }
    }
}

object ReviewAmenitiesDiffUtilCallback : DiffUtil.ItemCallback<ReviewAmenities>() {
    override fun areItemsTheSame(oldItem: ReviewAmenities, newItem: ReviewAmenities): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ReviewAmenities, newItem: ReviewAmenities): Boolean {
        return oldItem == newItem
    }
}