package com.mashup.ipdam.ui.search.adapter.kakao

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ItemPlaceBinding
import com.mashup.ipdam.entity.kakao.keyword.Place

class PlaceAdapter(
    private val placeClickListener: PlaceClickListener
) :
    BaseRecyclerView.Adapter<Place, ItemPlaceBinding>(
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

object PlacesDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }
}
