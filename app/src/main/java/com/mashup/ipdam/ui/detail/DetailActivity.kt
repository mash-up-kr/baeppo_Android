package com.mashup.ipdam.ui.detail

import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.mashup.base.BaseActivity
import com.mashup.ipdam.R
import com.mashup.ipdam.data.review.Review
import com.mashup.ipdam.databinding.ActivityDetailBinding
import com.mashup.ipdam.ui.detail.adapter.DetailReviewPointAdapter
import com.mashup.ipdam.ui.home.adapter.roomimage.RoomImageViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    override var logTag: String = "DetailActivity"

    private val detailViewModel: DetailViewModel by viewModels()

    private val roomImageViewPagerAdapter by lazy { RoomImageViewPagerAdapter() }
    private val detailReviewPointAdapter by lazy { DetailReviewPointAdapter() }

    override fun initLayout() {
        detailViewModel.setReview(intent?.getParcelableExtra("review"))
        initBinding()
    }

    private fun initBinding() {
        binding.apply {
            ivBack.setOnClickListener { finish() }
            rvReviewValues.adapter = detailReviewPointAdapter
            viewModel = detailViewModel
            itemReview.review = detailViewModel.review.value
            detailViewModel.review.observe(this@DetailActivity) {
                itemReview.cvPhotoViewPagerIndicator.createDotPanel(
                    it.images?.size ?: 0,
                    R.drawable.indicator_dot_off,
                    R.drawable.indicator_dot_on,
                    0
                )
            }
            itemReview.vpPhotoViewPager.adapter = roomImageViewPagerAdapter
            itemReview.vpPhotoViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    itemReview.cvPhotoViewPagerIndicator.selectDot(position)
                }
            })
        }
    }
}