package com.mashup.ipdam.ui.addedit.adapter.area

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.databinding.ItemAreaBinding
import com.mashup.ipdam.ui.addedit.AddEditViewModel

class AddAmenitiesAdapter(
    private val viewModel: AddEditViewModel
) : RecyclerView.Adapter<AddAmenitiesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAmenitiesViewHolder {
        return AddAmenitiesViewHolder(
            ItemAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: AddAmenitiesViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1
}

class AddAmenitiesViewHolder(
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