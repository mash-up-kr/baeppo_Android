package com.mashup.ipdam.ui.addedit.adapter.area

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ItemAreaBinding
import com.mashup.ipdam.databinding.ItemImageBinding
import com.mashup.ipdam.ui.addedit.AddEditViewModel

class AddAreaAdapter(
    private val viewModel: AddEditViewModel
) : RecyclerView.Adapter<AddAreaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAreaViewHolder {
        return AddAreaViewHolder(
            ItemAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: AddAreaViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1
}

class AddAreaViewHolder(
    private val binding: ItemAreaBinding,
    private val viewModel: AddEditViewModel
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.apply {
            root.setOnClickListener {
                viewModel.eventAddReviewArea()
            }
        }
    }
}