package com.mashup.ipdam.ui.search

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import com.mashup.base.BaseActivity
import com.mashup.base.ext.hideSoftKeyBoard
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    override var logTag: String = "SearchActivity"

    private val searchViewModel by viewModels<SearchViewModel>()

    override fun initLayout() {
        binding.viewModel = searchViewModel
        binding.searchView.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        searchViewModel.getPlaceByKeyword()
                        true
                    }
                    else -> false
                }
            })
        binding.searchBack.setOnClickListener {
            //TODO: 주소 반환?
            finish()
        }
    }

    override fun observeViewModel() {
        searchViewModel.placeList.observe(this) {
            if (it.isEmpty()) {
                hideResultExistLayout()
                showResultNoneLayout()
            } else {
                hideResultNoneLayout()
                showResultExistLayout()
            }
        }

        searchViewModel.isSearchingPlace.observe(this) { isSearching ->
            if (isSearching) {
                hideSoftKeyBoard()
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