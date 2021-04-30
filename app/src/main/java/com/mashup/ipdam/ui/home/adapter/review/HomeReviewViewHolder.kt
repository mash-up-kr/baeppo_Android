package com.mashup.ipdam.ui.home.adapter.review

import androidx.viewpager2.widget.ViewPager2
import com.mashup.base.BaseRecyclerView
import com.mashup.ipdam.R
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.databinding.ItemReviewBinding
import com.mashup.ipdam.ui.home.HomeViewModel
import com.mashup.ipdam.ui.home.adapter.roomimage.RoomImageViewPagerAdapter

class HomeReviewViewHolder(
    binding: ItemReviewBinding,
    bindingVariableId: Int?,
    private val homeViewModel: HomeViewModel
) : BaseRecyclerView.ViewHolder<ItemReviewBinding>(
    binding,
    bindingVariableId
) {

    private val roomImageViewPagerAdapter by lazy { RoomImageViewPagerAdapter() }

    init {
        binding.apply {
            isMyReview = false
            ivBookmark.setOnClickListener {
                review?.let {
                    homeViewModel.toggleBookmark(it)
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
                item.images.size,
                R.drawable.indicator_dot_off,
                R.drawable.indicator_dot_on,
                0
            )
        }
    }
}