package com.mashup.ipdam.ui.search.adapter.kakao

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ItemPlaceBinding
import com.mashup.ipdam.entity.kakao.Places

class PlaceAdapter(
    private val placeClickListener: PlaceClickListener
) :
    BaseRecyclerView.Adapter<Places, ItemPlaceBinding>(
        R.layout.item_place,
        BR.document, PlacesDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceViewHolder(
            ItemPlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), BR.document, placeClickListener
        )

    interface PlaceClickListener {
        fun onPlaceClick(position: Int)
    }
}

object PlacesDiffCallback : DiffUtil.ItemCallback<Places>() {
    override fun areItemsTheSame(oldItem: Places, newItem: Places): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Places, newItem: Places): Boolean {
        return oldItem.id == newItem.id
    }
}
