package com.mashup.ipdam.ui.search

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import com.mashup.base.BaseActivity
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    override var logTag: String = "SearchActivity"

    private val viewModel by viewModels<SearchViewModel>()

    override fun initLayout() {
        binding.viewModel = viewModel

        binding.searchView.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    viewModel.getPlaceByKeyword()
                    true
                }
                else -> false
            }
        })

        binding.searchBack.setOnClickListener {
            //TODO: 주소 반환?
            finish()
        }

        hideResultExistLayout()
    }

    override fun observeViewModel() {
        viewModel.placeList.observe(this) {
            if (it.isEmpty()) {
                hideResultExistLayout()
                showResultNoneLayout()
            } else {
                hideResultNoneLayout()
                showResultExistLayout()
            }
        }
    }

    private fun showResultNoneLayout() {
        binding.resultNoneImage.visibility = View.VISIBLE
        binding.resultNoneTextView.visibility = View.VISIBLE
    }

    private fun hideResultNoneLayout() {
        binding.resultNoneImage.visibility = View.GONE
        binding.resultNoneTextView.visibility = View.GONE
    }

    private fun showResultExistLayout() {
        binding.resultRecyclerView.visibility = View.VISIBLE
        binding.resultHeader.visibility = View.VISIBLE
    }

    private fun hideResultExistLayout() {
        binding.resultRecyclerView.visibility = View.GONE
        binding.resultHeader.visibility = View.GONE
    }
}