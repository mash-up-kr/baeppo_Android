package com.mashup.ipdam.ui.create

import androidx.activity.viewModels
import com.mashup.base.BaseActivity
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivityCreateBinding
import com.mashup.ipdam.ui.create.adapter.PointAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateActivity : BaseActivity<ActivityCreateBinding>(R.layout.activity_create) {
    override var logTag: String = "CreateActivity"
    private val viewModel: CreateViewModel by viewModels()

    override fun initLayout() {
        binding.viewModel = viewModel
        initPointRecyclerView()
    }

    private fun initPointRecyclerView() {
        binding.createPointRecyclerView.apply {
            setHasFixedSize(true)
            adapter = PointAdapter(viewModel)
        }
    }
}