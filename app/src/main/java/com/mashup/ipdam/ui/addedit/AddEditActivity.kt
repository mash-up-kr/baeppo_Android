package com.mashup.ipdam.ui.addedit

import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.mashup.base.BaseActivity
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivityAddEditBinding
import com.mashup.ipdam.ui.addedit.adapter.PointAdapter
import com.mashup.ipdam.ui.addedit.adapter.area.AddAreaAdapter
import com.mashup.ipdam.ui.addedit.adapter.area.AreaAdapter
import com.mashup.ipdam.ui.addedit.adapter.image.AddImageAdapter
import com.mashup.ipdam.ui.addedit.adapter.image.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditActivity : BaseActivity<ActivityAddEditBinding>(R.layout.activity_add_edit) {
    override var logTag: String = "add_editActivity"
    private val viewModel: AddEditViewModel by viewModels()

    override fun initLayout() {
        binding.viewModel = viewModel

        initPointRecyclerView()
        initAreaRecyclerView()
        initImageRecyclerView()
    }

    private fun initPointRecyclerView() {
        binding.addEditPointRecyclerView.apply {
            adapter = PointAdapter(viewModel)
        }
    }

    private fun initAreaRecyclerView() {
        binding.addEditAreaRecyclerView.apply {
            val addAdapter = AddAreaAdapter(viewModel)
            val areaAdapter = AreaAdapter(viewModel)
            adapter = ConcatAdapter(areaAdapter, addAdapter)
        }
    }

    private fun initImageRecyclerView() {
        binding.addEditImageRecyclerView.apply {
            val addAdapter = AddImageAdapter(viewModel)
            val imageAdapter = ImageAdapter(viewModel)
            adapter = ConcatAdapter(addAdapter, imageAdapter)
        }
    }


    override fun observeViewModel() {
        viewModel.addReviewImageEvent.observe(this) {
            //TODO 이미지 추가
        }
        viewModel.addReviewAreaEvent.observe(this) {
            //TODO 주변 상권 추가
        }
    }
}