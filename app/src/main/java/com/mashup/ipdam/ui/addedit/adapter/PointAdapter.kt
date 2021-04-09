package com.mashup.ipdam.ui.addedit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.databinding.ItemPointBinding
import com.mashup.ipdam.entity.review.PointType
import com.mashup.ipdam.entity.review.ReviewPoint
import com.mashup.ipdam.ui.addedit.AddEditViewModel

class PointAdapter(
    private val viewModel: AddEditViewModel
) : ListAdapter<ReviewPoint, PointViewHolder>(ReviewPointDiffCallback) {

    init {
        setHasStableIds(true)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        return PointViewHolder(
            ItemPointBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), viewModel
        )
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PointViewHolder(
    private val binding: ItemPointBinding,
    private val viewModel: AddEditViewModel
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.apply {
            pointBadImage.setOnClickListener {
                viewModel.setReviewType(bindingAdapterPosition, PointType.BAD)
            }
            pointNormalImage.setOnClickListener {
                viewModel.setReviewType(bindingAdapterPosition, PointType.NORMAL)
            }
            pointGoodImage.setOnClickListener {
                viewModel.setReviewType(bindingAdapterPosition, PointType.GOOD)
            }
        }
    }

    fun bind(reviewPoint: ReviewPoint) {
        binding.apply {
            item = reviewPoint

            val pointStrings = itemView.resources.getStringArray(reviewPoint.reviewType.arrayResId)
            pointTitle.text = pointStrings[0]
            pointBadText.text = pointStrings[1]
            pointNormalText.text = pointStrings[2]
            pointGoodText.text = pointStrings[3]

            executePendingBindings()
        }
    }
}

object ReviewPointDiffCallback : DiffUtil.ItemCallback<ReviewPoint>() {
    override fun areItemsTheSame(oldItem: ReviewPoint, newItem: ReviewPoint): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ReviewPoint, newItem: ReviewPoint): Boolean {
        return oldItem == newItem
    }
}