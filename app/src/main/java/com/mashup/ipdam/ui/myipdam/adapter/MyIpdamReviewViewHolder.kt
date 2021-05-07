package com.mashup.ipdam.ui.myipdam.adapter

import androidx.viewpager2.widget.ViewPager2
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.databinding.ItemReviewBinding
import com.mashup.ipdam.ui.home.adapter.roomimage.RoomImageViewPagerAdapter
import com.mashup.ipdam.ui.myipdam.MyIpdamViewModel

class MyIpdamReviewViewHolder(
    binding: ItemReviewBinding,
    bindingVariableId: Int?,
    private val MyIpdamViewModel: MyIpdamViewModel
) : BaseRecyclerView.ViewHolder<ItemReviewBinding>(
    binding,
    bindingVariableId
) {
    private val roomImageViewPagerAdapter by lazy { RoomImageViewPagerAdapter() }

    init {
        binding.apply {
            isMyReview = true
            ivBookmark.setOnClickListener {
                review?.let {
                    //TODO MyIpdamViewModel.toggleMyIpdam(it)
                }
            }
            vpPhotoViewPager.adapter = roomImageViewPagerAdapter
            vpPhotoViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    cvPhotoViewPagerIndicator.selectDot(position)
                }
            })
        }
    }

    override fun bind(item: Any) {
        super.bind(item)
        if (item is Review) {
            binding.cvPhotoViewPagerIndicator.createDotPanel(
                item.images?.size ?: 0,
                R.drawable.indicator_dot_off,
                R.drawable.indicator_dot_on,
                0
            )
        }
    }
}